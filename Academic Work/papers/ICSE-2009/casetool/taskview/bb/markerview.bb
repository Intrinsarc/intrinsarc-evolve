<Package name="markerview" stratum="true" relaxed="true">
  <uuid>mitchell-370c1e35-3f8d-4963-aa09-fd946c332a9e</uuid>
  <parent>mitchell-967d25dd-dd5e-4584-b12c-511c1a333838</parent>
  <dependsOn>backbone</dependsOn>
  <elements>
    <Interface name="IGridColumn">
      <uuid>mitchell-a6d0516c-69cd-48f5-9ddc-86250d2a58a5</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>interface (backbone :: backbone profile.interface)</stereotype>
          <properties>
            <entry>
              <Attribute name="implementation-class">
                <uuid>implementation-class</uuid>
                <type>String (backbone :: backbone profile.String)</type>
                <appliedStereotypes/>
              </Attribute>
              <string>markerview.IGridColumn</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Interface>
    <Interface name="IGrid">
      <uuid>mitchell-55abb0f7-46c0-4e3e-a17b-17843db8f918</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>interface (backbone :: backbone profile.interface)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>markerview.IGrid</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Interface>
    <Interface name="IGridDataEntry">
      <uuid>mitchell-77c34e2f-2f30-47d7-8a84-ebe1f3c12b94</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>interface (backbone :: backbone profile.interface)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>markerview.IGridDataEntry</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Interface>
    <Interface name="IView">
      <uuid>mitchell-2deadf74-2789-40f6-921d-522efdc0a7d8</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>interface (backbone :: backbone profile.interface)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>markerview.IView</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Interface>
    <Component name="GridWidget" kind="NORMAL">
      <uuid>mitchell-928f8992-c20d-4f99-a1ed-8172d6cd64c5</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>markerview.GridWidget</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port name="w" uuid="mitchell-ae7d8851-aa23-45a6-820d-45c59be64800">
          <provides>mitchell-55abb0f7-46c0-4e3e-a17b-17843db8f918 (markerview.IGrid)</provides>
          <appliedStereotypes/>
        </Port>
        <Port name="r" uuid="mitchell-5a69f515-7667-4518-acaa-685dcb084563">
          <lowerBound>0</lowerBound>
          <upperBound>-1</upperBound>
          <requires>mitchell-a6d0516c-69cd-48f5-9ddc-86250d2a58a5 (markerview.IGridColumn)</requires>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
    <Component name="MarkerViewController" kind="NORMAL">
      <uuid>mitchell-a51d5827-94b4-4d89-85fa-cebc8279d8c6</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>markerview.MarkerViewController</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port name="v" uuid="mitchell-b4bc7984-2926-4357-9858-6e60980adcaa">
          <provides>mitchell-2deadf74-2789-40f6-921d-522efdc0a7d8 (markerview.IView)</provides>
          <appliedStereotypes/>
        </Port>
        <Port name="e" uuid="mitchell-f518ba7c-eee8-4eb9-b1c9-a0876cd941c0">
          <lowerBound>0</lowerBound>
          <upperBound>-1</upperBound>
          <requires>mitchell-77c34e2f-2f30-47d7-8a84-ebe1f3c12b94 (markerview.IGridDataEntry)</requires>
          <appliedStereotypes/>
        </Port>
        <Port name="w" uuid="mitchell-aabbcc29-950a-413c-8c46-2265d65fbe4a">
          <requires>mitchell-55abb0f7-46c0-4e3e-a17b-17843db8f918 (markerview.IGrid)</requires>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
    <Component name="MarkerView" kind="NORMAL">
      <uuid>mitchell-ca149df3-a2cd-45af-9f84-43d0dd12443d</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string></string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port name="view" uuid="mitchell-47c2462f-26c8-42fa-affc-dec543597549">
          <appliedStereotypes/>
        </Port>
        <Port uuid="mitchell-dc1baf79-44be-4cdc-b7a8-76e80f8e12bf">
          <appliedStereotypes/>
        </Port>
      </addedPorts>
      <addedParts>
        <Part name="c" uuid="mitchell-3216c66a-d6a9-41a9-b7bd-80a398d6f2d9">
          <type>mitchell-a51d5827-94b4-4d89-85fa-cebc8279d8c6 (markerview.MarkerViewController)</type>
          <appliedStereotypes/>
        </Part>
        <Part uuid="mitchell-9cb13f28-5436-4c3a-a6ae-9c3d24570886">
          <type>mitchell-928f8992-c20d-4f99-a1ed-8172d6cd64c5 (markerview.GridWidget)</type>
          <appliedStereotypes/>
        </Part>
      </addedParts>
      <addedConnectors>
        <Connector uuid="mitchell-70892544-985e-4cff-8858-b650c52bba22">
          <fromPort>mitchell-b4bc7984-2926-4357-9858-6e60980adcaa (markerview :: MarkerViewController.v)</fromPort>
          <fromPart>mitchell-3216c66a-d6a9-41a9-b7bd-80a398d6f2d9 (markerview :: MarkerView.c)</fromPart>
          <toPort>mitchell-47c2462f-26c8-42fa-affc-dec543597549 (markerview :: MarkerView.view)</toPort>
          <appliedStereotypes/>
        </Connector>
        <Connector uuid="mitchell-dcca4528-d2c7-4b93-ab83-f69dbad97fda">
          <fromPort>mitchell-aabbcc29-950a-413c-8c46-2265d65fbe4a (markerview :: MarkerViewController.w)</fromPort>
          <fromPart>mitchell-3216c66a-d6a9-41a9-b7bd-80a398d6f2d9 (markerview :: MarkerView.c)</fromPart>
          <toPort>mitchell-ae7d8851-aa23-45a6-820d-45c59be64800 (markerview :: GridWidget.w)</toPort>
          <toPart>mitchell-9cb13f28-5436-4c3a-a6ae-9c3d24570886 (markerview :: MarkerView.)</toPart>
          <appliedStereotypes/>
        </Connector>
        <Connector uuid="mitchell-60288576-9f8d-46da-b2de-9414b8410fbc">
          <fromPort>mitchell-f518ba7c-eee8-4eb9-b1c9-a0876cd941c0 (markerview :: MarkerViewController.e)</fromPort>
          <fromPart>mitchell-3216c66a-d6a9-41a9-b7bd-80a398d6f2d9 (markerview :: MarkerView.c)</fromPart>
          <toPort>mitchell-dc1baf79-44be-4cdc-b7a8-76e80f8e12bf (markerview :: MarkerView.)</toPort>
          <appliedStereotypes/>
        </Connector>
      </addedConnectors>
    </Component>
    <Component name="GridColumn" kind="NORMAL">
      <uuid>mitchell-bf49380a-5aa8-485e-87ff-e39b32639596</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>markerview.GridColumn</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedAttributes>
        <Attribute name="name">
          <uuid>mitchell-7efa975f-7e86-4f30-bb20-b7832bf1e37c</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
      <addedPorts>
        <Port name="g" uuid="mitchell-70e3aea2-c420-44a6-9bfb-ed5d2e30130f">
          <provides>mitchell-a6d0516c-69cd-48f5-9ddc-86250d2a58a5 (markerview.IGridColumn)</provides>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
  </elements>
</Package>