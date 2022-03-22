package com.rob.wickettest.component.mce;

import com.rob.wickettest.page.AbstractPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ValidationException;

public class MceField extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(MceField.class);

    private static final String MCE_CONTENT_DEP = "mceContent";

    private final IModel<String> model;

    private  WebMarkupContainer viewContainer;
    private TextArea<String> rawText;
    private boolean editMode = true;

    public MceField(String id, IModel<String> model)
    {
        super(id);
        this.model = model;
    }

    public boolean getEditMode()
    {
        return editMode;
    }

    public void setEditMode(boolean editMode)
    {
        this.editMode = editMode;
    }



    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        // Edit contents

        final WebMarkupContainer editContainer = new WebMarkupContainer("editContainer")
        {
            @Override
            public void renderHead(IHeaderResponse response)
            {
                super.renderHead(response);

                // Initialize the MCE.
                final String initMce = String.format("tinymce.init({ mode: 'exact', elements: '%s' });", rawText.getMarkupId());
                final OnDomReadyHeaderItem headerInit = OnDomReadyHeaderItem.forScript(initMce);
                response.render(headerInit);
            }

            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                setVisible(editMode);
            }
        };
        editContainer.setOutputMarkupId(true);
        editContainer.setOutputMarkupPlaceholderTag(true);
        add(editContainer);

        // Create the textare which serves as the starting point for creating a tinymce object and contains the initial string value.
        rawText = new TextArea<>("rawText", model);
        rawText.setOutputMarkupId(true);
        rawText.setOutputMarkupPlaceholderTag(true);
        editContainer.add(rawText);

        final AjaxLink<Void> saveLink = new AjaxLink<Void>("saveLink")
        {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);
                attributes.getDynamicExtraParameters().add(String.format("return { '%s': tinymce.get('%s').getContent() }", MCE_CONTENT_DEP, rawText.getMarkupId()));
            }

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                final IRequestParameters params = getRequest().getRequestParameters();
                final String mceContent = params.getParameterValue(MCE_CONTENT_DEP).toString();

                try
                {
                    validateMceContent(mceContent);
                    model.setObject(mceContent);
                    onSave(target);
                    editMode = false;
                    target.add(viewContainer);
                    target.add(editContainer);
                }
                catch (ValidationException e)
                {
                    log.error("Rejecting value: " + mceContent);
                    MceField.this.error(e.getMessage());
                    target.add(((AbstractPage) getPage()).getFeedbackPanel());
                }
            }
        };

        editContainer.add(saveLink);

        // Read only contents

        viewContainer = new WebMarkupContainer("viewContainer")
        {
            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                setVisible(!editMode);
            }
        };
        viewContainer.setOutputMarkupId(true);
        viewContainer.setOutputMarkupPlaceholderTag(true);
        add(viewContainer);

        final Label readOnlyContent = new Label("readOnlyContent", model);
        readOnlyContent.setEscapeModelStrings(false);
        viewContainer.add(readOnlyContent);

        final AjaxLink<Void> editLink = new AjaxLink<Void>("editLink")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                editMode = true;
                target.add(editContainer);
                target.add(viewContainer);
            }
        };
        viewContainer.add(editLink);
    }

    protected void validateMceContent(String content) throws ValidationException
    {
        // override
    }

    protected void onSave(AjaxRequestTarget target)
    {
        // override
    }
}
