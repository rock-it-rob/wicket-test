package com.rob.wickettest.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author Rob Benton
 */
public class Home extends WebPage
{
    public Home()
    {
        add(new Label("message", "Wicket sucks :)"));
    }
}
