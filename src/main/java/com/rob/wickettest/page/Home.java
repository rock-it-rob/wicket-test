package com.rob.wickettest.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author Rob Benton
 */
public class Home extends WebPage
{
    private static final String DETAILS_ID = "details";

    public Home()
    {
        // Add text labels.
        add(new Label("message", "Wicket message :)"));

        // Add links.
        add(
            new Link<Void>(DETAILS_ID)
            {
                @Override
                public void onClick()
                {
                    setResponsePage(Details.class);
                }
            }
        );
    }
}
