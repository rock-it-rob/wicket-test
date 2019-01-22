package com.rob.wickettest;

import com.rob.wickettest.page.Home;
import org.apache.wicket.Page;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author Rob Benton
 */
public class WicketApplication extends WebApplication
{
    private static final String TEMPLATE_PATH = "template";

    @Override
    public Class<? extends Page> getHomePage()
    {
        return Home.class;
    }

    @Override
    protected void init()
    {
        super.init();
        /*
        getResourceSettings().getResourceFinders().add(
            new WebApplicationPath(getServletContext(), TEMPLATE_PATH)
        );
        */
    }
}
