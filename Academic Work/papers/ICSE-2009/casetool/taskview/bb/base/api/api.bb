<Package name="api" relaxed="true">
  <uuid>api</uuid>
  <parent>backbone</parent>
  <dependsOn>primitives (backbone.primitive types)</dependsOn>
  <elements>
    <Interface name="ICreate">
      <uuid>ICreate</uuid>
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
              <string>com.hopstepjump.backbone.api.ICreate</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Interface>
    <Component name="Creator" kind="NORMAL">
      <uuid>Creator</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>com.hopstepjump.backbone.api.Creator</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedAttributes>
        <Attribute name="factory">
          <uuid>factory</uuid>
          <type>int (backbone :: backbone profile.int)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
      <addedPorts>
        <Port name="create" uuid="createPort">
          <provides>ICreate (backbone :: api.ICreate)</provides>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
    <Interface name="ILifecycle">
      <uuid>ILifecycle</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>interface (backbone :: backbone profile.interface)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>com.hopstepjump.backbone.api.ILifecycle</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Interface>
    <Interface name="IRun">
      <uuid>IRun</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>interface (backbone :: backbone profile.interface)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Interface/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>com.hopstepjump.backbone.api.IRun</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Interface>
  </elements>
</Package>