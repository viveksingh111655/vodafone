package atg.siebel.configurator;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to store configuration command parameters and their values
 *
 * @author Bernard Brady
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/ConfigurationParams.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class ConfigurationParams implements Serializable {

    private static final long serialVersionUID = -324234324324l;

    public enum ContextParameters {
        QUOTE,
        PRODUCT_CONFIG_INSTANCE,
        ACCOUNT_NAME, 
        ACCOUNT_ID, 
        ORGANIZATION_ID, 
        CFG_ROOT_PRODUCT_ID, 
        CFG_ROOT_INTEGRATION_ID, 
        CFG_BUNDLE_INTEGRATION_ID, 
        CFG_PART_OF_BUNDLE, 
        LOCALE, 
        CATALOG_KEY, 
        QUOTE_PARENT_ITEM, 
        PRODUCT_ID,
        NEW_ATTRIBUTES,
        RELATIONSHIP_ID, 
        TARGET_INSTANCE, 
        RELATIONSHIP_OBJECT, 
        QUANTITY, 
        CHILD_CONTEXT_LIST
    }

    //-------------------------------------
    // Properties
    //-------------------------------------
    //-------------------------------------
    // property: param
    private final ConcurrentHashMap<ContextParameters, Object> map = 
        new ConcurrentHashMap<ContextParameters, Object>();

    public Object getParam(ContextParameters param) {
        return map.get(param);
    }

    public void setParam(ContextParameters param, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        map.put(param, object);
    }

}
