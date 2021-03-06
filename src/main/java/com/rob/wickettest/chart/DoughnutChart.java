package com.rob.wickettest.chart;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class DoughnutChart extends AbstractChart
{
    public DoughnutChart(String id)
    {
        super(id);
    }

    public DoughnutChart(String id, ChartDataset chartDataset)
    {
        super(id, chartDataset);
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);

        response.render(OnDomReadyHeaderItem.forScript("createDoughnutChart();"));
    }
}
