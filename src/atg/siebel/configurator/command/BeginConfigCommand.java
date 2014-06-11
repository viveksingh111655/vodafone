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
package atg.siebel.configurator.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import atg.nucleus.logging.ApplicationLogging;
import atg.nucleus.logging.ClassLoggingFactory;
import atg.repository.RepositoryItem;
import atg.siebel.configurator.ConfigurationContext;
import atg.siebel.configurator.ConfiguratorException;
import atg.siebel.configurator.Constants.ProductConfigInstanceStatus;
import atg.siebel.configurator.ProductConfigInstance;
import atg.siebel.configurator.RootProductConfigInstance;
import atg.siebel.configurator.status.CommandStatus;
import atg.siebel.integration.SiebelSession;
import atg.siebel.integration.SiebelUserSessionStore;
import atg.siebel.order.SiebelCommerceItem;
import atg.siebel.order.SiebelOrderTools;

import com.siebel.ordermanagement.configurator.BeginConfigurationInput;
import com.siebel.ordermanagement.configurator.BeginConfigurationOutput;
import com.siebel.ordermanagement.configurator.BeginConfigurationPort;
import com.siebel.ordermanagement.configurator.ProductConfigurator;
import com.siebel.ordermanagement.configurator.cfginteractdata.Item;
import com.siebel.ordermanagement.configurator.cfginteractdata.ListOfData;
import com.siebel.ordermanagement.configurator.cfginteractdata.ProductData;
import com.siebel.ordermanagement.configurator.cfglinkeditems.ListOfLinkedItems;
import com.siebel.ordermanagement.configurator.cfgproperties.ListOfProperties;
import com.siebel.ordermanagement.configurator.cfgproperties.Properties;
import com.siebel.ordermanagement.configurator.cfgproperties.Property;
import com.siebel.ordermanagement.configurator.cfgproperties.PropertyType;
import com.siebel.ordermanagement.quote.data.ListOfQuote;
import com.siebel.ordermanagement.quote.data.ListOfQuoteItem;
import com.siebel.ordermanagement.quote.data.Quote;
import com.siebel.ordermanagement.quote.data.QuoteItem;

