package com.rob.wickettest.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author Rob Benton
 */
public class HomePage extends AbstractPage
{
    private static final long serialVersionUID = 1L;

    private static final String DETAILS_ID = "detailsPageLink";
    private static final String FORM_ID = "formPageLink";
    private static final String VALIDATION_ID = "validationPageLink";
    private static final String ATTRIBUTE_ID = "attributePageLink";

    public HomePage()
    {
        // Add text labels.
        add(new Label("message", "Wicket message :)"));

        // Add links.
        add(new Link<Void>(DETAILS_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(DetailsPage.class);
            }
        });
        add(new Link<Void>(FORM_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(FormPage.class);
            }
        });
        add(new Link<Void>(VALIDATION_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(ValidationPage.class);
            }
        });
        add(new Link<Void>(ATTRIBUTE_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(AttributePage.class);
            }
        });
    }
}
