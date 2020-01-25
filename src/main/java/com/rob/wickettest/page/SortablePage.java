package com.rob.wickettest.page;

import com.rob.wickettest.component.AbstractWidget;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.ArrayList;

/**
 * SortablePage displays a movable grid of elements using the SortableJS library.
 *
 * @author Rob Benton
 */
public class SortablePage extends AbstractPage
{
    private static final String WIDGET_LIST_ID = "widgetList";
    private static final String WIDGET_PALETTE_ID = "widgetPalette";

    private final ArrayList<? extends AbstractWidget> widgetList = new ArrayList<>();
    private final ArrayList<? extends AbstractWidget> paletteList = new ArrayList<>();

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final RepeatingView widgetListView = new RepeatingView(WIDGET_LIST_ID)
        {
            // todo: add widgets
        };
        add(widgetListView);

        final RepeatingView paletteListView = new RepeatingView(WIDGET_PALETTE_ID)
        {
            // todo: add widgets
        };
        add(paletteListView);
    }
}
