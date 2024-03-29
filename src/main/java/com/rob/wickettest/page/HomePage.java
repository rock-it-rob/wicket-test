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
    private static final String SORTABLE_ID = "sortablePageLink";
    private static final String JAVASCRIPT_ID = "javascriptPageLink";
    private static final String CHART_ID = "chartPageLink";
    private static final String TINYMCE_ID = "tinymcePageLink";
    private static final String RESOURCE_ID = "resourcePageLink";
    private static final String DATERANGE_ID = "dateRangePageLink";
    private static final String COMPLEX_FORMCOMPONENT_ID = "complexFormComponentPageLink";
    private static final String UPLOAD_ID = "uploadPageLink";
    private static final String LIST_ID = "listPageLink";
    private static final String NESTED_FORM_ID = "nestedFormPageLink";
    private static final String FFEDBACK_ID = "feedbackPageLink";
    private static final String SUBMIT_ID = "submitPageLink";

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
        add(new Link<Void>(SORTABLE_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(SortablePage.class);
            }
        });
        add(new Link<Void>(JAVASCRIPT_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(JavascriptPage.class);
            }
        });
        add(new Link<Void>(CHART_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(ChartPage.class);
            }
        });
        add(new Link<Void>(TINYMCE_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(TinyMcePage.class);
            }
        });
        add(new Link<Void>(RESOURCE_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(ResourcePage.class);
            }
        });
        add(new Link<Void>(DATERANGE_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(DateRangePage.class);
            }
        });
        add(new Link<Void>(COMPLEX_FORMCOMPONENT_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(ComplexFormComponentPage.class);
            }
        });
        add(new Link<Void>(UPLOAD_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(UploadPage.class);
            }
        });
        add(new Link<Void>(LIST_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(ListPage.class);
            }
        });
        add(new Link<Void>(NESTED_FORM_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(NestedFormPage.class);
            }
        });
        add(new Link<Void>(FFEDBACK_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(FeedbackPage.class);
            }
        });
        add(new Link<Void>(SUBMIT_ID)
        {
            @Override
            public void onClick()
            {
                setResponsePage(SubmitPage.class);
            }
        });
    }
}
