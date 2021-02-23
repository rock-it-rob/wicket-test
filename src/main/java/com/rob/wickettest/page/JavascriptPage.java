package com.rob.wickettest.page;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Rob Benton
 */
public class JavascriptPage extends AbstractPage
{
    private static final Logger log = LoggerFactory.getLogger(JavascriptPage.class);

    private static final String CUSTOM_BEHAVIOR_LABEL_ID = "customBehaviorLabel";
    private static final String WICKET_FORM_ID = "wicketForm";
    private static final String WICKET_BUTTON_ID = "wicketButton";
    private static final String HANDLER3_LABEL_ID = "handler3Label";

    private final WicketModel wicketModel = new WicketModel();


    private Label customBehaviorLabel;
    private AjaxButton wicketButton;
    private Label label3;

    public JavascriptPage()
    {
        setDefaultModel(new CompoundPropertyModel<>(wicketModel));
    }

    private final AbstractDefaultAjaxBehavior behavior3 = new AbstractDefaultAjaxBehavior()
    {
        @Override
        protected void respond(AjaxRequestTarget target)
        {
            log.debug("Firing behavior 3 on: " + getCallbackUrl());

            handler3Label = new Date().toString();
            target.add(label3);
        }
    };

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);

        final String js = "function fire3() { Wicket.Ajax.get({ u: '" + behavior3.getCallbackUrl() + "' }); }";
        response.render(JavaScriptHeaderItem.forScript(js, "lbl3-cb-" + getId()));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final AbstractDefaultAjaxBehavior customBehavior = new AbstractDefaultAjaxBehavior()
        {
            @Override
            protected void respond(AjaxRequestTarget target)
            {
                log.debug("Firing custom behavior on url: " + getCallbackUrl());
                wicketModel.customBehaviorLabel = new Date().toString();
                target.add(customBehaviorLabel);
                setResponsePage(JavascriptPage.class);
            }
        };

        customBehaviorLabel = new Label(CUSTOM_BEHAVIOR_LABEL_ID)
        {
            @Override
            public void renderHead(IHeaderResponse response)
            {
                super.renderHead(response);

                // This technique refreshes the whole increasing the page render count. It's expensive and not a good idea.
                //final String js = "function fireCustomBehavior() { window.location.href='" + customBehavior.getCallbackUrl() + "'; }";
                final String js = "function fireCustomBehavior() { $.get('" + customBehavior.getCallbackUrl() + "'); };";
                //response.render(JavaScriptHeaderItem.forScript(js, "lbl-cb-" + getId()));
                response.render(JavaScriptHeaderItem.forScript(js, null));
            }
        };
        customBehaviorLabel.setOutputMarkupId(true);
        add(customBehaviorLabel);

        final Form<Object> wicketForm = new Form<>(WICKET_FORM_ID);
        add(wicketForm);

        wicketButton = new AjaxButton(WICKET_BUTTON_ID)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                super.onSubmit(target, form);

                wicketModel.customBehaviorLabel = "wicket update: " + new Date().toString();
                target.add(customBehaviorLabel);
            }
        };
        wicketForm.add(wicketButton);

        customBehaviorLabel.add(customBehavior);

        // Attempt 3

        add(behavior3);

        label3 = new Label(HANDLER3_LABEL_ID, new PropertyModel<>(this, "handler3Label"));
        label3.setOutputMarkupId(true);
        add(label3);
    }

    private String handler3Label;

    private static final class WicketModel implements Serializable
    {
        private String customBehaviorLabel;
    }
}
