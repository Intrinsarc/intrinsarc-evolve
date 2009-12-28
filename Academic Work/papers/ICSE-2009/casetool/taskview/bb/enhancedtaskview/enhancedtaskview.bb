<Package name="enhanced
taskview" stratum="true" destructive="true" relaxed="true">
  <uuid>mitchell-7af6620a-01d6-44d2-a383-c1d50f694ea3</uuid>
  <parent>mitchell-967d25dd-dd5e-4584-b12c-511c1a333838</parent>
  <dependsOn>mitchell-7a581af4-4792-46e9-84e7-ec0e5c44cf64 (taskview)</dependsOn>
  <elements>
    <Component name="EnhancedMarkerViewController" kind="NORMAL">
      <uuid>mitchell-212d3019-566d-42c9-86e2-9cf4203d0e0c</uuid>
      <appliedStereotypes>
        <AppliedStereotype>
          <stereotype>component (backbone :: backbone profile.component)</stereotype>
          <properties>
            <entry>
              <Attribute name="navigable">
                <uuid>navigable</uuid>
                <type>boolean (backbone :: backbone profile.boolean)</type>
                <appliedStereotypes/>
              </Attribute>
              <string>true</string>
            </entry>
            <entry>
              <Attribute name="implementation-class">
                <uuid>implementation-class</uuid>
                <type>String (backbone :: backbone profile.String)</type>
                <appliedStereotypes/>
              </Attribute>
              <string>enhancedtaskview.EnhancedMarkerViewController</string>
            </entry>
          </properties>
        </AppliedStereotype>
      </appliedStereotypes>
      <addedPorts>
        <Port name="view" uuid="mitchell-820923fb-376c-4e06-aeab-ab575ee8dff9">
          <provides>mitchell-2deadf74-2789-40f6-921d-522efdc0a7d8 (markerview.IView)</provides>
          <appliedStereotypes/>
        </Port>
        <Port name="e" uuid="mitchell-a26a2205-1690-42de-a160-307f269e58d1">
          <lowerBound>0</lowerBound>
          <upperBound>-1</upperBound>
          <requires>mitchell-77c34e2f-2f30-47d7-8a84-ebe1f3c12b94 (markerview.IGridDataEntry)</requires>
          <appliedStereotypes/>
        </Port>
        <Port name="w" uuid="mitchell-b87e40a1-764a-4a90-b8e6-1abbc80eadd7">
          <requires>mitchell-55abb0f7-46c0-4e3e-a17b-17843db8f918 (markerview.IGrid)</requires>
          <appliedStereotypes/>
        </Port>
      </addedPorts>
    </Component>
    <Component name="mitchell-463c39e6-24d5-44f5-a000-3dfa9a60125f" kind="NORMAL">
      <uuid>mitchell-463c39e6-24d5-44f5-a000-3dfa9a60125f</uuid>
      <substitutes>mitchell-7800d64e-56b9-45fa-9149-dd638ca49113 (taskview.TaskView)</substitutes>
      <resembles>mitchell-7800d64e-56b9-45fa-9149-dd638ca49113 (taskview.TaskView)</resembles>
      <replacedParts>
        <ReplacedPart>
          <original>mitchell-3216c66a-d6a9-41a9-b7bd-80a398d6f2d9</original>
          <Part name="c" uuid="mitchell-f15843c0-9e87-4ad1-a80a-25375e0fb753">
            <type>mitchell-212d3019-566d-42c9-86e2-9cf4203d0e0c (enhanced
taskview.EnhancedMarkerViewController)</type>
            <remaps>
              <remap>
                <uuid>mitchell-aabbcc29-950a-413c-8c46-2265d65fbe4a</uuid>
                <port>mitchell-b87e40a1-764a-4a90-b8e6-1abbc80eadd7 (enhanced
taskview :: EnhancedMarkerViewController.w)</port>
              </remap>
              <remap>
                <uuid>mitchell-f518ba7c-eee8-4eb9-b1c9-a0876cd941c0</uuid>
                <port>mitchell-a26a2205-1690-42de-a160-307f269e58d1 (enhanced
taskview :: EnhancedMarkerViewController.e)</port>
              </remap>
              <remap>
                <uuid>mitchell-b4bc7984-2926-4357-9858-6e60980adcaa</uuid>
                <port>mitchell-820923fb-376c-4e06-aeab-ab575ee8dff9 (enhanced
taskview :: EnhancedMarkerViewController.view)</port>
              </remap>
            </remaps>
            <appliedStereotypes/>
          </Part>
        </ReplacedPart>
      </replacedParts>
      <addedParts>
        <Part uuid="mitchell-ffcc4a47-0895-41c3-a284-613cb1834ade">
          <type>mitchell-bf49380a-5aa8-485e-87ff-e39b32639596 (markerview.GridColumn)</type>
          <slots>
            <Slot>
              <attribute>mitchell-7efa975f-7e86-4f30-bb20-b7832bf1e37c (markerview :: GridColumn.name)</attribute>
              <value>
                <Value>
                  <literal>&quot;Assigned to&quot;</literal>
                </Value>
              </value>
            </Slot>
          </slots>
          <appliedStereotypes/>
        </Part>
      </addedParts>
      <addedConnectors>
        <Connector uuid="mitchell-a6127294-f64b-4289-9e84-e4e04ab61f55">
          <fromPort>mitchell-70e3aea2-c420-44a6-9bfb-ed5d2e30130f (markerview :: GridColumn.g)</fromPort>
          <fromPart>mitchell-ffcc4a47-0895-41c3-a284-613cb1834ade (enhanced
taskview :: .)</fromPart>
          <toPort>mitchell-5a69f515-7667-4518-acaa-685dcb084563 (markerview :: GridWidget.r)</toPort>
          <toPart>mitchell-9cb13f28-5436-4c3a-a6ae-9c3d24570886 (markerview :: MarkerView.)</toPart>
          <appliedStereotypes/>
        </Connector>
      </addedConnectors>
    </Component>
  </elements>
</Package>