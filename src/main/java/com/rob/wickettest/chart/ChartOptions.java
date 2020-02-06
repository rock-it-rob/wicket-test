package com.rob.wickettest.chart;

import java.util.List;

public interface ChartOptions
{
    List<Number> getData();

    List<String> getBackgroundColor();

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

    default String getBackgroundColorValue()
    {
        return String.join(" ", getBackgroundColor().toArray(new String[0]));
    }

    default void setBackgroundColorValue()
    {
        throw new UnsupportedOperationException();
    }
}
