package atg.siebel.configurator.spu.generation;

import java.io.IOException;

import javax.servlet.ServletException;

import atg.beans.PropertyNotFoundException;
import atg.repository.RepositoryException;
import atg.repository.servlet.RepositoryFormHandler;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.siebel.catalog.SiebelCatalogProduct;
import atg.siebel.catalog.SiebelCatalogTools;
import atg.siebel.configurator.spu.StructuredProductUIException;

/**
 * Form handler which accepts a Product ID, and creates a product ui for it
 *
 * @author Gary McDowell
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/spu/generation/GenerateProductUIFormHandler.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class GenerateProductUIFormHandler extends RepositoryFormHandler
{
  // -------------------------------------
  // Properties
  // -------------------------------------
   
  // -------------------------------------
  // property: siebelCatalogTools
  protected SiebelCatalogTools mSiebelCatalogTools;
  
  public SiebelCatalogTools getSiebelCatalogTools()
  {
    return mSiebelCatalogTools;
  }
  
  public void setSiebelCatalogTools(SiebelCatalogTools pSiebelCatalogTools)
  {
    mSiebelCatalogTools = pSiebelCatalogTools;
  }  
  
  //-------------------------------------
  // property: siebelCatalogTools
  protected ProductUIBuilder mProductUIBuilder;
  
  public ProductUIBuilder getProductUIBuilder()
  {
    return mProductUIBuilder;
  }
  public void setProductUIBuilder(
      ProductUIBuilder pProductUIBuilder)
  {
    mProductUIBuilder = pProductUIBuilder;
  }
  
  /**
   * the product id for which to generate a new ui
   */
  protected String mProductId;
  public String getProductId()
  {
    return mProductId;
  }
  public void setProductId(String pProductId)
  {
    mProductId = pProductId;
  }
  
  public boolean handleGenerateUI(DynamoHttpServletRequest pRequest, DynamoHttpServletResponse pResponse)
      throws ServletException, IOException
  {
    
    if(getProductId()!=null&&!getProductId().isEmpty())
    {
      try
      {
        SiebelCatalogProduct product = getSiebelCatalogTools().getProductDetails(getProductId());
        
        getProductUIBuilder().buildProductUI(product);
      } 
      catch (RepositoryException e)
      {
        throw new ServletException(e);
      } 
      catch (PropertyNotFoundException e)
      {
        throw new ServletException(e);
      } 
      catch (StructuredProductUIException e)
      {
        throw new ServletException(e);
      }
    }
    else
    {
      throw new ServletException("No Product Id provided");
    }
    
    return checkFormSuccess(pRequest.getRequestURI(), pRequest, pResponse);
  }
}
