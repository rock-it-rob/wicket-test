package com.rob.wickettest.filter;

import org.apache.wicket.protocol.http.WicketFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author Rob Benton
 */
@WebFilter(
    urlPatterns = WicketAppFilter.URL_PATTERN,
    initParams = {
        @WebInitParam(name = "applicationClassName", value = "com.rob.wickettest.WicketApplication"),
        @WebInitParam(name = "filterMappingUrlPattern", value = WicketAppFilter.URL_PATTERN)
    }
)
public class WicketAppFilter extends WicketFilter
{
    static final String URL_PATTERN = "/*";
}
