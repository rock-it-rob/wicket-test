package com.rob.wickettest.page;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.validation.constraints.Pattern;

public class ValidationPage extends AbstractPage
{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(ValidationPage.class);

    private static final String ACCEPTED_VALUE = "abc";
    private static final String MESSAGE = "Accepted value is 'abc'";

    @Pattern(regexp = ACCEPTED_VALUE, message = MESSAGE)
    private String value;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final Form<Object> validateForm = new Form<>("validateForm");
        add(validateForm);

        final TextField<String> textInput = new TextField<>("textInput", new PropertyModel<>(this, "value"));
        textInput.setLabel(new Model<>("textInput"));
        textInput.add(new PropertyValidator<>());
        validateForm.add(textInput);
    }
}
