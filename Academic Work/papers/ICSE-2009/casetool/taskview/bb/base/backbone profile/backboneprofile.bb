<Package name="backbone profile" relaxed="true">
  <uuid>backbone-profile</uuid>
  <parent>backbone</parent>
  <elements>
    <Component name="String" kind="PRIMITIVE">
      <uuid>String</uuid>
    </Component>
    <Component name="backbone-element" kind="STEREOTYPE">
      <uuid>backbone-element</uuid>
      <addedAttributes>
        <Attribute name="implementation-class">
          <uuid>implementation-class</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
    </Component>
    <Component name="primitive-type" kind="STEREOTYPE">
      <uuid>primitive-type</uuid>
      <resembles>backbone-element (backbone :: backbone profile.backbone-element)</resembles>
    </Component>
    <Component name="boolean" kind="PRIMITIVE">
      <uuid>boolean</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component[2]/addedAttributes/Attribute"/>
              <string>java.lang.Boolean</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="port" kind="STEREOTYPE">
      <uuid>port</uuid>
      <resembles>backbone-element (backbone :: backbone profile.backbone-element)</resembles>
      <addedAttributes>
        <Attribute name="callbacks">
          <uuid>callbacks</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
    </Component>
    <Component name="common-component" kind="STEREOTYPE">
      <uuid>common-component</uuid>
      <resembles>backbone-element (backbone :: backbone profile.backbone-element)</resembles>
      <addedAttributes>
        <Attribute name="navigable">
          <uuid>navigable</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="protocols">
          <uuid>protocols</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
    </Component>
    <Component name="int" kind="PRIMITIVE">
      <uuid>int</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>primitive-type (backbone :: backbone profile.primitive-type)</stereotype>
          <properties>
            <entry>
              <Attribute reference="../../../../../../Component[2]/addedAttributes/Attribute"/>
              <string>java.lang.Integer</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
    </Component>
    <Component name="trace" kind="STEREOTYPE">
      <uuid>trace</uuid>
    </Component>
    <Component name="backbone-overriden-slot" kind="STEREOTYPE">
      <uuid>backbone-overriden-slot</uuid>
      <addedAttributes>
        <Attribute name="overriddenSlotText">
          <uuid>overriddenSlotText</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="overriddenSlotAlias">
          <uuid>overriddenSlotAlias</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
    </Component>
    <Component name="backbone-delta" kind="STEREOTYPE">
      <uuid>backbone-delta</uuid>
    </Component>
    <Component name="create-port" kind="STEREOTYPE">
      <uuid>create-port</uuid>
      <resembles>port (backbone :: backbone profile.port)</resembles>
    </Component>
    <Component name="stratum" kind="STEREOTYPE">
      <uuid>stratum</uuid>
      <addedAttributes>
        <Attribute name="destructive">
          <uuid>destructive</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="relaxed">
          <uuid>relaxed</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="preamble">
          <uuid>preamble</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="backboneJavaFolder">
          <uuid>backboneJavaFolder</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="backboneJavaSuppress">
          <uuid>backboneJavaSuppress</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="backboneSourceFolder">
          <uuid>backboneSourceFolder</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="backboneSourceSuppress">
          <uuid>backboneSourceSuppress</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="backboneClasspath">
          <uuid>backboneClasspath</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
    </Component>
    <Component name="component" kind="STEREOTYPE">
      <uuid>component</uuid>
      <resembles>common-component (backbone :: backbone profile.common-component)</resembles>
      <addedAttributes>
        <Attribute name="cluster">
          <uuid>cluster</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="factory">
          <uuid>factory</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="placeholder">
          <uuid>placeholder</uuid>
          <type>boolean (backbone :: backbone profile.boolean)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
    </Component>
    <Component name="interface" kind="STEREOTYPE">
      <uuid>interface</uuid>
      <resembles>backbone-element (backbone :: backbone profile.backbone-element)</resembles>
    </Component>
  </elements>
</Package>