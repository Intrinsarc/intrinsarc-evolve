stratum GWTkin is-relaxed is-destructive
    depends-on GWTaddress
{
    // names are global if they are of the form stratum.component: i.e. have a . or a - in them
    // if they are just component, the stratum. prefix is attached to make it global...
    // an inline name is /name/ -- this is purely descriptive
    // parts, connectors etc can occur in any order
    component 63146b11-8bf4-4093-aba6-f35887766ea2/Test/
         resembles stratum.GWTNameWidget replaces stratum.GWTNameWidget
    {
        parts:
            b2642c6e-2d82-44fd-9eb4-35c2e3fd2d30: Label
                text = "Next of kin";
            c445faa9-645b-4ece-8f1d-2fce475068a0: TextBox;
        connectors:
            52952047-de99-4a93-926b-f2f6526dd757 joins main@b2642c6e-2d82-44fd-9eb4-35c2e3fd2d30 to _widgets@v;
            6fe631b7-3b4e-48ef-b832-97ea0fcf19bc joins main@c445faa9-645b-4ece-8f1d-2fce475068a0 to _widgets@v;
    }
}
