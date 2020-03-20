package com.rob.wickettest.page;


import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;

public class TinyMCEPage extends AbstractPage
{
    private static final String TEXTAREA_ID = "textarea";
    private static final String LABEL_ID = "label";

    private String textarea;
    private String label;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        setDefaultModel(new CompoundPropertyModel<>(this));

        final TextArea textArea = new TextArea(TEXTAREA_ID);
        textArea.setOutputMarkupId(true);
        add(textArea);

        final Label label = new Label(LABEL_ID);
        label.setOutputMarkupId(true);
        add(label);

        textArea.add(new AjaxEventBehavior("blur")
        {
            @Override
            protected void onEvent(AjaxRequestTarget target)
            {
                TinyMCEPage.this.label = textarea;
                target.add(label);
            }
        });
    }
}
