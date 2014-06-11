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

package atg.siebel.asset;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import com.siebel.ordermanagement.catalog.CatalogPort;
import com.siebel.ordermanagement.catalog.CatalogWebService;
import com.siebel.ordermanagement.catalog.GetRelatedPromotionsInput;
import com.siebel.ordermanagement.catalog.GetRelatedPromotionsOutput;
import com.siebel.ordermanagement.catalog.data.catalogcontext.Context;
import com.siebel.ordermanagement.catalog.data.catalogcontext.ExternalContext;
import com.siebel.ordermanagement.catalog.data.catalogcontext.ListOfContext;
import com.siebel.ordermanagement.catalog.data.relatedpromotions.ListOfProduct;
import com.siebel.ordermanagement.catalog.data.relatedpromotions.ListOfRelatedPromotion;
import com.siebel.ordermanagement.quote.psp.CalculatePriceInput;
import com.siebel.ordermanagement.quote.psp.CalculatePriceOutput;
import com.siebel.ordermanagement.quote.psp.CalculatePricePort;
import com.siebel.ordermanagement.quote.psp.CalculatePriceWS;

import com.siebel.service.fs.assets.AssetManagement;
import com.siebel.service.fs.assets.AssetManagementComplex;
import com.siebel.service.fs.assets.AssetManagementComplexPort;
import com.siebel.service.fs.assets.AssetManagementComplexQueryPageInput;
import com.siebel.service.fs.assets.AssetManagementComplexQueryPageOutput;
import com.siebel.service.fs.assets.AssetManagementPort;
import com.siebel.service.fs.assets.AssetManagementQueryPageInput;
import com.siebel.service.fs.assets.AssetManagementQueryPageOutput;

import com.siebel.xml.asset_management_complex_io.data.AssetMgmtAssetHeaderData;
import com.siebel.xml.asset_management_complex_io.data.AssetMgmtAssetXaData;
import com.siebel.xml.asset_management_complex_io.data.ListOfAssetData;
import com.siebel.xml.asset_management_complex_io.data.ListOfAssetMgmtAssetXaData;
import com.siebel.xml.asset_management_complex_io.query.AssetMgmtAssetHeaderQuery;
import com.siebel.xml.asset_management_complex_io.query.AssetMgmtAssetXaQuery;
import com.siebel.xml.asset_management_complex_io.query.ListOfAssetMgmtAssetQuery;
import com.siebel.xml.asset_management_complex_io.query.ListOfAssetMgmtAssetXaQuery;
import com.siebel.xml.asset_management_complex_io.query.ListOfAssetQuery;
import com.siebel.xml.asset_management_complex_io.query.QueryType;

import com.siebel.xml.asset_management_io.data.AssetMgmtAssetData;
import com.siebel.xml.asset_management_io.query.AssetMgmtAssetQuery;
import com.siebel.xml.asset_management_io.query.ListOfAssetManagementIoQuery;

import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.repository.RepositoryView;

import atg.siebel.catalog.SiebelCatalogProduct;
import atg.siebel.catalog.SiebelCatalogTools;
import atg.siebel.integration.WebServiceHelper;
import atg.beans.DynamicBeans;
import atg.beans.PropertyNotFoundException;
import atg.repository.Query;
import atg.repository.QueryBuilder;

/**
 * A collection of helper methods for interacting with Siebel.
 * 
 * @author Sameena Y Patel
 */
public class SiebelAssetTools {
	// ----------------------------------------------------------------------------------
	// Class version string
	// ----------------------------------------------------------------------------------

	public static String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/asset/SiebelAssetTools.java#1 $$Change: 842106 $";

	public static final String M_PROMOTION = "Promotion";

	// --------- Property: webServiceHelper -----------
	protected WebServiceHelper mWebServiceHelper;

	public void setWebServiceHelper(WebServiceHelper pWebServiceHelper) {
		mWebServiceHelper = pWebServiceHelper;
	}

	public WebServiceHelper getWebServiceHelper() {
		return mWebServiceHelper;
	}

	public int getTotalAssets() {
		return customerAssets.size();
	}

	// --------- Property: catalogTools -----------
	protected SiebelCatalogTools mCatalogTools;

	public void setCatalogTools(SiebelCatalogTools pCatalogTools) {
		mCatalogTools = pCatalogTools;
	}

