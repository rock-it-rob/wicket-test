package com.rob.wickettest.component.mce;

import com.rob.wickettest.page.AbstractPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MceField extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(MceField.class);

    private static final String MCE_CONTENT_DEP = "mceContent";

    private final WebMarkupContainer mceHolder = new WebMarkupContainer("mceHolder");
    private final IModel<String> model;

    private boolean editMode = true;

    public MceField(String id, IModel<String> model)
    {
        super(id);
        this.model = model;
    }

    public boolean getEditMode()
    {
        return editMode;
    }

    public void setEditMode(boolean editMode)
    {
        this.editMode = editMode;
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);

        // Initialize the MCE.
        final String initMce = String.format("tinymce.init({ mode: 'specific_textareas', selector: '#%s textarea' });", mceHolder.getMarkupId());
        final OnDomReadyHeaderItem headerInit = OnDomReadyHeaderItem.forScript(initMce);
        response.render(headerInit);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        // Edit contents

        final WebMarkupContainer editContainer = new WebMarkupContainer("editContainer")
        {
            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                setVisible(editMode);
            }
        };
        editContainer.setOutputMarkupId(true);
        editContainer.setOutputMarkupPlaceholderTag(true);
        add(editContainer);

        mceHolder.setOutputMarkupId(true);
        editContainer.add(mceHolder);

        final AjaxLink<Void> saveLink = new AjaxLink<Void>("saveLink")
        {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);
                attributes.getDynamicExtraParameters().add(String.format("return { '%s': tinymce.get($('#%s').find('textarea')[0].id).getContent() }", MCE_CONTENT_DEP, mceHolder.getMarkupId()));
            }

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                final IRequestParameters params = getRequest().getRequestParameters();
                final String mceContent = params.getParameterValue(MCE_CONTENT_DEP).toString();

                try
                {
                    validateMceContent(mceContent);
                    model.setObject(mceContent);
                    onSave(target);
                }
                catch (Exception e)
                {
                    log.error("Rejecting value: " + mceContent);
                    MceField.this.error(e.getMessage());
                    target.add(((AbstractPage) getPage()).getFeedbackPanel());
                }
            }
        };

        editContainer.add(saveLink);

        // Read only contents

        final WebMarkupContainer viewContainer = new WebMarkupContainer("viewContainer")
        {
            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                setVisible(!editMode);
            }
        };
        viewContainer.setOutputMarkupId(true);
        viewContainer.setOutputMarkupPlaceholderTag(true);
        add(viewContainer);

        final Label readOnlyContent = new Label("readOnlyContent", model);
        readOnlyContent.setEscapeModelStrings(false);
        viewContainer.add(readOnlyContent);
    }

    // Can override
    protected void validateMceContent(String content) throws Exception
    {
        // override
    }

    protected void onSave(AjaxRequestTarget target)
    {
        // override
    }
}
