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

import javax.servlet.ServletException;

import atg.adapter.integrations.IntegrationRepository;
import atg.commerce.profile.CommerceProfileFormHandler;
import atg.droplet.DropletException;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryException;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;

/**
 * This form handler extends the CommerceProfileFormHandler so that the cart is refreshed when
 * the logged in user is changed. Also, it uses the integration
 * repository for updates so that changes are transferred to the Siebel system.
 * 
 * @author Paul O'Brien
 * @version $Id: //dev/horizon/main/projects/Siebel/src/atg/siebel/account/
 *          SiebelProfileFormHandler.java#6 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */

public class SiebelProfileFormHandler
  extends CommerceProfileFormHandler {

  // -------------------------------------
  /** Class version string */
  public static final String CLASS_VERSION =
    "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/account/SiebelProfileFormHandler.java#1 $$Change: 842106 $";

  // --------- Property: SiebelAccountRepository -----------
  protected IntegrationRepository mSiebelAccountRepository;

  /**
   * Sets the property SiebelAccountRepository. description: the
   * SiebelAccountRepository used to interact with the remote Siebel instance
   */
  public void setSiebelAccountRepository(
    IntegrationRepository pSiebelAccountRepository) {
    mSiebelAccountRepository = pSiebelAccountRepository;
  }

  /**
   * @return The value of the property SiebelAccountRepository.
   */
  public IntegrationRepository getSiebelAccountRepository() {
    return mSiebelAccountRepository;
  }

  /**
   * Operation called just after the user's password is changed. It sets the
   * entered login value on the profile and security status.
   * 
   * @param pRequest
   *          the servlet's request
   * @param pResponse
   *          the servlet's response
   * @exception ServletException
   *              if there was an error while executing the code
   * @exception IOException
   *              if there was an error with servlet io
   */
  protected void preChangePassword(DynamoHttpServletRequest pRequest,
    DynamoHttpServletResponse pResponse) throws ServletException, IOException {

    String loginPropertyName =
      getProfileTools().getPropertyManager().getLoginPropertyName();
    getProfile().setPropertyValue(loginPropertyName,
      getValue().get(loginPropertyName));
    String securityStatusPropertyName = 
      getProfileTools().getPropertyManager().getSecurityStatusPropertyName();
    getProfile().setPropertyValue(securityStatusPropertyName, 
      getProfileTools().getPropertyManager().getSecurityStatusLogin());
  
  }

  /**
   * Override updateUser/3 to use the integration repository, which will
   * synchronize the update with the Siebel system.
   */
  /**
   * This will replace the method below when the change to ProfileForm makes it
   * out into the wild.
   * 
   * <code>
  protected void updateUser(DynamoHttpServletRequest pRequest,
    DynamoHttpServletResponse pResponse) throws ServletException, IOException {
    // this get..() was replaced to get the IR instead of ProfileRepository
    MutableRepository repository = getSiebelAccountRepository();
    
    updateUser(pRequest, pResponse, repository);
  }
  * </code>
   */

  /**
   * Override updateUser to use the integration repository, which will
   * synchronize the update with the Siebel system.
   */
  protected void updateUser(DynamoHttpServletRequest pRequest,
    DynamoHttpServletResponse pResponse) throws ServletException, IOException {
    try {
      MutableRepository repository = getSiebelAccountRepository();
      MutableRepositoryItem mutableItem =
        repository.getItemForUpdate(getRepositoryId(), getCreateProfileType());

      // check required submit properties
      if (getCheckForRequiredParameters())
        checkForRequiredParameters(pRequest, pResponse);

      if (!getFormError()) {
        // Update the profile with the current form submission values
        updateProfileAttributes(mutableItem, getValue(), pRequest, pResponse);
        if (isUsingLdapProfile())
          updateLDAPProfileAttributes(mutableItem);

        // Check for any missing required properties
        if (getCheckForRequiredPropertiesAfterUpdate()) {
          checkForRequiredProperties(mutableItem, pRequest, pResponse);
        }

        if (!getFormError()) {
          if (isLoggingDebug())
            logDebug("updating the item in the repository");
          // save the update into the Repository
          repository.updateItem(mutableItem);
        }
      }
    } catch (RepositoryException exc) {
      addFormException(new DropletException(formatUserMessage(
        MSG_ERR_UPDATING_PROFILE, pRequest), exc, MSG_ERR_UPDATING_PROFILE));
      if (isLoggingError())
        logError(exc);
    }
  }
}