	public SiebelCatalogTools getCatalogTools() {
		return mCatalogTools;
	}

	// property: promotionRelationshipDescriptor

	private String mPromotionRelationshipDescriptor = "promotion-relationship";

	/**
	 * Returns the product class item descriptor name
	 * 
	 * @return the product class item descriptor name
	 */

	public String getPromotionRelationshipDescriptor() {
		return mPromotionRelationshipDescriptor;
	}

	/**
	 * Sets the product class item descriptor name
	 * 
	 * @param pPromotionRelationshipDescriptor
	 *            the product class item descriptor name to set
	 */

	public void setPromotionRelationshipDescriptor(
			String pPromotionRelationshipDescriptor) {
		mPromotionRelationshipDescriptor = pPromotionRelationshipDescriptor;
	}

	// --------- Property: customerAssets -----------
	protected LinkedHashMap<String, SiebelAsset> customerAssets;

	public LinkedHashMap<String, SiebelAsset> getCustomerAssets() {
		return customerAssets;
	}

	public ArrayList<SiebelAsset> getAssets(String accountId, String contactId,
			String status) {
		if (customerAssets != null) {
			return new ArrayList<SiebelAsset>(customerAssets.values());
		}

		AssetManagementQueryPageOutput output = invokeAssetManagementWS(
				accountId, contactId, status);

		if (output != null) {
			if (output.getListOfAssetManagementIo().getAssetMgmtAsset() != null
					&& output.getListOfAssetManagementIo().getAssetMgmtAsset()
							.size() > 0) {
				customerAssets = new LinkedHashMap<String, SiebelAsset>();
				Iterator<AssetMgmtAssetData> assetItr = output
						.getListOfAssetManagementIo().getAssetMgmtAsset()
						.iterator();

				while (assetItr.hasNext()) {
					AssetMgmtAssetData asset = (AssetMgmtAssetData) assetItr
							.next();

					SiebelAsset sblAsset = new SiebelAsset();
					sblAsset.setAssetNumber(asset.getAssetNumber());
					sblAsset.setId(asset.getAssetId());
					sblAsset.setIntegrationId(asset.getIntegrationId());
					sblAsset.setInstallDate(convertToDate(asset
							.getInstallDate()));
					sblAsset.setOwnerAccountId(asset.getOwnerAccountId());
					sblAsset.setOwnerContactId(asset.getOwnerContactId());
					sblAsset.setProdPromId(asset.getProdPromId());
					sblAsset.setProductId(asset.getProductId());
					sblAsset.setProductName(asset.getProductName());
					sblAsset.setProductType(asset.getProductType());
					//Modified by SYPATEL for bug# 17502220
					sblAsset.setOwner(asset.getAccountName());
					sblAsset.setBillingAccount(asset.getBillingAccount());
					sblAsset.setSoldToAccount(asset.getServiceAccount());
					sblAsset.setStatus(asset.getStatus());
					sblAsset.setBillingProfileName(asset
							.getBillingProfileName());
					sblAsset.setDescription(asset.getAssetDescription());
					sblAsset.setEffectiveEndDate(convertToDate(asset
							.getEffectiveEndDate()));
					if (asset.getExtendedQuantity() != null) {
						sblAsset.setExtendedQuantity(asset
								.getExtendedQuantity().toString());
					}
					sblAsset.setSerialNumber(asset.getSerialNumber());
					sblAsset.setWarrantyEndDate(convertToDate(asset
							.getWarrantyEndDate()));
					sblAsset.setWarrantyStartDate(convertToDate(asset
							.getWarrantyStartDate()));
					sblAsset.setWarrantyType(asset.getWarrantyType());
					sblAsset.setAdjustedListPrice(formatPrice(asset
							.getAdjustedListPrice()));

					if (asset.getAssetMgmtAsset() != null
							&& asset.getAssetMgmtAsset().size() > 0) {
						populateChildAssets(sblAsset, asset.getAssetMgmtAsset());
					}
					System.out.println("Adding to the map: "
							+ asset.getAssetId());
					System.out.println("Size of the map: "
							+ customerAssets.size());
					customerAssets.put(asset.getAssetId(), sblAsset);
				}
				return new ArrayList<SiebelAsset>(customerAssets.values());
			}
		}
		return null;
	}

