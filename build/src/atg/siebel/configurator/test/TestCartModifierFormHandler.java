package atg.siebel.configurator.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import atg.commerce.catalog.CatalogTools;
import atg.commerce.order.purchase.CartModifierFormHandler;
import atg.droplet.GenericFormHandler;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;

public class TestCartModifierFormHandler extends GenericFormHandler {

  // --------- Property: productId --------------
  String mProductId;

  public String getProductId() {
    return mProductId;
  }

  public void setProductId(String pProductId) {
    mProductId = pProductId;
  }

  // --------- Property: catalogTools -----------
  protected CatalogTools mCatalogTools;

  public void setCatalogTools(CatalogTools pCatalogTools) {
    mCatalogTools = pCatalogTools;
  }

  public CatalogTools getCatalogTools() {
    return mCatalogTools;
  }

  // --------- Property: cartModifierFormHandler -----------
  protected CartModifierFormHandler mCartModifierFormHandler;

  public void setCartModifierFormHandler(
      CartModifierFormHandler pCartModifierFormHandler) {
    mCartModifierFormHandler = pCartModifierFormHandler;
  }

  public CartModifierFormHandler getCartModifierFormHandler() {
    return mCartModifierFormHandler;
  }

  public boolean handleAddItemToOrder(DynamoHttpServletRequest pRequest,
      DynamoHttpServletResponse pResponse) throws ServletException, IOException {

    String productId = getProductId();
    String[] skuIds = null;

    if (productId == null) {
      setProductId("");
      return true;
    }
    try {
      RepositoryItem productItem = getCatalogTools().findProduct(productId);
      if (productItem == null) {
        setProductId("");
        return true;
      }
      List skus = (List) productItem.getPropertyValue("childSKUs");
      if (skus != null && !skus.isEmpty()) {
        RepositoryItem sku = (RepositoryItem) skus.get(0);
        skuIds = new String[1];
        skuIds[0] = sku.getRepositoryId();
      } else {
        setProductId("");
        return true;
      }
    } catch (RepositoryException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      setProductId("");
      return true;
    }

    // String[] productArray = new String[] { productId };
    getCartModifierFormHandler().setCatalogRefIds(skuIds);
    getCartModifierFormHandler().setProductId(productId);
    return getCartModifierFormHandler().handleAddItemToOrder(pRequest, pResponse);
  }

}
