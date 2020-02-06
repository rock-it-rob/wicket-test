package com.rob.wickettest.chart;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class DoughnutChart extends AbstractChart
{
    public DoughnutChart(String id, ChartOptions chartOptions)
    {
        super(id, chartOptions);
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);

        response.render(OnDomReadyHeaderItem.forScript("createDoughnutChart();"));
    }
}