	private String formatPrice(BigDecimal adjustedListPrice) {
		// TODO Auto-generated method stub
		if (adjustedListPrice != null) {
			NumberFormat currencyFormatter = NumberFormat
					.getCurrencyInstance(Locale.US);

			return currencyFormatter.format(adjustedListPrice);

		}
		return null;
	}

	private void populateChildAssets(SiebelAsset sblAsset,
			List<AssetMgmtAssetData> assetMgmtAsset) {
		// TODO Auto-generated method stub
		if (assetMgmtAsset != null && assetMgmtAsset.size() > 0) {
			Iterator<AssetMgmtAssetData> itr = assetMgmtAsset.iterator();
			ArrayList<SiebelAsset> childAssets = new ArrayList<SiebelAsset>();

			while (itr.hasNext()) {
				AssetMgmtAssetData asset = (AssetMgmtAssetData) itr.next();

				SiebelAsset childAsset = new SiebelAsset();
				childAsset.setAssetNumber(asset.getAssetNumber());
				childAsset.setId(asset.getAssetId());
				childAsset
						.setInstallDate(convertToDate(asset.getInstallDate()));
				childAsset.setDescription(asset.getAssetDescription());
				childAsset.setProductName(asset.getProductName());
				childAsset.setDescription(asset.getProductDescription());
				childAsset.setAdjustedListPrice(formatPrice(asset
						.getAdjustedListPrice()));
				childAssets.add(childAsset);
			}
			sblAsset.setChildAssets(childAssets);
		}

	}

	public Date convertToDate(XMLGregorianCalendar javaUtilCalendar) {
		if (javaUtilCalendar == null)
			return null;
		long javaMilliSeconds = javaUtilCalendar.toGregorianCalendar()
				.getTimeInMillis();
		java.util.Date javaDate = new java.util.Date();
		javaDate.setTime(javaMilliSeconds);
		return javaDate;
	}

	public ArrayList<SiebelAsset> getAssets(String accountId, String contactId) {
		return getAssets(accountId, contactId, null);
	}

	public ArrayList<SiebelAsset> getAssets(String accountId, String contactId,
			String status, int startIndex, int endIndex) {
		getAssets(accountId, contactId, status);

		if (customerAssets != null && !(startIndex < 0) && !(endIndex < 0)) {
			int start = 0, end = 0;
			if ((startIndex + 1) <= customerAssets.size()) {
				start = startIndex;
			}

			if ((endIndex + 1) <= customerAssets.size()) {
				end = endIndex + 1;
			} else {
				end = customerAssets.size();
			}

			ArrayList<SiebelAsset> subAssets = new ArrayList<SiebelAsset>(
					(new ArrayList<SiebelAsset>(customerAssets.values()))
							.subList(start, end));

			Iterator<SiebelAsset> itr = subAssets.iterator();

			while (itr.hasNext()) {
				SiebelAsset asset = itr.next();
				if (asset.getProductType().equalsIgnoreCase("Promotion")
						&& null == asset.getChildAssets()) {
					System.out.println("Populating child assets for: "
							+ asset.getProductName());
					populatePromoAssetChildren(asset);
				}
			}
			return subAssets;
		}

		return null;
	}

