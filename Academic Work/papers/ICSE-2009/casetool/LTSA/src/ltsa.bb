<Package name="ltsa" stratum="true" relaxed="true">
  <uuid>superfly2-7fe76fa8-98ee-49e3-bb78-c229259c9711</uuid>
  <parent>superfly2-673aa6d1-358e-4557-95be-e9e2f52eba72</parent>
  <dependsOn>backbone</dependsOn>
  <elements>
    <Component name="HPWindow" kind="NORMAL">
      <uuid>superfly2-d7a760be-b294-432b-ba84-3b57eb265877</uuid>
      <implementationClass>lts.ui.HPWindow</implementationClass>
      <addedPorts>
        <Port name="run" uuid="superfly2-66e5fa02-b2d9-4682-86dc-0e5280c43200">
          <provides>IRun (backbone :: api.IRun)</provides>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
      <appliedStereotypes>
        <AppliedStereotype name="component">
          <properties>
            <entry>
              <string>implementation class</string>
              <string>lts.ui.HPWindow</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
  </elements>
  <appliedStereotypes>
    <AppliedStereotype name="stratum">
      <properties>
        <entry>
          <string>relaxed</string>
          <string>true</string>
        </entry>
      </properties>
    </AppliedStereotype>
  </appliedStereotypes>
</Package>