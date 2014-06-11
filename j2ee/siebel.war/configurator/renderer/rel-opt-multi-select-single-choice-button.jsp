<%@ taglib prefix="dsp"
  uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<dsp:page>
  <!-- rel-opt-multi-select-single-choice-button.jsp -->
  <dsp:getvalueof param="node" var="uiNode" /> 
  <dsp:getvalueof param="index" var="index" /> 
  
  <dsp:include page="displayError.jsp">
    <dsp:param name="node" value="${uiNode}" />
  </dsp:include>
    
  <tr>
  <td>
    <button ${uiNode.htmlAttributes}
    onclick="updateUINode(${index}, '${uiNode.domainProducts[0].id}');"
    type="button">Add a ${uiNode.domainProducts[0].name}</button>
  </td>
  </tr>
</dsp:page>