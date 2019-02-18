package com.rob.wickettest.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormPage extends WebPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(FormPage.class);
	private static final String FORM_ID = "form";
	private static final String TEXT_ID = "textbox";
	private static final String UPDATE_ID = "updateText";

	private String text;
	private String updateText;

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		Form<IModel<?>> form = new Form<>(FORM_ID);
		add(form);

		// Add text field.
		TextField<String> tf = new TextField<>(TEXT_ID, new Model<String>(text));
		form.add(tf);

		// Add label.
		Label l = new Label(UPDATE_ID, new Model<String>(updateText));
		form.add(l);

		// Add update behavior to text field.
		tf.add(new AjaxFormComponentUpdatingBehavior("keyup")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget arg0)
			{
				log.info("Intercepted ajax event keyup");
			}
		});
		tf.add(new AjaxFormComponentUpdatingBehavior("change")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget arg0)
			{
				log.info("Intercepted ajax event change");
			}
		});
		tf.add(new AjaxFormComponentUpdatingBehavior("blur")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget arg0)
			{
				log.info("Intercepted ajax event blur");
			}
		});
	}
}
