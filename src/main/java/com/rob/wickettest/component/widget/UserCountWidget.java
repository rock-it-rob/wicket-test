package com.rob.wickettest.component.widget;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Sample widget that counts total users, new users added this week, and
 * users whose accounts were deleted or deactivated this week.
 *
 * @author Rob Benton
 */
public class UserCountWidget extends AbstractWidget
{
    private static final String TITLE = "User Statistics";

    private static final String TOTAL_USERS_ID = "totalUsers";
    private static final String NEW_USERS_ID = "newUsers";
    private static final String REMOVED_USERS_ID = "removedUsers";

    private final WidgetModel model = new WidgetModel();

    /**
     * Creates a new instance.
     *
     * @param id wicket id
     */
    public UserCountWidget(String id)
    {
        super(id, TITLE);
        setDefaultModel(new CompoundPropertyModel<WidgetModel>(model));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final Label totalLabel = new Label(TOTAL_USERS_ID);
        add(totalLabel);

        final Label newLabel = new Label(NEW_USERS_ID);
        add(newLabel);

        final Label removedLabel = new Label(REMOVED_USERS_ID);
        add(removedLabel);

        model.totalUsers = 42;
        model.newUsers = 2;
        model.removedUsers = 1;
    }
    /*
    @Override
    protected void maskData()
    {
        model.totalUsers = 42;
        model.newUsers = 2;
        model.removedUsers = 1;
    }
    */

    @Override
    protected void update(AjaxRequestTarget target)
    {
        // Just using dummy data for now. In a real app would make service calls
        // here.
        model.totalUsers = 10;
        model.newUsers = 4;
        model.removedUsers = 2;

        target.add(this);
    }

    private static final class WidgetModel
    {
        private int totalUsers;
        private int newUsers;
        private int removedUsers;
    }
}
