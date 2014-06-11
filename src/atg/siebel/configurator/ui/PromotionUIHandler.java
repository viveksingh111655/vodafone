package atg.siebel.configurator.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import atg.siebel.configurator.BaseConfigInstance;
import atg.siebel.configurator.ConfiguratorException;
import atg.siebel.configurator.PromotionConfigInstance;
import atg.siebel.configurator.RootProductConfigInstance;
import atg.siebel.configurator.spu.StructuredProductUIException;

public class PromotionUIHandler extends UIHandler 
{
	public UINodeWrapper generateUI(BaseConfigInstance pPCI) throws StructuredProductUIException 
	{
		UINodeWrapper wrapper = new UINodeWrapper();
		
		List<UINode> list = new ArrayList<UINode>();
		
		PromotionConfigInstance promo = (PromotionConfigInstance)pPCI;
		
		Iterator<RootProductConfigInstance> it = promo.getRootProducts().iterator();
		
		
		while(it.hasNext())
		{
			RootProductConfigInstance rootProduct = it.next();
			
			UINodeWrapper rootWrapper = generateUI(rootProduct);
			
			if(rootWrapper!=null)
			{
				list.addAll(rootWrapper.getUiNodeList());
			}
		}
		
		wrapper.setUiNodeList(list);
		//set the promotion's css path
		wrapper.setCssFilePath(getSpuTools().getPromotionUI(promo.getProductId()).getCssPath());
		
		return wrapper;
	}
	
	public UINodeWrapper getCachedUI(BaseConfigInstance pPCI)
	{
		return getUiCache().getUINodeWrapper(((PromotionConfigInstance)pPCI).getCommerceItemId());
	}
	
	public void cacheUI(BaseConfigInstance pPCI, UINodeWrapper pWrapper)
	{
		getUiCache().cacheUINodeWrapper(((PromotionConfigInstance)pPCI).getCommerceItemId(), pWrapper);
	}
	
	public void removeUI(BaseConfigInstance pPCI)
	{
		getUiCache().removeUINodeWrapper(((PromotionConfigInstance)pPCI).getCommerceItemId());
	}
	
	public void endConfiguration(BaseConfigInstance pPCI)
	{
		((PromotionConfigInstance)pPCI).endConfiguration();
	}
	
	public void beginConfiguration(BaseConfigInstance pPCI) throws ConfiguratorException
	{
		((PromotionConfigInstance)pPCI).beginConfigRootProducts();
	}

}
