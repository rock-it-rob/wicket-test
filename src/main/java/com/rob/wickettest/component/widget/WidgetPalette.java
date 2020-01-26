package com.rob.wickettest.component.widget;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * WidgetPalette is a wicket component that holds widgets for potential use on
 * a WidgetCanvas.
 *
 * @author Rob Benton
 */
public class WidgetPalette extends Panel
{
    private static final String PALETTE_ID = "palette";
    private static final String LIST_ID = "paletteList";

    private final WebMarkupContainer paletteContainer = new WebMarkupContainer(PALETTE_ID);
    private final RepeatingView paletteList = new RepeatingView(LIST_ID);

    public WidgetPalette(String id)
    {
        super(id);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        add(paletteContainer);
        paletteContainer.add(paletteList);
    }

    /**
     * Adds a widget to the palette.
     *
     * @param widgetCreator a lambda provided the new wicket id.
     */
    public void addWidget(WidgetCreator widgetCreator)
    {
        paletteList.add(widgetCreator.create(paletteList.newChildId()));
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
