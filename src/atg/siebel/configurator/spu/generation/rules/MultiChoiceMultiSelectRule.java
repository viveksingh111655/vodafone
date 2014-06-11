package atg.siebel.configurator.spu.generation.rules;

import atg.siebel.catalog.SiebelCatalogRelationship;
import atg.siebel.configurator.spu.StructuredProductUIException;

public class MultiChoiceMultiSelectRule extends RelationshipRendererRule
{

  @Override
  public String determineRenderer(SiebelCatalogRelationship pRelationship)
      throws StructuredProductUIException
  {
    if(pRelationship.getChildProducts().size()>1 &&
        pRelationship.getMaximumCardinality()>1)
      return getRenderer();
    
    return null;
  }
}
