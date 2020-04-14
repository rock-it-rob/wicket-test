package com.rob.wickettest.page;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;

import java.util.Arrays;
import java.util.List;

/**
 * ResourcePage tests using resource bundles to populate wicket component messages.
 *
 * @author Rob Benton
 */
public class ResourcePage extends AbstractPage
{
    private static final String FORM_ID = "form";
    private static final String SELECT_ID = "select";
    private static final List<Boolean> YES_NO_LIST = Arrays.asList(Boolean.TRUE, Boolean.FALSE);

    private Boolean value;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final Form<Void> form = new Form<>(FORM_ID);
        add(form);

        final DropDownChoice<Boolean> select = new DropDownChoice<>(SELECT_ID, new PropertyModel<>(this, "value"), YES_NO_LIST);
        select.setNullValid(true);
        form.add(select);
    }
}
