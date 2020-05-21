package com.rob.wickettest.page;

import com.rob.wickettest.component.date.DateRangeField;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.LocalDate;

public class ComplexFormComponentPage extends AbstractPage
{
    private static final String FORM_ID = "form";
    private static final String DATERANGE_ID = "dateRangeField";
    private static final String SUBMIT_BUTTON_ID = "submit";

    private final DateRangeField.DateRangeModel dateRangeModel = new DateRangeField.DateRangeModel(new LocalDate(), new LocalDate());

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final Form<Void> form = new Form<>(FORM_ID);
        form.setOutputMarkupId(true);
        add(form);

        final DateRangeField dateRangeField = new DateRangeField(DATERANGE_ID, new PropertyModel<>(this, "dateRangeModel"));
        form.add(dateRangeField);

        final AjaxButton submitButton = new AjaxButton(SUBMIT_BUTTON_ID)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                success("Saved");
                target.add(feedbackPanel);
                target.add(form);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                target.add(feedbackPanel);
            }
        };
        form.add(submitButton);
    }
}
