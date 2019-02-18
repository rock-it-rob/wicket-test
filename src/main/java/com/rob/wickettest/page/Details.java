package com.rob.wickettest.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import java.util.Date;

/**
 * @author Rob Benton
 */
public class Details extends WebPage
{
	private static final long serialVersionUID = 1L;
	
	private static final String VERSION_ID = "version";
    private static final String GEN_DATE_ID = "generatedDate";

    private final String version;

    public Details()
    {
        version = getClass().getPackage().getImplementationVersion();
        add(new Label(VERSION_ID, version));
        add(new Label(GEN_DATE_ID, new Date()));
    }
}
