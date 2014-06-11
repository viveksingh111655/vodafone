<%@ taglib prefix="dsp"
  uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<dsp:page>
  <!--  rel-opt-multi-select-multi-choice-select.jsp -->
  <dsp:getvalueof param="node" var="uiNode" /> 
  <dsp:getvalueof param="index" var="index" /> 
  <tr>
  <td ><dsp:valueof value="${uiNode.displayName}" /></td>
  </tr>
  <dsp:include page="displayError.jsp">
    <dsp:param name="node" value="${uiNode}" />
  </dsp:include>
  <tr>
  <td>
    <select
      onchange="updateUINode(${index}, this.value);">
      <option>--Please select--</option>
      <c:forEach items="${uiNode.domainProducts}" var="domainProduct"
        varStatus="domainStatus">
        <option value="${domainProduct.id}">
          <dsp:valueof value="${domainProduct.name}" />
        </option>
      </c:forEach>
      </select>
  </td>  
  </tr>
  
  <tr>
    <td><h4>Currently Selected</h4></td>
  </tr>
  
  
</dsp:page>