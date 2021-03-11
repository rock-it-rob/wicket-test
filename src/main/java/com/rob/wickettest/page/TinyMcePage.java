package com.rob.wickettest.page;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TinyMcePage extends AbstractPage
{
    private static final Logger log = LoggerFactory.getLogger(TinyMcePage.class);

    private static final String FORM_ID = "form";
    private static final String MCE_ID = "mce";
    private static final String BUFFER_ID = "buffer";
    private static final String SAVE_BUTTON_ID = "saveButton";
    private static final String MCE_CONTENT_DEP = "mceContent";
    private static final String SAVED_CONTENT_ID = "savedContent";

    private String mce; // Not used
    private String buffer;
    private String savedContent;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        setDefaultModel(new CompoundPropertyModel<>(this));

        final Form<Void> form = new Form<>(FORM_ID);
        form.setOutputMarkupId(true);
        add(form);

        // Temporary holding for mce text.
        final HiddenField<String> hiddenBuffer = new HiddenField<>(BUFFER_ID);
        hiddenBuffer.setOutputMarkupId(true);
        form.add(hiddenBuffer);

        final TextArea<String> mceArea = new TextArea<>(MCE_ID);
        mceArea.setOutputMarkupId(true);
        form.add(mceArea);

        // Create a call listener that will execute the required mce functions before the request.
        final AjaxCallListener preSubmitListener = new AjaxCallListener()
        {
            @Override
            public CharSequence getBeforeSendHandler(Component component)
            {
                // Run the javascript to copy the mce text to the buffer before submission.
                return String.format("var mceId = $('#%s').find('textarea')[0].id;" +
                                "var text = tinymce.get(mceId).getContent();" +
                                "$('#%s').val(text);" +
                                "%s = text;",
                        form.getMarkupId(), hiddenBuffer.getMarkupId(), MCE_CONTENT_DEP);
            }
        };

        // Display changes.
        final Label savedContentLabel = new Label(SAVED_CONTENT_ID);
        savedContentLabel.setOutputMarkupId(true);
        add(savedContentLabel);

        final AjaxButton saveButton = new AjaxButton(SAVE_BUTTON_ID)
        {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);
                attributes.getDynamicExtraParameters().add(String.format("return { '%s': tinymce.get($('#%s').find('textarea')[0].id).getContent() }", MCE_CONTENT_DEP, form.getMarkupId()));
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                super.onError(target, form);
                target.add(savedContentLabel);
                target.add(getFeedbackPanel());
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {

                final IRequestParameters params = getRequest().getRequestParameters();
                final String mceContent = params.getParameterValue(MCE_CONTENT_DEP).toString();
                savedContent = mceContent;
                target.add(savedContentLabel);
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
                    log.error("Rejecing value: " + mceContent);
                    validatable.error(new ValidationError(e.getMessage()));
                }
            }
        });
        form.add(saveButton);
    }

    private void validateMceContent(String content) throws Exception
    {
        if (content != null && content.equalsIgnoreCase("<p>reject</p>"))
        {
            throw new Exception("Content not accepted");
        }
    }
}
