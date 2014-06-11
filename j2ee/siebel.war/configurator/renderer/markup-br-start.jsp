<%@ taglib prefix="dsp"
  uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<dsp:page>
  <!--  markup-br-start.jsp -->
  <dsp:getvalueof param="node" var="uiNode" />
  <br ${uiNode.htmlAttributes}>
  

</dsp:page>