package com.rob.wickettest.page;

import com.rob.wickettest.toastr.ToastrFeedbackPanel;
import org.apache.wicket.markup.html.WebPage;

public abstract class AbstractPage extends WebPage
{
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final ToastrFeedbackPanel toastrFeedbackPanel = new ToastrFeedbackPanel("feedbackPanel");
        add(toastrFeedbackPanel);
    }
}
