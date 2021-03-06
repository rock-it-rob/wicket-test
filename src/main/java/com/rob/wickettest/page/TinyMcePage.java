package com.rob.wickettest.page;


import com.rob.wickettest.component.mce.MceField;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ValidationException;

public class TinyMcePage extends AbstractPage
{
    private static final Logger log = LoggerFactory.getLogger(TinyMcePage.class);

    private String mceText = "<p>begin</p>";
    private MceField mceField;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        setDefaultModel(new CompoundPropertyModel<>(this));

        final Label enteredLabel = new Label("enteredText", new PropertyModel<>(this, "mceText"));
        enteredLabel.setOutputMarkupId(true);
        add(enteredLabel);

        mceField = new MceField("mceText", new PropertyModel<>(this, "mceText"))
        {
            @Override
            protected void onSave(AjaxRequestTarget target)
            {
                target.add(enteredLabel);
            }

            @Override
            protected void validateMceContent(String content) throws ValidationException
            {
                if (content != null && content.equalsIgnoreCase("<p>reject</p>"))
                {
                    throw new ValidationException("Content not accepted");
                }
            }
        };
        mceField.setOutputMarkupId(true);
        mceField.setOutputMarkupPlaceholderTag(true);
        add(mceField);

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
