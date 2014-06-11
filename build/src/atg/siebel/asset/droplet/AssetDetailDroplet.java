package atg.siebel.asset.droplet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import atg.servlet.DynamoServlet;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.siebel.asset.SiebelAsset;
import atg.siebel.asset.SiebelAssetTools;

import javax.servlet.ServletException;

public class AssetDetailDroplet extends DynamoServlet {
	// INPUT PARAMS
	public static final String INPUT_ASSET_ID = "assetId";
	
	// OUTPUT PARAMS
	public static final String OUTPUT_MESSAGE = "message";
	public static final String OUTPUT_SUCCESS = "success";
	public static final String OUTPUT_ASSET = "asset";
	
	// OPARAMS
	public static final String OPARAM_OUTPUT = "output";
	public static final String OPARAM_EMPTY = "empty";
	
	// -------------------------------------
	// Properties
	// -------------------------------------
	
	
	// -------------------------------------
	// property: siebelAssetTools
	private SiebelAssetTools sblAssetTools;
	
	public SiebelAssetTools getSiebelAssetTools() {
		return sblAssetTools;
	}
	public void setSiebelAssetTools(SiebelAssetTools _sblAssetTools) {
		sblAssetTools = _sblAssetTools;
	}	

	/** {@inheritDoc} */
	@Override
	public void service(DynamoHttpServletRequest req,
			DynamoHttpServletResponse res) throws ServletException, IOException {

		String assetId;
			
		if (null != req.getParameter(INPUT_ASSET_ID)) {
			assetId = (String)req.getParameter(INPUT_ASSET_ID);
		} else {
			throw new ServletException("No assetId specified!!");
		}
		
		if(isLoggingDebug()) {
            logDebug("assetId is: " + assetId);
		}
		
		SiebelAsset asset = null;
		
		try
		{
			asset = sblAssetTools.getAssetDetail(assetId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if (null == asset) {
			req.serviceParameter(OPARAM_EMPTY, req, res);
		} else {
			req.setParameter(OUTPUT_ASSET, asset);
			req.serviceParameter(OPARAM_OUTPUT, req, res);
		}
		
	}

}
