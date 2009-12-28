<Package name="standard profile" relaxed="true">
  <uuid>standard-profile</uuid>
  <parent>backbone</parent>
  <dependsOn>primitives (backbone.primitive types)</dependsOn>
  <elements>
    <Component name="error" kind="STEREOTYPE">
      <uuid>error</uuid>
      <addedAttributes>
        <Attribute name="error-description">
          <uuid>error-description</uuid>
          <type>String (backbone :: backbone profile.String)</type>
          <appliedStereotypes/>
        </Attribute>
      </addedAttributes>
    </Component>
  </elements>
</Package>