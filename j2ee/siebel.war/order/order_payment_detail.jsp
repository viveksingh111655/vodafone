<%@ taglib prefix="dsp" uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<dsp:page>

  <dsp:getvalueof param="orderPaymentDetail" var="orderPaymentDetail" />  

  Method - ${orderPaymentDetail.paymentType}<br>
  Card Number - ${orderPaymentDetail.creditCardNumberDisplay}<br>
  Amount - $${orderPaymentDetail.transactionAmount}
  
  <p>

</dsp:page>