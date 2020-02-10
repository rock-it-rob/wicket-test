package com.rob.wickettest.chart;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * AbstractChart is a wicket component for a Chart.js chart.
 *
 * @author Rob Benton
 */
public abstract class AbstractChart extends Panel
{
    private static final String LABELS_ID = "labels";
    private static final String BACKGROUNDCOLOR_ID = "backgroundColor";
    private static final String DATA_ID = "data";
    private static final String CHART_HOLDER_ID = "chartHolder";

    private final WicketModel wicketModel = new WicketModel();

    public AbstractChart(String id)
    {
        super(id);
        setDefaultModel(new CompoundPropertyModel<>(wicketModel));
    }

    protected AbstractChart(String id, ChartDataset chartDataset)
    {
        this(id);
        setChartDataset(chartDataset);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final HiddenField<String> labelsField = new HiddenField<>(LABELS_ID);
        add(labelsField);

        // TODO: Turn into a repeating view
        final HiddenField<String> backgroundColorField = new HiddenField<>(BACKGROUNDCOLOR_ID);
        add(backgroundColorField);

        // TODO: Turn into a repeating view
        final HiddenField<String> dataField = new HiddenField<>(DATA_ID);
        add(dataField);

        final WebMarkupContainer chartHolder = new WebMarkupContainer(CHART_HOLDER_ID);
        add(chartHolder);
    }

    @SafeVarargs
    public final void setLabels(String... labels)
    {
        this.wicketModel.labels = String.join(" ", labels);
    }

    public void setChartDataset(ChartDataset chartDataset)
    {
        wicketModel.chartDataset = chartDataset;
    }

    private static final class WicketModel implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private String labels;
        private ChartDataset chartDataset;

        // TODO: Turn into a list
        public String getBackgroundColor()
        {
            return String.join(" ", chartDataset.getBackgroundColor());

        }

        // TODO: Turn into a list
        public String getData()
        {
            return String.join(" ",
                    chartDataset.getData()
                            .stream()
                            .flatMap(list -> list.stream())
                            .map(d -> d.toString())
                            .collect(Collectors.toList())
            );
        }
    }
}
