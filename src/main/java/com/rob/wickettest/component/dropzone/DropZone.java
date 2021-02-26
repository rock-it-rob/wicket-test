package com.rob.wickettest.component.dropzone;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.resource.ContextRelativeResourceReference;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class DropZone extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(DropZone.class);

    private static final String UPLOAD_JS = "js/upload.js";
    private static final int MAX_MB = 40;
    private static final int MAX_FILES = 3;

    private boolean dzVisible = true;
    private WebMarkupContainer dzDiv;
    private AbstractDefaultAjaxBehavior uploadBehavior;

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

        /*
        //
        // Links for testing
        //

        final AjaxLink<Void> refreshLink = new AjaxLink<Void>("refresh")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                target.add(container);
            }
        };
        add(refreshLink);

        final AjaxLink<Void> toggleLink = new AjaxLink<Void>("toggleForm")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                dzVisible = !dzVisible;
                target.add(container);
            }
        };
        add(toggleLink);

        final AjaxLink<Void> noopLink = new AjaxLink<Void>("noop")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                // nothing
                //target.add(this);
            }
        };
        add(noopLink);
        */

        //
        // Behaviors
        //

        uploadBehavior = new AbstractDefaultAjaxBehavior()
        {
            @Override
            protected void respond(AjaxRequestTarget target)
            {
                log.info("Received request");

                onUpload(target);
                target.add(container);
            }
        };
        add(uploadBehavior);
    }

    private void onUpload(AjaxRequestTarget target)
    {
        final ServletWebRequest servletWebRequest = (ServletWebRequest) getRequest();
        try
        {
            final MultipartServletWebRequest multipartServletWebRequest = servletWebRequest.newMultipartWebRequest(Bytes.megabytes(MAX_MB), "unused");
            multipartServletWebRequest.parseFileParts();
            for (Map.Entry<String, List<FileItem>> me : multipartServletWebRequest.getFiles().entrySet())
            {
                log.info("Files in: " + me.getKey());

                for (FileItem fi : me.getValue())
                {
                    processFile(fi);
                }
            }
            done();
        }
        catch (FileUploadException e)
        {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            final HttpServletResponse httpServletResponse = (HttpServletResponse) getResponse().getContainerResponse();
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            /*
            try
            {
                httpServletResponse.setContentType("text/plain");
                httpServletResponse.getWriter().print(e.getMessage());
                httpServletResponse.getWriter().close();
            }
            catch (IOException iox)
            {
                throw new RuntimeException(iox);
            }
             */
        }
    }

    private void processFile(FileItem fileItem) throws Exception
    {
        log.info(fileItem.getName());

        if (1 == 1) throw new Exception("no uploads for you");
    }

    private void done()
    {
        log.info("Processed all files successfully");
    }
}
