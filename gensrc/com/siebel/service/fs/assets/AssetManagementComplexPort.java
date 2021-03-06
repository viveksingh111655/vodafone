
package com.siebel.service.fs.assets;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1-b03-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "AssetManagementComplexPort", targetNamespace = "http://www.siebel.com/Service/FS/Assets")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.siebel.xml.asset_management_complex_io.id.ObjectFactory.class,
    com.siebel.xml.asset_management_complex_io.query.ObjectFactory.class,
    com.siebel.service.fs.assets.ObjectFactory.class,
    com.siebel.xml.asset_management_complex_io.data.ObjectFactory.class
})
public interface AssetManagementComplexPort {


    /**
     * 
     * @param assetManagementComplexInsertInput
     * @return
     *     returns com.siebel.service.fs.assets.AssetManagementComplexInsertOutput
     */
    @WebMethod(operationName = "AssetManagementComplexInsert", action = "document/http://www.siebel.com/Service/FS/Assets:AssetManagementComplexInsert")
    @WebResult(name = "AssetManagementComplexInsert_Output", targetNamespace = "http://www.siebel.com/Service/FS/Assets", partName = "AssetManagementComplexInsert_Output")
    public AssetManagementComplexInsertOutput assetManagementComplexInsert(
        @WebParam(name = "AssetManagementComplexInsert_Input", targetNamespace = "http://www.siebel.com/Service/FS/Assets", partName = "AssetManagementComplexInsert_Input")
        AssetManagementComplexInsertInput assetManagementComplexInsertInput);

    /**
     * 
     * @param assetManagementComplexUpdateInput
     * @return
     *     returns com.siebel.service.fs.assets.AssetManagementComplexUpdateOutput
     */
    @WebMethod(operationName = "AssetManagementComplexUpdate", action = "document/http://www.siebel.com/Service/FS/Assets:AssetManagementComplexUpdate")
    @WebResult(name = "AssetManagementComplexUpdate_Output", targetNamespace = "http://www.siebel.com/Service/FS/Assets", partName = "AssetManagementComplexUpdate_Output")
    public AssetManagementComplexUpdateOutput assetManagementComplexUpdate(
        @WebParam(name = "AssetManagementComplexUpdate_Input", targetNamespace = "http://www.siebel.com/Service/FS/Assets", partName = "AssetManagementComplexUpdate_Input")
        AssetManagementComplexUpdateInput assetManagementComplexUpdateInput);

    /**
     * 
     * @param assetManagementComplexQueryPageInput
     * @return
     *     returns com.siebel.service.fs.assets.AssetManagementComplexQueryPageOutput
     */
    @WebMethod(operationName = "AssetManagementComplexQueryPage", action = "document/http://www.siebel.com/Service/FS/Assets:AssetManagementComplexQueryPage")
    @WebResult(name = "AssetManagementComplexQueryPage_Output", targetNamespace = "http://www.siebel.com/Service/FS/Assets", partName = "AssetManagementComplexQueryPage_Output")
    public AssetManagementComplexQueryPageOutput assetManagementComplexQueryPage(
        @WebParam(name = "AssetManagementComplexQueryPage_Input", targetNamespace = "http://www.siebel.com/Service/FS/Assets", partName = "AssetManagementComplexQueryPage_Input")
        AssetManagementComplexQueryPageInput assetManagementComplexQueryPageInput);

}
