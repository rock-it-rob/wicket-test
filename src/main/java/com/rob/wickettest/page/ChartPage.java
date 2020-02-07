package com.rob.wickettest.page;

import com.rob.wickettest.chart.ChartDataset;
import com.rob.wickettest.chart.DoughnutChart;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class ChartPage extends AbstractPage
{
    private static final String FORM_ID = "datasetForm";
    private static final String GREEN_ID = "greenCount";
    private static final String BLUE_ID = "blueCount";
    private static final String RED_ID = "redCount";
    private static final String SUBMIT_BUTTON_ID = "datasetSubmitButton";
    private static final String CHART_ID = "chart";

    private final WicketModel wicketModel = new WicketModel();
    private final DoughnutChart chart = new DoughnutChart(CHART_ID, wicketModel);

    public ChartPage()
    {
        setDefaultModel(new CompoundPropertyModel<>(wicketModel));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        chart.setLabels("Green", "Blue", "Red");
        chart.setOutputMarkupId(true);
        add(chart);

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
                chart.modelChanged();
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

    private static final class WicketModel implements ChartDataset
    {
        @NotNull
        private Integer greenCount = 3;

        @NotNull
        private Integer blueCount = 5;

        @NotNull
        private Integer redCount = 1;

        @Override
        public List<Number> getData()
        {
            return Arrays.asList(new Number[]{greenCount, blueCount, redCount});
        }

        @Override
        public List<String> getBackgroundColor()
        {
            return Arrays.asList("#8d8", "#88d", "#d88");
        }
    }
}
