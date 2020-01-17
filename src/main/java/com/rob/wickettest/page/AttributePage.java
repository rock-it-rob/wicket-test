package com.rob.wickettest.page;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributePage extends AbstractPage
{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(AttributePage.class);

    private static final ContextRelativeResourceReference ATTRIBUTE_JS = new ContextRelativeResourceReference("js/attribute.js");

    @Override
    public void renderHead(IHeaderResponse response)
    {
        response.render(JavaScriptHeaderItem.forReference(ATTRIBUTE_JS));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final CompoundPropertyModel<PageModel> pageModel = new CompoundPropertyModel<>(new PageModel());
        setDefaultModel(pageModel);

        final Form<Object> form = new Form<Object>("attributeForm")
        {
            @Override
            protected void onSubmit()
            {

            }
        };
        add(form);

        final TextField<String> attributesField = new TextField<>("attributes");
        form.add(attributesField);

        final HiddenField<String> hiddenField = new HiddenField<>("hiddenElement");
        hiddenField.add(new AttributeModifier("class", pageModel.bind("attributes")));
        form.add(hiddenField);
    }

    private static final class PageModel
    {
        private String attributes;
        private String hiddenElement;
    }
}
