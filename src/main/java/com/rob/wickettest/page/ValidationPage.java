package com.rob.wickettest.page;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
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
    }
}
