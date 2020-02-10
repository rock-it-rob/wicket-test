package com.rob.wickettest.chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * ChartDataset contains the properties common to all charts.
 */
public class ChartDataset
{
    protected ArrayList<String> backgroundColor;
    protected ArrayList<String> borderColor;
    protected ArrayList<ChartDataset.Data> data;

    public List<String> getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(Collection<String> backgroundColor)
    {
        this.backgroundColor.clear();
        this.backgroundColor.addAll(backgroundColor);
    }

    public List<String> getBorderColor()
    {
        return borderColor;
    }

    public void setBorderColor(Collection<String> borderColor)
    {
        this.borderColor.clear();
        this.borderColor.addAll(borderColor);
    }

    public List<ChartDataset.Data> getData()
    {
        return data;
    }

    public void setData(Collection<Data> data)
    {
        this.data.clear();
        this.data.addAll(data);
    }

    /**
     * Data is the numeric content represented by the chart.
     */
    public static class Data extends ArrayList<Number>
    {
        private Data()
        {
        }

        @SafeVarargs
        public static final Data forValues(Number... values)
        {
            final Data data = new Data();
            data.addAll(Arrays.asList(values));

            return data;
        }
    }


}
