<Package name="documentation profile" relaxed="true">
  <uuid>documentation-profile</uuid>
  <parent>backbone</parent>
  <dependsOn>primitives (backbone.primitive types)</dependsOn>
  <elements>
    <Component name="documentation-see-also" kind="STEREOTYPE">
      <uuid>documentation-see-also</uuid>
    </Component>
    <Component name="documentation-figure" kind="STEREOTYPE">
      <uuid>documentation-figure</uuid>
    </Component>
    <Component name="documentation-included" kind="STEREOTYPE">
      <uuid>documentation-included</uuid>
    </Component>
    <Component name="documentation-top" kind="STEREOTYPE">
      <uuid>documentation-top</uuid>
      <addedAttributes>
        <Attribute name="documentName">
          <uuid>documentName</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="owner">
          <uuid>owner</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="email">
          <uuid>email</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="copyrightYears">
          <uuid>copyrightYears</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="numberOfSpacesForPadding">
          <uuid>numberOfSpacesForPadding</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="pageTitlePrefix">
          <uuid>pageTitlePrefix</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
        <Attribute name="siteIndex">
          <uuid>siteIndex</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
    </Component>
  </elements>
</Package>