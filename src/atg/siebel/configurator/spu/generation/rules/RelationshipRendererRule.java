package atg.siebel.configurator.spu.generation.rules;

import atg.nucleus.GenericService;
import atg.siebel.catalog.SiebelCatalogRelationship;
import atg.siebel.configurator.spu.StructuredProductUIException;

public abstract class RelationshipRendererRule extends GenericService
{
  private String mRenderer;
  public String getRenderer()
  {
    return mRenderer;
  }
  public void setRenderer(String pRenderer)
  {
    mRenderer = pRenderer;
  }
  
  public abstract String determineRenderer(SiebelCatalogRelationship pRelationship)
    throws StructuredProductUIException;
}
