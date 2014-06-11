package atg.siebel.configurator.ui;

import atg.siebel.configurator.BaseConfigInstance;
import atg.siebel.configurator.ConfiguratorException;
import atg.siebel.configurator.Constants.ProductConfigInstanceStatus;
import atg.siebel.configurator.PromotionConfigInstance;
import atg.siebel.configurator.RootProductConfigInstance;
import atg.siebel.configurator.spu.StructuredProductUIException;
import atg.siebel.integration.SiebelUserSessionStore;

public class RootProductUIHandler extends UIHandler {

	public UINodeWrapper generateUI(BaseConfigInstance pPCI)  throws StructuredProductUIException
	{
		RootProductConfigInstance rootPCI = (RootProductConfigInstance)pPCI;
		
		return generateUI(rootPCI);
	}
	
	public UINodeWrapper getCachedUI(BaseConfigInstance pPCI)
	{
		return getUiCache().getUINodeWrapper(((RootProductConfigInstance)pPCI).getKey().toString());
	}
	
	public void cacheUI(BaseConfigInstance pPCI, UINodeWrapper pWrapper)
	{
		getUiCache().cacheUINodeWrapper(((RootProductConfigInstance)pPCI).getKey().toString(), pWrapper);
	}
	
	public void removeUI(BaseConfigInstance pPCI)
	{
		getUiCache().removeUINodeWrapper(((RootProductConfigInstance)pPCI).getKey().toString());
	}
	
	public void endConfiguration(BaseConfigInstance pPCI)
	{
		((RootProductConfigInstance)pPCI).endConfiguration();
	}
	
	public void beginConfiguration(BaseConfigInstance pPCI) throws ConfiguratorException
	{
		if(((RootProductConfigInstance)pPCI).getStatus().equals(ProductConfigInstanceStatus.CONFIGURED))
		{
			((RootProductConfigInstance)pPCI).editConfiguration();
			SiebelUserSessionStore.reset();
		}
		else if(((RootProductConfigInstance)pPCI).getStatus().equals(ProductConfigInstanceStatus.NEW))
		{
			((RootProductConfigInstance)pPCI).beginConfiguration();
			SiebelUserSessionStore.reset();
		}
		
	}
	  
}
