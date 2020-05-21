package com.rob.wickettest.component.date;

import com.rob.wickettest.page.AbstractPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.ValidationError;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class DateRangeField extends Panel
{
    private static final Logger log = LoggerFactory.getLogger(DateRangeField.class);

    private static final String FORM_ID = "form";
    private static final String START_DATE_ID = "startDate";
    private static final String END_DATE_ID = "endDate";
    private static final String START_DATE_FEEDBACK_ID = "startDateFeedback";
    private static final String END_DATE_FEEDBACK_ID = "endDateFeedback";

    private final Form<Void> form = new Form<>(FORM_ID);
    private final TextField<LocalDate> startDateInput = new TextField<>(START_DATE_ID);
    private final TextField<LocalDate> endDateInput = new TextField<>(END_DATE_ID);
    private final FeedbackPanel startDateFeedback = new FeedbackPanel(START_DATE_FEEDBACK_ID, new ComponentFeedbackMessageFilter(startDateInput));
    private final FeedbackPanel endDateFeedback = new FeedbackPanel(END_DATE_FEEDBACK_ID, new ComponentFeedbackMessageFilter(endDateInput));

    public DateRangeField(String id, IModel<DateRangeModel> model)
    {
        super(id);
        setDefaultModel(new CompoundPropertyModel<>(model));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        form.setOutputMarkupId(true);
        form.add(new IFormValidator()
        {
            @Override
            public FormComponent<?>[] getDependentFormComponents()
            {
                return new FormComponent[]{startDateInput, endDateInput};
            }

            @Override
            public void validate(Form<?> form)
            {
                DateRangeField.this.validate(startDateInput.getConvertedInput(), endDateInput.getConvertedInput(), startDateInput);
            }
        });

        startDateInput.setLabel(new PropertyModel<>(getDefaultModel(), "startDateLabel"));
        startDateInput.add(new AjaxFormSubmitBehavior("blur")
        {
            @Override
            protected void onError(AjaxRequestTarget target)
            {
                final AbstractPage page = (AbstractPage) getPage();
                target.add(startDateFeedback);
            }
        });

        endDateInput.setLabel(new PropertyModel<>(getDefaultModel(), "endDateLabel"));
        endDateInput.add(new AjaxFormSubmitBehavior("blur")
        {
            @Override
            protected void onError(AjaxRequestTarget target)
            {
                final AbstractPage page = (AbstractPage) getPage();
                target.add(endDateFeedback);
            }
        });

        startDateFeedback.setOutputMarkupId(true);
        endDateFeedback.setOutputMarkupId(true);

        add(form);
        form.add(startDateInput);
        form.add(endDateInput);
        form.add(startDateFeedback);
        form.add(endDateFeedback);
    }

    private void validateDateRange()
    {
        final LocalDate start = startDateInput.getConvertedInput();
        final LocalDate end = endDateInput.getConvertedInput();
        final DateRangeModel dateRangeModel = (DateRangeModel) getDefaultModelObject();

        if (start.compareTo(end) > 0)
        {
            startDateInput.error(new ValidationError(String.format("%s must be on or before %s", dateRangeModel.getStartDateLabel(), dateRangeModel.getEndDateLabel())));
        }
    }

    /**
     * Override for custom date range validation.
     *
     * @param start         start date.
     * @param end           end date.
     * @param formComponent component to register validation errors against.
     */
    protected void validate(LocalDate start, LocalDate end, FormComponent<?> formComponent)
    {
        validateDateRange();
    }

    public static class DateRangeModel implements Serializable
    {
        private LocalDate startDate;
        private LocalDate endDate;
        private String startDateLabel = "Start Date";
        private String endDateLabel = "End Date";

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
