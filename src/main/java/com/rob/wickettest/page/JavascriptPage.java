package com.rob.wickettest.page;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
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

    private final WicketModel wicketModel = new WicketModel();
    private final CustomBehavior customBehavior = new CustomBehavior(this);

    private Label customBehaviorLabel;
    private AjaxButton wicketButton;

    public JavascriptPage()
    {
        setDefaultModel(new CompoundPropertyModel<>(wicketModel));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        customBehaviorLabel = new Label(CUSTOM_BEHAVIOR_LABEL_ID)
        {
            @Override
            public void renderHead(IHeaderResponse response)
            {
                super.renderHead(response);

                // This technique refreshes the whole increasing the page render count. It's expensive and not a good idea.
                final String js = "function fireCustomBehavior() { window.location.href='" + customBehavior.getCallbackUrl() + "'; }";
                response.render(JavaScriptHeaderItem.forScript(js, "lbl-cb-" + getId()));
            }
        };
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
    }


    private static final class WicketModel implements Serializable
    {
        private String customBehaviorLabel;
    }


    private static final class CustomBehavior extends AbstractDefaultAjaxBehavior
    {
        private final JavascriptPage page;

        private CustomBehavior(JavascriptPage page)
        {
            this.page = page;
        }

        @Override
        protected void respond(AjaxRequestTarget target)
        {
            log.debug("Firing custom behavior on url: " + getCallbackUrl());
            page.wicketModel.customBehaviorLabel = new Date().toString();
            target.add(page.customBehaviorLabel);
        }


    }
}
