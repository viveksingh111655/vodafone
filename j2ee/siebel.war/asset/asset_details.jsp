<%@ taglib prefix="dsp"
	uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<dsp:page>
	<head>
<link href="../general.css" rel="stylesheet" type="text/css">
	</head>

	<dsp:importbean bean="/atg/siebel/asset/droplet/AssetDetailDroplet" />
	<dsp:importbean bean="/atg/dynamo/droplet/ForEach" />
	<dsp:importbean bean="/atg/userprofiling/Profile"/>
<dsp:getvalueof param="assetNumber" var="assetNumber"/>
<dsp:getvalueof param="pageNum" var="pageNum"/>
<table border=0 cellpadding=0 cellspacing=0 width=800>
        <tr bgcolor="#DBDBDB">
            <td colspan=2 height=18>&nbsp; <span class=small> <dsp:a
                        href="../admin/company_admin.jsp">
                        <dsp:valueof bean="Profile.parentOrganization.name" />
                    </dsp:a> &gt; 
            </span>
            <span class=small> <dsp:a
                        href="../asset/asset_list.jsp?pageNum=${pageNum}">
                        <dsp:valueof value="Assets"/>
                    </dsp:a> &gt; ${assetNumber}
                    
            </span>
            </td>
        </tr>
    </table><br>
    
    
	<dsp:droplet name="AssetDetailDroplet">
		<dsp:param name="assetId" param="assetId" />

		<dsp:oparam name="empty">
			Asset details not found!!!
		</dsp:oparam>

		<dsp:oparam name="output">
			<table>
				<tr>
					<td colspan="2">
						<!-- Details of the asset and its children go here -->
						<table>
							<tr>
								<td width="15%"><dsp:img src="dummy.jpg"></dsp:img></td>
								<td width="500px">
									<h1>
										<b><dsp:valueof param="asset.productName" /></b>
									</h1>
									<br> <dsp:valueof param="asset.description" /><br> <dsp:valueof
										param="asset.adjustedListPrice" /> <input
									type="submit" value="Change" /> <br>
								<br>
								</td>
							</tr>
						</table>
					</td>
				</tr> 
					<tr><td valign="top">	<h2>Child Assets</h2> </td>		</tr>				
				<dsp:droplet name="ForEach">
					<dsp:param name="array" param="asset.childAssets" />

					<dsp:oparam name="empty">
						<!-- <tr><td>No child assets found!!!</td> -->
					</dsp:oparam>

					<dsp:oparam name="outputStart">
						<tr>
							<td>
					</dsp:oparam>

					<dsp:oparam name="output">
						<b><dsp:valueof param="element.productName" /></b>
						<br>
						<hr size="1" />
						<table>
							<tr>
								<td width="400px"><dsp:valueof param="element.description" />
								</td>
								<td align="right"><dsp:valueof
										param="element.adjustedListPrice" /></td>
								 <td align="right"><br></td>
							</tr>
						</table>

						<dsp:droplet name="ForEach">
							<dsp:param name="array" param="element.attributes" />

							<dsp:oparam name="output">
								<dsp:valueof param="element.displayName" /> - <dsp:valueof
									param="element.value" />
								<br>
							</dsp:oparam>
						</dsp:droplet>
 
					</dsp:oparam>

					<dsp:oparam name="outputEnd">
				</tr>
						</td>
					</dsp:oparam>
				</dsp:droplet>


				<!-- Attribute details go here -->
				<td valign="top"><dsp:droplet name="ForEach">
						<dsp:param name="array" param="asset.attributes" />

						<dsp:oparam name="empty">
							<!--  <tr><td>No features found!!!</td></tr> -->
						</dsp:oparam>

						<dsp:oparam name="outputStart">
							<table>
								<tr>
									<td><b>Included Features</b>
									<hr size="1" /></td>
								</tr>
								</dsp:oparam>

								<dsp:oparam name="output">
									<tr>
										<td valign="top"><dsp:valueof param="element.displayName" />
											- <dsp:valueof param="element.value" /><br></td>
									</tr>
								</dsp:oparam>

								<dsp:oparam name="outputEnd">
							</table>
						</dsp:oparam>
					</dsp:droplet></td>
				</tr>
				<br>
				
				<tr>
				<!-- RELATED Promotions  -->
				<td valign="top">
					
					<dsp:droplet name="ForEach">
						<dsp:param name="array" param="asset.relatedPromotions" />

						<dsp:oparam name="empty">
			<!--  <tr><td>This asset has no related Promotions</td></tr> -->
						</dsp:oparam>

						<dsp:oparam name="outputStart">
							<table>
								<tr>
									<td><b>Recommended Promotions</b>
									<hr size="1" /></td>
								</tr>
								</dsp:oparam>

								<dsp:oparam name="output">
									<tr>
										<td valign="top">
										<dsp:valueof param="element" /> 
											
						<input type="submit" value="Apply" /><br>
										</td>
									</tr>
								</dsp:oparam>

								<dsp:oparam name="outputEnd">
							</table>
						</dsp:oparam>
					</dsp:droplet></td>
				</tr>
			</table>
		</dsp:oparam>
		<dsp:importbean bean="/atg/dynamo/droplet/For" />
	</dsp:droplet>
</dsp:page>