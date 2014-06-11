<%@ taglib prefix="dsp"
  uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0"%>

<dsp:page>
  <!-- product-content-heading.jsp -->
  <dsp:getvalueof param="node" var="uiNode" />
  
    <tr><td ${uiNode.htmlAttributes}>
      <b><dsp:valueof value="${uiNode.productConfigInstance.displayName}" /></b>
    </td></tr>
</dsp:page>