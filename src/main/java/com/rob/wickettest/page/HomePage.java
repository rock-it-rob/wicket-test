package com.rob.wickettest.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author Rob Benton
 */
public class HomePage extends AbstractPage
{
    private static final long serialVersionUID = 1L;

    private static final String DETAILS_ID = "details";
    private static final String FORM_ID = "form";

    public HomePage()
    {
        // Add text labels.
        add(new Label("message", "Wicket message :)"));

        // Add links.
        add(new Link<Void>(DETAILS_ID)
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick()
            {
                setResponsePage(DetailsPage.class);
            }
        });
        add(new Link<Void>(FORM_ID)
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick()
            {
                setResponsePage(FormPage.class);
            }
        });
    }
}
