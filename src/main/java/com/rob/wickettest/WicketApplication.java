package com.rob.wickettest;

import com.rob.wickettest.page.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author Rob Benton
 */
public class WicketApplication extends WebApplication
{
	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}
}
