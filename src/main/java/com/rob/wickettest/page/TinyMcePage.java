package com.rob.wickettest.page;


import com.rob.wickettest.component.mce.MceField;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TinyMcePage extends AbstractPage
{
    private static final Logger log = LoggerFactory.getLogger(TinyMcePage.class);

    private String mceText;
    private MceField mceField;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        setDefaultModel(new CompoundPropertyModel<>(this));

        final Label enteredLabel = new Label("enteredText", new PropertyModel<>(this, "mceText"));
        enteredLabel.setOutputMarkupId(true);
        add(enteredLabel);

        final Form<Void> form = new Form<>("form");
        add(form);

        mceField = new MceField("mceText", new PropertyModel<>(this, "mceText"))
        {
            /*
            @Override
            protected void onSave(AjaxRequestTarget target)
            {
                target.add(enteredLabel);
            }
             */

            @Override
            protected void validateMceContent(String content) throws Exception
            {
                if (content != null && content.equalsIgnoreCase("<p>reject</p>"))
                {
                    throw new Exception("Content not accepted");
                }
            }
        };
        mceField.setOutputMarkupId(true);
        mceField.setOutputMarkupPlaceholderTag(true);
        form.add(mceField);

        final AjaxButton saveLink = new AjaxButton("saveLink")
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                super.onSubmit(target, form);

                log.info("Saving");
                target.add(enteredLabel);
            }
        };
        form.add(saveLink);

        final AjaxLink<Void> toggleLink = new AjaxLink<Void>("toggle")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                mceField.setEditMode(!mceField.getEditMode());
                target.add(mceField);
            }
        };
        add(toggleLink);
    }
}
