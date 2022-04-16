package com.rob.wickettest.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

public class FeedbackPage extends AbstractPage
{
    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final AjaxLink<Void> sessionLink = new AjaxLink<Void>("sessionLink")
        {
            private int messageCount = 1;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                getSession().info("Session Message: " + messageCount++);
            }
        };
        add(sessionLink);

        final AjaxLink<Void> requestLink = new AjaxLink<Void>("requestLink")
        {
            private int messageCount = 1;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                info("Request Message: " + messageCount++);
            }
        };
        add(requestLink);
    }
}
