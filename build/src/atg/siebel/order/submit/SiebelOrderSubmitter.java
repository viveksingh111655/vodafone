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
package atg.siebel.order.submit;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.xml.ws.soap.SOAPFaultException;

import atg.commerce.CommerceException;
import atg.commerce.order.Order;
import atg.commerce.order.OrderManager;
import atg.commerce.states.OrderStates;
import atg.commerce.states.StateDefinitions;
import atg.dtm.TransactionDemarcation;
import atg.dtm.TransactionDemarcationException;
import atg.nucleus.logging.ApplicationLogging;
import atg.nucleus.logging.ClassLoggingFactory;
import atg.siebel.integration.WebServiceHelper;
import atg.siebel.order.SiebelOrderTools;
import atg.siebel.states.SiebelOrderStates;

import com.siebel.ordermanagement.quote.quoting.ExecuteQuotingOutput;

/**
 * This class calls siebel to submit the order and updates the order in the 
 * repository
 *
 * @author Gary McDowell
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/order/submit/SiebelOrderSubmitter.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class SiebelOrderSubmitter implements Runnable
{
  //----------------------------------------------------------------------------------
  // Class version string
  //----------------------------------------------------------------------------------

  public static String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/order/submit/SiebelOrderSubmitter.java#1 $$Change: 842106 $";

  /** Logger */
  protected static ApplicationLogging mLogger = ClassLoggingFactory
      .getFactory().getLoggerForClass(SiebelOrderSubmitter.class);
  
  //order states
  private OrderStates mOrderStates = StateDefinitions.ORDERSTATES;
  
  private Order mOrder;
  private OrderManager mOrderManager;
  private TransactionManager mTransactionManager;
  
  
  /**
   * Constructor sets instance members on the object
   * 
   * @param pOrder
   */
  SiebelOrderSubmitter(Order pOrder, 
      WebServiceHelper pWebServiceHelper,
      OrderManager pOrderManager,
      TransactionManager pTransactionManager)
  {
    mOrder = pOrder;
    mOrderManager = pOrderManager;
    mTransactionManager = pTransactionManager;
  }
  
  /**
   * This method creates a transaction, calls Siebel to submit the order,
   * then updates the order status and commits the transaction
   */
  public void run()
  {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("submitting order " + mOrder + " from a new thread");
    }
    
    //create a new transaction, submit the order to siebel, set the status to
    //processed, then end the transaction
    
    TransactionDemarcation td = new TransactionDemarcation();
    
    try
    {
      td.begin(mTransactionManager, TransactionDemarcation.REQUIRED);
    } 
    catch (TransactionDemarcationException tde)
    {
      if(mLogger.isLoggingError())
        mLogger.logError(tde);
      return;
    }
    
    try
    {
      ((SiebelOrderTools) mOrderManager.getOrderTools()).syncOrderToSiebel(mOrder);
      mOrder.setState(mOrderStates.getStateValue(SiebelOrderStates.SUBMITTED));
      mOrderManager.updateOrder(mOrder);
    }
    catch (CommerceException e)
    {
      setRollBack();
    }
    //end the transaction
    finally
    {
      try 
      {
        td.end();
      }
      catch (TransactionDemarcationException tde) 
      {
        if(mLogger.isLoggingError())
          mLogger.logError(tde);
      }
    }
  }
  
  /**
   * @param pException
   * @return
   */
  protected void produceResult(SOAPFaultException pException) 
  {
    if (mLogger.isLoggingError()) {
      mLogger.logError(pException);
    }
    return;
  }
  
  /**
   * @param pException
   * @return
   */
  protected void produceResult(ExecuteQuotingOutput pOutput) {
    
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug(pOutput.toString());
    }
    return;
  }
  
  /**
   * marks the transaction as rollback
   */
  private void setRollBack()
  {
    try
    {
      mTransactionManager.getTransaction().setRollbackOnly();
    } 
    catch (IllegalStateException ise)
    {
      if(mLogger.isLoggingError())
        mLogger.logError(ise);
    } 
    catch (SystemException se)
    {
      if(mLogger.isLoggingError())
        mLogger.logError(se);
    }
  }
}
