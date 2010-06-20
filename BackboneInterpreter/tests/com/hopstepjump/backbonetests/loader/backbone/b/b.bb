stratum d51eb48c-d176-48a9-8761-d6f5ad595001/b/
    parent e9203d68-6a1a-4fc6-a771-950265501fda/model/
     is-relaxed
{
    interface 826135d0-7c12-4dfc-93c8-13bb1b078aeb/IFace/
    {
    }

    component 66d4504e-51bb-4ee2-bfbe-f12c46badd78/B/ implementation-class test.A
    {
        attributes:
            f378bdd6-fb04-48ce-be89-3c9e73fe56a7/bb/: b43efa1f-3777-4885-a318-96398be01fe0/Prim/;
        ports:
            294af5c0-9190-4b08-885e-8cc1fe2cf918/port/ provides 826135d0-7c12-4dfc-93c8-13bb1b078aeb/IFace/;
    }

    component b43efa1f-3777-4885-a318-96398be01fe0/Prim/ is-primitive implementation-class java.lang.Integer
    {
    }
    
}