	/**
	 * Calls the webservice - uses webServiceHelper to provide port
	 * configuration
	 * 
	 * @param accountId
	 *            contactId status
	 * 
	 * @return
	 */
	protected AssetManagementQueryPageOutput invokeAssetManagementWS(
			String accountId, String contactId, String status) {
		System.out.println("invokeAssetManagementWS() called : pInput == {0}"
				+ accountId);
		System.out.println("invokeAssetManagementWS() called : pInput == {1}"
				+ contactId);

		AssetManagementQueryPageInput input = new AssetManagementQueryPageInput();
		input.setLOVLanguageMode("LDC");
		input.setViewMode("All");

		ListOfAssetManagementIoQuery listOfAssetMgmtQuery = new ListOfAssetManagementIoQuery();
		listOfAssetMgmtQuery
				.setStartrownum(new BigInteger(Integer.toString(0)));
		listOfAssetMgmtQuery.setPagesize(new BigInteger("100"));
		listOfAssetMgmtQuery.setRecordcountneeded(true);

		AssetMgmtAssetQuery assetQuery = new AssetMgmtAssetQuery();

		assetQuery.setAssetId(getEmptyQueryType());
		assetQuery.setAssetNumber(getEmptyQueryType());
		assetQuery.setProductPartNumber(getEmptyQueryType());
		assetQuery.setProductId(getEmptyQueryType());
		assetQuery.setProductName(getEmptyQueryType());
		assetQuery.setProductDescription(getEmptyQueryType());
		assetQuery.setProductType(getEmptyQueryType());
		assetQuery.setBillingAccountId(getEmptyQueryType());
		assetQuery.setBillingAccount(getEmptyQueryType());
		assetQuery.setServiceAccountId(getEmptyQueryType());
		assetQuery.setServiceAccount(getEmptyQueryType());
		assetQuery.setOwnerContactId(getEmptyQueryType());
		assetQuery.setPriceType(getEmptyQueryType());
		assetQuery.setAdjustedValue(getEmptyQueryType());
		assetQuery.setInstallDate(getEmptyQueryType());
		assetQuery.setIntegrationId(getEmptyQueryType());
		assetQuery.setSerialNumber(getEmptyQueryType());
		assetQuery.setInstallDate(getEmptyQueryType());
		assetQuery.setStatus(getEmptyQueryType());
		assetQuery.setProdPromId(getEmptyQueryType());
		assetQuery.setProductType(getEmptyQueryType());
		assetQuery.setProductDefTypeCode(getEmptyQueryType());
		assetQuery.setPurchaseDate(getEmptyQueryType());
		assetQuery.setAssetCurrencyCode(getEmptyQueryType());
		assetQuery.setAdjustedListPrice(getEmptyQueryType());
		assetQuery.setConvertToAgreementFlag(getEmptyQueryType());
		assetQuery.setAssetDescription(getEmptyQueryType());
		//Bug 17442026 - set empty query type for owner
		assetQuery.setOwnerAccountId(getEmptyQueryType());
		//Modified by SYPATEL for bug# 17502220
		assetQuery.setAccountName(getEmptyQueryType());

		System.out.println("Setting the passed criteria in the query to WS");

		String searchSpec = "[ProdPromId] = ''";
		boolean addANDClause = true;

		if (isNonNull(accountId)) {
			if (addANDClause)
				searchSpec = searchSpec + " AND";
			searchSpec = searchSpec + "(";
			searchSpec = searchSpec + "[BillingAccountId] = '" + accountId
					+ "' OR [ServiceAccountId] = '" + accountId
					+ "' OR [OwnerAccountId] = '" + accountId + "'";
			if (isNonNull(contactId)) {
				searchSpec = searchSpec + " OR [OwnerContactId] = '"
						+ contactId + "'";
			}
			searchSpec = searchSpec + ")";
			addANDClause = true;
		}
		if (!isNonNull(accountId) && isNonNull(contactId)) {
			if (addANDClause)
				searchSpec = searchSpec + " AND";
			searchSpec = searchSpec + " [OwnerContactId] = '" + contactId + "'";
			addANDClause = true;
		}
		if (isNonNull(status)) {
			if (addANDClause)
				searchSpec = searchSpec + " AND";
			searchSpec = searchSpec + " [Status] ='" + status + "'";
			addANDClause = true;
		}

		System.out
				.println("Setting the following searchspec in the Asset WebService Call : "
						+ searchSpec);
		assetQuery.setSearchspec(searchSpec);

		listOfAssetMgmtQuery.setAssetMgmtAsset(assetQuery);
		input.setListOfAssetManagementIo(listOfAssetMgmtQuery);

		AssetManagement assetWS = new AssetManagement();
		AssetManagementPort assetWSPort = assetWS.getAssetManagementPort();
		getWebServiceHelper().prepareConnection((BindingProvider) assetWSPort);
		AssetManagementQueryPageOutput output = assetWSPort
				.assetManagementQueryPage(input);

		return output;
	}

