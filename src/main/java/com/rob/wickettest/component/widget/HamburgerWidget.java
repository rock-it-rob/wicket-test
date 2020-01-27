package com.rob.wickettest.component.widget;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Sample widget that displays the number of cooked hamburgers, the number eaten
 * today, and the number cooked today.
 * @author Rob Benton
 */
public class HamburgerWidget extends AbstractWidget
{
    private static final String TITLE = "Hamburgers";

    private final WidgetModel model = new WidgetModel();

    public HamburgerWidget(String id)
    {
        super(id, TITLE);
        setDefaultModel(new CompoundPropertyModel<>(model));
    }

    @Override
    protected void update(AjaxRequestTarget target)
    {
        // Just using dummy data for now. In a real app would make service calls
        // here.
        model.totalBurgers = 10;
        model.eaten = 5;
        model.cooked = 3;

        target.add(this);
    }

    private static final class WidgetModel
    {
        private Integer totalBurgers;
        private Integer eaten;
        private Integer cooked;
    }
}
