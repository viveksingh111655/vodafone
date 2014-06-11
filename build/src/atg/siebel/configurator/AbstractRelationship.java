package atg.siebel.configurator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import atg.nucleus.logging.ApplicationLogging;
import atg.nucleus.logging.ClassLoggingFactory;

/**
 * Abstract class that represents properties and behaviour common to all relationship types.
 *
 * @author Bernard Brady
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/AbstractRelationship.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public abstract class AbstractRelationship {

	// -------------------------------------
	/** Class version string */
	public static final String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/AbstractRelationship.java#1 $$Change: 842106 $";
	
	/** Logger */
	protected static ApplicationLogging mLogger = 
	    ClassLoggingFactory.getFactory().getLoggerForClass(AbstractRelationship.class);

	// -------------------------------------
	// Constants
	// -------------------------------------

	// -------------------------------------
	// Member variables
	// -------------------------------------
	private Integer mMinimumQuantity = 0;
	private Integer mMaximumQuantity = 0;
	private Integer mDefaultQuantity = 0;
	private String mRelationshipId;
	private Integer mQuantity = 0;
	private final BaseConfigInstance mParentInstance;
	


	// -------------------------------------
	// Properties
	// -------------------------------------

	// -------------------------------------
	// property: message
	private String mMessage;
	public String getMessage() {
		return mMessage;
	}
	public void setMessage(String pMessage) {
		mMessage = pMessage;
	}

	
	// -------------------------------------
	// property: childInstances
	protected List<ProductConfigInstance> mChildInstances;

	public ProductConfigInstance[] getChildInstances() {
		return mChildInstances.toArray(new ProductConfigInstance[mChildInstances.size()]);
	}
	public void setChildInstances(
			ProductConfigInstance[] pChildInstances) {
		if (pChildInstances != null) {
			mChildInstances = new ArrayList<ProductConfigInstance>(Arrays.asList(pChildInstances));
		} else {
			mChildInstances = new ArrayList<ProductConfigInstance>();
		}
	}
	
	// -------------------------------------
	// property: displayName
	protected String mDisplayName;
	public String getDisplayName() {
		return mDisplayName;
	}
	public void setDisplayName(String pDisplayName) {
		this.mDisplayName = pDisplayName;
	}
	
	
	// -------------------------------------
	// property: domainProducts
	protected DomainProduct[] mDomainProducts;
	public DomainProduct[] getDomainProducts() {
		return mDomainProducts;
	}
	public void setDomainProducts(DomainProduct[] pDomainProducts) {
		mDomainProducts = pDomainProducts;
	}
	
	
	// -------------------------------------
	// property: defaultProductId
	protected String mDefaultProductId;
	public String getDefaultProductId() {
		return mDefaultProductId;
	}
	public void setDefaultProductId(String pDefaultProductId) {
		this.mDefaultProductId = pDefaultProductId;
	}
	
	
	// -------------------------------------
	// property: productLineId
	protected String mProductLineId;
	public String getProductLineId() {
		return mProductLineId;
	}
	public void setProductLineId(String pProductLineId) {
		this.mProductLineId = pProductLineId;
	}
	
	
	// -------------------------------------
	// property: defaultProductId
	protected String mDefaultProductName;
	public String getDefaultProductName() {
		return mDefaultProductName;
	}
	public void setDefaultProductName(String pDefaultProductName) {
		this.mDefaultProductName = pDefaultProductName;
	}


	/**
	 * @param pInstance
	 * @param pId
	 * @param pMinQuantity
	 * @param pMaxQuantity
	 * @param pDefaultQuantity
	 */
	public AbstractRelationship(
			BaseConfigInstance pInstance,
			String pId,
			String pMinQuantity, 
			String pMaxQuantity,
			String pDefaultQuantity) {

		mParentInstance = pInstance;
		setCardinality(pMinQuantity, pMaxQuantity, pDefaultQuantity);
		mQuantity = mDefaultQuantity;
		mRelationshipId = pId;
		mChildInstances = new ArrayList<ProductConfigInstance>(mMaximumQuantity);		
		mMessage = "";
	}
	
	
	/**
	 * @param pInstance
	 * @param pId
	 * @param pMinQuantity
	 * @param pMaxQuantity
	 * @param pDefaultQuantity
	 */
	public AbstractRelationship(
			BaseConfigInstance pInstance,
			String pId,
			Integer pMinQuantity, 
			Integer pMaxQuantity,
			Integer pDefaultQuantity) {

		mParentInstance = pInstance;
		mMinimumQuantity = pMinQuantity;
		mMaximumQuantity = pMaxQuantity;
		mDefaultQuantity = pDefaultQuantity;
		mQuantity = mDefaultQuantity;
		mRelationshipId = pId;
		mChildInstances = new ArrayList<ProductConfigInstance>(mMaximumQuantity);
		mMessage = "";
	}


	/**
	 * @return the mDefault
	 */
	public Integer getDefaultQuantity() {
		return mDefaultQuantity;
	}
	
	
	/**
	 * @return the mRelationshipId
	 */
	public String getId() {
		return mRelationshipId;
	}
	

	/**
	 * @return the mQuantity
	 */
	public Integer getQuantity() {	
		if (getDomainProductCount() > 0) {
			mQuantity = 0;
			for (int i=0; i<getDomainProducts().length; i++) {
				mQuantity += getDomainProducts()[i].getQuantity();
			}
		}
		else
		{
			//if we have an instance, but the quantity is set to zero, 
			//then we're deleting an instance. Return zero
			if(!mChildInstances.isEmpty())
			{
				if(mQuantity==0)
					return new Integer(0);
				else if(mQuantity==mChildInstances.size())
				  mQuantity=new Integer(mChildInstances.size());
					
			}
		}
		return mQuantity;
	}
	
	
	/**
	 * @param pQuantity
	 */
	public void setQuantity(Integer pQuantity) {
		mQuantity = pQuantity;
	}

	
	/**
	 * @return the mMinimum
	 */
	public Integer getMinimumQuantity() {
		return mMinimumQuantity;
	}

	
	/**
	 * @return the mMaximum
	 */
	public Integer getMaximumQuantity() {
		return mMaximumQuantity;
	}
	
	
	/**
	 * @return the displayname of this product relationship
	 */
	public String getProductName() {
		return mDisplayName;
	}


	/**
	 * Convenience method to assign cardinalities
	 * 
	 * @param pMinQuantity - min cardinality
	 * @param pMaxQuantity - max cardinality
	 * @param pDefaultQuantity - default cardinality
	 */
	protected void setCardinality(String pMinQuantity, String pMaxQuantity,
			String pDefaultQuantity) {

		// Sometimes only max and min are set 
		try {
			mMinimumQuantity = Integer.valueOf(pMinQuantity);
		} catch (NumberFormatException nfe) {
		}
		
		try {
			mDefaultQuantity = Integer.valueOf(pDefaultQuantity);
		} catch (NumberFormatException nfe) {
			mDefaultQuantity = mMinimumQuantity;
		}
		
		try {
			mMaximumQuantity = Integer.valueOf(pMaxQuantity);
		} catch (NumberFormatException nfe) {
			mMaximumQuantity = mDefaultQuantity;
		}
	}
	
	
	/**
	 * Validates that the quantity property value lies within
	 * the permitted cardinalities
	 * 
	 * @return true if the quantity property value is ok otherwise false.
	 */
	public boolean validateQuantity() {
		if (getQuantity() > mMaximumQuantity || getQuantity() < mMinimumQuantity) {
			mMessage = "Invalid quantity ["+getQuantity()+
					"], must be greater than "+mMinimumQuantity+
					" and less than "+mMaximumQuantity;
			return false;
		}
		return true;
	}
	

	/**
	 * @return - the parent instance
	 */
	public BaseConfigInstance getParentInstance() {
		return mParentInstance;
	}
	
	
	/**
	 * Derives the next instance number to be used in defining
	 * the next PCI's key value.
	 * 
	 * @param productId - product id of next child
	 * @return
	 */
	protected int getNextInstanceNumber(String productId) {
		int nextId = 0;
		for (ProductConfigInstance instance : getChildInstances()) {
			if (instance.getProductId().equals(productId)) {
				nextId = instance.getKey().getInstanceNumber()+1;
			}
			
		}
		return nextId++;
	}


	/**
	 * @return the number of domain products in this relationship
	 */
	public int getDomainProductCount() {
		return (mDomainProducts == null) ? 0 : mDomainProducts.length;
	}
	
	
	/**
	 * @return the number of child product instances in this relationship
	 */
	public int getChildInstanceCount() {
		return (mChildInstances == null) ? 0 : mChildInstances.size();
	}
	
	
	/**
	 * @param pProductConfigInstance
	 * @throws ConfiguratorException 
	 */
	public void removeChildInstance(BaseConfigInstance pProductConfigInstance) {	  
	  // Default behaviour does nothing
	}
	
	
	/**
	 * Simple bean class that represents a domain child product definition
	 * 
	 * @author bbrady
	 *
	 */
	public class DomainProduct {
		private String mId = null;
		private String mName = null;
		private Integer mDefaultCardinality = 0;
		private Integer mQuantity = 0;
    // property to identify excluded products
    private boolean isExcluded = false;

    public boolean isExcluded() {
      return isExcluded;
    }

    public void setExcluded(boolean isExcluded) {
      this.isExcluded = isExcluded;
    }
    /**
		 * initially return the previously set quantity, but if we have child 
		 * product instances, count them and return them
		 * 
		 * @return
		 */
		public Integer getQuantity() {
			
			int childProductCount = 0;
			
			if(AbstractRelationship.this.getChildInstances() != null)
			{
				for(ProductConfigInstance instance : AbstractRelationship.this.getChildInstances())
				{
					if(instance.getProductId().equals(this.getId()))
					{
						childProductCount++;
					}
				}
			}
			
			//return which ever is greater
			return mQuantity>childProductCount?mQuantity:childProductCount;
		}
		public void setQuantity(Integer pQuantity) {
			this.mQuantity = pQuantity;
		}
		
		public String getId() {
			return mId;
		}
		public void setId(String pId) {
			this.mId = pId;
		}
		
		public String getName() {
			return mName;
		}
		public void setName(String pName) {
			this.mName = pName;
		}
		
		public Integer getDefaultCardinality() {
			return mDefaultCardinality;
		}
		public void setDefaultCardinality(Integer pDefaultCardinality) {
			this.mDefaultCardinality = pDefaultCardinality;
		}
		
	}

	
	
	@Override
	public String toString() {
		return "Relationship {\n\tmMinimumQuantity=" + mMinimumQuantity
				+ "\n\tmMaximumQuantity=" + mMaximumQuantity
				+ "\n\tmDefaultQuantity=" + mDefaultQuantity + "\n\tmId=" + mRelationshipId
				+ "\n\tmQuantity=" + mQuantity + "\n\tmMessage=" + mMessage
				+ "\n\tmChildInstances=" + mChildInstances
				+ "\n\tmDisplayName=" + mDisplayName + "\n\tmDomainProducts="
				+ mDomainProducts + "\n\tmDefaultProductId="
				+ mDefaultProductId + "\n\tmDefaultProductName="
				+ mDefaultProductName + "]\n}";
	}
}
