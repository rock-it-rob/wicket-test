package com.rob.wickettest.page;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Arrays;
import java.util.List;

public class ListPage extends AbstractPage
{
    private final IModel<List<String>> model = Model.ofList(Arrays.asList("one", "two", "three"));

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final ListView<String> listView = new ListView<String>("list", model)
        {
            @Override
            protected void populateItem(ListItem<String> item)
            {
                item.add(new Label("value", item.getModel()));

                item.add(new Behavior()
                {
                    @Override
                    public void onComponentTag(Component component, ComponentTag tag)
                    {
                        tag.setName("span");
                    }
                });
            }
        };
        add(listView);
    }
}
