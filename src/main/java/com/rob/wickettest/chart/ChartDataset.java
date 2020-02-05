package com.rob.wickettest.chart;

import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChartDataset implements Serializable
{
    private static final long serialVersionUID = 1L;

    private ArrayList<String> labels = new ArrayList<>();
    private ArrayList<IModel<Number>> data = new ArrayList<>();

    public List<String> getLabels()
    {
        return labels;
    }

    public void setLabels(Collection<String> labels)
    {
        this.labels.clear();
        this.labels.addAll(labels);
    }

    public List<IModel<Number>> getData()
    {
        return data;
    }

    public void setData(Collection<IModel<Number>> data)
    {
        this.data.clear();
        this.data.addAll(data);
    }
}
