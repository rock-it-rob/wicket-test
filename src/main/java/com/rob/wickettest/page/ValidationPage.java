package com.rob.wickettest.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

//
// TODO: Test out validating one component from another FormComponentUpdating behavior.
// TODO: Test out validating multiple components with a form validator inside a form with an AjaxFormSubmit behavior
// where the submit is tied to a blur/change event. Will that fire for any change inside the form?
//
public class ValidationPage extends AbstractPage
{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(ValidationPage.class);

    private static final String ACCEPTED_VALUE = "abc";
    private static final String MESSAGE = "Accepted value is 'abc'";

    @NotNull
    @Pattern(regexp = ACCEPTED_VALUE, message = MESSAGE)
    private String componentTextValue;

    @NotNull
    @Email(message = "A valid email address is required")
    private String emailInputValue;

    private String numeric;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final Form<Object> componentValidateForm = new Form<>("componentValidateForm");
        add(componentValidateForm);

        final TextField<String> componentTextInput = new TextField<>("componentTextInput", new PropertyModel<>(this, "componentTextValue"));
        componentTextInput.setLabel(new Model<>("componentTextInput"));
        componentTextInput.add(new PropertyValidator<>());
        componentValidateForm.add(componentTextInput);

        final TextField<String> emailInput = new TextField<>("emailInput", new PropertyModel<>(this, "emailInputValue"));
        emailInput.setLabel(new Model<>("Email Input"));
        emailInput.add(new PropertyValidator<>());
        componentValidateForm.add(emailInput);

        final Button componentSubmitButton = new Button("componentSubmitButton")
        {
            @Override
            public void onSubmit()
            {
                success("All components validated");
            }

            @Override
            public void onError()
            {
                log.error("Error submitting component form.");
            }
        };
        componentValidateForm.add(componentSubmitButton);

        // Form Validation

        final Form<Void> formValidationForm = new Form<>("formValidationForm");
        add(formValidationForm);

        final TextField<String> numericInput = new TextField<>("numericInput", new PropertyModel<>(this, "numeric"));
        numericInput.setRequired(true);
        formValidationForm.add(numericInput);

        final AjaxButton formValidationSubmitButton = new AjaxButton("formValidationSubmitButton")
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                success("You did good!");
                target.add(getFeedbackPanel());
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                target.add(getFeedbackPanel());
            }
        };
        formValidationForm.add(formValidationSubmitButton);

        formValidationForm.add(new IFormValidator()
        {
            @Override
            public FormComponent<?>[] getDependentFormComponents()
            {
                return new FormComponent<?>[]{numericInput};
            }

            @Override
            public void validate(Form<?> form)
            {
                // The model has not been updated at this point so we must obtain the input value to validate through another method.
                final String value = numericInput.getConvertedInput();
                if (!value.matches("\\d+"))
                {
                    numericInput.error(new ValidationError("Value must be an integer"));
                }
            }
        });
    }
}
