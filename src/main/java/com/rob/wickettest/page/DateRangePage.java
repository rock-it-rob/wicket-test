package com.rob.wickettest.page;

import com.rob.wickettest.converter.LocalDateConverter;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.ValidationError;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

public class DateRangePage extends AbstractPage
{
    private static final String INVALID_DATE_RANGE_MSG = "Invalid date range";

    private static final String FORM_ID = "form";
    private static final String BEGIN_DATE_ID = "beginDate";
    private static final String END_DATE_ID = "endDate";
    private static final String SUBMIT_ID = "submit";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(LocalDateConverter.FORMAT);

    private final PageModel model = new PageModel();
    private final TextField<LocalDate> beginDateInput = new TextField<>(BEGIN_DATE_ID);
    private final TextField<LocalDate> endDateInput = new TextField<>(END_DATE_ID);

    private final Form<Void> form = new Form<>(FORM_ID);

    public DateRangePage()
    {
        setDefaultModel(new CompoundPropertyModel<>(model));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        add(form);
        form.add(beginDateInput);
        form.add(endDateInput);

        // Put a blur handler on the begin date input to update its model on blur.
        beginDateInput.add(new AjaxFormComponentUpdatingBehavior("blur")
        {
            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
            }
        });

        // Add an event keyup handler on the begin date.
        beginDateInput.add(new AjaxEventBehavior("keyup")
        {
            @Override
            protected void onEvent(AjaxRequestTarget target)
            {
                // If the input matches the date format of the converter validate the input.
                try
                {
                    DATE_TIME_FORMATTER.parseLocalDate(beginDateInput.getInput());
                    beginDateInput.validate();
                    if (model.endDate != null)
                    {
                        if (inValidDateRange(beginDateInput.getConvertedInput(), model.endDate))
                        {
                            error(INVALID_DATE_RANGE_MSG);
                        }
                    }
                    target.add(feedbackPanel);
                }
                catch (IllegalArgumentException e)
                {
                    // nothing.
                }
            }
        });

        // Put a blur handler on the end date to update its model.
        endDateInput.add(new AjaxFormComponentUpdatingBehavior("blur")
        {
            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
            }
        });

        // Add a keyup handler on the end date.
        endDateInput.add(new AjaxEventBehavior("keyup")
        {
            @Override
            protected void onEvent(AjaxRequestTarget target)
            {
                try
                {
                    DATE_TIME_FORMATTER.parseLocalDate(endDateInput.getInput());
                    endDateInput.validate();
                    if (model.beginDate != null)
                    {
                        if (inValidDateRange(model.beginDate, endDateInput.getConvertedInput()))
                        {
                            error(INVALID_DATE_RANGE_MSG);
                        }
                    }
                    target.add(feedbackPanel);
                }
                catch (IllegalArgumentException e)
                {
                    // nothing
                }
            }
        });

        final AjaxButton submitButton = new AjaxButton(SUBMIT_ID)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                // TODO: validate on submit.
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                target.add(feedbackPanel);
            }
        };
        form.add(submitButton);
        form.add(buildFormValidator());
    }

    private IFormValidator buildFormValidator()
    {
        return new IFormValidator()
        {
            @Override
            public FormComponent<?>[] getDependentFormComponents()
            {
                return new FormComponent[]{beginDateInput, endDateInput};
            }

            @Override
            public void validate(Form<?> form)
            {
                if (beginDateInput.getConvertedInput() != null && endDateInput.getConvertedInput() != null)
                {
                    if (inValidDateRange(beginDateInput.getConvertedInput(), endDateInput.getConvertedInput()))
                    {
                        beginDateInput.error(new ValidationError(INVALID_DATE_RANGE_MSG));
                    }
                }
            }
        };
    }

    private boolean inValidDateRange(LocalDate from, LocalDate to)
    {
        return (from.compareTo(to) > 0);
    }

    private static final class PageModel implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private LocalDate beginDate;
        private LocalDate endDate;
    }
}
