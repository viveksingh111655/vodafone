/**
 * $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/ProductConfigInstanceKey.java#1 $
 *
 * Created for the Oracle/ATG PoC at Swisscom in 2011.
 */
package atg.siebel.configurator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>The key for the ProductConfigInstance Map has the following structure:<br>
     prod_id , instance number<br>
     <br>
     Each level of nesting is reflected by appending the corresponding pair to the parent product key.<br>
     <br>
     <b>Example:</b><br>
     Given the following product structure:<br>
     </p>
     <pre>
     bundle
        -> root 1 
           -> child 1.1 (0)
              -> child 1.1.1 (0)
              -> child 1.1.1 (1)
           -> child 1.1 (1)
              -> child 1.1.1 (0)
              -> child 1.1.1 (1)
        -> root 2
           ....
     </pre>
     <p>      
     As we always only regard the subtree from a root_product the unique key for the 2nd sub-child-instance of the 2nd 
     child would look as follow (the children have a cardinality 2 relationship to their parents)
     </p>
     <pre>key= [(root_id_1|0),(child_id_1|1),(child_id_1|1)]</pre>
 *
 *  @author falco.knapp@cgi.com
 */
public class ProductConfigInstanceKey implements Cloneable{

    public class ProductInstancePair {
        public String productId;
        public int instanceNumber;
        
        public ProductInstancePair(String prodId, int instanceNumber) {
            this.productId = prodId;
            this.instanceNumber = instanceNumber;
        }
    }
    
    private List<ProductInstancePair> keyElements = new ArrayList<ProductInstancePair>();
    
    private static Pattern pattern = Pattern.compile("^\\[\\([^\\,\\|\\(\\[\\]\\)]++\\|\\d++\\){1}+(?:\\,\\([^\\,\\|\\(\\[\\]\\)]++\\|\\d++\\))*+\\]");

    public ProductConfigInstanceKey() {
    }
    
    public ProductConfigInstanceKey(String prodId, int instanceNumber) {
        keyElements.add(new ProductInstancePair(prodId, instanceNumber));
    }
    
    public void appendKeyElement(String prodId, int instanceNumber) {
        keyElements.add(new ProductInstancePair(prodId, instanceNumber));
    }
    
    /**
     * Gets a string and will generate the corresponding key with it. 
     * @param keyString
     * @return a key object if the string represents a valid key pattern, null in any other case.
     */
    public static ProductConfigInstanceKey createKeyFromString(String keyString) {
        Matcher matcher = pattern.matcher(keyString);
        
        if (!matcher.matches()) {
            return null;
        } 
        
        ProductConfigInstanceKey newKey = new ProductConfigInstanceKey();
        
        keyString = keyString.replace("[", "").replace("(", "").replace(")","").replace("]", "");
        String[] keys = keyString.split("\\,");
        for(String key : keys) {
            String [] parts = key.split("\\|");
            newKey.appendKeyElement(parts[0], Integer.valueOf(parts[1]));
        }
        
        return newKey;
    } 
    
    /** {@inheritDoc} */
    @Override
    public Object clone() {
        ProductConfigInstanceKey cloneKey = new ProductConfigInstanceKey();
        for (ProductInstancePair pair: keyElements) {
            cloneKey.appendKeyElement(pair.productId, pair.instanceNumber);
        }
        return cloneKey;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int count = 0;
        for (ProductInstancePair pair : keyElements) {
            builder.append("(");
            builder.append(pair.productId);
            builder.append("|");
            builder.append(pair.instanceNumber);
            builder.append(")");
            String end = ++count < keyElements.size() ? "," : "";
            builder.append(end);
        }
        builder.append("]");
        return builder.toString();
    }
    
    /**
     * Same as toString() but without brackets
     * @return
     */
    public String asSimpleString() {
      StringBuilder builder = new StringBuilder();
      builder.append("");
      for (ProductInstancePair pair : keyElements) {
          builder.append(pair.productId);
          builder.append(".");
          builder.append(pair.instanceNumber);
          builder.append(".");
      }
      builder.append("]");
      return builder.toString();
  }
    
    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.toString().hashCode();
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductConfigInstanceKey other = (ProductConfigInstanceKey) obj;
        if (keyElements == null) {
            if (other.keyElements != null)
                return false;
        } else if (!this.toString().equals(other.toString()))
            return false;
        return true;
    }
    
    /**
     * This method returns a hashCode representation of the key (used for instance in jsp for javascripts).
     * Be aware that using this hash doesn't allow to recover the key object.
     * @return
     */
    public String getHashCode() {
        return "" + Math.abs(this.toString().hashCode());
    }
    
    public int getInstanceNumber() {
    	if (keyElements == null || keyElements.isEmpty()) {
    		// ????
    		return 0;
    	}
    	return keyElements.get(keyElements.size()-1).instanceNumber;
    }
}
