package com.rob.wickettest.page;

import com.rob.wickettest.toastr.ToastrFeedbackPanel;
import org.apache.wicket.markup.html.WebPage;

public abstract class AbstractPage extends WebPage
{
    private static final long serialVersionUID = 1L;

    protected ToastrFeedbackPanel feedbackPanel;

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        feedbackPanel = new ToastrFeedbackPanel("feedbackPanel");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    public ToastrFeedbackPanel getFeedbackPanel()
    {
        return feedbackPanel;
    }
}
