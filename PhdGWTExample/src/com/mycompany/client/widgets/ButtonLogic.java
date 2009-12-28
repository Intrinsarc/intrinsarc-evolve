package com.mycompany.client.widgets;

import com.google.gwt.user.client.ui.*;

public class ButtonLogic
{
// start generated code

	private com.hopstepjump.backbone.runtime.api.ICreate create_ICreateRequired;
	public void setCreate_ICreate(com.hopstepjump.backbone.runtime.api.ICreate create_ICreateRequired) { this.create_ICreateRequired = create_ICreateRequired; }
	private com.google.gwt.user.client.ui.ClickListener listener_ClickListenerProvided = new ClickListenerListenerImpl();
	public com.google.gwt.user.client.ui.ClickListener getListener_ClickListener(Class<?> required) { return listener_ClickListenerProvided; }
// end generated code


	private class ClickListenerListenerImpl implements com.google.gwt.user.client.ui.ClickListener
	{
		private boolean showing;

		public void onClick(Widget sender)
		{
			if (!showing)
				create_ICreateRequired.create(null);
			showing = true;
		}
	}
}