	protected void populatePromoAssetChildren(SiebelAsset parentAsset) {
		if (parentAsset != null && parentAsset.getIntegrationId() != null) {
			System.out.println("queryAssetChildren() called : pInput "
					+ parentAsset.getIntegrationId());

			AssetManagementQueryPageInput input = new AssetManagementQueryPageInput();
			input.setLOVLanguageMode("LDC");
			input.setViewMode("All");

			ListOfAssetManagementIoQuery listOfAssetMgmtQuery = new ListOfAssetManagementIoQuery();
			listOfAssetMgmtQuery.setStartrownum(new BigInteger(Integer
					.toString(0)));
			listOfAssetMgmtQuery.setPagesize(new BigInteger("100"));
			listOfAssetMgmtQuery.setRecordcountneeded(true);

			AssetMgmtAssetQuery assetQuery = new AssetMgmtAssetQuery();

			assetQuery.setAssetId(getEmptyQueryType());
			assetQuery.setAssetNumber(getEmptyQueryType());
			assetQuery.setProductPartNumber(getEmptyQueryType());
			assetQuery.setProductId(getEmptyQueryType());
			assetQuery.setProductName(getEmptyQueryType());
			assetQuery.setProductDescription(getEmptyQueryType());
			assetQuery.setProductType(getEmptyQueryType());
			assetQuery.setBillingAccountId(getEmptyQueryType());
			assetQuery.setBillingAccount(getEmptyQueryType());
			assetQuery.setServiceAccountId(getEmptyQueryType());
			assetQuery.setServiceAccount(getEmptyQueryType());
			assetQuery.setOwnerContactId(getEmptyQueryType());
			assetQuery.setPriceType(getEmptyQueryType());
			assetQuery.setAdjustedValue(getEmptyQueryType());
			assetQuery.setInstallDate(getEmptyQueryType());
			assetQuery.setIntegrationId(getEmptyQueryType());
			assetQuery.setSerialNumber(getEmptyQueryType());
			assetQuery.setInstallDate(getEmptyQueryType());
			assetQuery.setStatus(getEmptyQueryType());
			assetQuery.setProdPromId(getEmptyQueryType());
			assetQuery.setProductType(getEmptyQueryType());
			assetQuery.setProductDefTypeCode(getEmptyQueryType());
			assetQuery.setPurchaseDate(getEmptyQueryType());
			assetQuery.setAssetCurrencyCode(getEmptyQueryType());
			assetQuery.setAdjustedListPrice(getEmptyQueryType());
			assetQuery.setConvertToAgreementFlag(getEmptyQueryType());

			System.out
					.println("Setting the passed criteria in the query to WS");

			String searchSpec = "[ProdPromInstanceId] = '"
					+ parentAsset.getIntegrationId() + "'";
			searchSpec = searchSpec + " AND [Status] ='Active'";

			System.out
					.println("Setting the following searchspec in the Asset WebService Call : "
							+ searchSpec);
			assetQuery.setSearchspec(searchSpec);

			listOfAssetMgmtQuery.setAssetMgmtAsset(assetQuery);
			input.setListOfAssetManagementIo(listOfAssetMgmtQuery);

			AssetManagement assetWS = new AssetManagement();
			AssetManagementPort assetWSPort = assetWS.getAssetManagementPort();
			getWebServiceHelper().prepareConnection(
					(BindingProvider) assetWSPort);
			AssetManagementQueryPageOutput output = assetWSPort
					.assetManagementQueryPage(input);

			if (output != null && output.getListOfAssetManagementIo() != null) {
				populateChildAssets(parentAsset, output
						.getListOfAssetManagementIo().getAssetMgmtAsset());
			}

		}
	}

