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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.transaction.SystemException;

import atg.commerce.CommerceException;
import atg.commerce.fulfillment.SubmitOrder;
import atg.commerce.messaging.CommerceMessage;
import atg.commerce.messaging.SourceSinkTemplate;
import atg.commerce.order.Order;
import atg.commerce.states.OrderStates;
import atg.commerce.states.StateDefinitions;
import atg.dtm.TransactionDemarcation;
import atg.dtm.TransactionDemarcationException;
import atg.nucleus.ServiceException;
import atg.repository.Query;
import atg.repository.QueryBuilder;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.repository.RepositoryView;
import atg.siebel.integration.WebServiceHelper;
import atg.siebel.order.SiebelOrderManager;
import atg.siebel.states.SiebelOrderStates;

/**
 * This class is the Message Sink from Patch Bay which submits orders to Siebel after 
 * the order has been submitted to the commerce processOrder pipeline
 *
 * @author Gary McDowell
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/order/submit/SiebelOrderSourceSink.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class SiebelOrderSourceSink extends SourceSinkTemplate
{
//----------------------------------------------------------------------------------
  // Class version string
  //----------------------------------------------------------------------------------

  public static String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/order/submit/SiebelOrderSourceSink.java#1 $$Change: 842106 $";
  
  //Order States
  private OrderStates mOrderStates = StateDefinitions.ORDERSTATES;
  

  
  protected SiebelOrderManager mOrderManager;
  
  /**
   * @return the mOrderManager
   */
  public SiebelOrderManager getOrderManager()
  {
    return mOrderManager;
  }

  /**
   * @param mOrderManager the mOrderManager to set
   */
  public void setOrderManager(SiebelOrderManager pOrderManager)
  {
    mOrderManager = pOrderManager;
  }
  
  protected String mOrderStatePropertyName;
  /**
   * @return the mOrderStatePropertyName
   */
  public String getOrderStatePropertyName()
  {
    return mOrderStatePropertyName;
  }

  /**
   * @param mOrderStatePropertyName the mOrderStatePropertyName to set
   */
  public void setOrderStatePropertyName(String pOrderStatePropertyName)
  {
    mOrderStatePropertyName = pOrderStatePropertyName;
  }


  protected WebServiceHelper mWebServiceHelper;
  /**
   * @return the mWebServiceHelper
   */
  public WebServiceHelper getWebServiceHelper()
  {
    return mWebServiceHelper;
  }

  /**
   * @param mWebServiceHelper the mWebServiceHelper to set
   */
  public void setWebServiceHelper(WebServiceHelper mWebServiceHelper)
  {
    this.mWebServiceHelper = mWebServiceHelper;
  }
  
  /**
   * called on startup. Calls into the order manager to submit any
   * orders with SIEBEL_PROCESSING state to siebel
   */
  public void doStartService() throws ServiceException
  {
    super.doStartService();
    getAllSiebelProcessingOrders();
  }

  //-------------------------------------
  /**
   *
   * <p> This is called to notify the component that a Message has arrived through the given
   * input port.  The MessageSink should be prepared to handle concurrent calls of this method
   * from multiple Threads. This is left as an empty method in this class and should be
   * overriden by the implementor. </p>
   * 
   * <p> receiveMessage can be extended to handle extra types of messages by overriding
   * the handleNewMessageType method. </p>
   *
   * @beaninfo
   *          description: This method is called whenever a new message is sent to this class.
   **/
  public void receiveMessage (String pPortName,
            Message pMessage)
    throws JMSException
  {
    if(pMessage == null) {
      if(isLoggingError())          
        logError("Message is null");
      return;
    }
    
    if(isLoggingDebug())
    {
      logDebug("recieved a message from processOrder pipeline");
      logDebug("portName = " + pPortName);
      logDebug("message = " + pMessage.toString());
    }
    
    
    ObjectMessage oMessage = (ObjectMessage) pMessage;
    if(!oMessage.getJMSType().equals(SubmitOrder.TYPE))
    {
      return;
    }
    
    CommerceMessage cMessage = (CommerceMessage) oMessage.getObject();
    if(cMessage == null) {
      if(isLoggingError())          
        logError("Commerce message is null");
      return;
    }
    
    SubmitOrder submitOrderMessage = (SubmitOrder)cMessage;
    
    TransactionDemarcation td = new TransactionDemarcation();
    
    try
    {
      td.begin(getTransactionManager(), TransactionDemarcation.REQUIRED);
    } 
    catch (TransactionDemarcationException tde)
    {
      if(isLoggingError())
        logError(tde);
      return;
    }
    
    Order order = null;
    
    //if we had an error beginning the transaction, we wont get to 
    //this point
    try
    {
      order = loadOrder(submitOrderMessage.getOrderId());
      setOrderStatus(order, mOrderStates.getStateValue(SiebelOrderStates.SIEBEL_PROCESSING));
      saveOrder(order);
    } 
    //we had an error loading the order and setting the status. This
    //must be an atomic action, so that we don't lose any orders
    catch (CommerceException ce)
    {
      if(isLoggingError())
        logError(ce);
      //mark roll back
      try
      {
        getTransactionManager().getTransaction().setRollbackOnly();
      } 
      catch (IllegalStateException ise)
      {
        if(isLoggingError())
          logError(ise);
      } 
      catch (SystemException se)
      {
        if(isLoggingError())
          logError(se);
      }
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
        if(isLoggingError())
          logError(tde);
      }
    }
    
    if(order!=null)
    {
      submitOrderToSiebel(order);
    }
  }
  
  /**
   * updates the order
   * 
   * @param pOrder
   * @throws CommerceException
   */
  private void saveOrder(Order pOrder)  throws CommerceException
  {
    getOrderManager().updateOrder(pOrder);
    
  }

  /**
   * loads the order with the given id
   * 
   * @param pOrderId
   * @return
   * @throws CommerceException
   */
  private Order loadOrder(String pOrderId) throws CommerceException
  {
    return getOrderManager().loadOrder(pOrderId);
  }

  
  /**
   * set the order status to SIEBEL_PROCESSING
   * 
   * @param pOrder
   */
  private void setOrderStatus(Order pOrder, int pState)
  {
    pOrder.setState(pState);
  }
  
  protected void getAllSiebelProcessingOrders()
  {
    try
    {
      RepositoryView orderView = 
          getOrderManager().getOrderTools()
          .getOrderRepository().getView(getOrderManager().getOrderItemDescriptorName());
      
      QueryBuilder queryBuilder = orderView.getQueryBuilder();
      
      Query orderQuery = queryBuilder.createComparisonQuery (
          queryBuilder.createConstantQueryExpression (SiebelOrderStates.SIEBEL_PROCESSING),
          queryBuilder.createPropertyQueryExpression (getOrderStatePropertyName()),
          QueryBuilder.EQUALS);
      
      RepositoryItem[] orderItems = orderView.executeQuery (orderQuery);
      
      if (orderItems != null && orderItems.length > 0) 
      {
      	//loop through the returned orders and submit them to siebel
      	for(int i = 0; i < orderItems.length; i++)
      	{
      		submitOrderToSiebel(orderItems[i]);
      	}
      }
    } catch (RepositoryException e)
    {
      if(isLoggingError())
      {
        logError(e);
      }
    } catch (CommerceException e)
    {
      if(isLoggingError())
      {
        logError(e);
      }
    }
  }
  
  /**
   * submits the order item siebel
   * 
   * @param pOrderItem
   * @throws CommerceException 
   */
  private void submitOrderToSiebel(RepositoryItem pOrderItem) throws CommerceException
  {
    submitOrderToSiebel(loadOrder(pOrderItem.getRepositoryId()));
  }
  
  
  /**
   * submits the order siebel
   * 
   * @param pOrder
   */
  private void submitOrderToSiebel(Order pOrder)
  {
    if(isLoggingDebug())
      logDebug("about to start a new thread for order " + pOrder);
    
    Thread thread = new Thread(new SiebelOrderSubmitter(pOrder, 
        getWebServiceHelper(),
        getOrderManager(),
        getTransactionManager()));
    
    thread.start();
    
  }
}
