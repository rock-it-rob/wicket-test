package com.rob.wickettest.page;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationPage extends AbstractPage
{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(ValidationPage.class);

    private static final String ACCEPTED_VALUE = "abc";

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final Form<Object> validateForm = new Form<>("validateForm");
        add(validateForm);

        final TextField<String> textInput = new TextField<>("textInput", new Model<>());
        textInput.setLabel(new Model<>("textInput"));
        textInput.add(new IValidator<String>()
        {
            @Override
            public void validate(IValidatable<String> v)
            {
                log.debug("Validating: " + v.getValue());

                if (!v.getValue().equals(ACCEPTED_VALUE))
                {
                    log.warn("Validation failed.");
                    v.error(new ValidationError("You are WRONG. Enter: " + ACCEPTED_VALUE));
                }
            }
        });
        validateForm.add(textInput);
    }
}
