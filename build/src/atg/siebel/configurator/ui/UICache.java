package atg.siebel.configurator.ui;

import java.util.HashMap;
import java.util.Map;

import atg.nucleus.GenericService;

public class UICache extends GenericService 
{
	
	private Map<String, UINodeWrapper> mCache = new HashMap<String, UINodeWrapper>();
	
	public UINodeWrapper getUINodeWrapper(String pKey)
	{
		return mCache.get(pKey);
	}
	
	public void cacheUINodeWrapper(String pKey, UINodeWrapper pWrapper)
	{
		mCache.put(pKey, pWrapper);
	}
	
	public void removeUINodeWrapper(String pKey)
	{
		mCache.remove(pKey);
	}

}