	protected void populateAssetAttributes(SiebelAsset _asset) {
		if (_asset != null) {
			System.out.println("getAssetAttributes() called : pInput "
					+ _asset.getId());

			AssetManagementComplexQueryPageInput input = new AssetManagementComplexQueryPageInput();
			input.setLOVLanguageMode("LDC");
			input.setViewMode("All");
			input.setExecutionMode("ForwardOnly");

			ListOfAssetQuery lstAssetQuery = new ListOfAssetQuery();
			lstAssetQuery.setPagesize(new BigInteger("10"));
			lstAssetQuery.setRecordcountneeded(true);

			AssetMgmtAssetHeaderQuery assetHeaderQuery = new AssetMgmtAssetHeaderQuery();

			assetHeaderQuery.setAssetNumber(getEmptyComplexQueryType());
			assetHeaderQuery.setProductId(getEmptyComplexQueryType());
			assetHeaderQuery.setProductName(getEmptyComplexQueryType());
			assetHeaderQuery.setId(getEmptyComplexQueryType());

			System.out
					.println("Setting the passed criteria in the query to WS");

			String searchSpec = "[Id] ='" + _asset.getId() + "'";

			if (_asset.getProductType().equals(M_PROMOTION)
					&& _asset.getChildAssets() != null) {
				Iterator<SiebelAsset> childAssetItr = _asset.getChildAssets()
						.iterator();

				while (childAssetItr.hasNext()) {
					SiebelAsset childAsset = childAssetItr.next();
					searchSpec.concat(" OR [Id] = '");
					searchSpec.concat(childAsset.getId());
					searchSpec.concat("'");
				}
			}
			System.out
					.println("Setting the following searchspec in the Asset WebService Call : "
							+ searchSpec);

			assetHeaderQuery.setSearchspec(searchSpec);

			ListOfAssetMgmtAssetQuery lstAssetMgmtAssetQuery = new ListOfAssetMgmtAssetQuery();
			lstAssetMgmtAssetQuery.setPagesize(new BigInteger("10"));

			com.siebel.xml.asset_management_complex_io.query.AssetMgmtAssetQuery assetMgmtAssetQuery = new com.siebel.xml.asset_management_complex_io.query.AssetMgmtAssetQuery();
			assetMgmtAssetQuery.setAssetId(getEmptyComplexQueryType());
			assetMgmtAssetQuery.setAssetNumber(getEmptyComplexQueryType());

			ListOfAssetMgmtAssetXaQuery lstAssetMgmtAssetXaQuery = new ListOfAssetMgmtAssetXaQuery();
			AssetMgmtAssetXaQuery assetMgmtAssetXaQuery = new AssetMgmtAssetXaQuery();
			assetMgmtAssetXaQuery.setDisplayName(getEmptyComplexQueryType());
			assetMgmtAssetXaQuery.setDescription(getEmptyComplexQueryType());
			assetMgmtAssetXaQuery.setValue(getEmptyComplexQueryType());
			lstAssetMgmtAssetXaQuery.setAssetMgmtAssetXa(assetMgmtAssetXaQuery);

			assetMgmtAssetQuery
					.setListOfAssetMgmtAssetXa(lstAssetMgmtAssetXaQuery);
			lstAssetMgmtAssetQuery.setAssetMgmtAsset(assetMgmtAssetQuery);

			assetHeaderQuery.setListOfAssetMgmtAsset(lstAssetMgmtAssetQuery);
			lstAssetQuery.setAssetMgmtAssetHeader(assetHeaderQuery);
			input.setListOfAsset(lstAssetQuery);

			AssetManagementComplex assetComplexWS = new AssetManagementComplex();
			AssetManagementComplexPort assetComplexWSPort = assetComplexWS
					.getAssetManagementComplexPort();
			getWebServiceHelper().prepareConnection(
					(BindingProvider) assetComplexWSPort);
			AssetManagementComplexQueryPageOutput output = assetComplexWSPort
					.assetManagementComplexQueryPage(input);

			if (output != null
					&& output.getListOfAsset() != null
					&& output.getListOfAsset().getAssetMgmtAssetHeader() != null
					&& output.getListOfAsset().getAssetMgmtAssetHeader().size() > 0) {
				if (_asset.getProductType().equals(M_PROMOTION)) {
					populatePromotionAndChildAttributes(_asset, output
							.getListOfAsset().getAssetMgmtAssetHeader());
				} else {
					if (output.getListOfAsset().getAssetMgmtAssetHeader()
							.get(0) != null
							&& output.getListOfAsset()
									.getAssetMgmtAssetHeader().get(0)
									.getListOfAssetMgmtAsset() != null
							&& output.getListOfAsset()
									.getAssetMgmtAssetHeader().get(0)
									.getListOfAssetMgmtAsset()
									.getAssetMgmtAsset() != null
							&& output.getListOfAsset()
									.getAssetMgmtAssetHeader().get(0)
									.getListOfAssetMgmtAsset()
									.getAssetMgmtAsset().size() > 0) {
						populateProductAndChildAttributes(_asset, output
								.getListOfAsset().getAssetMgmtAssetHeader()
								.get(0).getListOfAssetMgmtAsset()
								.getAssetMgmtAsset().get(0));
					}
				}

			}

		}
	}

