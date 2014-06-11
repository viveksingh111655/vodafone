package atg.siebel.account.command;

import java.util.List;

import javax.xml.ws.BindingProvider;

import com.siebel.selfservice.common.accountbillingprofile.ComInvoiceProfile;
import com.siebel.selfservice.common.accountbillingprofile.SelfServiceAccountBillingProfile;
import com.siebel.selfservice.common.accountbillingprofile.SelfServiceAccountBillingProfileExecuteInput;
import com.siebel.selfservice.common.accountbillingprofile.SelfServiceAccountBillingProfileExecuteOutput;
import com.siebel.selfservice.common.accountbillingprofile.SelfServiceAccountBillingProfileQueryPageInput;
import com.siebel.selfservice.common.accountbillingprofile.SelfServiceAccountBillingProfileQueryPageOutput;
import com.siebel.selfservice.common.accountbillingprofile.SelfServiceAccountBillingProfileSetPrimaryInput;
import com.siebel.selfservice.common.accountbillingprofile.SelfServiceAccountBillingProfileSetPrimaryOutput;
import com.siebel.selfservice.common.accountbillingprofile.SelfServiceAccountBillingProfile_Service;
import com.siebel.selfservice.common.accountbillingprofile.data.ComInvoiceProfileData;
import com.siebel.selfservice.common.accountbillingprofile.data.ListOfSsAccountbillingprofileIoData;
import com.siebel.selfservice.common.accountbillingprofile.id.ComInvoiceProfileId;
import com.siebel.selfservice.common.accountbillingprofile.query.ComInvoiceProfileQuery;
import com.siebel.selfservice.common.accountbillingprofile.query.ListOfSsAccountbillingprofileIoQuery;

import atg.integrations.CommandInvocationException;
import atg.integrations.CommandResult;
import atg.integrations.CommandTimeoutException;
import atg.integrations.InvalidInputException;
import atg.repository.MutableRepositoryItem;

/**
 * This command creates a new address in the Siebel system using the
 * SelfServiceAddress web service
 */
public class CreditCardCommand extends SiebelCommand {

	// -------------------------------------
	// Class version string
	// -------------------------------------

	public static String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/account/command/CreditCardCommand.java#1 $$Change: 842106 $";

	/**
	 * Construct a SelfServiceAddressExecuteInput object using the local
	 * contactInfo repository input and invoke the web service to add the
	 * address to the Siebel system.
	 * 
	 * @param pInput
	 *            the local contactInfo repository item created
	 * @return a CommandResult containing the Siebel Id of the newly-created
	 *         address.
	 */
	public CommandResult invokeRPC(Object pInput)
			throws CommandInvocationException, CommandTimeoutException,
			InvalidInputException {
		vlogDebug("invokeRPC with Object: {0}", pInput);

		// Instantiate the command result outside the try block so it can be
		// returned outside the try block
		CommandResult commandResult = new CommandResult();

		// Any number of exceptions can occur during this process, so we have a
		// catch-all try block
		try {
			/*
			 * For "add" and "update" this will be needed to create a new remote
			 * item or copy property values. pInput will be a repository item.
			 * 
			 * For "remove" pInput will be a map containing the remote id
			 * property name and the value.
			 */
			CommandInputBean parsedInput = parseInputParameter(pInput,
					getOperation());
			MutableRepositoryItem item = parsedInput.getItem();
			String remoteId = parsedInput.getRemoteId();

			// update operation b/c an update w/o an ID will be an insert
			setOperation(parsedInput.getCommand());

			// if insert, check for existing address
			if (getOperation().equals(CMD_UPDATE)) {
				vlogDebug("Checking for existing credit card for {0}", item);

				if (!hasMappedData(item)) {
					vlogDebug("No changed mapped properties on bean: {0}", item);
					return commandResult;
				}
			}

			// Instantiate the object that will hold the address data for the
			// webservice call
			ComInvoiceProfileData billingprofile = new ComInvoiceProfileData();
			billingprofile.setOperation(getOperation());

			// If doing an "add" or an "update", populate the contents of the
			// webservice object. Otherwise, it is a delete and we only need to
			// populate the ID
			if (getOperation().equals(CMD_UPDATE)) {
				populateSiebelObject(billingprofile, getSiebelRepository(),
						item, "credit-card");
			} else {
				billingprofile.setId(remoteId);
			}

			commandResult = makeSiebelCall(billingprofile);
		} catch (Throwable e) {
			throw new CommandInvocationException(e);
		}

		return commandResult;
	}

	/**
	 * Makes the call to Siebel and returns the resulting data. This assumes the
	 * ComInvoiceProfile object is all setup. THIS HANDLES UPDATE & SET PRIMARY
	 * ONLY Billingprofiles CANNOT BE CREATED from ATG
	 * 
	 * @param pAddress
	 * @return
	 */
	protected CommandResult makeSiebelCall(ComInvoiceProfileData pBP) {
		CommandResult commandResult = new CommandResult();

		// Instantiate the webservice port
		SelfServiceAccountBillingProfile_Service service = new SelfServiceAccountBillingProfile_Service();
		SelfServiceAccountBillingProfile port = service
				.getSelfServiceAccountBillingProfilePort();
		getWebServiceHelper().prepareConnection((BindingProvider) port);

		ListOfSsAccountbillingprofileIoData listOfBP = new ListOfSsAccountbillingprofileIoData();
		ComInvoiceProfileQuery comQuery = new ComInvoiceProfileQuery();
		List<ComInvoiceProfileData> bprofiles = listOfBP.getComInvoiceProfile();
		bprofiles.add(pBP);

		// Fill out the rest of the input properties of the webservice
		SelfServiceAccountBillingProfileExecuteInput input = new SelfServiceAccountBillingProfileExecuteInput();
		input.setListOfSsAccountbillingprofileIo(listOfBP);
		input.setExecutionMode("BiDirectional");
		input.setLOVLanguageMode("LDC");
		input.setViewMode("All");

		SelfServiceAccountBillingProfileExecuteOutput result = port
				.selfServiceAccountBillingProfileExecute(input);

		
		// Get the Siebel Id from the webservices result and put it into the
		// command result

		ComInvoiceProfileId output = result
				.getListOfSsAccountbillingprofileIo().getComInvoiceProfile()
				.get(0);
		commandResult.setResult(output);

		return commandResult;
	}

} // end of class
