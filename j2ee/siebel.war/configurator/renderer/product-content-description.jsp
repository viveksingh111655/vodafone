<%@ taglib prefix="dsp"
  uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0"%>

<dsp:page>
  <!-- product-content-description.jsp -->
	<dsp:getvalueof param="node" var="uiNode" />

		
			<tr>
				<td><dsp:valueof value="${uiNode.productConfigInstance.description}" /></td>
			</tr>
	
</dsp:page>