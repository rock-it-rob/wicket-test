package com.rob.wickettest.chart;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * AbstractSingleDatasetChart is a wicket component for a Chart.js chart that has a single data set.
 *
 * @param <T> the type of dataset
 * @author Rob Benton
 */
public abstract class AbstractSingleDatasetChart extends Panel
{
    private static final String DATASET_ID = "dataset";
    private static final String DATASET_VALUE_ID = "datasetValue";
    private static final String LABEL_ID = "dataset-label";
    private static final String LABEL_VALUE_ID = "labelValue";
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

        final ListView<Number> datasetList = new ListView<Number>(DATASET_ID, chartDataset.getDataModel())
        {
            @Override
            protected void populateItem(ListItem<Number> item)
            {
                final HiddenField<Number> hiddenField = new HiddenField<>(DATASET_VALUE_ID, item.getModel());
                item.add(hiddenField);
            }
        };
        add(datasetList);

        final ListView<String> labelList = new ListView<String>(LABEL_ID, chartDataset.getLabelModel())
        {
            @Override
            protected void populateItem(ListItem<String> item)
            {
                final HiddenField<String> hiddenField = new HiddenField<>(LABEL_VALUE_ID, item.getModel());
                item.add(hiddenField);
            }
        };
        add(labelList);

        final WebMarkupContainer chartHolder = new WebMarkupContainer(CHART_HOLDER_ID);
        add(chartHolder);
    }
}
