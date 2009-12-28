<Package name="taskview" stratum="true" relaxed="true">
  <uuid>mitchell-7a581af4-4792-46e9-84e7-ec0e5c44cf64</uuid>
  <parent>mitchell-967d25dd-dd5e-4584-b12c-511c1a333838</parent>
  <dependsOn>mitchell-370c1e35-3f8d-4963-aa09-fd946c332a9e (markerview)</dependsOn>
  <elements>
    <Component name="DemoController" kind="NORMAL">
      <uuid>mitchell-6ca4c5e6-8af5-46ef-98ca-4f83ac531a3f</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute name="implementation-class">
                <uuid>implementation-class</uuid>
                <type>String (backbone :: backbone profile.String)</type>
                <appliedStereotypes/>
              </Attribute>
              <string>taskview.DemoController</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port name="v" uuid="mitchell-df50f740-bc79-4a9e-907a-47cb14fefc4e">
          <requires>mitchell-2deadf74-2789-40f6-921d-522efdc0a7d8 (markerview.IView)</requires>
          <appliedStereotypes/>
        </Port>
        <Port name="main" uuid="mitchell-52060cb3-5930-4f00-8130-03850460f629">
          <provides>IRun (backbone :: api.IRun)</provides>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
    <Component name="DummyDataEntry" kind="NORMAL">
      <uuid>mitchell-007e6619-d0bf-437c-914f-867d23d4c165</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>taskview.DummyDataEntry</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port name="g" uuid="mitchell-b9dfeb00-5b65-45ef-896c-51bd748292b0">
          <provides>mitchell-77c34e2f-2f30-47d7-8a84-ebe1f3c12b94 (markerview.IGridDataEntry)</provides>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
    <Component name="TaskView" kind="NORMAL">
      <uuid>mitchell-7800d64e-56b9-45fa-9149-dd638ca49113</uuid>
      <resembles>mitchell-ca149df3-a2cd-45af-9f84-43d0dd12443d (markerview.MarkerView)</resembles>
      <addedParts>
        <Part uuid="mitchell-ec5b0ba5-76e1-4779-867d-8e7f1ab6aae7">
          <type>mitchell-bf49380a-5aa8-485e-87ff-e39b32639596 (markerview.GridColumn)</type>
          <slots>
            <Slot>
              <attribute>mitchell-7efa975f-7e86-4f30-bb20-b7832bf1e37c (markerview :: GridColumn.name)</attribute>
              <value>
                <Value>
                  <literal>&quot;Description&quot;</literal>
                </Value>
              </value>
            </Slot>
          </slots>
          <appliedStereotypes/>
        </Part>
        <Part uuid="mitchell-b3bef065-c1f8-4867-af93-df05d7420602">
          <type>mitchell-bf49380a-5aa8-485e-87ff-e39b32639596 (markerview.GridColumn)</type>
          <slots>
            <Slot>
              <attribute>mitchell-7efa975f-7e86-4f30-bb20-b7832bf1e37c (markerview :: GridColumn.name)</attribute>
              <value>
                <Value>
                  <literal>&quot;Location&quot;</literal>
                </Value>
              </value>
            </Slot>
          </slots>
          <appliedStereotypes/>
        </Part>
      </addedParts>
      <addedConnectors>
        <Connector uuid="mitchell-d4fecec3-6141-4a43-ab4a-0d0adfe95793">
          <fromPort>mitchell-70e3aea2-c420-44a6-9bfb-ed5d2e30130f (markerview :: GridColumn.g)</fromPort>
          <fromPart>mitchell-ec5b0ba5-76e1-4779-867d-8e7f1ab6aae7 (taskview :: TaskView.)</fromPart>
          <toPort>mitchell-5a69f515-7667-4518-acaa-685dcb084563 (markerview :: GridWidget.r)</toPort>
          <toPart>mitchell-9cb13f28-5436-4c3a-a6ae-9c3d24570886 (markerview :: MarkerView.)</toPart>
          <appliedStereotypes/>
        </Connector>
        <Connector uuid="mitchell-eef8077f-e51d-47f1-a71e-45123e939eed">
          <fromPort>mitchell-70e3aea2-c420-44a6-9bfb-ed5d2e30130f (markerview :: GridColumn.g)</fromPort>
          <fromPart>mitchell-b3bef065-c1f8-4867-af93-df05d7420602 (taskview :: TaskView.)</fromPart>
          <toPort>mitchell-5a69f515-7667-4518-acaa-685dcb084563 (markerview :: GridWidget.r)</toPort>
          <toPart>mitchell-9cb13f28-5436-4c3a-a6ae-9c3d24570886 (markerview :: MarkerView.)</toPart>
          <appliedStereotypes/>
        </Connector>
      </addedConnectors>
    </Component>
    <Component name="Main" kind="NORMAL">
      <uuid>mitchell-19d67557-0fb3-4545-bf64-aa3bdb2b7b30</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port name="main" uuid="mitchell-b05972d7-c13f-4aa1-9218-998c8bdb8fee">
          <appliedStereotypes/>
        </Port>
      </addedPorts>
      <addedParts>
        <Part uuid="mitchell-85430714-479f-4220-9e52-e87ddac7d3dd">
          <type>mitchell-7800d64e-56b9-45fa-9149-dd638ca49113 (taskview.TaskView)</type>
          <appliedStereotypes/>
        </Part>
        <Part uuid="mitchell-326d93a4-4372-44fc-9822-c276acb9443f">
          <type>mitchell-007e6619-d0bf-437c-914f-867d23d4c165 (taskview.DummyDataEntry)</type>
          <appliedStereotypes/>
        </Part>
        <Part uuid="mitchell-636dcbc8-8c7b-4990-8037-883d67a409a1">
          <type>mitchell-6ca4c5e6-8af5-46ef-98ca-4f83ac531a3f (taskview.DemoController)</type>
          <appliedStereotypes/>
        </Part>
      </addedParts>
      <addedConnectors>
        <Connector uuid="mitchell-2c093364-ac59-48f0-b801-f78d4f7b3cd3">
          <fromPort>mitchell-b9dfeb00-5b65-45ef-896c-51bd748292b0 (taskview :: DummyDataEntry.g)</fromPort>
          <fromPart>mitchell-326d93a4-4372-44fc-9822-c276acb9443f (taskview :: Main.)</fromPart>
          <toPort>mitchell-dc1baf79-44be-4cdc-b7a8-76e80f8e12bf (markerview :: MarkerView.)</toPort>
          <toPart>mitchell-85430714-479f-4220-9e52-e87ddac7d3dd (taskview :: Main.)</toPart>
          <appliedStereotypes/>
        </Connector>
        <Connector uuid="mitchell-da665f55-1d6f-4a38-9be4-b9dfa1dd87da">
          <fromPort>mitchell-47c2462f-26c8-42fa-affc-dec543597549 (markerview :: MarkerView.view)</fromPort>
          <fromPart>mitchell-85430714-479f-4220-9e52-e87ddac7d3dd (taskview :: Main.)</fromPart>
          <toPort>mitchell-df50f740-bc79-4a9e-907a-47cb14fefc4e (taskview :: DemoController.v)</toPort>
          <toPart>mitchell-636dcbc8-8c7b-4990-8037-883d67a409a1 (taskview :: Main.)</toPart>
          <appliedStereotypes/>
        </Connector>
        <Connector uuid="mitchell-00a09f8b-993c-487b-a861-568e67bed82e">
          <fromPort>mitchell-52060cb3-5930-4f00-8130-03850460f629 (taskview :: DemoController.main)</fromPort>
          <fromPart>mitchell-636dcbc8-8c7b-4990-8037-883d67a409a1 (taskview :: Main.)</fromPart>
          <toPort>mitchell-b05972d7-c13f-4aa1-9218-998c8bdb8fee (taskview :: Main.main)</toPort>
          <appliedStereotypes/>
        </Connector>
      </addedConnectors>
    </Component>
  </elements>
</Package>