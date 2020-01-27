package com.rob.wickettest.component.widget;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WidgetPalette is a wicket component that holds widgets for potential use on
 * a WidgetCanvas.
 *
 * @author Rob Benton
 */
public class WidgetPalette extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(WidgetPalette.class);

    private static final String PALETTE_ID = "palette";
    private static final String LIST_ID = "widgets";

    private final WebMarkupContainer palette = new WebMarkupContainer(PALETTE_ID);
    private final RepeatingView widgets = new RepeatingView(LIST_ID);

    public WidgetPalette(String id)
    {
        super(id);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        add(palette);
        palette.add(widgets);
    }

    /**
     * Adds a widget to the palette.
     *
     * @param widgetCreator a lambda provided the new wicket id.
     */
    public void addWidget(WidgetCreator widgetCreator)
    {
        final AbstractWidget widget = widgetCreator.create(widgets.newChildId());
        //widget.add(new WidgetCanvas.CanvasDropBehavior());
        widgets.add(widget);
        widget.setDataId(String.valueOf(widgets.size()));
    }

    @FunctionalInterface
    public static interface WidgetCreator<T extends AbstractWidget>
    {
        /**
         * Creates a new widget.
         *
         * @param id wicket id
         * @return a new widget
         */
        T create(String id);
    }
}
