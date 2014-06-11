/*<ORACLECOPYRIGHT>
 * Copyright (C) 1994-2012 Oracle and/or its affiliates. All rights reserved.
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 * UNIX is a registered trademark of The Open Group.
 *
 * This software and related documentation are provided under a license agreement
 * containing restrictions on use and disclosure and are protected by intellectual property laws.
 * Except as expressly permitted in your license agreement or allowed by law, you may not use, copy,
 * reproduce, translate, broadcast, modify, license, transmit, distribute, exhibit, perform, publish,
 * or display any part, in any form, or by any means. Reverse engineering, disassembly,
 * or decompilation of this software, unless required by law for interoperability, is prohibited.
 *
 * The information contained herein is subject to change without notice and is not warranted to be error-free.
 * If you find any errors, please report them to us in writing.
 *
 * U.S. GOVERNMENT RIGHTS Programs, software, databases, and related documentation and technical data delivered to U.S.
 * Government customers are "commercial computer software" or "commercial technical data" pursuant to the applicable
 * Federal Acquisition Regulation and agency-specific supplemental regulations.
 * As such, the use, duplication, disclosure, modification, and adaptation shall be subject to the restrictions and
 * license terms set forth in the applicable Government contract, and, to the extent applicable by the terms of the
 * Government contract, the additional rights set forth in FAR 52.227-19, Commercial Computer Software License
 * (December 2007). Oracle America, Inc., 500 Oracle Parkway, Redwood City, CA 94065.
 *
 * This software or hardware is developed for general use in a variety of information management applications.
 * It is not developed or intended for use in any inherently dangerous applications, including applications that
 * may create a risk of personal injury. If you use this software or hardware in dangerous applications,
 * then you shall be responsible to take all appropriate fail-safe, backup, redundancy,
 * and other measures to ensure its safe use. Oracle Corporation and its affiliates disclaim any liability for any
 * damages caused by use of this software or hardware in dangerous applications.
 *
 * This software or hardware and documentation may provide access to or information on content,
 * products, and services from third parties. Oracle Corporation and its affiliates are not responsible for and
 * expressly disclaim all warranties of any kind with respect to third-party content, products, and services.
 * Oracle Corporation and its affiliates will not be responsible for any loss, costs,
 * or damages incurred due to your access to or use of third-party content, products, or services.
 </ORACLECOPYRIGHT>*/

package atg.siebel.catalog.mapper;

public final class MappingConstants {

  private MappingConstants() {    
  }

  // standard siebel identity property
  public static final String SIEBEL_ID = "Id";
  public static final String SIEBEL_GENERATED_ID = "GeneratedId";
  
  // standard atg identity property
  public static final String ATG_ID = "id";
  
  
  // ATG item-desriptor names
  public static final String ITEM_DESC_PRICE = "price";
  
	// Product Type Constants
	public static final String PROD_TYPE_SIMPLE = "Simple Product";
	public static final String PROD_TYPE_SIMPLE_WITH_ATTR = "Simple Product With Attributes";
	public static final String PROD_TYPE_SIMPLE_BUNDLE = "Simple Product Bundle";
	public static final String PROD_TYPE_CONFIG = "Configurable Product";
	public static final String PROD_TYPE_PROMOTION = "Promotion";

	// Product Type Code Constants
	public static final String PROD_TYPE_CODE_PROMOTION = "Promotion";
	public static final String PROD_TYPE_CODE_OTHER = "Other";

	// Product Def Type Code Constants
	public static final String PROD_DEF_TYPE_CODE_NONE = "None";
	public static final String PROD_DEF_TYPE_CODE_BUNDLE = "Bundle";
	public static final String PROD_DEF_TYPE_CODE_CUST = "Customizable";

	// Siebel Action Codes
	public static final String ACTION_DELETE = "Delete";
	public static final String ACTION_UPSERT = "Upsert";
	public static final String ACTION_REFERENCE = "Reference";
	
	// Attribute name for the start SQL import file xml 
	public static final String ATTR_ATG_EXPORT_ITEM_ID = "ExportItemId";
	
	// Attribute names of Siebel entities in the Siebel export files (job files) 
  public static final String ATTR_ACTION_CODE = "action";  
  public static final String ATTR_EXPORT_ITEM_ID = "exportItemId";
  
  // Siebel Currency Codes
  public static final String CURRENCY_CODE_US = "USD";
  public static final String CURRENCY_CODE_EURO = "EUR";
  public static final String CURRENCY_CODE_YEN = "JPY";
  
  // ATG priceList.locale property values 
  public static final String LOCALE_US = "en_US";
  public static final String LOCALE_DE = "de_DE_EURO";
  public static final String LOCALE_JP = "ja_JP";
  
  // ATG pricingScheme property values in the price item-descriptor
  public static final String PRICING_SCHEME_LIST = "listPrice";
  public static final String PRICING_SCHEME_BULK = "bulkPrice";
  public static final String PRICING_SCHEME_TIERED = "tieredPrice";
  
  // Siebel property values for VolumeDiscount.DiscountMethod 
  public static final String DISCOUNT_METHOD_SIMPLE = "Simple";
  public static final String DISCOUNT_METHOD_TIERED = "Tiered";
  
  // Repository component strings
  public static final String REPOSITORY_PRODUCT_CATALOG = "/atg/commerce/catalog/ProductCatalog";
  public static final String REPOSITORY_PRICE_LIST = "/atg/commerce/pricing/priceLists/PriceLists";
	
}
