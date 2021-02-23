package com.rob.wickettest.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.resource.ContextRelativeResourceReference;

public class UploadPage extends AbstractPage
{
    private static final String UPLOAD_JS = "js/upload.js";

    private boolean formVisible = true;
    private final Form<Void> form = new Form<>("form");

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final WebMarkupContainer container = new WebMarkupContainer("container")
        {
            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                setVisible(formVisible);
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
                final String js = String.format("createDropzone(document.getElementById('%s'), document.getElementById('%s'));", form.getMarkupId(), getMarkupId());
                //final String js = "console.log('hi')";
                final OnDomReadyHeaderItem headerItem = OnDomReadyHeaderItem.forScript(js);
                //final JavaScriptHeaderItem headerItem = JavaScriptHeaderItem.forScript(js, null);
                response.render(headerItem);
            }
        };
        container.setOutputMarkupId(true);
        container.setOutputMarkupPlaceholderTag(true);
        add(container);

        container.setOutputMarkupId(true);
        container.setOutputMarkupPlaceholderTag(true);

        container.add(form);

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
                formVisible = !formVisible;
                target.add(container);
            }
        };
        add(toggleLink);
    }
}
