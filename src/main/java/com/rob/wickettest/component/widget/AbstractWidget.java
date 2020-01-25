package com.rob.wickettest.component.widget;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * AbstractWidget represents a drag-and-drop component on the page.
 *
 * @author Rob Benton
 */
public abstract class AbstractWidget extends Panel
{
    private static final String WIDGET_ID = "widget";
    private static final String TITLE_ID = "title";
    private static final String REMOVE_LINK_ID = "removeLink";

    private String title;

    protected WebMarkupContainer widget;

    /**
     * Creates a new instance with the given title and model.
     *
     * @param id    wicket id
     * @param title
     */
    public AbstractWidget(String id, String title)
    {
        super(id);
        this.title = title;
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        widget = new WebMarkupContainer(WIDGET_ID);
        add(widget);

        final Label titleLabel = new Label(TITLE_ID, new Model<>(title));
        widget.add(titleLabel);

        final Link<Void> removeLink = new Link<Void>(REMOVE_LINK_ID)
        {
            @Override
            public void onClick()
            {
                // TODO: add remove logic
            }
        };
        widget.add(removeLink);
    }

    /**
     * Every widget provides logic to mask its data. This is to serve as a
     * visual representation of the behavior of the widget without actually
     * having to fetch and/or calculate data. While a widget is in a palete where
     * it can be chosen, but hasn't actually been added to a canvas it will
     * call this method for displaying data.
     * <p>
     * Use this method to set the model to some dummy values.
     */
    protected abstract void maskData();

    /**
     * This method is invoked whenever an ajax request would trigger an update
     * of the widget. An example is when this widget is added from a palete to
     * a canvas and real data needs to be fetched and displayed.
     *
     * @param target
     */
    protected abstract void update(AjaxRequestTarget target);
}
