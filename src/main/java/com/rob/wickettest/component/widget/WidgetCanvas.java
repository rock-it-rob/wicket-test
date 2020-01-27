package com.rob.wickettest.component.widget;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class WidgetCanvas extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(WidgetCanvas.class);

    private static final String CANVAS_ID = "canvas";
    private static final String WIDGETS_ID = "widgets";

    private ArrayList<String> canvasOrder = new ArrayList<>();

    public WidgetCanvas(String id)
    {
        super(id);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final WebMarkupContainer canvas = new WebMarkupContainer(CANVAS_ID);
        add(canvas);

        final RepeatingView widgets = new RepeatingView(WIDGETS_ID)
        {
            // todo: add widgets
        };
        canvas.add(widgets);
    }


    public static final class CanvasDropBehavior extends AjaxEventBehavior
    {
        private static final String EVENT = "dragend";
        private static final String ORDER_PARAM = "order";
        private static final String DEP = String.format("return { '%s': getCanvasDataIdOrder() };", ORDER_PARAM);

        public CanvasDropBehavior()
        {
            super(EVENT);
        }

        @Override
        protected void onEvent(AjaxRequestTarget target)
        {
            // todo: make abstract. Real work is done in respond method.
            log.info("Drag end");

            final IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
            final List<StringValue> order = params.getParameterValues(ORDER_PARAM);

            // TODO: Put code here for assigning order.

            log.info("order: " + order);
            /*
            for (String n : params.getParameterNames())
            {
                log.info("Request parameter " + n + ": " + params.getParameterValue(n));
            }
            */
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
        {
            super.updateAjaxAttributes(attributes);
            attributes.getDynamicExtraParameters().add(DEP);
        }
    }
}

