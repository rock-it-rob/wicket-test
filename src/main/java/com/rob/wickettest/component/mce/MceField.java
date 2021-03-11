package com.rob.wickettest.component.mce;

import com.rob.wickettest.page.AbstractPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MceField extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(MceField.class);

    private static final String MCE_CONTENT_DEP = "mceContent";

    private final WebMarkupContainer mceHolder = new WebMarkupContainer("mceHolder");
    private final IModel<String> model;

    public MceField(String id, IModel<String> model)
    {
        super(id);
        this.model = model;
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);

        // Initialize the MCE.
        final String initMce = String.format("tinymce.init({ mode: 'specific_textareas', selector: '#%s textarea' });", mceHolder.getMarkupId());
        final OnDomReadyHeaderItem headerInit = OnDomReadyHeaderItem.forScript(initMce);
        response.render(headerInit);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        mceHolder.setOutputMarkupId(true);
        add(mceHolder);

        final AjaxButton saveButton = new AjaxButton("saveButton")
        {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);
                attributes.getDynamicExtraParameters().add(String.format("return { '%s': tinymce.get($('#%s').find('textarea')[0].id).getContent() }", MCE_CONTENT_DEP, mceHolder.getMarkupId()));
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                super.onError(target, form);
                target.add(((AbstractPage) getPage()).getFeedbackPanel());
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                final IRequestParameters params = getRequest().getRequestParameters();
                final String mceContent = params.getParameterValue(MCE_CONTENT_DEP).toString();
                model.setObject(mceContent);
                onSave(target);
            }
        };

        saveButton.add(new IValidator<String>()
        {
            @Override
            public void validate(IValidatable<String> validatable)
            {
                final IRequestParameters params = getRequest().getRequestParameters();
                final String mceContent = params.getParameterValue(MCE_CONTENT_DEP).toString();

                try
                {
                    validateMceContent(mceContent);
                }
                catch (Exception e)
                {
                    log.error("Rejecting value: " + mceContent);
                    validatable.error(new ValidationError(e.getMessage()));
                }
            }
        });

        add(saveButton);
    }

    // Can override
    protected void validateMceContent(String content) throws Exception
    {
        // override
    }

    protected void onSave(AjaxRequestTarget target)
    {
        // override
    }
}
