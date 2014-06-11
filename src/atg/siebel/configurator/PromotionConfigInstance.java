/*<ORACLECOPYRIGHT>
 * Copyright (C) 1994-2012 Oracle and/or its affiliates. All rights reserved.
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 * UNIX is a registered trademark of The Open Group.
 *
 * This software and related documentation are provided under a license agreement
 * containing restrictions on use and disclosure and are protected by intellectual property laws.
 * Except as expressly permitted in your license agreement or allowed by law, you may not use, copy,
 * reproduce, translate, broadcast, modify, license, transmit, distribute, exhibit, perform, publish,
 * or display any part, in any form, or by any means. Reverse engineering, disassembly,
 * or decompilation of this software, unless required by law for interoperability, is prohibited.
 *
 * The information contained herein is subject to change without notice and is not warranted to be error-free.
 * If you find any errors, please report them to us in writing.
 *
 * U.S. GOVERNMENT RIGHTS Programs, software, databases, and related documentation and technical data delivered to U.S.
 * Government customers are "commercial computer software" or "commercial technical data" pursuant to the applicable
 * Federal Acquisition Regulation and agency-specific supplemental regulations.
 * As such, the use, duplication, disclosure, modification, and adaptation shall be subject to the restrictions and
 * license terms set forth in the applicable Government contract, and, to the extent applicable by the terms of the
 * Government contract, the additional rights set forth in FAR 52.227-19, Commercial Computer Software License
 * (December 2007). Oracle America, Inc., 500 Oracle Parkway, Redwood City, CA 94065.
 *
 * This software or hardware is developed for general use in a variety of information management applications.
 * It is not developed or intended for use in any inherently dangerous applications, including applications that
 * may create a risk of personal injury. If you use this software or hardware in dangerous applications,
 * then you shall be responsible to take all appropriate fail-safe, backup, redundancy,
 * and other measures to ensure its safe use. Oracle Corporation and its affiliates disclaim any liability for any
 * damages caused by use of this software or hardware in dangerous applications.
 *
 * This software or hardware and documentation may provide access to or information on content,
 * products, and services from third parties. Oracle Corporation and its affiliates are not responsible for and
 * expressly disclaim all warranties of any kind with respect to third-party content, products, and services.
 * Oracle Corporation and its affiliates will not be responsible for any loss, costs,
 * or damages incurred due to your access to or use of third-party content, products, or services.
 </ORACLECOPYRIGHT>*/
package atg.siebel.configurator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import atg.siebel.configurator.Constants.ProductConfigInstanceStatus;
import atg.siebel.integration.SiebelUserSessionStore;
import atg.siebel.order.SiebelCommerceItem;

