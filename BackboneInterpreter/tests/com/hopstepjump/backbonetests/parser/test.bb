stratum GWTbilling
	parent-stratum foo
		is-relaxed is-destructive
    	depends-on GWTaddress, bbb
{
    component GWTCustomerWidget2
         resembles GWTCustomerWidget replaces GWTCustomerWidget
    {
        parts:
            f: AddressFactory;
            l: ButtonLogic;
            b: Button
                text = "Enter billing address";
        replace-parts:
            d becomes d: SimpleTabPanel;
        connectors:
            s joins create@l to creator@f;
            t joins main@b to _widgets@h;
            cl joins listener@l to clickListeners@b;
            f joins panel@f to _widgets@d;
        replace-connectors:
            c becomes 15171817-fcfd-467e-9855-d7a6654e49a2 joins main@h to _widgets@d;
    }
    
    interface IGrid implementation-class com.google.gwt.user.client.ui.Grid
         resembles IHTMLTable
    {
        «interface: UML2Attribute(bean)»
    }
    

    component ButtonLogic implementation-class com.myapplication.client.widgets.ButtonLogic
    {
     		«component: UML2Attribute(bean), UML2Attribute(lifecycle-callbacks)» 
        ports:
            create requires  ICreate;
            listener provides  IClickListener;
    }

    component AddressFactory is-factory
         resembles FactoryBase
    {
        ports:
            «port» panel;
        parts:
            78a3ad9b-b34f-4a09-b4a3-fd8287f26d7f: GWTAddressWidget
                text = "<b>Enter billing address</b>";
            5ed0659e-393c-435c-9767-9270cb58c638: HorizontalPanel
                title = "Billing address";
        connectors:
            387af3a4-b488-4057-9cfa-7c9e4ab4163d joins panel@78a3ad9b-b34f-4a09-b4a3-fd8287f26d7f to _widgets@5ed0659e-393c-435c-9767-9270cb58c638;
            31ac06ca-1f3e-4862-a4ed-78eb8c240028 joins main@5ed0659e-393c-435c-9767-9270cb58c638 to panel;
    }

}

