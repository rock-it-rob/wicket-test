package com.rob.wickettest.page;

import com.rob.wickettest.component.widget.HamburgerWidget;
import com.rob.wickettest.component.widget.UserCountWidget;
import com.rob.wickettest.component.widget.WidgetPalette;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * SortablePage displays a movable grid of elements using the SortableJS library.
 *
 * @author Rob Benton
 */
public class SortablePage extends AbstractPage
{
    private static final String WIDGET_PALETTE_ID = "widgetPalette";
    private static final String WIDGET_CANVAS_ID = "widgetCanvasList";

    //private final ArrayList<AbstractWidget> paletteWidgets = new ArrayList<>();
    //private final ArrayList<AbstractWidget> widgets = new ArrayList<>();

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final WidgetPalette widgetPalette = new WidgetPalette(WIDGET_PALETTE_ID);
        widgetPalette.addWidget((id) -> new UserCountWidget(id));
        widgetPalette.addWidget((id -> new HamburgerWidget(id)));
        add(widgetPalette);

        final RepeatingView widgetListView = new RepeatingView(WIDGET_CANVAS_ID)
        {
            // todo: add widgets
        };
        add(widgetListView);
    }
}
