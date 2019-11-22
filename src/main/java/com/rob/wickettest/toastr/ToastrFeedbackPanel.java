package com.rob.wickettest.toastr;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToastrFeedbackPanel extends FeedbackPanel
{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(ToastrFeedbackPanel.class);

    private String js = "";

    public ToastrFeedbackPanel(String id)
    {
        super(id);
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);

        if (js.length() != 0)
        {
            response.render(OnDomReadyHeaderItem.forScript(js));
            js = "";
        }
    }

    @Override
    protected Component newMessageDisplayComponent(String id, FeedbackMessage message)
    {
        log.info("updating toastr panel");

        switch (message.getLevel())
        {
            case FeedbackMessage.ERROR:
            case FeedbackMessage.FATAL:
                addJs("toastr.error(\"" + message.getMessage() + "\");");
                break;
            case FeedbackMessage.WARNING:
                addJs("toastr.warning(\"" + message.getMessage() + "\");");
                break;
            case FeedbackMessage.SUCCESS:
                addJs("toastr.success(\"" + message.getMessage() + "\");");
                break;
            case FeedbackMessage.INFO:
            case FeedbackMessage.DEBUG:
                addJs("toastr.info(\"" + message.getMessage() + "\");");
                break;
        }

        final Component c = super.newMessageDisplayComponent(id, message);
        c.setVisible(false);
        return c;
    }

    private void addJs(String s)
    {
        js += s;
    }
}
