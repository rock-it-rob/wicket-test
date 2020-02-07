package com.rob.wickettest.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * ChartDataset contains the properties common to all charts.
 */
public class ChartDataset
{
    protected ArrayList<String> backgroundColor;
    protected ArrayList<String> borderColor;
    protected ArrayList<ChartDataset.Data> data;

    public ArrayList<String> getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(ArrayList<String> backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public ArrayList<String> getBorderColor()
    {
        return borderColor;
    }

    public void setBorderColor(ArrayList<String> borderColor)
    {
        this.borderColor = borderColor;
    }

    public ArrayList<ChartDataset.Data> getData()
    {
        return data;
    }

    public void setData(ArrayList<ChartDataset.Data> data)
    {
        this.data = data;
    }

    /**
     * Data is the numeric content represented by the chart.
     */
    public interface Data extends List<Number>
    {
    }
}
