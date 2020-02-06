package com.rob.wickettest.chart;

import java.util.List;

public interface ChartDataset
{
    List<String> getLabels();

    List<Number> getData();

    default String getLabelsValue()
    {
        return String.join(" ", getLabels().toArray(new String[0]));
    }

    default void setLabelsValue()
    {
        throw new UnsupportedOperationException();
    }

    default String getDataValue()
    {
        final String[] values = getData()
                .stream()
                .map(value -> value.toString())
                .toArray(String[]::new);

        return String.join(" ", values);
    }

    default void setDataValue()
    {
        throw new UnsupportedOperationException();
    }
}
