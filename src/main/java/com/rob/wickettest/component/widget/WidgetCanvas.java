package com.rob.wickettest.component.widget;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

public class WidgetCanvas extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(WidgetCanvas.class);

    private static final String CANVAS_ID = "canvas";
    private static final String WIDGETS_ID = "widgets";
    private static final String CANVAS_ORDER_ID = "canvasOrder";

    private final CanvasModel canvasModel = new CanvasModel();

    public WidgetCanvas(String id)
    {
        super(id);
        setDefaultModel(new CompoundPropertyModel<>(canvasModel));
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

        final TextField<String> canvasOrderField = new TextField<>(CANVAS_ORDER_ID);
        add(canvasOrderField);
        canvasOrderField.add(new AjaxFormComponentUpdatingBehavior("click")
        {
            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
                log.info("click: " + canvasModel.canvasOrder);
            }
        });
        /*
        canvasOrderField.add(new AjaxEventBehavior("click")
        {
            @Override
            protected void onEvent(AjaxRequestTarget target)
            {
                log.info("click: " + canvasOrder);
            }
        });
        */
    }


    public static final class CanvasDropBehavior extends AjaxEventBehavior
    {
        private static final String EVENT = "dragend";

        public CanvasDropBehavior()
        {
            super(EVENT);
        }

        @Override
        protected void onEvent(AjaxRequestTarget target)
        {
            // todo: make abstract. Real work is done in respond method.
            log.info("Dropping");
            log.info("getCallbackScript(): " + getCallbackScript());
            log.info("getCallbackFunction(): " + getCallbackFunction());
            //log.info("getCallbackFunctionBody(): " + getCallbackFunctionBody());
            final AjaxRequestAttributes attrs = getAttributes();
            for (CharSequence dep: attrs.getDynamicExtraParameters())
            {
                log.info("dynamic param: " + dep);
            }
            for (Map.Entry<String, Object> e : attrs.getExtraParameters().entrySet())
            {
                log.info("param " + e.getKey() + ": " + e.getValue());
            }
            log.info("URL: " + getCallbackUrl());

            final IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
            for (String n : params.getParameterNames())
            {
                log.info("Request parameter " + n + ": " + params.getParameterValue(n));
            }
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
        {
            super.updateAjaxAttributes(attributes);
            //attributes.getDynamicExtraParameters().add("getCanvasDataIdOrder()");
            attributes.getDynamicExtraParameters().add("return { 'order': getCanvasDataIdOrder() };");
        }
    }

    private static final class CanvasModel implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private String canvasOrder;
    }
}

