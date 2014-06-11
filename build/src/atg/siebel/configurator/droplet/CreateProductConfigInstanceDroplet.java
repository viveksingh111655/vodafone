package atg.siebel.configurator.droplet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;

import atg.commerce.CommerceException;
import atg.commerce.order.Order;
import atg.commerce.order.OrderHolder;
import atg.commerce.order.purchase.PurchaseProcessHelper;
import atg.commerce.pricing.PricingModelHolder;
import atg.commerce.util.PipelineErrorHandler;
import atg.core.i18n.LayeredResourceBundle;
import atg.core.util.StringUtils;
import atg.repository.RepositoryItem;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;
import atg.siebel.configurator.ConfiguratorException;
import atg.siebel.configurator.ConfiguratorManager;
import atg.siebel.configurator.Constants.ProductConfigInstanceStatus;
import atg.siebel.configurator.RootProductConfigInstance;
import atg.siebel.configurator.spu.StructuredProductUIException;
import atg.siebel.configurator.ui.UIManager;
import atg.siebel.validation.SiebelValidator;

/**
 * Droplet which creates a Product Config Instance when given a commerce item
 *
 * @author Gary McDowell
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/droplet/CreateProductConfigInstanceDroplet.java#2 $$Change: 847685 $
 * @updated $DateTime: 2013/10/30 07:35:20 $$Author: rvepa $
 */
public class CreateProductConfigInstanceDroplet extends DynamoServlet implements PipelineErrorHandler
{
	// INPUT PARAMS
	public static final String INPUT_COMMERCE_ITEM_PARAM = "commerceItemId";
	
	// OUTPUT PARAMS
	public static final String OUTPUT_ERROR_PARAM = "errorMessage";
	public static final String ERROR_MSG = "errorOccured";
	
	//OPARAMS
	public static final String OPARAM_OUTPUT = "output";
	
	//ResourceBundle Name
	private static final String SIEBEL_RESOURCE_BUNDLE_NAME = "atg.siebel.WebAppResources";
	private static ResourceBundle sResourceBundle = LayeredResourceBundle.getBundle(SIEBEL_RESOURCE_BUNDLE_NAME, atg.service.dynamo.LangLicense.getLicensedDefault());
	private static final String UI_NOTFOUND_ERROR_MESSAGE="uiNotFound";



	// -------------------------------------
	// Properties
	// -------------------------------------
		
	// -------------------------------------
	// property: configuratorManager
	private ConfiguratorManager mConfiguratorManager;
	public ConfiguratorManager getConfiguratorManager() {
		return mConfiguratorManager;
	}
	public void setConfiguratorManager(ConfiguratorManager pConfiguratorManager) {
		mConfiguratorManager = pConfiguratorManager;
	}
	//property: UIManager
	protected UIManager mUIManager;
	public UIManager getUiManager() {
	  return mUIManager;
	}
	public void setUiManager(UIManager pUIManager) {
	  mUIManager = pUIManager;
	}
	
	//---------------------------------------------------------------------------
	// property: Order
	//---------------------------------------------------------------------------
	Order mOrder;
	public void setOrder(Order pOrder) {
	  mOrder = pOrder;
	}
	
	public Order getOrder() {
	  if (mOrder != null)
	    return mOrder;
	  else
	    return getConfiguratorManager().getConfiguratorTools().getOrderHolder().getCurrent();
	}
	

	//---------------------------------------------------------------------------
	// property: Profile
	//---------------------------------------------------------------------------
	RepositoryItem mProfile;
	public void setProfile(RepositoryItem pProfile) {
	  mProfile = pProfile;
	}
	
	public RepositoryItem getProfile() {
	  return mProfile;
	}
	
	//-------------------------------------
	// property: userPricingModels
	//-------------------------------------
	PricingModelHolder mUserPricingModels;
	public void setUserPricingModels(PricingModelHolder pUserPricingModels) {
	  mUserPricingModels = pUserPricingModels;
	}
	
	public PricingModelHolder getUserPricingModels() {
	  return mUserPricingModels;
	}
	
	//-------------------------------------
	// property: purchaseProcessHelper
	//-------------------------------------
	PurchaseProcessHelper mPurchaseProcessHelper;
	public void setPurchaseProcessHelper(PurchaseProcessHelper pPurchaseProcessHelper) {
	  mPurchaseProcessHelper = pPurchaseProcessHelper;
	}
	
	public PurchaseProcessHelper getPurchaseProcessHelper() {
	  return mPurchaseProcessHelper;
	}
	
