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


import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import atg.commerce.order.CommerceItem;
import atg.commerce.order.CommerceItemNotFoundException;
import atg.commerce.order.InvalidParameterException;
import atg.commerce.order.Order;
import atg.core.i18n.LayeredResourceBundle;
import atg.core.util.StringUtils;
import atg.nucleus.GenericService;
import atg.siebel.configurator.Constants.ConfigInstanceType;
import atg.siebel.configurator.Constants.ConfigurationCommandType;
import atg.siebel.configurator.Constants.ProductConfigInstanceStatus;
import atg.siebel.configurator.command.CommandResult;
import atg.siebel.configurator.status.CommandStatus;
import atg.siebel.order.SiebelCommerceItem;

/**
 * Manager class used to coordinate and orchestrate product configuration operations.
 * Will mostly delegate to ConfiguratorTools which does more of the low-level work
 *
 * @author Bernard Brady
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/ConfiguratorManager.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class ConfiguratorManager extends GenericService {
  // -------------------------------------
  /** Class version string */
  public static final String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/ConfiguratorManager.java#1 $$Change: 842106 $";

  // -------------------------------------
  // Constants
  // -------------------------------------
  private static final String SIEBEL_RESOURCE_BUNDLE_NAME = "atg.siebel.WebAppResources";
  private static final String MIN_CARDINALITY_NOT_SATISFIED=  "minCardinalityNotSatisfied";
  //ResourceBundle
  private static ResourceBundle sResourceBundle = LayeredResourceBundle.getBundle(SIEBEL_RESOURCE_BUNDLE_NAME, atg.service.dynamo.LangLicense.getLicensedDefault());
  // -------------------------------------
  // Member variables
  // -------------------------------------

  // -------------------------------------
  // Properties
  // -------------------------------------

  // -------------------------------------
  // property: configuratorTools
  protected ConfiguratorTools mConfiguratorTools;

  public ConfiguratorTools getConfiguratorTools() {
    return mConfiguratorTools;
  }

  public void setConfiguratorTools(ConfiguratorTools pConfiguratorTools) {
    mConfiguratorTools = pConfiguratorTools;
  }
  
  // -------------------------------------
  // property: callRepriceOrderForAllPromotionRootCPs
  protected boolean mCallRepriceOrderForAllPromotionRootCPs = true;

  public boolean isCallRepriceOrderForAllPromotionRootCPs() {
    return mCallRepriceOrderForAllPromotionRootCPs;
  }

  public void setCallRepriceOrderForAllPromotionRootCPs(
      boolean pCallRepriceForAllPromotionRootCPs) {
    mCallRepriceOrderForAllPromotionRootCPs = pCallRepriceForAllPromotionRootCPs;
  }


  // -------------------------------------
  // Constructors
  // -------------------------------------
  // -------------------------------------
  /**
   * Constructs a ConfiguratorManager.
   */
  public ConfiguratorManager() {
  }

  
  // -------------------------------------
  // Methods
  // -------------------------------------
    
  /**
   * Retrieves the promotion definition from Siebel i.e. root products and their 
   * cardinalities
   * 
   * @param pInstance - promotion instance whose definition we want to retrieve 
   *                    from Siebel
   * @return a CommandResult object that defines the outcome status of the command.
   * @throws ConfiguratorException
   */
  public CommandResult getPromotionDefinition(PromotionConfigInstance pInstance)
      throws ConfiguratorException {

    if (isLoggingDebug()) {
      logDebug("Entered getPromotionDefinition() : instance = " + pInstance);
    }
    ConfigurationContext context = new ConfigurationContext(pInstance,
        ConfigurationCommandType.GET_PROMOTION_DEFINITION);
    CommandResult result = getConfiguratorTools().callGetProductPromotion(
        context);
    if (isLoggingDebug()) {
      logDebug("Leaving getPromotionDefinition() : result == " + result);
    }
    return result;
  }

  
  /**
   * If a promotion instance already exists for the given commerce item id
   * then retrieve it from the cache otherwise create a new PromotionConfigInstance
   * 
   * @param pCommerceItemId - identifies the commerce item that we 
   *                          want a promotion instance for.
   * @return the promotion instance corresponding to the commerce item
   * @throws ConfiguratorException
   */
  public PromotionConfigInstance getPromotionTemplate(String pCommerceItemId)
      throws ConfiguratorException {
    if (isLoggingDebug()) {
      logDebug("Entered getPromotionTemplate() : pCommerceItemId = "
          + pCommerceItemId);
    }

    if (StringUtils.isEmpty(pCommerceItemId)) {
      String error = "Invald pCommerceItemId parameter - [" + pCommerceItemId
          + "]";
      logError(error);
      throw new ConfiguratorException(error);
    }
    PromotionConfigInstance instance = (PromotionConfigInstance) getConfiguratorTools()
        .getProductConfigInstanceCache().getInstance(pCommerceItemId);
    if (instance == null) {
      instance = (PromotionConfigInstance) getConfiguratorTools()
          .createConfigInstance(pCommerceItemId,
              ConfigInstanceType.PROMOTION_CONFIG_INSTANCE, this);
      getPromotionDefinition(instance);
      getConfiguratorTools().getProductConfigInstanceCache().cacheInstance(
          pCommerceItemId, instance);
    }
    if (isLoggingDebug()) {
      logDebug("Leaving getPromotionTemplate() : instance == "
          + instance);
    }
    return instance;
  }

  
  /**
   * If a root product instance already exists for the given commerce item id
   * then retrieve it from the cache otherwise create a new RootProductConfigInstance
   * 
   * @param pCommerceItemId - identifies the commerce item that we 
   *                          want a root product instance for.
   * @return the root product instance corresponding to the commerce item
   * @throws ConfiguratorException
   */
  public RootProductConfigInstance createProductInstanceFromCommerceItem(
      String pCommerceItemId) throws ConfiguratorException {
    
    if (isLoggingDebug()) {
      logDebug("Entered createProductInstanceFromCommerceItem() : pCommerceItemId = "
          + pCommerceItemId);
    }

    if (StringUtils.isEmpty(pCommerceItemId)) {
      String error = "Invald pCommerceItemId parameter - [" + pCommerceItemId
          + "]";
      logError(error);
      throw new ConfiguratorException(error);
    }
    
    RootProductConfigInstance instance = (RootProductConfigInstance) getConfiguratorTools()
        .getProductConfigInstanceCache().getInstance(
            pCommerceItemId);
    if (instance == null) {
      instance = (RootProductConfigInstance) getConfiguratorTools()
          .createConfigInstance(pCommerceItemId,
              ConfigInstanceType.PRODUCT_CONFIG_INSTANCE, this);
      getConfiguratorTools().getProductConfigInstanceCache()
          .cacheProductConfigInstance(instance);
    }
    
    if (isLoggingDebug()) {
      logDebug("Leaving createProductInstanceFromCommerceItem() : instance == "
          + instance);
    }
    return instance;
  }
  
  /**
   * @param pCommerceItemId
   * @return
   * @throws ConfiguratorException
   */
  public RootProductConfigInstance createProductInstanceFromCommerceItem(
      CommerceItem pCommerceItem, int pInstanceNumber) throws ConfiguratorException {
    
    if (isLoggingDebug()) {
      logDebug("Entered getProductInstanceFromCommerceItem() : pCommerceItemId = "
          + pCommerceItem.getId());
    }

    if (StringUtils.isEmpty(pCommerceItem.getId())) {
      String error = "Invald pCommerceItemId parameter - [" + pCommerceItem.getId()
          + "]";
      logError(error);
      throw new ConfiguratorException(error);
    }
    
    RootProductConfigInstance instance = (RootProductConfigInstance) getConfiguratorTools()
        .getProductConfigInstanceCache().getInstance(
        		pCommerceItem.getId());
    if (instance == null) {
      instance = (RootProductConfigInstance) getConfiguratorTools()
          .createConfigInstance(pCommerceItem,
              ConfigInstanceType.PRODUCT_CONFIG_INSTANCE, this, pInstanceNumber);
      getConfiguratorTools().getProductConfigInstanceCache()
          .cacheProductConfigInstance(instance);
    }
    
    instance.setStatus(ProductConfigInstanceStatus.CONFIGURED);
    
    if (isLoggingDebug()) {
      logDebug("Leaving getProductInstanceFromCommerceItem() : instance == "
          + instance);
    }
    return instance;
  }
  
  /**
   * Method that gets called when an already configured product is being 
   * re-configured
   * 
   * @param pInstance - 
   * @return
   * @throws ConfiguratorException
   */
  public CommandResult performProductConfigurationEdit(
      RootProductConfigInstance pInstance)
      throws ConfiguratorException {
    
    if (isLoggingDebug()) {
      logDebug("Entered performProductConfigurationEdit() : pInstance == "
          + pInstance);
    }
    
    CommandResult result = pInstance.editConfiguration();
    
    if (isLoggingDebug()) {
      logDebug("Leaving performProductConfigurationEdit() : result == " + result);
    }
    
    return result;
  }

  
  /**
   * This is the main entry point into the product configuration call stack
   * 
   * @param pConfigurationContext - the context that will define the desired 
   *                                configuration change behaviour
   * @return a new CommandResult which defines the outcome of the configuration change
   */
  public CommandResult performProductConfigurationChange(
      ConfigurationContext pConfigurationContext) {

    if (isLoggingDebug()) {
      logDebug("Entered performProductConfigurationChange() : pEvent == "
          + pConfigurationContext);
    }

    CommandResult result = null;

    try {
      switch (pConfigurationContext.getCommandType()) {
      case BEGIN_CONFIG:
        if (((ProductConfigInstance) pConfigurationContext.getInstance())
            .isConfigurableViaSiebelWebservice()) {
          result = getConfiguratorTools()
              .callBeginConfig(pConfigurationContext);
        } else {
          result = getConfiguratorTools().configureInstanceFromRepositoryData(
              pConfigurationContext);
        }
        break;
      case END_CONFIG:
        if (((ProductConfigInstance) pConfigurationContext.getInstance())
            .isConfigurableViaSiebelWebservice()) {
          result = getConfiguratorTools().callEndConfig(pConfigurationContext);
        } else {
          result = getConfiguratorTools().callUpdateCart(pConfigurationContext);
        }
        if (result.getStatus().isSuccessfull()) {
          ProductConfigInstance instance = (ProductConfigInstance) pConfigurationContext
              .getInstance();
          getConfiguratorTools().getProductConfigInstanceCache()
              .cacheProductConfigInstance(instance);
          getConfiguratorTools()
            .setParentRelationshipOnCommerceItem(
                (ProductConfigInstance)pConfigurationContext.getInstance());
          boolean performReprice = true;
          if (instance.getPromotionInstance() != null) {
            // For a promotion we have a choice of 2 re-pricing strategies controlled
            // by the callRepriceForAllPromotionRootCPs flag:-
            // 1. when true we call re-price order each time we're done configuring a root product 
            // 2. when false we only re-price when we've configured the last root product
            if (!isCallRepriceOrderForAllPromotionRootCPs()) {
              int index = instance.getPromotionInstance().getRootProducts().indexOf(instance);
              if (index < instance.getPromotionInstance().getRootProductSize()-1) {
                performReprice = false;
              }
            }
          }
          if (performReprice) {
            // Call 'repriceAndUpdateOrder' pipelinechain as 
            // this will do both reprice and updateorder           
            try {
              Order order = getConfiguratorTools().getOrderHolder().getCurrent();
              getConfiguratorTools().runRepriceUpdateOrderProcess(order);
            } catch (ConfiguratorException e) {
              vlogError(
                  "ConfiguratorException calling runRepriceUpdateOrderProcess - {0}", e);
            }
          }
        }
        break;
      case SET_ATTRIBUTES:
        if (((ProductConfigInstance) pConfigurationContext.getInstance())
            .isConfigurableViaSiebelWebservice()) {
          result = getConfiguratorTools().callSetAttributes(pConfigurationContext);
        } else {
          result = getConfiguratorTools().updateProductConfigInstance(pConfigurationContext);
        }
        break;
      case ADD_PRODUCT:
        if (((ProductConfigInstance) pConfigurationContext.getInstance())
            .isConfigurableViaSiebelWebservice()) {
          result = getConfiguratorTools().callAddProduct(pConfigurationContext);
        } else {
          result = getConfiguratorTools().updateProductConfigInstance(pConfigurationContext);
        }        
        break;        
      case REPLACE_PRODUCT:
        result = getConfiguratorTools().callReplaceProduct(
            pConfigurationContext);
        break;       
      case REMOVE_PRODUCT:
        ProductConfigInstance parentInstance = (ProductConfigInstance) 
            ((ChildProductConfigInstance) pConfigurationContext.getInstance()).
                getParentRelationship().getParentInstance();
        if (parentInstance.isConfigurableViaSiebelWebservice()) {
          result = getConfiguratorTools().callRemoveProduct(pConfigurationContext);
        } else {
          result = getConfiguratorTools().updateProductConfigInstance(pConfigurationContext);
        }
        break;       
      case MULTI_COMMAND_UPDATE:
        if (((ProductConfigInstance) pConfigurationContext.getInstance())
            .isConfigurableViaSiebelWebservice()) {
          result = getConfiguratorTools().callMultiFacetUpdate(
              pConfigurationContext);
        } else {
          result = getConfiguratorTools().updateProductConfigInstance(pConfigurationContext);
        } 
        break;
      default:
        result = createErrorResult(pConfigurationContext.mCommandType,
            Constants.UNSUPPORTED_OPERATION, pConfigurationContext.mCommandType
                + " is an unsupported operation");
        break;
      }
    } catch (ConfiguratorException ce) {
      logError(ce);
      String errorCode = (ce.mErrorCode != null) ? ce.mErrorCode
          : Constants.APPLICATION_ERROR;
      result = createErrorResult(pConfigurationContext.mCommandType, errorCode,
          ce.getMessage());
    }

    if (isLoggingDebug()) {
      logDebug("Leaving performProductConfigurationChange() : result == " + result);
    }
    return result;

  } 
  
  
  /**
   * Convenience method to create an error CommandResult object. This method is 
   * usually called when an error occurs in the ATG Configurator framework
   * (i.e. usually not a web service problem)
   * 
   * @param pCommandType - command being executed
   * @param pErrorCode - code that identifies the error
   * @param pErrorDescription - some additional information about the error
   * @return
   */
  public CommandResult createErrorResult(ConfigurationCommandType pCommandType,
      String pErrorCode, String pErrorDescription) {
    if (isLoggingDebug()) {
      logDebug("Entered createErrorResult : pError == " + pErrorCode
          + " : pDescription == " + pErrorDescription);
    }
    CommandStatus status = getConfiguratorTools().getCommandStatusManager()
        .createCommandStatusFromConfiguratorError(pCommandType, pErrorCode,
            pErrorDescription);
    CommandResult result = new CommandResult(status);

    if (isLoggingDebug()) {
      logDebug("Leaving createErrorResult : result == " + result);
    }
    return result;
  }
  
  /**
   * builds and caches a Promotion PCI given an Order
   * 
   * @param pPromotionCommerceItem
   * @throws ConfiguratorException
   */
  public BaseConfigInstance buildPromotionFromCommerceItem(SiebelCommerceItem pCommerceItem)
  	throws ConfiguratorException, CommerceItemNotFoundException, InvalidParameterException
  {
	  //for each child commerce item on the promotion, build a PCI for it
	  
	  List<SiebelCommerceItem> children = pCommerceItem.getCommerceItems();
	  
	  //if there are no children, treat this as a brand new promotion, so return to
	  //the promotion edit screen with a promotion commerce item
	  if(null==children || children.isEmpty())
	  {
		  return null;
	  }
	  
	  //construct an empty promotion instance
	  PromotionConfigInstance promo = getPromotionTemplate(pCommerceItem.getId());
	  
	  if(promo==null)
	  {
		  throw new ConfiguratorException("Error creating promotion with commerce item id : " 
				  + pCommerceItem.getId());
	  }
	  
	  //initialise each relationship quantity to zero
	  List<PromotionRootCPRelationship> relationships = promo.getChildRelationships();
	  Iterator<PromotionRootCPRelationship> relIt = relationships.iterator();
	  while(relIt.hasNext())
	  {
		  PromotionRootCPRelationship relationship = relIt.next();
		  if(relationship.getDomainProductCount()>0)
		  {
			  for(PromotionRootCPRelationship.DomainProduct domainProduct: relationship.getDomainProducts())
			  {
				  domainProduct.setQuantity(new Integer(0));
			  }
		  }
		  else
		  {
			  relationship.setQuantity(new Integer(0));
		  }
	  }
	  
	  //add the root products to the relationships
	  Iterator<SiebelCommerceItem> it = (Iterator<SiebelCommerceItem>) children.iterator();
		
		while(it.hasNext())
		{
			SiebelCommerceItem child = it.next();
			
			PromotionRootCPRelationship relationship = 
					promo.findChildRelationshipById(child.getParentRelationshipId());
			
			//find the current quantity of products so we have the
			//correct instance number
			int instanceNumber = 0;
			
			if(relationship.getDomainProductCount()==0)
			{
				//if it's not an aggregate relationship, just use the quantity
				instanceNumber=relationship.getQuantity();
			}
			else
			{
				//otherwise, find the current # of children with that product id
				ProductConfigInstance[] childInstances = relationship.getChildInstances();
				for(ProductConfigInstance instance: childInstances)
				{
					if(instance.getProductId().equals(child.getAuxiliaryData().getProductId()))
					{
						instanceNumber++;
					}
				}
			}
			
			RootProductConfigInstance rootProduct =  createProductInstanceFromCommerceItem(child, instanceNumber);
			rootProduct.setPromotionInstance(promo);
			
			relationship.addRootProductInstance(rootProduct);

		}
		return promo;
  }  

  /**
   * Checks the actual quantity of the relationship to its minimum cardinality
   * and if it is less throw the exception
   * 
   * @param pInstance
   * @return 
   */
  public void checkMinimumCardinality(BaseConfigInstance pInstance)
      throws Exception {
    String errorMsg = "";
    if (pInstance instanceof PromotionConfigInstance) {
      // minimum cardinality check for relationship in Promotion
      PromotionConfigInstance instance = (PromotionConfigInstance) pInstance;
      List<RootProductConfigInstance> rootProducts = instance.getRootProducts();
      for (RootProductConfigInstance rootProduct : rootProducts) {
        ProductConfigRelationship[] childRelationships = rootProduct
            .getChildRelationships();
        for (ProductConfigRelationship relationship : childRelationships) {
          int actualCount = relationship.getChildInstanceCount();
          int minCardinality = relationship.getMinimumQuantity();
          if (actualCount < minCardinality) {
            errorMsg = MessageFormat.format(
                sResourceBundle.getString(MIN_CARDINALITY_NOT_SATISFIED),
                relationship.getMinimumQuantity(),
                relationship.getDisplayName());
            throw new Exception(errorMsg);
          }
        }
      }
    } else if (pInstance instanceof ProductConfigInstance) {
      // minimum cardinality check for relationship
      ProductConfigInstance instance = (ProductConfigInstance) pInstance;
      ProductConfigRelationship[] childRelationships = instance
          .getChildRelationships();
      for (ProductConfigRelationship relationship : childRelationships) {
        int actualCount = relationship.getChildInstanceCount();
        int minCardinality = relationship.getMinimumQuantity();
        if (actualCount < minCardinality) {
          errorMsg = MessageFormat.format(
              sResourceBundle.getString(MIN_CARDINALITY_NOT_SATISFIED),
              relationship.getMinimumQuantity(), relationship.getDisplayName());
          throw new Exception(errorMsg);
        }
      }
    }
  }
}
