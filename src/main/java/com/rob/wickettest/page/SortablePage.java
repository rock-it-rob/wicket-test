package com.rob.wickettest.page;

import com.rob.wickettest.component.widget.AbstractWidget;
import com.rob.wickettest.component.widget.UserCountWidget;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import java.util.ArrayList;

/**
 * SortablePage displays a movable grid of elements using the SortableJS library.
 *
 * @author Rob Benton
 */
public class SortablePage extends AbstractPage
{
    private static final String WIDGET_PALETTE_ID = "widgetPaletteList";
    private static final String WIDGET_CANVAS_ID = "widgetCanvas";

    private final ArrayList<AbstractWidget> paletteWidgets = new ArrayList<>();
    private final ArrayList<AbstractWidget> widgets = new ArrayList<>();

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final RepeatingView paletteListView = new RepeatingView(WIDGET_PALETTE_ID, new Model<>(paletteWidgets));
        add(paletteListView);
        paletteWidgets.add(new UserCountWidget(paletteListView.newChildId()));
        for (AbstractWidget w : paletteWidgets)
        {
            paletteListView.add(w);
        }

        final RepeatingView widgetListView = new RepeatingView(WIDGET_CANVAS_ID)
        {
            // todo: add widgets
        };
        add(widgetListView);
    }
}
