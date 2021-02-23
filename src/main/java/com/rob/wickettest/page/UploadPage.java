package com.rob.wickettest.page;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.core.request.handler.EmptyAjaxRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.resource.ContextRelativeResourceReference;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadPage extends AbstractPage
{
    private static final Logger log = LoggerFactory.getLogger(UploadPage.class);

    private static final String UPLOAD_JS = "js/upload.js";
    private static final int MAX_BYTES = 1024 * 10; // 10 MB

    private boolean dzVisible = true;
    private WebMarkupContainer dzDiv;
    private AbstractDefaultAjaxBehavior uploadBehavior;

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
                        "createDropzone(document.getElementById('%s'), document.getElementById('%s'), '%s');",
                        dzDiv.getMarkupId(),
                        getMarkupId(),
                        uploadBehavior.getCallbackUrl()
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

        //
        // Behaviors
        //

        uploadBehavior = new AbstractDefaultAjaxBehavior()
        {
            @Override
            protected void respond(AjaxRequestTarget target)
            {
                log.info("Received request");

                // This seems to keep wicket from freaking out from not using its Wicket.Ajax.* wrapper for javascript.
                // TODO: We may not need this once the redirect is in place.
                //getRequestCycle().scheduleRequestHandlerAfterCurrent(EmptyAjaxRequestHandler.getInstance());
                //setResponsePage(UploadPage.class);
                target.add(container);
            }
        };

        add(uploadBehavior);

        //
        // Control buttons
        //

        /*
        final ProcessQueueListener processQueueListener = new ProcessQueueListener(form);

        final AjaxButton uploadButton = new AjaxButton("upload")
        {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);
                attributes.getAjaxCallListeners().add(processQueueListener);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                super.onSubmit(target, form);
                final ServletWebRequest servletWebRequest = (ServletWebRequest) getRequest();
                try
                {
                    final MultipartServletWebRequest multipartServletWebRequest = servletWebRequest.newMultipartWebRequest(Bytes.bytes(MAX_BYTES), "unused");
                    multipartServletWebRequest.parseFileParts();
                    multipartServletWebRequest.getFiles()
                            .forEach((k, v) -> log.info("File: " + k));
                }
                catch (FileUploadException e)
                {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        };
        form.add(uploadButton);

         */
    }

    private void onUpload()
    {
        final ServletWebRequest servletWebRequest = (ServletWebRequest) getRequest();
        try
        {
            final MultipartServletWebRequest multipartServletWebRequest = servletWebRequest.newMultipartWebRequest(Bytes.bytes(MAX_BYTES), "unused");
            multipartServletWebRequest.parseFileParts();
            multipartServletWebRequest.getFiles()
                    .forEach((k, v) -> {
                        log.info("Files in: " + k);
                        v.forEach(fi -> log.info(fi.getName()));
                    });
            //setResponsePage(UploadPage.class);
            //getRequestCycle().setResponsePage(HomePage.class, RenderPageRequestHandler.RedirectPolicy.ALWAYS_REDIRECT);
            //getRequestCycle().setResponsePage(HomePage.class);
        }
        catch (FileUploadException e)
        {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /*
    private static final class ProcessQueueListener extends AjaxCallListener
    {
        private final Form<?> form;

        // Make a new listener with a component that is a drozpone. It needs to have a markup id.
        private ProcessQueueListener(Form<?> form)
        {
            this.form = form;
        }

        @Override
        public CharSequence getBeforeSendHandler(Component component)
        {
            return String.format(";Dropzone.forElement('#%s').processQueue();", form.getMarkupId());
        }
    }

    private static final class ProcessFileBehavior extends AbstractDefaultAjaxBehavior
    {
        @Override
        protected void respond(AjaxRequestTarget target)
        {
            log.info("Processing request");
        }
    }
     */
}