	protected void populateRelatedPromotions(SiebelAsset pAsset)
			throws RepositoryException, PropertyNotFoundException {

		try {
			if (pAsset != null) {
				System.out.println("Inside populateRelatedPromotions "
						+ pAsset.getId());
				String prodId = pAsset.getProductId();
				RepositoryView prodPromoRepositoryView = getCatalogTools()
						.getCatalog().getView(
								getPromotionRelationshipDescriptor());
				QueryBuilder prodPromoQueryBuilder = prodPromoRepositoryView
						.getQueryBuilder();

				// Build query
				//Modified by SYPATEL in order avoid the NullPointerException that causes
				//asset details page to display asset not found message for valid assets.
				RepositoryItem prodItem = null;
				if (getCatalogTools().getProductDetails(prodId) != null) {
        				prodItem = getCatalogTools().getProductDetails(
    						prodId).getProductRepositoryItem();
                                }

        			Query prodPromoQuery = prodPromoQueryBuilder
						.createComparisonQuery(
								prodPromoQueryBuilder
										.createPropertyQueryExpression("product"),
								prodPromoQueryBuilder
										.createConstantQueryExpression(prodItem),
								QueryBuilder.EQUALS);
				// Execute query

				RepositoryItem[] relPromoItems = prodPromoRepositoryView
						.executeQuery(prodPromoQuery);
				//Bug 17439609 - Only if related promo items are not null
				//add it to the asset
				if(relPromoItems != null){
				 ArrayList<String>  promoItems = new  ArrayList<String>() ;
				 RepositoryItem riProd;
				  for (int index = 0; index < relPromoItems.length; index++)
				    {
				      riProd = (RepositoryItem)relPromoItems[index].getPropertyValue("promotion");
				      if (riProd != null){
				      	promoItems.add((String)riProd.getPropertyValue("displayName"));
				      }
				    }
				pAsset.setRelatedPromotions(promoItems);
				}

			}
		} catch (RepositoryException e) {
			throw new RepositoryException(e);
		} catch (PropertyNotFoundException e) {
			throw new PropertyNotFoundException(e);
		}

	}

	private void populateProductAndChildAttributes(
			SiebelAsset _asset,
			com.siebel.xml.asset_management_complex_io.data.AssetMgmtAssetData _assetData) {
		if (_asset != null && _assetData != null) {
			_asset.setAttributes(convertToSiebelAttributes(_assetData
					.getListOfAssetMgmtAssetXa()));

			if (_assetData.getAssetMgmtAsset() != null
					&& _assetData.getAssetMgmtAsset().size() > 0) {
				Iterator<com.siebel.xml.asset_management_complex_io.data.AssetMgmtAssetData> childAssetItr = _assetData
						.getAssetMgmtAsset().iterator();

				while (childAssetItr.hasNext()) {
					com.siebel.xml.asset_management_complex_io.data.AssetMgmtAssetData childAssetData = childAssetItr
							.next();

					if (childAssetData != null) {
						SiebelAsset childAsset = findChildAsset(_asset,
								childAssetData.getAssetId());

						if (childAsset != null) {
							childAsset
									.setAttributes(convertToSiebelAttributes(childAssetData
											.getListOfAssetMgmtAssetXa()));
						}
					}
				}
			}
		}
	}

