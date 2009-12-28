<Package name="primitive types" relaxed="true">
  <uuid>primitives</uuid>
  <parent>backbone</parent>
  <dependsOn>backbone-profile (backbone.backbone profile)</dependsOn>
  <elements>
    <Component name="Interval" kind="PRIMITIVE">
      <uuid>Interval</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute name="implementation-class">
                <uuid>implementation-class</uuid>
                <type>String (backbone :: backbone profile.String)</type>
                <appliedStereotypes/>
              </Attribute>
              <string>java.lang.Long</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="DateTime" kind="PRIMITIVE">
      <uuid>DateTime</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>java.util.Date</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="Time" kind="PRIMITIVE">
      <uuid>Time</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>java.lang.Long</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="Date" kind="PRIMITIVE">
      <uuid>Date</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>java.util.Date</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="double" kind="PRIMITIVE">
      <uuid>double</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>java.lang.Double</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="float" kind="PRIMITIVE">
      <uuid>float</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>java.lang.Float</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="long" kind="PRIMITIVE">
      <uuid>long</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>java.lang.Long</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="Color" kind="PRIMITIVE">
      <uuid>Color</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>java.awt.Color</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="type" kind="PRIMITIVE">
      <uuid>type</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component/appliedStereotypes/AppliedStereotype/properties/entry/Attribute"/>
              <string>Type</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
  </elements>
</Package>