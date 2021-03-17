package com.rob.wickettest.component.mce;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MceField extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(MceField.class);

    private static final String MCE_CONTENT_DEP = "mceContent";

    private final IModel<String> model;

    private TextArea<String> rawTextArea;
    private WebMarkupContainer viewContainer;

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
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);

        // Initialize the MCE.
        final String initMce = String.format("tinymce.init({ mode: 'exact', elements: '%s' });", rawTextArea.getMarkupId());
        final OnDomReadyHeaderItem headerInit = OnDomReadyHeaderItem.forScript(initMce);
        response.render(headerInit);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        // Edit contents

        final WebMarkupContainer editContainer = new WebMarkupContainer("editContainer")
        {
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

        rawTextArea = new TextArea<>("rawText", model);
        rawTextArea.add(new IValidator<String>()
        {
            @Override
            public void validate(IValidatable<String> validatable)
            {
                try
                {
                    validateMceContent(validatable.getValue());
                }
                catch (Exception e)
                {
                    validatable.error(new ValidationError(e.getMessage()));
                }
            }
        });
        rawTextArea.setOutputMarkupId(true);
        rawTextArea.setOutputMarkupPlaceholderTag(true);
        editContainer.add(rawTextArea);

        final AjaxCallListener saveMceListener = new AjaxCallListener()
        {
            @Override
            public CharSequence getInitHandler(Component component)
            {
                return String.format("tinymce.get('%s').save();", rawTextArea.getMarkupId());
            }
        };

        final AjaxLink<Void> saveLink = new AjaxLink<Void>("saveLink")
        {
            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                setVisible(editMode);
            }

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);

                // Add listener to execute save on the tinymce object.
                attributes.getAjaxCallListeners().add(saveMceListener);
            }

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                editMode = false;
                target.add(MceField.this);
            }

            /*
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);
                attributes.getDynamicExtraParameters().add(String.format("return { '%s': tinymce.get($('#%s').find('textarea')[0].id).getContent() }", MCE_CONTENT_DEP, mceHolder.getMarkupId()));
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
                }
                catch (Exception e)
                {
                    log.error("Rejecting value: " + mceContent);
                    MceField.this.error(e.getMessage());
                    target.add(((AbstractPage) getPage()).getFeedbackPanel());
                }
            }
             */
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
            protected void onConfigure()
            {
                super.onConfigure();
                setVisible(!editMode);
            }

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                target.add(MceField.this);
                editMode = true;
            }
        };
        viewContainer.add(editLink);
    }

    protected void validateMceContent(String content) throws Exception
    {
        // override
    }

    /*
    protected void onSave(AjaxRequestTarget target)
    {
        // override
    }
     */
}