/**
 * Class that sends a BeginConfig message to Siebel
 * 
 * @author bbrady
 * @version $Id:
 *          //product/Siebel/version/0.4.0.0.0/src/atg/siebel/configurator/command
 *          /BeginConfigCommand.java#4 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class BeginConfigCommand extends AbstractConfiguratorCommand {

  /** Logger */
  protected static ApplicationLogging mLogger = ClassLoggingFactory
      .getFactory().getLoggerForClass(BeginConfigCommand.class);

  public static final String SIEBEL_ACCOUNT_ID_STRING = "siebelAccountId";
  public static final String PARENT_ORGANISATION_STRING = "parentOrganization";
  public static final String NAME_STRING = "name";
  public static final String NUMERIC_1_STRING = "1";
  public static final String LINE_ITEM_STRING = "Line Item";
  public static final String PRODUCT_TYPE_CODE_STRING = "Product Type Code";
  public static final String PRODUCT_STRING = "Product";
  public static final String PRODUCT_STRUCTURE_TYPE_STRING = "Product Structure Type";
  public static final String CUSTOMIZABLE_STRING = "Customizable";
  public static final String EMPTY_STRING = "";

  private enum Mode {
    // Used for ABO
    LOAD,
    // Legacy
    ADD,
    // Used for Add/Edit
    SET
  }

  public BeginConfigCommand(ConfigurationContext pEvent) {
    super(pEvent);
  }

  @Override
  protected CommandResult doExecute() throws ConfiguratorException {
    if (mLogger.isLoggingDebug()) {
      mLogger.logDebug("entered doExecute()");
    }

    ProductConfigurator cfg = new ProductConfigurator();
    BeginConfigurationInput input = createInput();
    BeginConfigurationPort port = cfg.getBeginConfigurationPort();
    SiebelUserSessionStore.reset();
    getConfiguratorTools().getWebServiceHelper().prepareConnection(
        (BindingProvider) port);
    try {
      BeginConfigurationOutput output = port.beginConfiguration(input);
      return produceResult(output);
    } catch (SOAPFaultException sfe) {
      return produceResult(sfe);
    } finally {
      SiebelUserSessionStore.reset();
    }

  }

  /**
   * @return
   * @throws ConfiguratorException
   */
  protected BeginConfigurationInput createInput() throws ConfiguratorException {

    BeginConfigurationInput input = createBeginConfigurationInput();

    // Quote Id and Header Id must be the same
    input.setHeaderId(getInstance().getOrderId());

    // populate product ID
    input.setProductId(getInstance().getProductId());

    input.setInitInstanceOperation(Mode.SET.name());

    input.setListOfProperties(createListOfProperties());
    input.setListOfLinkedItems(new ListOfLinkedItems());

    ProductData productData = new ProductData();

    Item item = createProductItem();
    if (getRootInstance().getStatus() == ProductConfigInstanceStatus.CONFIGURED) {
      item.setIntegrationId(EMPTY_STRING);
    }
    productData.getItem().add(item);

    ListOfData listOfData = new ListOfData();
    listOfData.setProductData(productData);
    input.setListOfData(listOfData);

    return input;
  }

  /**
   * 
   * @param pOutput
   * @return
   * @throws ConfiguratorException
   */
  protected CommandResult produceResult(BeginConfigurationOutput pOutput)
      throws ConfiguratorException {

    CommandStatus status = createCommandStatus(pOutput.getErrorSpcCode(),
        pOutput.getErrorSpcMessage(), pOutput.getListOfStatus());
    CommandResult result = new CommandResult(status);
    if (status.isSuccessfull()) {
      // Get the session details for this configuration session
      // and save in our getInstance() to be reused in further update/end
      // config messages
      SiebelSession session = SiebelUserSessionStore.getSiebelSession();
      ProductConfigInstance instance = (ProductConfigInstance) getInstance();
      instance.setSession(session);

      // Not sure what to do with the rootId ????
      instance.setIntegrationId(pOutput.getRootIntegrationId());

      // This equates to our getInstance() item
      configureInstancefromProductData(pOutput.getListOfData().getProductData());
    }

    return result;
  }

  /**
   * @return
   */
  private ListOfProperties createListOfProperties() {
    PropertyType propertyType = new PropertyType();
    propertyType.setObjectType(LINE_ITEM_STRING);

    Property prop1 = new Property();
    prop1.setName(PRODUCT_TYPE_CODE_STRING);
    prop1.setValue(PRODUCT_STRING);

    Property prop2 = new Property();
    prop2.setName(PRODUCT_STRUCTURE_TYPE_STRING);
    prop2.setValue(CUSTOMIZABLE_STRING);

    propertyType.getProperty().add(prop1);
    propertyType.getProperty().add(prop2);

    Properties properties = new Properties();
    properties.getPropertyType().add(propertyType);

    ListOfProperties listOfProperties = new ListOfProperties();
    listOfProperties.setProperties(properties);

    return listOfProperties;
  }

  /**
   * @return
   * @throws ConfiguratorException
   */
  protected BeginConfigurationInput createBeginConfigurationInput()
      throws ConfiguratorException {

    BeginConfigurationInput beginInput = new BeginConfigurationInput();

    RootProductConfigInstance instance = (RootProductConfigInstance) getInstance();
    RepositoryItem user = getUserProfile(instance.getOrderId());
    ListOfQuote listOfQuote = new ListOfQuote();
    Quote quote = createQuote(user, instance);
    listOfQuote.getQuote().add(quote);
    beginInput.setListOfQuote(listOfQuote);

    ListOfQuoteItem quoteItems = quote.getListOfQuoteItem();
    if (quoteItems != null && quoteItems.getQuoteItem() != null
        && !quoteItems.getQuoteItem().isEmpty()) {
      String rootProductId = getRootInstance().getProductId();
      for (QuoteItem quoteItem : quoteItems.getQuoteItem()) {
        if (rootProductId.equals(quoteItem.getProductId())) {
          beginInput.setRootId(quoteItem.getId());
          break;
        }
      }
    }

    return beginInput;

  }

  /**
   * @param pUser
   * @param pProductInstance
   * @return
   * @throws ConfiguratorException
   */
  protected Quote createQuote(RepositoryItem pUser,
      RootProductConfigInstance pProductInstance) throws ConfiguratorException {

    Quote quote = new Quote();
    quote.setRevision(NUMERIC_1_STRING);

    quote.setQuoteNumber(pProductInstance.getOrderId());
    quote.setId(pProductInstance.getOrderId());

    String accountId = null;

    if (pUser != null) {
      accountId = (String) pUser.getPropertyValue(SIEBEL_ACCOUNT_ID_STRING);
      quote.setAccountId(accountId);
      RepositoryItem org = (RepositoryItem) pUser
          .getPropertyValue(PARENT_ORGANISATION_STRING);
      if (org != null) {
        quote.setAccount((String) org.getPropertyValue(NAME_STRING));
      }
    }

    QuoteItem quoteItem = null;
    try {
      SiebelCommerceItem commerceItem = getConfiguratorTools().getCommerceItem(
          pProductInstance);
      List<SiebelCommerceItem> ciList = new ArrayList<SiebelCommerceItem>();
      ciList.add(commerceItem);
      
      SiebelCommerceItem promotionCommerceItem = null;
      if (pProductInstance.getPromotionInstance() != null) {
        promotionCommerceItem = getConfiguratorTools().getCommerceItem(
            pProductInstance.getPromotionInstance());
      }
      List<QuoteItem> qiList = ((SiebelOrderTools) getConfiguratorTools()
          .getOrderManager().getOrderTools())
          .convertCommerceItemsToQuoteItems(ciList, promotionCommerceItem, accountId);
      if (qiList != null && !qiList.isEmpty()) {
        quoteItem = qiList.get(0);
      }
    } catch (Exception ex) {
      throw new ConfiguratorException(ex);
    }

    ListOfQuoteItem listQuoteItems = new ListOfQuoteItem();
    listQuoteItems.getQuoteItem().add(quoteItem);
    quote.setListOfQuoteItem(listQuoteItems);

    return quote;
  }

  /**
   * @return
   */
  protected QuoteItem createQuoteItem() {

    QuoteItem quoteItem = new QuoteItem();

    quoteItem.setActionCode(Mode.ADD.name());
    quoteItem.setQuantity(NUMERIC_1_STRING);

    return quoteItem;
  }

}
