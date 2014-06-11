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

import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import atg.siebel.configurator.BaseConfigInstance;
import atg.siebel.configurator.ConfigurationContext;
import atg.siebel.configurator.ConfiguratorException;
import atg.siebel.configurator.Constants;
import atg.siebel.configurator.ProductConfigInstance;
import atg.siebel.integration.SiebelSession;
import atg.siebel.integration.SiebelUserSessionStore;

import com.siebel.ordermanagement.configurator.EndConfigurationInput;
import com.siebel.ordermanagement.configurator.EndConfigurationOutput;
import com.siebel.ordermanagement.configurator.EndConfigurationPort;
import com.siebel.ordermanagement.configurator.ProductConfigurator;
import com.siebel.ordermanagement.quote.data.Quote;
import com.siebel.ordermanagement.quote.data.QuoteItem;

/**
 * Sends EndConfig command to Siebel
 * 
 * @author bbrady
 * @version $Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/configurator/command/EndConfigCommand.java#1 $$Change: 842106 $
 * @updated $DateTime: 2013/10/07 11:36:08 $$Author: jsiddaga $
 */
public class EndConfigCommand extends AbstractConfiguratorCommand {

	private static final Log logger = LogFactory
			.getLog(BeginConfigCommand.class);
	// decided to make its static as it will not be changed from time to time
	private final static EndConfigurationInput siebelInput = new EndConfigurationInput();

	static {
		siebelInput.setSaveInstanceFlag("N");
	}

	/**
	 * Creates End command
	 * @param pParameterMap - Config Instance of Root Product
	 */
	public EndConfigCommand(ConfigurationContext pEvent) {
		super(pEvent);
	}

  protected CommandResult doExecute() throws ConfiguratorException {

    if (logger.isDebugEnabled()) {
      logger.debug("doExecute() called");
    }
    // Get the session details for this configuration session
    // stored in getInstance() and set it as the current for this
    // thread - thus it will be used in the Soap Header
    SiebelSession session = ((ProductConfigInstance) getInstance())
        .getSession();
    SiebelUserSessionStore.setSiebelSession(session);

    ProductConfigurator cfg = new ProductConfigurator();
    EndConfigurationPort port = cfg.getEndConfigurationPort();
    getConfiguratorTools().getWebServiceHelper().prepareConnection(
        (BindingProvider) port);
    try {
      EndConfigurationOutput output = port.endConfiguration(siebelInput);

      CommandResult cmdResult = new CommandResult();
      cmdResult.setStatus(createCommandStatus(output.getErrorSpcCode(),
          output.getErrorSpcMessage(), null));

      if (cmdResult.getStatus().isSuccessfull()) {
        QuoteItem item = findQuoteItem(output.getListOfQuote().getQuote());
        cmdResult.getContext().put(Constants.QUOTE_ITEM, item);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("Leaving doExecute() : cmdResult == " + cmdResult);
      }
      return cmdResult;
    } catch (SOAPFaultException sfe) {
      return produceResult(sfe);
    } finally {
      SiebelUserSessionStore.reset();
    }
  }

	/**
	 * @param pQuotes
	 * @return
	 */
	private QuoteItem findQuoteItem(List<Quote> pQuotes) {
		QuoteItem targetItem = null;
		BaseConfigInstance myInstance = getInstance();
		for (Quote quote : pQuotes) {
			List<QuoteItem> quoteItems = quote.getListOfQuoteItem().getQuoteItem();
			for (QuoteItem quoteItem : quoteItems) {
				if (quoteItem.getProductId().equalsIgnoreCase(myInstance.getProductId()) 
						&& quoteItem.getIntegrationId().equalsIgnoreCase(myInstance.getIntegrationId())) {
					targetItem = quoteItem;
					break;
				}
			}
			if (targetItem != null) {
				break;
			}
		}
		return targetItem;
	}

}
