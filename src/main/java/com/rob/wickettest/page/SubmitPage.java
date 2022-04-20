package com.rob.wickettest.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

/**
 * SubmitPage demonstrates various ways to submit a form.
 */
public class SubmitPage extends AbstractPage
{
    private String noButtonText;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        setDefaultModel(new CompoundPropertyModel<>(this));
        createNoButtonForm();
    }

    private void createNoButtonForm()
    {
        final Form<Void> form = new Form<>("noButtonForm");
        form.setOutputMarkupId(true);
        add(form);

        final AjaxButton secretButton = new AjaxButton("secretButton")
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                success("Secret Submit Button Pressed");
                target.add(getFeedbackPanel());
                target.add(form);
            }
        };
        form.add(secretButton);

        final TextField textField = new TextField("noButtonText");
        form.add(textField);

        final Label label = new Label("noButtonDisplay", new PropertyModel<>(this, "noButtonText"));
        form.add(label);

        final AjaxButton button = new AjaxButton("noButtonSubmit", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                success("Outside Submit Button Pressed");
                target.add(getFeedbackPanel());
                target.add(form);
            }
        };

        // To use an input element outside a form it needs a form attribute
        // with the id of the form.
        //button.add(new AttributeModifier("form", form.getMarkupId()));

        add(button);
    }
}