/**
 * <p>This object contains all information that are required for one specific promotion instance that is
 * being configured by the user.
 * 
 * @author Bernard Brady
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/PromotionConfigInstance.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class PromotionConfigInstance extends BaseConfigInstance {

  // -------------------------------------
  /** Class version string */
  public static final String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/PromotionConfigInstance.java#1 $$Change: 842106 $";

  // -------------------------------------
  // Constants
  // -------------------------------------

  // -------------------------------------
  // Properties
  // -------------------------------------

  // -------------------------------------
  // property: childRelationships
  private List<PromotionRootCPRelationship> mChildRelationships;

  public void setChildRelationships(
      List<PromotionRootCPRelationship> pChildRelationships) {
    mChildRelationships = pChildRelationships;
  }

  public List<PromotionRootCPRelationship> getChildRelationships() {
    return mChildRelationships;
  }

  /**
   * @param pOrderId
   * @param pCommerceItemId
   * @param pProductId
   * @param pManager
   * @throws ConfiguratorException
   */
  public PromotionConfigInstance(String pOrderId, String pCommerceItemId,
      String pProductId, ConfiguratorManager pManager)
      throws ConfiguratorException {

    super(pOrderId, pProductId, pManager);

    if (pProductId == null) {
      mLogger.logError("null pProductId value passed into Constructor");
      throw new ConfiguratorException(
          "A product config instance must have a valid product reference");
    }
    setCommerceItemId(pCommerceItemId);
  }

  
	
  /**
   * Creates root product instances for this promotion instance
   * 
   * @param pProductConfigInstanceCache - instance cache
   * @throws ConfiguratorException
   * 
   */
  public void createRootProducts(
      ProductConfigInstanceCache pProductConfigInstanceCache)
      throws ConfiguratorException {

    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered createRootProducts() : this == " + toString());
    }
    List<PromotionRootCPRelationship> childRelationships = getChildRelationships();
    for (PromotionRootCPRelationship nextChild : childRelationships) {
      createRootProduct(pProductConfigInstanceCache, nextChild);
    }
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Leaving createRootProducts()");
    }
  }

  
  /**
   * Creates root product instances for this root product relationship
   * 
   * @param pProductConfigInstanceCache
   * @param pChildRelationShip
   * @throws ConfiguratorException
   */
  public void createRootProduct(
      ProductConfigInstanceCache pProductConfigInstanceCache,
      PromotionRootCPRelationship pChildRelationShip)
      throws ConfiguratorException {
    if (mLogger.isLoggingDebug())
      mLogger.logDebug("Entered createRootProduct() : this == " + toString());
    pChildRelationShip.createRootCPInstances(pProductConfigInstanceCache);
  }

  
  /**
   * Method which triggers the creation and initialisation of this promotion's
   * root product instances.
   * 
   * @param pProductConfigInstanceCache
   * @throws ConfiguratorException
   */
  public void initialiseRootProducts(ProductConfigInstanceCache pProductConfigInstanceCache) 
      throws ConfiguratorException {

    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered initialiseRootProducts() : this == "
          + toString());
    }
    
    //create the root product instances
    createRootProducts(pProductConfigInstanceCache);
    
    
  }
  
  /**
   * calls begin config on root products
   * 
   * @throws ConfiguratorException
   */
  public void beginConfigRootProducts() throws ConfiguratorException
  {
	List<PromotionRootCPRelationship> childRelationships = getChildRelationships();
	for (PromotionRootCPRelationship nextChild : childRelationships) {
	  initialiseRootProduct(nextChild);
	}
	if (mLogger.isLoggingDebug()) {
	  mLogger.logDebug("Leaving initialiseRootProducts()");
	}
  }

  
  /**
   * Initialises all root product instances defined for the 
   * passed in PromotionRootCPRelationship
   * 
   * @param pChildRelationship - root product relationship
   * @throws ConfiguratorException
   */
  public void initialiseRootProduct(
      PromotionRootCPRelationship pChildRelationship)
      throws ConfiguratorException {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered initialiseRootProducts() : this == "
          + toString());
    }
    ProductConfigInstance[] instances = pChildRelationship.getChildInstances();
    if (instances != null && instances.length > 0) {
      for (ProductConfigInstance next : instances) {
        if (next.getIsRootInstance()) {
          if (mLogger.isLoggingDebug()) {
            mLogger.logDebug("About to initialise next child == " + next);
          }
          initializeRootProductInstance((RootProductConfigInstance) next);
        }
      }
    }
  }

  
  /**
   * Initialises the root product instance - if instance is a CP then 
   * this will result in a call to the Siebel webservice otherwise (if required e.g. SPWA) we
   * get the product definition from our product catalog repository.
   * 
   * @param pInstance - the root product instance to initialise
   * @throws ConfiguratorException
   */
  public void initializeRootProductInstance(RootProductConfigInstance pInstance)
      throws ConfiguratorException {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered initializeRootProductInstance() : pInstance == "
          + pInstance);
    }

    if(pInstance.getStatus().equals(ProductConfigInstanceStatus.CONFIGURED))
 	{
    	pInstance.editConfiguration();
    	SiebelUserSessionStore.reset();
 	}
 	else if(pInstance.getStatus().equals(ProductConfigInstanceStatus.NEW))
 	{
 		pInstance.beginConfiguration();
 		SiebelUserSessionStore.reset();
 	}
    
    
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Leaving initializeRootProductInstance() : pInstance == "
          + pInstance);
    }
  }
	
  
  /**
   * Convenience method to return all root products for this promotion
   * 
   * @return - a list of RootProductConfigInstance's
   */
  public List<RootProductConfigInstance> getRootProducts() {

    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Entered getRootProducts() : this == " + toString());
    }
    List<RootProductConfigInstance> rootProducts = new ArrayList<RootProductConfigInstance>();
    List<PromotionRootCPRelationship> childRelationships = getChildRelationships();
    for (PromotionRootCPRelationship nextChild : childRelationships) {
      for (ProductConfigInstance instance : nextChild.getChildInstances()) {
        rootProducts.add((RootProductConfigInstance) instance);
      }
    }
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("Leaving getRootProducts() : rootProducts == "
          + rootProducts);
    }
    return rootProducts;
  }
  
  /**
   * helper method for JSPs to see the size of the Root Product list, and JSP EL doesn't support calling the size()
   * function directly
   * 
   * @return
   */
  public int getRootProductSize()
  {
	  return getRootProducts().size();
  }
  
  /**
   * set the quantities of root products
   * 
   * @param pQuantities [relationshipIndex][domainProductIndex]
   */
  public void validateQuantities() throws ConfiguratorException
  {
	  Iterator<PromotionRootCPRelationship> it = getChildRelationships().iterator();
	  
	  while(it.hasNext())
	  {
		  PromotionRootCPRelationship relationship = it.next();
		  if(!relationship.validateQuantity())
		  {
			  throw new ConfiguratorException("Quantity not valid for " + relationship.getDisplayName());
		  }
	  }
  }
  
  /**
   * method which calls end configuration on all root products
   * 
   */
  public void endConfiguration()
  {
	  List<RootProductConfigInstance> rootProductList = getRootProducts();
		
	  Iterator<RootProductConfigInstance> it = rootProductList.iterator();
		
		while(it.hasNext())
		{
			RootProductConfigInstance rootProduct = it.next();
			if(rootProduct != null && rootProduct.getStatus().equals(Constants.ProductConfigInstanceStatus.INITIALISED))
			{
				rootProduct.endConfiguration();
			}
		}
  }
  
  
  /**
   * @param pRootProduct
   * @return
   * @throws ConfiguratorException
   */
  public PromotionRootCPRelationship findChildRelationshipByRootProduct(
      RootProductConfigInstance pRootProduct) throws ConfiguratorException {
    Iterator<PromotionRootCPRelationship> it = mChildRelationships.iterator();

    while (it.hasNext()) {
      PromotionRootCPRelationship relationship = it.next();
      ProductConfigInstance[] childInstances = relationship.getChildInstances();
      if (childInstances != null && childInstances.length > 0) {
        for (ProductConfigInstance nextChild : childInstances) {
          if (nextChild == pRootProduct) {
            return relationship;
          }
        }
      }
    }

    throw new ConfiguratorException("Root Product " + pRootProduct.getProductId()
        + " not found in Promotion " + getCommerceItemId());
  }
	
  /**
   * 
   * @param pRelationshipID
   * @return
   */
  public PromotionRootCPRelationship findChildRelationshipById(String pRelationshipID)
  	throws ConfiguratorException
  {
	  Iterator<PromotionRootCPRelationship> it = mChildRelationships.iterator();
	  
	  while(it.hasNext())
	  {
		  PromotionRootCPRelationship relationship = it.next();
		  if(relationship.getId().equals(pRelationshipID))
		  {
			  return relationship;
		  }
	  }
	  
	  throw new ConfiguratorException("Relationship " + pRelationshipID + " not found on Promotion " + getCommerceItemId());
  }

	/**
	 * @param pUpdatedProductInstance
	 */
	public void rootProductUpdated(ProductConfigInstanceKey pUpdatedProductInstanceKey) {

    // In time we may do other things with this notification (hence we pass in the updated key)
    // but for now we just use it to re-determine the value of the promotion
    // commerceItem's configured property; i.e. if ALL root product commerceItem's are
    // "configured" then the promotion is also.
    boolean configured = true;
    if (getRootProducts() == null || getRootProducts().isEmpty()) {
      configured = false;
    } else {
      for (RootProductConfigInstance rootProduct : getRootProducts()) {
        SiebelCommerceItem rootCommerceItem = getConfiguratorManager()
            .getConfiguratorTools().getCommerceItem(rootProduct);
        if (rootCommerceItem == null || !rootCommerceItem.isConfigured()) {
          configured = false;
          break;
        }
      }
    }
	  
    SiebelCommerceItem commerceItem = 
        getConfiguratorManager().getConfiguratorTools().getCommerceItem(this);
    commerceItem.setConfigured(configured);
  }
}



