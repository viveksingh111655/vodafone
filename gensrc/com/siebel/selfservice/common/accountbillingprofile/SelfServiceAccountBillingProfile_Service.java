
package com.siebel.selfservice.common.accountbillingprofile;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1-b03-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "SelfServiceAccountBillingProfile", targetNamespace = "http://www.siebel.com/SelfService/Common/AccountBillingProfile", wsdlLocation = "http://localhost/wsdl/SelfServiceAccountBillingProfile.wsdl")
public class SelfServiceAccountBillingProfile_Service
    extends Service
{

    private final static URL SELFSERVICEACCOUNTBILLINGPROFILE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://localhost/wsdl/SelfServiceAccountBillingProfile.wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SELFSERVICEACCOUNTBILLINGPROFILE_WSDL_LOCATION = url;
    }

    public SelfServiceAccountBillingProfile_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SelfServiceAccountBillingProfile_Service() {
        super(SELFSERVICEACCOUNTBILLINGPROFILE_WSDL_LOCATION, new QName("http://www.siebel.com/SelfService/Common/AccountBillingProfile", "SelfServiceAccountBillingProfile"));
    }

    /**
     * 
     * @return
     *     returns SelfServiceAccountBillingProfile
     */
    @WebEndpoint(name = "SelfServiceAccountBillingProfilePort")
    public SelfServiceAccountBillingProfile getSelfServiceAccountBillingProfilePort() {
        return (SelfServiceAccountBillingProfile)super.getPort(new QName("http://www.siebel.com/SelfService/Common/AccountBillingProfile", "SelfServiceAccountBillingProfilePort"), SelfServiceAccountBillingProfile.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SelfServiceAccountBillingProfile
     */
    @WebEndpoint(name = "SelfServiceAccountBillingProfilePort")
    public SelfServiceAccountBillingProfile getSelfServiceAccountBillingProfilePort(WebServiceFeature... features) {
        return (SelfServiceAccountBillingProfile)super.getPort(new QName("http://www.siebel.com/SelfService/Common/AccountBillingProfile", "SelfServiceAccountBillingProfilePort"), SelfServiceAccountBillingProfile.class, features);
    }

}
