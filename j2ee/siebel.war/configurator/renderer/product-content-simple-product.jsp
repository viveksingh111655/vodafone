<%@ taglib prefix="dsp"
  uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<dsp:page>
  <!-- product-content-simple-product.jsp -->
  <dsp:getvalueof param="node" var="uiNode" />
  
  <tr>
  <td>
  <div>
  <table ${uiNode.htmlAttributes}>
  
  <tr>
  <td>
	<div><b><dsp:valueof value="${uiNode.productConfigInstance.displayName}" /></b></div>
	</td>
	</tr>
	
	<tr>
	<td>	
	<div><dsp:valueof value="${uiNode.productConfigInstance.description}" /></div>
  </td>
  </tr>
  
  </table>
  </div>
  </td>
  
  </tr>

</dsp:page>