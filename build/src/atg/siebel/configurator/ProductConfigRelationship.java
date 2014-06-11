package atg.siebel.configurator;

import java.text.MessageFormat;

import atg.core.util.StringUtils;
import atg.siebel.configurator.ConfigurationParams.ContextParameters;
import atg.siebel.configurator.Constants.ConfigurationCommandType;

public class ProductConfigRelationship extends AbstractRelationship {

	// -------------------------------------
	/** Class version string */
	public static final String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/ProductConfigRelationship.java#1 $$Change: 842106 $";

	// -------------------------------------
	// Constants
	// -------------------------------------

	// -------------------------------------
	// Member variables
	// -------------------------------------
	
	// -------------------------------------
	// Properties
	// -------------------------------------
	protected String mError=null;
	
	public String getError()
	{
	  return mError;
	}
	public void setError(String pError)
	{
	  mError = pError;
	}


	public ProductConfigRelationship(
			BaseConfigInstance pInstance,
			String pId,
			String pMinQuantity, 
			String pMaxQuantity,
			String pDefaultQuantity) {

		super(pInstance, pId, pMinQuantity, pMaxQuantity, pDefaultQuantity);
		setCardinality(pMinQuantity, pMaxQuantity, pDefaultQuantity);
	}
	
	
	public ProductConfigRelationship(
			BaseConfigInstance pInstance,
			String pId,
			Integer pMinQuantity, 
			Integer pMaxQuantity,
			Integer pDefaultQuantity) {

		super(pInstance, pId, pMinQuantity, pMaxQuantity, pDefaultQuantity);
	}

	
  /**
   * @param pProductId
   * @return
   * @throws ConfiguratorException
   */
  public ConfigurationContext createAddProductContext(String pProductId)
      throws ConfiguratorException {
    return createAddProductContext(pProductId, 1);
  }

  
  /**
   * @param pProductId
   * @param pQuantity
   * @return
   * @throws ConfiguratorException
   */
  public ConfigurationContext createAddProductContext(String pProductId,
      int pQuantity) throws ConfiguratorException {

    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered createAddProductContext() : pProductId == "
          + pProductId + " : pQuantity == " + pQuantity);
    }

    if (StringUtils.isEmpty(pProductId)) {
      if (mLogger.isLoggingDebug()) {
        mLogger
            .logDebug("Null or empty pProductId parameter - will see if there is a default for this relationship");
      }
      pProductId = getDefaultDomainProductId();
      if (pProductId == null) {
        String errorString = "Null or empty pProductId parameter - NO default for this relationship - "
            + getId();
        mLogger.logError(errorString);
        throw new ConfiguratorException(errorString);
      }
    }
    if (!pProductId.equals(getDefaultProductId())) {
      boolean isValid = false;
      for (DomainProduct domainProduct : getDomainProducts()) {
        if (pProductId.equalsIgnoreCase(domainProduct.getId())) {
          isValid = true;
          break;
        }
      }
      if (!isValid) {
        String errorString = "Invalid pProductId parameter : pProductId == "
            + pProductId;
        mLogger.logError(errorString);
        throw new ConfiguratorException(errorString);
      }
    }

    if (!canAddOrReplaceChildProduct(pProductId)) {
      String errorString = "quantity exceeds max : max == "
          + getMaximumQuantity();
      mLogger.logError(errorString);
      throw new ConfiguratorException(Constants.ERROR_MAX_CARDINALITY, errorString);
    }

    // This method should also figure out if an add/create is
    // required - check Swisscom
    ConfigurationContext context = null;
    ConfigurationParams params = new ConfigurationParams();
    params.setParam(ContextParameters.PRODUCT_ID, pProductId);

    // Special case where only 1 child is permitted
    // If we are trying to add a different product than is
    // already selected then we'll send a REPLACE_PRODUCT
    // command to Siebel
    if (getChildInstanceCount() == getMaximumQuantity()
        && getMaximumQuantity() == 1) {
      context = new ConfigurationContext(getChildInstances()[0],
          ConfigurationCommandType.REPLACE_PRODUCT);
    } else {
      params.setParam(ContextParameters.RELATIONSHIP_ID, getId());
      params.setParam(ContextParameters.QUANTITY, pQuantity);
      context = new ConfigurationContext(
          (ProductConfigInstance) getParentInstance(),
          ConfigurationCommandType.ADD_PRODUCT);
    }
    context.setConfigurationParams(params);
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Leaving createAddProductContext() : context == "
          + context);
    }
    return context;
  }
	
  /**
   * @param pProductId
   * @param pIntegrationId
   * @throws ConfiguratorException
   */
  public ChildProductConfigInstance addChildInstance(String pProductId)
      throws ConfiguratorException {

    ChildProductConfigInstance instance = new ChildProductConfigInstance(this,
        pProductId, getNextInstanceNumber(pProductId));
    mChildInstances.add(instance);
    return instance;
  }
  
  /**
   * @param pInstance
   * @throws ConfiguratorException
   */
  public void addChildInstance(ChildProductConfigInstance pInstance)
      throws ConfiguratorException {

    pInstance.refreshChildProductConfigInstance(this);
    mChildInstances.add(pInstance);
  }

  /**
   * @param pProductConfigInstance
   * @throws ConfiguratorException
   */
  public void removeChildInstance(BaseConfigInstance pProductConfigInstance) {

    if (mLogger.isLoggingDebug()) {
      mLogger
          .logDebug("Entered removeChildInstance() : pProductConfigInstance == "
              + pProductConfigInstance);
    }
    mChildInstances.remove(pProductConfigInstance);
  }
	
  
  /**
   * @return
   */
  protected String getDefaultDomainProductId() {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered getDefaultDomainProductId()");
    }
    String productId = getDefaultProductId();
    if (productId == null) {
      if (getDomainProductCount() == 1) {
        productId = getDomainProducts()[0].getId();
      }
    }
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Leaving getDefaultDomainProductId() : productId == "
          + productId);
    }
    return productId;
  }

  
  /**
   * @param pProductId
   * @param pQuantity
   * @return
   * @throws ConfiguratorException
   */
  public boolean canAddChildProducts(String pProductId, int pQuantity)
      throws ConfiguratorException {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered canAddChildProducts : pProductId = "
          + pProductId + "pQuantity" + pQuantity);
    }
    boolean canAdd = false;

    if (pQuantity == 1) {
      canAdd = canAddOrReplaceChildProduct(pProductId);
    } else if (pQuantity < 1
        || (getChildInstanceCount() + pQuantity <= getMaximumQuantity())) {
      canAdd = true;
    }

    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Leaving canAddChildProducts() : canAdd == " + canAdd);
    }
    return canAdd;
  }
  
  
  /**
   * @param pQuantity
   * @return
   * @throws ConfiguratorException
   */
  public boolean canDeletedChildProducts(int pQuantity) {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered canDeletedChildProducts : pQuantity" + pQuantity);
    }
    boolean canAdd = false;

    if (getChildInstanceCount() - pQuantity >= getMinimumQuantity()) {
      canAdd = true;
    }

    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Leaving canDeletedChildProducts() : canAdd == " + canAdd);
    }
    return canAdd;
  }

  
  /**
   * @param pProductId
   * @return
   * @throws ConfiguratorException
   */
  public boolean canAddOrReplaceChildProduct(String pProductId) throws ConfiguratorException {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered canAddOrReplaceChildProduct : pProductId = "
          + pProductId);
    }
    String productId = (!StringUtils.isEmpty(pProductId) ? pProductId
        : getDefaultDomainProductId());
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("productId = " + productId);
    }
    if (productId == null) {
      String errorString = "canAddOrReplaceChildProduct - productId == null : relationshipId = "
          + getId();
      mLogger.logError(errorString);
      throw new ConfiguratorException(Constants.APPLICATION_ERROR, errorString);
    }
    boolean canAddOrReplace = false;

    if (getChildInstanceCount() + 1 <= getMaximumQuantity()
        || (getChildInstanceCount() == getMaximumQuantity()
            && getMaximumQuantity() == 1 && !(productId
              .equals(getChildInstances()[0].getProductId())))) {
      canAddOrReplace = true;
    }

    if (mLogger.isLoggingDebug()) {
      mLogger
          .logDebug("Leaving canAddOrReplaceChildProduct() : canAddOrReplace == "
              + canAddOrReplace);
    }
    return canAddOrReplace;
  }

}
