stratum GWTbilling
	parent-stratum foo
		is-relaxed is-destructive
    	depends-on GWTaddress, bbb
{
    component GWTNameWidget
    {
    		delete-attributes:
    				a, b;
        attributes:
            read-only text: String = ("hello", 2),
            a: int = 12;
    		replace-attributes:
    				text becomes text2: String = "hello",
    				a becomes a: int;
        ports:
            panel,
            panel provides Iface requires Iface;
        parts:
            v: VerticalPanel,
            U760859d7-2c90-4399-886e-48698ddd1b88: Label
                text = "First:"
                name = 2,
            U61e4dec7-7258-49ef-a4cb-0802a00600fd: TextBox,
            U5e741443-00b6-4dde-8a2d-7fca0cfffdf5: Label
                text = "Last",
            Ua03381d4-a27a-46ac-8792-471db354bb2f: TextBox,
            Ub11438fd-2001-47a6-b7bb-26c5ee79eb4a: HTML
                hTML (text);
        connectors:
            U040db492-ad25-489a-b579-a0f75530db18 joins main@v to panel,
            U5c15cd0f-1be7-42ad-9732-e4df9d8bc374 joins main[2]@U760859d7-2c90-4399-886e-48698ddd1b88 to _widgets[0]@v,
            U2dd2c4e1-0c80-4a0c-8384-118890a25c10 joins main@U61e4dec7-7258-49ef-a4cb-0802a00600fd to _widgets@v,
            U3f921efe-fa1b-4a59-b7e5-19ea136ab8dc joins main@U5e741443-00b6-4dde-8a2d-7fca0cfffdf5 to _widgets@v,
            U4dc65cab-1741-4e69-8778-424deaafce0d joins main@Ua03381d4-a27a-46ac-8792-471db354bb2f to _widgets@v,
            U4fdb8dc0-3848-4275-a39a-9ef8aa6a0f9f joins main@Ub11438fd-2001-47a6-b7bb-26c5ee79eb4a to _widgets@v;
    }
    
    component GWTCustomerWidget2
         resembles GWTCustomerWidget replaces GWTCustomerWidget
    {
    		delete-parts:
    			a, b;
        parts:
            f: AddressFactory,
            l: ButtonLogic,
            b: Button
                text = "Enter billing address";
        replace-parts:
            d becomes d: SimpleTabPanel;
        connectors:
            s joins create@l to creator@f,
            t joins main@b to _widgets@h,
            cl joins listener@l to clickListeners@b,
            f joins panel@f to _widgets@d;
        replace-connectors:
            c becomes U15171817-fcfd-467e-9855-d7a6654e49a2 joins main@h to _widgets@d;
    }
    
    interface IGrid implementation-class com.google.gwt.user.client.ui.Grid
         resembles IHTMLTable
    {
        «interface: bean, name="hello", foo=2»
    }
    

    component ButtonLogic implementation-class com.myapplication.client.widgets.ButtonLogic
    {
     		«component: bean, lifecycle-callbacks» 
        ports:
            create requires ICreate,
            listener provides IClickListener & IFace;
    }

    component AddressFactory is-factory
         resembles FactoryBase
    {
        ports:
            «port» panel;
        parts:
            U78a3ad9b-b34f-4a09-b4a3-fd8287f26d7f: GWTAddressWidget
                text = "<b>Enter billing address</b>",
            U5ed0659e-393c-435c-9767-9270cb58c638: HorizontalPanel
                title = "Billing address";
        connectors:
            U387af3a4-b488-4057-9cfa-7c9e4ab4163d joins panel@U78a3ad9b-b34f-4a09-b4a3-fd8287f26d7f to _widgets@U5ed0659e-393c-435c-9767-9270cb58c638,
            U31ac06ca-1f3e-4862-a4ed-78eb8c240028 joins main@U5ed0659e-393c-435c-9767-9270cb58c638 to panel;
    }

}

