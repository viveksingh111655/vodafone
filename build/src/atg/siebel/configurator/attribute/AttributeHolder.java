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

package atg.siebel.configurator.attribute;

import atg.nucleus.logging.ApplicationLogging;
import atg.nucleus.logging.ClassLoggingFactory;



/**
 * Lightweight Class that encapsulates a Siebel attribute - for use with UI
 *
 * @author bbrady
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/attribute/AttributeHolder.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class AttributeHolder {
  
  /** Class version string */
  public static final String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/attribute/AttributeHolder.java#1 $$Change: 842106 $";
  /** Logger */
  protected static ApplicationLogging mLogger = ClassLoggingFactory
      .getFactory().getLoggerForClass(AttributeHolder.class);
  
  //-------------------------------------
  // Constants
  //-------------------------------------


  //-------------------------------------
  // Member variables
  //-------------------------------------
  final private String mId;
  final private String mName;
  final private String mDomainType;
  final private String mValueType;
  final private boolean mRequired;
  final private boolean mHidden;
  final private boolean mReadOnly;
  
    
  //-------------------------------------
  // Properties
  //-------------------------------------

  private String mValue;
  
  public void setValue(String pValue) {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug(
          "Entered setValue() : pValue = [" + pValue+"] "+toString());
    }
    mValue = pValue;
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug(
          "setValue() successful : "+toString());
    }
  }
  
  public String getValue() {
    return mValue;
  }
  

  
  //-------------------------------------
  // Constructors
  //-------------------------------------

  //-------------------------------------  

  
  /**
   * Copy constructor
   * 
   * @param pAttribute
   */
  public AttributeHolder(Attribute pAttribute) {
    mId = pAttribute.getId();
    mName = pAttribute.getName(); 
    mDomainType = pAttribute.getDomainType();
    mValueType = pAttribute.getValueType();
    mRequired = pAttribute.getRequired();
    mHidden = pAttribute.getHidden();
    mReadOnly = pAttribute.getReadOnly();
    mValue = pAttribute.getValue();
  }
  
  
  //-------------------------------------
  // Methods
  //-------------------------------------

  //-------------------------------------
  public String getName() {
    return mName;
  }

  public String getDomainType() {
    return mDomainType;
  }
  
  public String getValueType() {
    return mValueType;
  }
 
  public String getId() {
    return mId;
  }

  public boolean isRequired() {
    return mRequired;
  }

  public boolean isHidden() {
    return mHidden;
  }

  public boolean isReadOnly() {
    return mReadOnly;
  }

  @Override
  public String toString() {
    return "AttributeHolder [mName=" + mName + ", mType=" + mDomainType + ", mId=" + mId
        + ", mValue=" + mValue + ", mRequired=" + mRequired + ", mHidden="
        + mHidden + ", mReadOnly=" + mReadOnly;
  }
  
}