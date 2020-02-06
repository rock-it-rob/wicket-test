package com.rob.wickettest.chart;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

public interface ChartDataset
{
    List<String> getLabels();

    List<Number> getData();

    default IModel<List<String>> getLabelModel()
    {
        return new PropertyModel<>(this, "labels");
    }

    default IModel<List<Number>> getDataModel()
    {
        return new PropertyModel<>(this, "data");
    }
}
