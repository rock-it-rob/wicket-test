package com.rob.wickettest.component.widget;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * AbstractWidget represents a drag-and-drop component on the page.
 *
 * @param <T> the object type of the model of this widget
 * @author Rob Benton
 */
public abstract class AbstractWidget<T> extends Panel
{
    private static final String TITLE_ID = "title";
    private static final String REMOVE_LINK_ID = "removeLink";

    private String title;
    private IModel<T> model;

    /**
     * Creates a new instance with the given title and model.
     *
     * @param id    wicket id
     * @param title
     * @param model
     */
    public AbstractWidget(String id, String title, IModel<T> model)
    {
        super(id);
        this.title = title;
        this.model = model;
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        setDefaultModel(model);

        final Label titleLabel = new Label(TITLE_ID);
        add(titleLabel);

        final Link<Void> removeLink = new Link<Void>(REMOVE_LINK_ID)
        {
            @Override
            public void onClick()
            {
                // TODO: add remove logic
            }
        };
        add(removeLink);
    }

    /**
     * Every widget provides logic to mask its data. This is to serve as a
     * visual representation of the behavior of the widget without actually
     * having to fetch and/or calculate data. While a widget is in a list where
     * it can be chosen, but hasn't actually been added to a palette it will
     * call this method for displaying data.
     * <p>
     * Use this method to set the model to some dummy values.
     */
    protected abstract void maskData();
}
