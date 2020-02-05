package com.rob.wickettest.page;

import com.rob.wickettest.chart.ChartDataset;
import com.rob.wickettest.chart.DoughnutChart;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class ChartPage extends AbstractPage
{
    private static final String FORM_ID = "datasetForm";
    private static final String GREEN_ID = "greenCount";
    private static final String BLUE_ID = "blueCount";
    private static final String RED_ID = "redCount";
    private static final String SUBMIT_BUTTON_ID = "datasetSubmitButton";
    private static final String CHART_ID = "chart";

    private final WicketModel wicketModel = new WicketModel();
    private final ChartDataset chartDataset = new ChartDataset();

    public ChartPage()
    {
        setDefaultModel(new CompoundPropertyModel<>(wicketModel));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final DoughnutChart chart = new DoughnutChart(CHART_ID, chartDataset);
        chart.setOutputMarkupId(true);
        add(chart);

        // Add the page model to the chart dataset model.
        chartDataset.getData().add(new PropertyModel<>(wicketModel, "greenCount"));
        chartDataset.getData().add(new PropertyModel<>(wicketModel, "blueCount"));
        chartDataset.getData().add(new PropertyModel<>(wicketModel, "redCount"));

        chartDataset.getLabels().add("Green");
        chartDataset.getLabels().add("Blue");
        chartDataset.getLabels().add("Red");

        final Form<Object> datasetForm = new Form<>(FORM_ID);
        add(datasetForm);

        final TextField<Integer> greenField = new TextField<>(GREEN_ID);
        greenField.add(new PropertyValidator<>());
        datasetForm.add(greenField);

        final TextField<Integer> blueField = new TextField<>(BLUE_ID);
        blueField.add(new PropertyValidator<>());
        datasetForm.add(blueField);

        final TextField<Integer> redField = new TextField<>(RED_ID);
        redField.add(new PropertyValidator<>());
        datasetForm.add(redField);

        final AjaxButton submitButton = new AjaxButton(SUBMIT_BUTTON_ID)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                target.add(chart);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                target.add(feedbackPanel);
            }
        };
        datasetForm.add(submitButton);


    }

    private static final class WicketModel
    {
        @NotNull
        private Integer greenCount = new Integer(3);

        @NotNull
        private Integer blueCount = new Integer(5);

        @NotNull
        private Integer redCount = new Integer(1);
    }
}
