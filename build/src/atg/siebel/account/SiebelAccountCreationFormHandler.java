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

package atg.siebel.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;

import atg.commerce.order.OrderHolder;
import atg.core.i18n.LayeredResourceBundle;
import atg.droplet.DropletException;
import atg.droplet.GenericFormHandler;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.siebel.profile.SiebelProfileTools;
import atg.userprofiling.Profile;

/**
 * The Class is for creation of a new siebel account
 * 
 * @author shaikuku
 * 
 */
public class SiebelAccountCreationFormHandler extends GenericFormHandler {

  // -------------------------------------
  /** Class version string */
  public static String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/account/SiebelAccountCreationFormHandler.java#1 $$Change: 842106 $";

  // -------------------------------------
  /** ResourceBundle **/
 // ResourceBundle
  private static ResourceBundle sResourceBundle = LayeredResourceBundle
      .getBundle(SiebelAccountConstants.SIEBEL_WEBAPP_RESOURCE_BUNDLE_NAME,
          atg.service.dynamo.LangLicense.getLicensedDefault());

  /** Form properties **/

  // Account name
  private String mAccountName;
  // Account main phone number
  private String mAccountMainPhoneNumber;
  // First name of the user
  private String mFirstName;
  // Last name of the user
  private String mLastName;
  // Success URL
  private String mCreateOrganizationSuccessURL;
  // Failure URL
  private String mCreateOrganizationErrorURL;

  /** helper objects **/

  // SiebelAccountTools
  private SiebelAccountTools mSiebelAccountTools;
  // Profile
  private Profile mProfile;
  // SiebelProfileTools
  private SiebelProfileTools mProfileTools;
  // OrderHolder (shopping cart)
  private OrderHolder mShoppingCart;

  // ------------getters and setters-------

  /**
   * @return the mProfile
   */
  public Profile getProfile() {
    return mProfile;
  }

  /**
   * @param pProfile
   *          the mProfile to set
   */
  public void setProfile(Profile pProfile) {
    this.mProfile = pProfile;
  }

  /**
   * @return the mAccountName
   */
  public String getAccountName() {
    return mAccountName;
  }

  /**
   * @param pAccountName
   *          the mAccountName to set
   */
  public void setAccountName(String pAccountName) {
    this.mAccountName = pAccountName;
  }

  /**
   * @return the mAccountMainPhoneNumber
   */
  public String getAccountMainPhoneNumber() {
    return mAccountMainPhoneNumber;
  }

  /**
   * @param pAccountMainPhoneNumber
   *          the mAccountMainPhoneNumber to set
   */
  public void setAccountMainPhoneNumber(String pAccountMainPhoneNumber) {
    this.mAccountMainPhoneNumber = pAccountMainPhoneNumber;
  }

  /**
   * @return the mFirstName
   */
  public String getFirstName() {
    return mFirstName;
  }

  /**
   * @param pFirstName
   *          the mFirstName to set
   */
  public void setFirstName(String pFirstName) {
    this.mFirstName = pFirstName;
  }

  /**
   * @return the mLastName
   */
  public String getLastName() {
    return mLastName;
  }

  /**
   * @param pLastName
   *          the mLastName to set
   */
  public void setLastName(String pLastName) {
    this.mLastName = pLastName;
  }

  /**
   * @return the mSiebelAccountTools
   */
  public SiebelAccountTools getSiebelAccountTools() {
    return mSiebelAccountTools;
  }

  /**
   * @param pSiebelAccountTools
   *          the mSiebelAccountTools to set
   */
  public void setSiebelAccountTools(SiebelAccountTools pSiebelAccountTools) {
    this.mSiebelAccountTools = pSiebelAccountTools;
  }

  /**
   * @return the mCreteOrganizationSuccessURL
   */
  public String getCreateOrganizationSuccessURL() {
    return mCreateOrganizationSuccessURL;
  }

  /**
   * @param pCreateOrganizationSuccessURL
   *          the mCreateOrganizationSuccessURL to set
   */
  public void setCreateOrganizationSuccessURL(String pCreateOrganizationSuccessURL) {
    this.mCreateOrganizationSuccessURL = pCreateOrganizationSuccessURL;
  }

  /**
   * @return the mCreateOrganizationErrorURL
   */
  public String getCreateOrganizationErrorURL() {
    return mCreateOrganizationErrorURL;
  }

  /**
   * @param pCreateOrganizationErrorURL
   *          the mCreateOrganizationErrorURL to set
   */
  public void setCreateOrganizationErrorURL(String pCreateOrganizationErrorURL) {
    this.mCreateOrganizationErrorURL = pCreateOrganizationErrorURL;
  }

  /**
   * @return the mProfileTools
   */
  public SiebelProfileTools getProfileTools() {
    return mProfileTools;
  }

  /**
   * @param pProfileTools
   *          the mProfileTools to set
   */
  public void setProfileTools(SiebelProfileTools pProfileTools) {
    mProfileTools = pProfileTools;
  }

 
  /**
   * @return the mShoppingCart
   */
  
  public OrderHolder getShoppingCart() {
    return mShoppingCart;
  }
  

  /**
   * @param pShoppingCart
   *          the mShoppingCart to set
   */
  
  public void setShoppingCart(OrderHolder pShoppingCart) {
    mShoppingCart = pShoppingCart;
  }
  
  

  // ----Methods----------------------

  /**
   * This method creates an account in Siebel and sync the account with local repository
   * @param pRequest
   * @param pResponse
   * @return
   * @throws ServletException
   * @throws IOException
   */
  public boolean handleCreateAccount(DynamoHttpServletRequest pRequest,
      DynamoHttpServletResponse pResponse) throws ServletException, IOException {
    try {
      // creating intermediate input beam
      SiebelAccountInputBean accountBean = new SiebelAccountInputBean();
      accountBean.setAccountName(getAccountName());
      accountBean.setAccountMainPhoneNumber(getAccountMainPhoneNumber());
      accountBean.setUserFirstName(getFirstName());
      accountBean.setUserLastName(getLastName());
      // create account at Siebel side and update the Profile adapter Repository
      RepositoryItem[] accounts = getSiebelAccountTools()
          .createAccountInSiebel(accountBean);

      // sync the session profile
      if (accounts != null && accounts.length > 0) {
        Map<String, String> userQueryConstraint = new HashMap<String, String>();
        userQueryConstraint.put("firstName", getFirstName());
        userQueryConstraint.put("lastName", getLastName());
        userQueryConstraint.put("siebelId", accountBean.getSiebelContactId());
        getSiebelAccountTools().updateSeesionProfile(getProfile(),
            userQueryConstraint, accounts[0]);

      } else {
        addFormException(new DropletException(
            sResourceBundle.getString(SiebelAccountConstants.SIEBEL_GENERIC_EXCEPTION_MESSAGE)));
      }

      getProfileTools().persistShoppingCarts(getProfile(), getShoppingCart());

    } catch (RepositoryException rpex) {

      addFormException(new DropletException(
          sResourceBundle.getString(SiebelAccountConstants.SIEBEL_GENERIC_EXCEPTION_MESSAGE)));
      if (isLoggingError()) {
        logError(rpex);
      }

    } catch (Exception ex) {
      addFormException(new DropletException(ex.getMessage()));
      if (isLoggingError()) {
        logError(ex);
      }

    }

    // If form errors are found, redirect to the error URL else redirect to
    // successURL.
    return checkFormRedirect(getCreateOrganizationSuccessURL(), getCreateOrganizationErrorURL(),
        pRequest, pResponse);

  }

}