	private void populatePromotionAndChildAttributes(SiebelAsset _asset,
			List<AssetMgmtAssetHeaderData> _assetHeaders) {
		if (_asset != null && _assetHeaders != null && !_assetHeaders.isEmpty()) {
			Iterator<AssetMgmtAssetHeaderData> assetHeaderItr = _assetHeaders
					.iterator();

			while (assetHeaderItr.hasNext()) {
				AssetMgmtAssetHeaderData assetHeader = assetHeaderItr.next();

				if (assetHeader != null) {
					if (assetHeader.getId().equalsIgnoreCase(_asset.getId())) {
						if (assetHeader.getListOfAssetMgmtAsset() != null
								&& assetHeader.getListOfAssetMgmtAsset()
										.getAssetMgmtAsset() != null
								&& !assetHeader.getListOfAssetMgmtAsset()
										.getAssetMgmtAsset().isEmpty()
								&& assetHeader.getListOfAssetMgmtAsset()
										.getAssetMgmtAsset().get(0) != null) {
							_asset.setAttributes(convertToSiebelAttributes(assetHeader
									.getListOfAssetMgmtAsset()
									.getAssetMgmtAsset().get(0)
									.getListOfAssetMgmtAssetXa()));
						}
					} else {
						SiebelAsset childAsset = findChildAsset(_asset,
								assetHeader.getId());
						if (assetHeader.getListOfAssetMgmtAsset() != null
								&& assetHeader.getListOfAssetMgmtAsset()
										.getAssetMgmtAsset() != null
								&& !assetHeader.getListOfAssetMgmtAsset()
										.getAssetMgmtAsset().isEmpty()
								&& assetHeader.getListOfAssetMgmtAsset()
										.getAssetMgmtAsset().get(0) != null) {
							childAsset
									.setAttributes(convertToSiebelAttributes(assetHeader
											.getListOfAssetMgmtAsset()
											.getAssetMgmtAsset().get(0)
											.getListOfAssetMgmtAssetXa()));
						}
					}
				}
			}
		}
	}

	private SiebelAsset findChildAsset(SiebelAsset _asset, String _id) {
		// TODO Auto-generated method stub
		if (_asset != null && _asset.getChildAssets() != null
				&& _asset.getChildAssets().size() > 0) {
			Iterator<SiebelAsset> childAssetItr = _asset.getChildAssets()
					.iterator();

			while (childAssetItr.hasNext()) {
				SiebelAsset childAsset = childAssetItr.next();

				if (childAsset != null
						&& childAsset.getId().equalsIgnoreCase(_id)) {
					return childAsset;
				}
			}
		}
		return null;
	}

	private ArrayList<SiebelAttribute> convertToSiebelAttributes(
			ListOfAssetMgmtAssetXaData listOfAssetMgmtAssetXa) {
		// TODO Auto-generated method stub
		if (listOfAssetMgmtAssetXa != null
				&& listOfAssetMgmtAssetXa.getAssetMgmtAssetXa() != null) {
			ArrayList<AssetMgmtAssetXaData> lstAssetMgmtAssetXaData = (ArrayList<AssetMgmtAssetXaData>) listOfAssetMgmtAssetXa
					.getAssetMgmtAssetXa();
			if (lstAssetMgmtAssetXaData.size() > 0) {
				Iterator<AssetMgmtAssetXaData> itr = lstAssetMgmtAssetXaData
						.iterator();
				ArrayList<SiebelAttribute> attributes = new ArrayList<SiebelAttribute>();

				while (itr.hasNext()) {
					AssetMgmtAssetXaData assetXaData = (AssetMgmtAssetXaData) itr
							.next();
					SiebelAttribute attr = new SiebelAttribute();
					attr.setDescription(assetXaData.getDescription());
					attr.setDisplayName(assetXaData.getDisplayName());
					attr.setValue(assetXaData.getValue());
					attributes.add(attr);
				}
				return attributes;
			}
		}
		return null;
	}

	private com.siebel.xml.asset_management_io.query.QueryType getEmptyQueryType() {
		com.siebel.xml.asset_management_io.query.QueryType emptyQuery = new com.siebel.xml.asset_management_io.query.QueryType();
		emptyQuery.setValue("");
		return emptyQuery;
	}

	private QueryType getEmptyComplexQueryType() {
		QueryType emptyQuery = new QueryType();
		emptyQuery.setValue("");
		return emptyQuery;
	}

	private boolean isNonNull(String str) {
		if ("".equals(str) || str == null) {
			return false;
		}
		return true;
	}

	public SiebelAsset getAssetDetail(String assetId)
			throws RepositoryException, PropertyNotFoundException {
		try {
			if (customerAssets != null && customerAssets.size() > 0) {
				if (customerAssets.containsKey(assetId)) {
					SiebelAsset asset = (SiebelAsset) customerAssets
							.get(assetId);
					populateAssetAttributes(asset);
					populateRelatedPromotions(asset);
					return asset;
				}
			}
		} catch (RepositoryException e) {
			throw new RepositoryException(e);
		} catch (PropertyNotFoundException e) {
			throw new PropertyNotFoundException(e);
		}

		return null;
	}
}
