package com.rob.wickettest.component.dropzone;

import com.rob.wickettest.page.AbstractPage;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.resource.ContextRelativeResourceReference;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class DropZone extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(DropZone.class);

    private static final String UPLOAD_JS = "js/upload.js";
    private static final int MAX_MB = 40;
    private static final int MAX_FILES = 3;

    private boolean dzVisible = true;
    private WebMarkupContainer dzDiv;
    private AbstractDefaultAjaxBehavior uploadBehavior;
    private Label uploadMsgLabel;
    private String uploadMsg;

    public DropZone(String id)
    {
        super(id);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        //
        // Container holding the form.
        //
        final WebMarkupContainer container = new WebMarkupContainer("container")
        {
            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                setVisible(dzVisible);
            }

            @Override
            public void renderHead(IHeaderResponse response)
            {
                super.renderHead(response);

                // Include the functions for dropzone manipulation.
                final ContextRelativeResourceReference resourceReference = new ContextRelativeResourceReference(UPLOAD_JS, false);
                final JavaScriptReferenceHeaderItem referenceHeaderItem = JavaScriptReferenceHeaderItem.forReference(resourceReference);
                response.render(referenceHeaderItem);

                // Create the javascript to create a dropzone on this form. Use a null on the header item identifier because we want this to run on each render.
                final String js = String.format(
                        "createDropzone(document.getElementById('%s'), document.getElementById('%s'), '%s', %d);",
                        dzDiv.getMarkupId(),
                        getMarkupId(),
                        uploadBehavior.getCallbackUrl(),
                        MAX_FILES
                );
                final OnDomReadyHeaderItem headerItem = OnDomReadyHeaderItem.forScript(js);
                response.render(headerItem);
            }
        };
        container.setOutputMarkupId(true);
        container.setOutputMarkupPlaceholderTag(true);
        add(container);

        //
        // Form that is the dropzone
        //

        dzDiv = new WebMarkupContainer("dzDiv");
        container.add(dzDiv);

        //
        // Label for testing.
        //

        uploadMsgLabel = new Label("uploadMsg", new PropertyModel<>(this, "uploadMsg"));
        uploadMsgLabel.setOutputMarkupId(true);
        add(uploadMsgLabel);

        //
        // Behaviors
        //

        uploadBehavior = new AbstractDefaultAjaxBehavior()
        {
            @Override
            protected void respond(AjaxRequestTarget target)
            {
                log.info("Received request");

                final ServletWebRequest servletWebRequest = (ServletWebRequest) getRequest();

                try
                {
                    final MultipartServletWebRequest multipartServletWebRequest = servletWebRequest.newMultipartWebRequest(Bytes.megabytes(MAX_MB), "unused");
                    multipartServletWebRequest.parseFileParts();
                    final List<FileItem> files = multipartServletWebRequest.getFiles().values().stream()
                            .map(l -> l.get(0))
                            .collect(Collectors.toList());

                    onUpload(target, files);
                }
                catch (FileUploadException e)
                {
                    final String msg = e.getMessage() == null ? "Upload failed" : e.getMessage();
                    log.error(msg);
                    error(msg);
                    AbstractPage page = (AbstractPage) getPage();
                    target.add(page.getFeedbackPanel());
                    final HttpServletResponse response = (HttpServletResponse) getResponse().getContainerResponse();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                target.add(container);
            }
        };
        add(uploadBehavior);
    }

    private void onUpload(AjaxRequestTarget target, List<FileItem> files) throws FileUploadException
    {
        for (FileItem fi : files)
        {
            log.info(fi.getName());
            if (fi.getSize() == 0) throw new FileUploadException("Don't send me an empty file!");
        }
        log.info("Processed all files successfully");
    }
}
