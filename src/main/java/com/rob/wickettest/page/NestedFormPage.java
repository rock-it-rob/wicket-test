package com.rob.wickettest.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NestedFormPage extends AbstractPage
{
    private static final Logger log = LoggerFactory.getLogger(NestedFormPage.class);

    private Form<Void> outerForm;
    private Form<Void> innerForm1;
    private Form<Void> innerForm2;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        createOuterForm();
        createInnerForm1();
        createInnerForm2();
    }

    private void createOuterForm()
    {
        outerForm = new Form<Void>("outerForm")
        {
            @Override
            protected void onSubmit()
            {
                super.onSubmit();
                echo(this);
            }
        };
        add(outerForm);

        final Button submitOuterForm = new Button("submitOuterForm");
        outerForm.add(submitOuterForm);
    }

    private void createInnerForm1()
    {
        innerForm1 = new Form<Void>("innerForm1")
        {
            @Override
            protected void onSubmit()
            {
                super.onSubmit();
                echo(this);
            }
        };
        outerForm.add(innerForm1);

        final Button submitInnerForm1 = new Button("submitInnerForm1");
        innerForm1.add(submitInnerForm1);
    }

    private void createInnerForm2()
    {
        innerForm2 = new Form<Void>("innerForm2")
        {
            @Override
            protected void onSubmit()
            {
                super.onSubmit();
                echo(this);
            }
        };
        outerForm.add(innerForm2);

        final Button submitInnerForm2 = new Button("submitInnerForm2");
        innerForm2.add(submitInnerForm2);
    }

    private void echo(AjaxRequestTarget target, Form<?> form)
    {
        echo(form);
        target.add(getFeedbackPanel());
    }

    private void echo(Form<?> form)
    {
        final String msg = "submitted form: " + form.getId();

        log.info(msg);
        info(msg);
    }
}

