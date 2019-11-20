package com.rob.wickettest.page;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormPage extends AbstractPage
{
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(FormPage.class);
    private static final String FORM_ID = "form";
    private static final String TEXT_ID = "textbox";
    private static final String UPDATE_ID = "updateText";
    private static final String SUBMIT_ID = "submitButton";

    private String text;
    private String updateText;

    @Override
    @SuppressWarnings("serial")
    protected void onInitialize()
    {
        super.onInitialize();

        Form<IModel<?>> form = new Form<>(FORM_ID);
        add(form);

        // Add text field.
        TextField<String> tf = new TextField<>(TEXT_ID, new Model<String>(text));
        form.add(tf);

        // Add label.
        Label l = new Label(UPDATE_ID, new Model<String>(updateText));
        form.add(l);

        // Add the submit button.
        final IndicatingAjaxButton submitButton = new IndicatingAjaxButton(SUBMIT_ID)
        {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                // This listener disables the button for the duration of the ajax call.
                attributes.getAjaxCallListeners().add(
                        new AjaxCallListener()
                        {
                            @Override
                            public CharSequence getBeforeHandler(Component component)
                            {
                                return String.format("$('#%s').prop('disabled', true);", getMarkupId());
                            }

                            @Override
                            public CharSequence getCompleteHandler(Component component)
                            {
                                return String.format("$('#%s').prop('disabled', false);", getMarkupId());
                            }
                        }
                );
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                try
                {
                    log.info("Begin submit handler");
                    Thread.sleep(2000);
                    log.info("End submit handler");
                }
                catch (InterruptedException e)
                {
                    log.error(e.getMessage());
                }
            }
        };
        form.add(submitButton);

        // Add update behavior to text field.
        tf.add(new AjaxFormComponentUpdatingBehavior("keyup")
        {
            @Override
            protected void onUpdate(AjaxRequestTarget arg0)
            {
                log.info("Intercepted ajax event keyup");
            }
        });
        tf.add(new AjaxFormComponentUpdatingBehavior("change")
        {
            @Override
            protected void onUpdate(AjaxRequestTarget arg0)
            {
                log.info("Intercepted ajax event change");
            }
        });
        tf.add(new AjaxFormComponentUpdatingBehavior("blur")
        {
            @Override
            protected void onUpdate(AjaxRequestTarget arg0)
            {
                log.info("Intercepted ajax event blur");
            }
        });
    }
}
