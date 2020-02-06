package com.rob.wickettest.chart;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * AbstractChart is a wicket component for a Chart.js chart.
 *
 * @author Rob Benton
 */
public abstract class AbstractChart extends Panel
{
    private static final String DATA_ID = "data";
    private static final String BACKGROUNDCOLOR_ID = "backgroundColor";
    private static final String LABELS_ID = "labels";
    private static final String CHART_HOLDER_ID = "chartHolder";

    private final ChartOptions chartOptions;

    protected AbstractChart(String id, ChartOptions chartOptions)
    {
        super(id);
        this.chartOptions = chartOptions;
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final HiddenField<String> labelsField = new HiddenField<>(LABELS_ID, new PropertyModel<>(chartOptions, "labelsValue"));
        add(labelsField);

        final HiddenField<String> backgroundColorField = new HiddenField<>(BACKGROUNDCOLOR_ID, new PropertyModel<>(chartOptions, "backgroundColorValue"));
        add(backgroundColorField);

        final HiddenField<String> dataField = new HiddenField<>(DATA_ID, new PropertyModel<>(chartOptions, "dataValue"));
        add(dataField);

        final WebMarkupContainer chartHolder = new WebMarkupContainer(CHART_HOLDER_ID);
        add(chartHolder);
    }
}
