package atg.siebel.configurator.ui;

import atg.nucleus.GenericService;
import atg.siebel.configurator.BaseConfigInstance;
import atg.siebel.configurator.ConfiguratorException;
import atg.siebel.configurator.RootProductConfigInstance;
import atg.siebel.configurator.spu.ProductUI;
import atg.siebel.configurator.spu.StructuredProductUIException;
import atg.siebel.configurator.spu.StructuredProductUITools;

public abstract class UIHandler extends GenericService {
	
	// -------------------------------------
	// spuTools property
	private StructuredProductUITools mSpuTools;

	public StructuredProductUITools getSpuTools() {
	  return mSpuTools;
	}

	public void setSpuTools(StructuredProductUITools pSpuTools) {
	  mSpuTools = pSpuTools;
	}
	
	private UINodeGeneratorService mUINodeGeneratorService;

	public UINodeGeneratorService getUiNodeGeneratorService() {
		return mUINodeGeneratorService;
	}

	public void setUiNodeGeneratorService(UINodeGeneratorService pUINodeGeneratorService) {
		mUINodeGeneratorService = pUINodeGeneratorService;
	}
	
	private String mReconfigureSuccessURL;

	public String getReconfigureSuccessURL() {
		return mReconfigureSuccessURL;
	}

	public void setReconfigureSuccessURL(String pReconfigureSuccessURL) {
	  mReconfigureSuccessURL = pReconfigureSuccessURL;
	}
	
	private String mEndConfigSuccessURL;

  public String getEndConfigSuccessURL() {
    return mEndConfigSuccessURL;
  }

  public void setEndConfigSuccessURL(String pEndConfigSuccessURL) {
    mEndConfigSuccessURL = pEndConfigSuccessURL;
  }
	
	protected UICache mUICache;
	/**
	 * @return the mUICache
	 */
	public UICache getUiCache() {
		return mUICache;
	}
	/**
	 * @param mUICache the mUICache to set
	 */
	public void setUiCache(UICache pUICache) {
		mUICache = pUICache;
	}
	
	/**
	 * generates a UINodeWrapper for a given instance
	 * 
	 * @param pPCI
	 * @return
	 * @throws StructuredProductUIException
	 */
	public abstract UINodeWrapper generateUI(BaseConfigInstance pPCI) throws StructuredProductUIException;
	
	/**
	 * returns an instance from the cache
	 * 
	 * @param pPCI
	 * @return
	 */
	public abstract UINodeWrapper getCachedUI(BaseConfigInstance pPCI);
	
	/**
	 * caches an instance
	 * 
	 * @param pPCI
	 * @param pWrapper
	 */
	public abstract void cacheUI(BaseConfigInstance pPCI, UINodeWrapper pWrapper);
	
	/**
	 * removes a UI from the cache
	 * 
	 * @param pPCI
	 * @param pWrapper
	 */
	public abstract void removeUI(BaseConfigInstance pPCI);
	
	public abstract void beginConfiguration(BaseConfigInstance pPCI) throws ConfiguratorException;
	
	public abstract void endConfiguration(BaseConfigInstance pPCI);
	  
	protected UINodeWrapper generateUI(RootProductConfigInstance pRootPCI) throws StructuredProductUIException
	{
	   UINodeWrapper wrapper = new UINodeWrapper();
	   //generate a UI for a root product
	   //If it's in a promotion, pass in the promo id
	   String promoID = pRootPCI.getPromotionInstance()!=null?pRootPCI.getPromotionInstance().getProductId():null;
		  
       ProductUI uiTemplate = getSpuTools().getProductUI(pRootPCI.getProductId(), promoID);
       
       //if we don't get a Product UI back for this PCI, it doesn't have a corresponding UI definition in the
       //SPU. Therefore we don't want to display it, so we'll return null to the caller. If something has
       //gone wrong with the DB, we'll throw an error.
       if(uiTemplate==null)
       {
    	   return null;
       }
       
       wrapper.setCssFilePath(uiTemplate.getCssPath());
       wrapper.setUiNodeList(getUiNodeGeneratorService().getUINodeList(pRootPCI, uiTemplate));
		  
       return wrapper;
	 }

}
