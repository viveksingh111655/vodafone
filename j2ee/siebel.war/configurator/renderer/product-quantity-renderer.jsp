<%@ taglib prefix="dsp"
  uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0"%>

<dsp:page>
  <!-- product-quantity-renderer-1.jsp -->
  <dsp:getvalueof param="node" var="uiNode" />

    
      <tr>
        <td ${uiNode.htmlAttributes}>Quantity  <dsp:valueof value="${uiNode.productConfigInstance.quantity}" /></td>
      </tr>
  
</dsp:page>