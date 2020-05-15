package com.rob.wickettest.component.date;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class DateRangeField extends FormComponentPanel<DateRangeField.DateRangeModel>
{
    private static final Logger log = LoggerFactory.getLogger(DateRangeField.class);

    private static final String START_DATE_ID = "startDate";
    private static final String END_DATE_ID = "endDate";

    private final TextField<LocalDate> startDateInput = new TextField<>(START_DATE_ID);
    private final TextField<LocalDate> endDateInput = new TextField<>(END_DATE_ID);

    public DateRangeField(String id, IModel<DateRangeModel> model)
    {
        super(id);
        setDefaultModel(new CompoundPropertyModel<>(model));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        startDateInput.setLabel(new PropertyModel<>(getDefaultModel(), "startDateLabel"));
        endDateInput.setLabel(new PropertyModel<>(getDefaultModel(), "endDateLabel"));

        final String event = "blur";
        add(new AjaxEventBehavior(event)
        {
            @Override
            protected void onEvent(AjaxRequestTarget target)
            {
                log.info(event);
            }
        });

        add(startDateInput);
        add(endDateInput);
    }

    public static class DateRangeModel implements Serializable
    {
        private LocalDate startDate;
        private LocalDate endDate;
        private String startDateLabel;
        private String endDateLabel;

        public DateRangeModel()
        {
        }

        public DateRangeModel(LocalDate startDate, LocalDate endDate)
        {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public DateRangeModel(LocalDate startDate, String startDateLabel, LocalDate endDate, String endDateLabel)
        {
            this.startDate = startDate;
            this.startDateLabel = startDateLabel;
            this.endDate = endDate;
            this.endDateLabel = endDateLabel;
        }

        public LocalDate getStartDate()
        {
            return startDate;
        }

        public void setStartDate(LocalDate startDate)
        {
            this.startDate = startDate;
        }

        public LocalDate getEndDate()
        {
            return endDate;
        }

        public void setEndDate(LocalDate endDate)
        {
            this.endDate = endDate;
        }

        public String getStartDateLabel()
        {
            return startDateLabel;
        }

        public void setStartDateLabel(String startDateLabel)
        {
            this.startDateLabel = startDateLabel;
        }

        public String getEndDateLabel()
        {
            return endDateLabel;
        }

        public void setEndDateLabel(String endDateLabel)
        {
            this.endDateLabel = endDateLabel;
        }
    }
}
