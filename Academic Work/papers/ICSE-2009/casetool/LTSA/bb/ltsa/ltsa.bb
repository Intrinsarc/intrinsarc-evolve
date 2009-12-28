<Package name="ltsa" stratum="true" relaxed="true">
  <uuid>superfly2-f41836cd-3e1d-4bea-b39e-2c96f57ae4a6</uuid>
  <parent>superfly2-53636547-a66c-42a2-a07d-1d8b48754d70</parent>
  <dependsOn>backbone</dependsOn>
  <elements>
    <Interface name="IAlphabetWindow">
      <uuid>superfly2-b2779a10-f9f8-49ce-8250-ccfb1215c079</uuid>
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
              <string>ui.IAlphabetWindow</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Interface>
    <Component name="AlphabetWindow" kind="NORMAL">
      <uuid>superfly2-1935f420-a007-4d81-a488-db547749c31a</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>ui.AlphabetWindow</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port name="alpha" uuid="superfly2-22e758e3-f977-4205-b8d5-bf97e5bfda93">
          <provides>superfly2-b2779a10-f9f8-49ce-8250-ccfb1215c079 (ltsa.IAlphabetWindow)</provides>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
    <Component name="AlphabetWindowFactory" kind="NORMAL">
      <uuid>superfly2-6c77e9c9-82be-4f2e-9f79-1db11234a625</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute name="factory">
                <uuid>factory</uuid>
                <type>boolean (backbone :: backbone profile.boolean)</type>
                <appliedStereotypes/>
              </Attribute>
              <string>true</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port uuid="superfly2-cba19f2f-a957-4147-b5bd-8acee064dc57">
          <appliedStereotypes/>
        </Port>
        <Port name="create" uuid="superfly2-7acf9bc1-c3a2-484f-8f84-47ae5289b0d5">
          <appliedStereotypes>
            <AppliedStereotype>
              <stereotype>create-port (backbone :: backbone profile.create-port)</stereotype>
            </AppliedStereotype>
          </appliedStereotypes>
          <createPort>true</createPort>
        </Port>
      </addedPorts>
      <addedParts>
        <Part uuid="superfly2-2e25bcc9-dc76-480c-9ef2-20fa6398fe52">
          <type>superfly2-1935f420-a007-4d81-a488-db547749c31a (ltsa.AlphabetWindow)</type>
          <appliedStereotypes/>
        </Part>
      </addedParts>
      <addedConnectors>
        <Connector uuid="superfly2-17efdf94-d952-47ad-b73e-b0480eb3a1a1">
          <fromPort>superfly2-22e758e3-f977-4205-b8d5-bf97e5bfda93 (ltsa :: AlphabetWindow.alpha)</fromPort>
          <fromPart>superfly2-2e25bcc9-dc76-480c-9ef2-20fa6398fe52 (ltsa :: AlphabetWindowFactory.)</fromPart>
          <toPort>superfly2-cba19f2f-a957-4147-b5bd-8acee064dc57 (ltsa :: AlphabetWindowFactory.)</toPort>
          <appliedStereotypes/>
        </Connector>
      </addedConnectors>
    </Component>
    <Component name="HPWindow" kind="NORMAL">
      <uuid>superfly2-09ca1921-83b5-4c98-a0f0-433f19442d6b</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>ui.HPWindow</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedAttributes>
        <Attribute name="title">
          <uuid>superfly2-4719a9d7-34a8-42eb-ade4-19e88325d7e8</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
      <addedPorts>
        <Port name="run" uuid="superfly2-fa886235-83c7-41d0-aaac-9b2f0765f9e7">
          <provides>IRun (backbone :: api.IRun)</provides>
          <appliedStereotypes/>
        </Port>
        <Port name="alpha" uuid="superfly2-5930dcd6-9dd4-4958-a0fb-4e64e17187c3">
          <lowerBound>0</lowerBound>
          <upperBound>1</upperBound>
          <requires>superfly2-b2779a10-f9f8-49ce-8250-ccfb1215c079 (ltsa.IAlphabetWindow)</requires>
          <appliedStereotypes/>
        </Port>
        <Port name="createAlpha" uuid="superfly2-75c17bfa-5d3d-4e71-bd2d-344cf31898c1">
          <lowerBound>0</lowerBound>
          <upperBound>1</upperBound>
          <requires>ICreate (backbone :: api.ICreate)</requires>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
    <Component name="LTSA" kind="NORMAL">
      <uuid>superfly2-9bb6591a-8d98-46d3-bae2-e1a9425c3365</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedAttributes>
        <Attribute name="title">
          <uuid>superfly2-2e2d9bc1-7f74-45c5-8386-437110429ac8</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <defaultValue>
            <Value>
              <literal>&quot;LTSA using Backbone&quot;</literal>
            </Value>
          </defaultValue>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
      <addedPorts>
        <Port name="run" uuid="superfly2-310d2e30-be0e-4089-b4cd-2f4ab5c30475">
          <appliedStereotypes/>
        </Port>
      </addedPorts>
      <addedParts>
        <Part uuid="superfly2-ed4cf3b5-f6b0-40de-8acc-5c21f3a512c7">
          <type>superfly2-09ca1921-83b5-4c98-a0f0-433f19442d6b (ltsa.HPWindow)</type>
          <slots>
            <Slot>
              <attribute>superfly2-4719a9d7-34a8-42eb-ade4-19e88325d7e8 (ltsa :: HPWindow.title)</attribute>
              <environmentAlias>superfly2-2e2d9bc1-7f74-45c5-8386-437110429ac8 (ltsa :: LTSA.title)</environmentAlias>
            </Slot>
          </slots>
          <appliedStereotypes/>
        </Part>
        <Part uuid="superfly2-4f7bd6ac-b7de-478f-b52d-5cc972db4c95">
          <type>superfly2-6c77e9c9-82be-4f2e-9f79-1db11234a625 (ltsa.AlphabetWindowFactory)</type>
          <appliedStereotypes/>
        </Part>
      </addedParts>
      <addedConnectors>
        <Connector uuid="superfly2-00462bec-1798-4426-a24a-35c9f973fa77">
          <fromPort>superfly2-fa886235-83c7-41d0-aaac-9b2f0765f9e7 (ltsa :: HPWindow.run)</fromPort>
          <fromPart>superfly2-ed4cf3b5-f6b0-40de-8acc-5c21f3a512c7 (ltsa :: LTSA.)</fromPart>
          <toPort>superfly2-310d2e30-be0e-4089-b4cd-2f4ab5c30475 (ltsa :: LTSA.run)</toPort>
          <appliedStereotypes/>
        </Connector>
        <Connector uuid="superfly2-1388c786-77d7-4de0-9ac6-dc8404b81c74">
          <fromPort>superfly2-5930dcd6-9dd4-4958-a0fb-4e64e17187c3 (ltsa :: HPWindow.alpha)</fromPort>
          <fromPart>superfly2-ed4cf3b5-f6b0-40de-8acc-5c21f3a512c7 (ltsa :: LTSA.)</fromPart>
          <toPort>superfly2-cba19f2f-a957-4147-b5bd-8acee064dc57 (ltsa :: AlphabetWindowFactory.)</toPort>
          <toPart>superfly2-4f7bd6ac-b7de-478f-b52d-5cc972db4c95 (ltsa :: LTSA.)</toPart>
          <appliedStereotypes/>
        </Connector>
        <Connector uuid="superfly2-4809dc79-34c7-454a-81e7-061118ba6a9a">
          <fromPort>superfly2-75c17bfa-5d3d-4e71-bd2d-344cf31898c1 (ltsa :: HPWindow.createAlpha)</fromPort>
          <fromPart>superfly2-ed4cf3b5-f6b0-40de-8acc-5c21f3a512c7 (ltsa :: LTSA.)</fromPart>
          <toPort>superfly2-7acf9bc1-c3a2-484f-8f84-47ae5289b0d5 (ltsa :: AlphabetWindowFactory.create)</toPort>
          <toPart>superfly2-4f7bd6ac-b7de-478f-b52d-5cc972db4c95 (ltsa :: LTSA.)</toPart>
          <appliedStereotypes/>
        </Connector>
      </addedConnectors>
    </Component>
  </elements>
</Package>