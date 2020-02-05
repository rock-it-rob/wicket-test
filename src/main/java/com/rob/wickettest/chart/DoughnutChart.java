package com.rob.wickettest.chart;

import org.apache.wicket.model.IModel;

public class DoughnutChart extends AbstractSingleDatasetChart
{
    public DoughnutChart(String id, ChartDataset dataset)
    {
        super(id, dataset);
    }


    // TODO: Eventually there needs to be custom javascript in here for
    // creating the specific type of chart.
}
