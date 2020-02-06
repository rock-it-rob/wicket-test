package com.rob.wickettest.chart;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * AbstractSingleDatasetChart is a wicket component for a Chart.js chart that has a single data set.
 *
 * @author Rob Benton
 */
public abstract class AbstractSingleDatasetChart extends Panel
{
    private static final String DATASET_ID = "dataset";
    private static final String LABELS_ID = "labels";
    private static final String CHART_HOLDER_ID = "chartHolder";

    private final ChartDataset chartDataset;

    protected AbstractSingleDatasetChart(String id, ChartDataset chartDataset)
    {
        super(id);
        this.chartDataset = chartDataset;
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final HiddenField<String> labelsField = new HiddenField<String>(LABELS_ID, new PropertyModel<>(chartDataset, "labelsValue"));
        add(labelsField);

        final HiddenField<String> datasetField = new HiddenField<>(DATASET_ID, new PropertyModel<>(chartDataset, "dataValue"));
        add(datasetField);

        final WebMarkupContainer chartHolder = new WebMarkupContainer(CHART_HOLDER_ID);
        add(chartHolder);
    }
}