	//----------------------------------------------------------------------------------
	// property: mSiebelValidator
	private SiebelValidator mSiebelValidator;
	public SiebelValidator getSiebelValidator() {
	  return mSiebelValidator;
	}
	
	public void setSiebelValidator(SiebelValidator pSiebelValidator) {
	  this.mSiebelValidator = pSiebelValidator;
	}
	
	
	//-------------------------------------------------------------------------------
	//property: url
	private String mUrl;
	public String getUrl() {
	  return mUrl;
	}
	
	public void setUrl(String pUrl) {
	  this.mUrl = pUrl;
	}
	
	/**
	 *   Method to retrieve Locale from the shopping cart
	 *  
	 *  @param pShoppingCart
	 *            Shopping cart
	 *  @return
	 */
	 private Locale retrieveLocale(OrderHolder pShoppingCart) {
	   String locale = (String) pShoppingCart.getProfile().getPropertyValue("locale");
	   Locale defaultLocale=null;
	   if (StringUtils.isEmpty(locale)) {
	     defaultLocale = getConfiguratorManager().getConfiguratorTools().getProfileTools().getLocaleService().getLocale();
	     locale = defaultLocale.getDisplayName();
	   }
	   return defaultLocale;
	 }
  

	/**
	 *  Method that creates the RootProductConfigInstance and does a begin configuration with it.
	 *  
	 */
	public void service(DynamoHttpServletRequest req,
			DynamoHttpServletResponse res) throws ServletException, IOException 
	{
		String commerceItemId = req.getParameter(INPUT_COMMERCE_ITEM_PARAM);
		if(commerceItemId!=null||!commerceItemId.isEmpty())
		{
		  try {
		    RootProductConfigInstance instance = getConfiguratorManager().createProductInstanceFromCommerceItem(commerceItemId);
		    getUiManager().beginConfiguration(instance);
		    // Check if the begin configuration status is successful or not
		    String[] commerceIds=new String[]{commerceItemId};
		    Locale locale=retrieveLocale(getConfiguratorManager().getConfiguratorTools().getOrderHolder());
		    /* If the status is not successful and PCI is not initialized redirect
               to the given url with the error message */
            if (!instance.getLastCommandStatus().isSuccessfull()
              && !instance.getStatus().equals(
                ProductConfigInstanceStatus.INITIALISED)) {
		      //Delete the commerce items from the order
		      String errorMsg= instance.getLastCommandStatus().getStatusDetails().getDescription();
		      deleteItemsFromOrder(errorMsg, commerceIds, locale, req, res);		      
		      return;
		    }
		    //Check if the UI is found for the product, if not redirect to the given url with error message.
		    boolean isUIFound=getSiebelValidator().isProductUIFoundFromInstance(instance);
		    if(!isUIFound){
		      //Delete the commerce items from the order
		      String errorMsg=MessageFormat.format(sResourceBundle.getString(UI_NOTFOUND_ERROR_MESSAGE),instance.getProductId());
		      deleteItemsFromOrder(errorMsg, commerceIds, locale, req, res);
		      return;
		    }
		    
		  }
		  catch(ConfiguratorException ce)
		  {
		    throw new ServletException(ce);
		  } 
		  catch (StructuredProductUIException e)
		  {
		    throw new ServletException(e);
		  }
		  catch (CommerceException e)
		  {
		    throw new ServletException(e);
		  }
		  catch (Exception e)
      {
        throw new ServletException(e);
      }
		}
		else
		{
		  throw new ServletException("No Commerce Item Id provided");
		}
  }
	
	/**
	 * Method to delete the given commerce item from the order
	 * @param errorMsg
	 * @param commerceIds
	 * @param locale
	 * @param pReq
	 * @param pRes
	 * @throws CommerceException
	 * @throws Exception
	 */
	public void deleteItemsFromOrder(String errorMsg, String[] commerceIds, Locale locale,DynamoHttpServletRequest pReq,DynamoHttpServletResponse pRes)throws CommerceException,Exception{
	  getPurchaseProcessHelper().deleteItems(getOrder(), commerceIds, getUserPricingModels(), locale, getProfile(), this);
	  getUiManager().redirect(pReq, pRes, getUrl(), errorMsg);
    pReq.setParameter(OUTPUT_ERROR_PARAM,ERROR_MSG);
    pReq.serviceParameter(OPARAM_OUTPUT, pReq, pRes);
	  
	}
	
	@Override
	public void handlePipelineError(Object pError, String pErrorKey) {
	  if(isLoggingInfo())
	    logInfo("In handlePipelineError Method");
	  
	}
	
}
