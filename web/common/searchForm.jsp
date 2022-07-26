<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/popupheader.jsp"%>
<%@ page import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html dir="rtl" xml:lang="arabic" lang="arabic">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message key="commons.caption.client" /></title>
<link rel="stylesheet" href="/Requests/web/common/css/all.css">
<script type="text/javascript" src="/Requests/dwr/interface/qry.js"></script>
<script language="JavaScript" type="text/javascript"
	src="/Requests/web/common/js/toolTipContent.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/Requests/web/common/js/popup.js"></SCRIPT>
<script type="text/javascript" src="/Requests/web/common/js/myLiveSearch.js"></script>
<script type='text/javascript' src='/Requests/dwr/interface/commonManager.js'></script>
<script type="text/javascript" src="/Requests/web/common/js/sorttable.js"></script>
<link rel="stylesheet" type="text/css" href="/Requests/web/common/css/epoch_styles.css" />
<script type="text/javascript" src="/Requests/web/common/js/epoch_classes.js"></script>


<script>   
window.onload = function () {
	$('.calendar').calendarsPicker();
}
	function removeAttributes(){
		alert("bye 1");
		commonManager.removeSearchAttributesFromSession(removeAttributesCallBack,'<%=session%>');
		alert("bye 2");
	}

	function removeAttributesCallBack() {
	}

	function enableFilter(type){
		
		if (type == 'name'){
			input1 = document.getElementById('name');	
			input2 = document.getElementById('code');
			document.getElementById('match1').disabled = false;
			document.getElementById('match2').disabled = true;
		}
		if(type == 'code'){
			input1 = document.getElementById('code');
			input2 = document.getElementById('name');	
			document.getElementById('match1').disabled = true;
			document.getElementById('match2').disabled = false;
		}
		
		if (input1.value != ''){
			input2.disabled = true;
		}
		else{
			input2.disabled = false;
		}
		
	}	
	
	function updateDestinationQuery(inputId, table, firstKey, secondKey,
			firstParam, secondParam, control) {
		var paramString = "";
		var branch = document.getElementById('branch').value;
		var canSeeAllStore = document.getElementById('canSeeAllStore').value;
		
		if (control.value != null && control.value != '') {
			var arrCode = control.value;
			if(canSeeAllStore =='true'){
				paramString = "groupf_id=" + arrCode;
			}else{
				paramString = "groupf_id=" + arrCode+"@@"+branch ;
			}
			
		}else{
			if(canSeeAllStore =='true'){
				paramString = "groupf_id=" + arrCode;
			}else{
				paramString = "@@"+branch ;
			}
			
		}
		
		changeHiddenValues(inputId, table, firstKey, secondKey, firstParam, secondParam, paramString, '');
		document.getElementById(inputId).value = "";
	}
	//window.onunload = removeAttributes;
</script>
<%
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String nowDate=sdf.format(cal.getTime());
	%>
<c:forEach items="${jsIncludeList}" var="incl">
	<script type="text/javascript" src="${incl}"></script>
</c:forEach>
</head>
<c:set var="dateNow" value="<%=nowDate%>" />
<c:set var="canSeeAllStore" value="${canSeeAllStore}" />

<input type="hidden" name="branch" id="branch" value="${branchId}"/>
<input type="hidden" name="canSeeAllStore" id="canSeeAllStore" value="${canSeeAllStore}"/>

<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0"
	marginwidth="0" marginheight="0">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table width="90%" border="0" cellspacing="0" cellpadding="0"
			style="padding-right: 10px">
			<tr>
				<td colspan="2" height="30"></td>
			</tr>
			<tr>
				<td colspan="2">
				<form id="searchForm" name="searchForm" method="POST"
					action="<c:url value="/common/searchForm.html"/>">
				<table rules="all" align="center" width="70%" class="sofT">
					<tr id="head_1_ep">
						<td class="helpTitle" colspan="4" nowrap><abc:i18n
							property="commons.header.searchPage" /><fmt:message
							key="commons.header.searchPage" /></td>
					</tr>
					<tr>
						<td nowrap class="formBod"><abc:i18n property="${firstKey}" /><fmt:message>${firstKey}</fmt:message>
						</td>
						<c:if test="${table!='dist_names'}">
						<td class="formBodControl"><input type="text" size="15"
							name="searchCommandId" id="name" value="${searchCommandId}" onkeyup="enableFilter('name');" value="${searchCommandId}"/></td>
						</c:if>
						<c:if test="${table=='dist_names'}">
						<td class="formBodControl"><input type="text" size="15"
							name="searchCommandId" id="name" onkeyup="enableFilter('name');" value="${searchCommandId}" /></td>
						</c:if>
						<td nowrap class="formBod"><abc:i18n
							property="${commons.caption.matchMode}" /><fmt:message
							key="commons.caption.matchMode" /></td>
						<td class="formBodControl"><select name="match1" id="match1">
							<option value="1" ${m1=='1' ? ' selected' : ''}><fmt:message
								key="commons.caption.anywhere" /></option>
							<option value="2" ${m1=='2' ? ' selected' : ''}><fmt:message
								key="commons.caption.start" /></option>
							<option value="3" ${m1=='3' ? ' selected' : ''}><fmt:message
								key="commons.caption.end" /></option>
							<option value="4" ${m1=='4' ? ' selected' : ''}><fmt:message
								key="commons.caption.exact" /></option>
						</select></td>
					</tr>
					
					<c:if test="${table=='dist_names'}">
					<tr>
						<td nowrap class="formBod"><abc:i18n property="commons.caption.Code" /><fmt:message>commons.caption.Code</fmt:message>
						</td>
						<td class="formBodControl"><input type="text" size="15"
							name="searchCommandName" id="code" onkeyup="enableFilter('code');" value="${searchCommandName}" /></td>
						<td nowrap class="formBod"><abc:i18n
								property="${commons.caption.matchMode}" /><fmt:message
								key="commons.caption.matchMode" /></td>

							<td class="formBodControl"><select name="match2" id="match2">
								<option value="2" ${m2=='2' ? ' selected' : ''}><fmt:message
									key="commons.caption.start" /></option>
								<option value="3" ${m2=='3' ? ' selected' : ''}><fmt:message
									key="commons.caption.end" /></option>
								<option value="1" ${m2=='1' ? ' selected' : ''}><fmt:message
									key="commons.caption.anywhere" /></option>
								<option value="4" ${m2=='4' ? ' selected' : ''}><fmt:message
									key="commons.caption.exact" /></option>
							</select></td>
					</tr>
					</c:if>
					
					<!-- 
					<c:if test="${table=='store_destination_groupf'}">
					<tr>
						<td nowrap class="formBod"><abc:i18n property="commons.caption.Code" /><fmt:message>commons.caption.Code</fmt:message>
						</td>
						<td class="formBodControl"><input type="text" size="15"
							name="searchCommandName" id="code" onkeyup="enableFilter('code');" value="${searchCommandName}" /></td>
						<td nowrap class="formBod"><abc:i18n
								property="${commons.caption.matchMode}" /><fmt:message
								key="commons.caption.matchMode" /></td>

							<td class="formBodControl"><select name="match2" id="match2">
								<option value="2" ${m2=='2' ? ' selected' : ''}><fmt:message
									key="commons.caption.start" /></option>
								<option value="3" ${m2=='3' ? ' selected' : ''}><fmt:message
									key="commons.caption.end" /></option>
								<option value="1" ${m2=='1' ? ' selected' : ''}><fmt:message
									key="commons.caption.anywhere" /></option>
								<option value="4" ${m2=='4' ? ' selected' : ''}><fmt:message
									key="commons.caption.exact" /></option>
							</select></td>
					</tr>
					</c:if>
					 -->
					<c:if test="${secondKey != null && secondKey != ''}">
						<tr>

							<td nowrap class="formBod"><abc:i18n property="${secondKey}" /><fmt:message>${secondKey}</fmt:message>
							</td>

							<td class="formBodControl"><input type="text" size="15"
								name="searchCommandName" value="${searchCommandName}" /></td>
							<td nowrap class="formBod"><abc:i18n
								property="${commons.caption.matchMode}" /><fmt:message
								key="commons.caption.matchMode" /></td>

							<td class="formBodControl"><select name="match2">
								<option value="2" ${m2=='2' ? ' selected' : ''}><fmt:message
									key="commons.caption.start" /></option>
								<option value="3" ${m2=='3' ? ' selected' : ''}><fmt:message
									key="commons.caption.end" /></option>
								<option value="1" ${m2=='1' ? ' selected' : ''}><fmt:message
									key="commons.caption.anywhere" /></option>
								<option value="4" ${m2=='4' ? ' selected' : ''}><fmt:message
									key="commons.caption.exact" /></option>
							</select></td>
						</tr>
					</c:if>
					
					<c:if test="${canSeeAllStore=='true' && table =='STORE_STORES_LIST'}">
					<tr>
						<td nowrap="nowrap" class="formBod" colspan="1"><abc:i18n
							property="stores.caption.refrigerator" /> <fmt:message
							key="stores.caption.refrigerator" />&nbsp;</td>
						<td  class="formBodControl" colspan="3">
						
						<select id="branchId" name="branchId" style="width: 120px;">
						<option value=""><fmt:message
							key="commons.caption.select" /></option>
								<c:forEach items="${branchList}" var="branches">
								<option value="${branches.code}" ${branches.code==branchId?' selected' : ''}>${branches.descr}</option>
								</c:forEach>
							</select>
						
						</td>
					</tr>
					</c:if>
					
					<c:if test="${transTableFlag=='true'}">
					<tr>
					<td nowrap class="helpTitle"><abc:i18n
						property="stores.caption.fromDate" /> <fmt:message
						key="stores.caption.fromDate" />&nbsp;</td>
					<td nowrap class="helpTitle">
					<c:if test="${fromDate != null}">
					<input type="text" class="calendar" id="fromDate" name="fromDate" value="${fromDate}">
					</c:if>
					<c:if test="${fromDate == null}">
					<input type="text" class="calendar" id="fromDate" name="fromDate" value="${dateNow}">
					</c:if>
					</td>
					<td nowrap class="helpTitle"><abc:i18n
						property="stores.caption.toDate" /> <fmt:message
						key="stores.caption.toDate" />&nbsp;</td>
					<td nowrap class="helpTitle">
					<c:if test="${toDate != null}">
					<input type="text" class="calendar" id="toDate" name="toDate" value="${toDate}">
					</c:if>
					<c:if test="${toDate == null}">
					<input type="text"  class="calendar" id="toDate" name="toDate" value="${dateNow}">
					</c:if>
					</td>
					</tr>
					
					<!-- tr>
						
							<c:set var="valueStringFrom"
								value="${transType.fromDestination.name}" />
							<c:set var="valueIdFrom" value="${transType.fromDestination.id}" />
							<c:if test="${transType.fromDestinationType.id != null}">
								<c:if test="${canSeeAllStore == true}">
								<c:set var="paramStringFrom"
									value="groupf_id=${transType.fromDestinationType.id}" />
								</c:if>	
								<c:if test="${canSeeAllStore == false}">
								<c:set var="paramStringFrom"
									value="groupf_id=${transType.fromDestinationType.id}" />
								</c:if>
							</c:if>
						
						
							<td nowrap="nowrap" class="formBod">&nbsp;<abc:i18n
								property="stores.caption.fromDestination" /><fmt:message
								key="stores.caption.fromDestination" />&nbsp;</td>
							
								<td class="formBodControl">
								<select id="fromDestinationType"
									name="fromDestinationType" style="width: 225px;"
									onchange="updateDestinationQuery('fromCode','store_destination_groupf','commons.caption.description','','name_ar','id',this);">
									<option value=""><fmt:message
										key="commons.caption.select" /></option>
									<c:forEach items="${groupfList}" var="aGroupf">
										<option value="${aGroupf.id}">
											${aGroupf.name}</option>
									</c:forEach>
								</select>
								
								</td>
							
							
						
						<td nowrap="nowrap" class="formBod">&nbsp;<abc:i18n
							property="commons.caption.code" /><fmt:message
							key="commons.caption.code" />&nbsp;</td>
						<td class="formBodControl">
						<c:if test="${canSeeAllStore == true}">
						
							<abc:autocomplete inputId="fromCode"
								inputName="fromCodeId"
								table="store_destination_groupf"
								firstKey="commons.caption.description" firstParam="name_ar"
								secondParam="id" valueString="${valueStringFrom}"
								valueId="${valueIdFrom}" bindById="true" showSearchButton="true"
								searchButtonUrl="/common/searchForm.html"
								paramString="${paramStringFrom}" />
						
						</c:if>
						<c:if test="${canSeeAllStore == false}">
						
							<abc:autocomplete inputId="fromCode"
								inputName="fromCodeId"
								table="store_destination_groupf"
								firstKey="commons.caption.description" firstParam="name_ar"
								secondParam="id" valueString="${valueStringFrom}"
								valueId="${valueIdFrom}" bindById="true" showSearchButton="true"
								searchButtonUrl="/common/searchForm.html"
								paramString="${paramStringFrom}@@${branchId}" />
						
						</c:if>
						</td>
					</tr>
					<tr>
						
							<c:set var="valueStringTo" value="${transType.toDestination.name}" />
							<c:set var="valueIdTo" value="${transType.toDestination.id}" />
							<c:if test="${transType.toDestinationType.id != null}">
								<c:if test="${canSeeAllStore == true}">
								<c:set var="paramStringTo"
									value="groupf_id=${transType.toDestinationType.id}" />
								</c:if>	
								<c:if test="${canSeeAllStore == false}">
								<c:set var="paramStringTo"
									value="groupf_id=${transType.toDestinationType.id}" />
								</c:if>
							</c:if>
						
						
						
							<td nowrap="nowrap" class="formBod">&nbsp;<abc:i18n
								property="stores.caption.toDestination" /><fmt:message
								key="stores.caption.toDestination" />&nbsp;</td>
							
								<td class="formBodControl"><select id="toDestinationType"
									name="toDestinationType" style="width: 225px;"
									onchange="updateDestinationQuery('toCode','store_destination_groupf','commons.caption.description','','name_ar','id',this);">
									<option value=""><fmt:message
										key="commons.caption.select" /></option>
									<c:forEach items="${groupfList}" var="aGroupf" varStatus="loop">
										<option value="${aGroupf.id}">
											${aGroupf.name}</option>
										
									</c:forEach>
								</select></td>
							
							
						<td nowrap="nowrap" class="formBod">&nbsp;<abc:i18n
							property="commons.caption.code" /><fmt:message
							key="commons.caption.code" />&nbsp;</td>
						<td class="formBodControl">
						<c:if test="${canSeeAllStore == true}">
						
							<abc:autocomplete inputId="toCode"
								inputName="toCodeId"
								table="store_destination_groupf"
								firstKey="commons.caption.description" firstParam="name_ar"
								secondParam="id" valueString="${valueStringTo}"
								valueId="${valueIdTo}" bindById="true" showSearchButton="false"
								searchButtonUrl="/common/searchForm.html"
								paramString="${paramStringTo}" />
						
						</c:if>
						<c:if test="${canSeeAllStore == false}">
						
							<abc:autocomplete inputId="toCode"
								inputName="toCodeId"
								table="store_destination_groupf"
								firstKey="commons.caption.description" firstParam="name_ar"
								secondParam="id" valueString="${valueStringTo}"
								valueId="${valueIdTo}" bindById="true" showSearchButton="false"
								searchButtonUrl="/common/searchForm.html"
								paramString="${paramStringTo}@@${branchId}"/>
						
						</c:if>
						</td>
					</tr-->
					
					</c:if>
					
					<tr>
						<td colspan="4" class="formBodControl"><abc:i18n
							property="commons.button.search" /><input type="submit"
							name="save" value="<fmt:message key="commons.button.search"/>"
							class="button" />&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<input type="hidden" name="transTypeId" value="${transTypeId}" />
					<input type="hidden" name="storeTransDefId" value="${storeTransDefId}"/>
					<input type="hidden" name="table" value="${table}" />
					<input type="hidden" name="firstKey" value="${firstKey}" />
					<input type="hidden" name="secondKey" value="${secondKey}" />
					<input type="hidden" name="firstParam" value="${firstParam}" />
					<input type="hidden" name="secondParam" value="${secondParam}" />
					<input type="hidden" name="inputId" value="${inputId}" />
					<input type="hidden" name="paramString" value="${paramString}" />
					<input type="hidden" name="onblur" value="${onblur}" />
					<input type="hidden" name="jsIncludes" value="${jsIncludes}" />
					<input type="hidden" name="onlinkclick" value="${onlinkclick}" />
					<input type="hidden" name="branchForValidRoom"  value="${branchForValidRoom}"/>
				</table>
			</form>
			<br />
			<c:if test="${results != null}">
				<abc:paging
					parametersString="table=${table}&firstParam=${firstParam}&secondParam=${secondParam}&firstKey=${firstKey}&secondKey=${secondKey}&match1=${m1}&match2=${m2}&inputId=${inputId}&searchCommandId=${searchCommandId}&searchCommandName=${searchCommandName}&onblur=${onblur}&jsIncludes=${jsIncludes}&onlinkclick=${onlinkclick}&paramString=${paramString}&branchId=${branchId}&fromDate=${fromDate}&toDate=${toDate}&transTypeId=${transTypeId}&storeTransDefId=${storeTransDefId}&branchForValidRoom=${branchForValidRoom}" />
			</c:if>
			<table width=100% border=0 cellpadding=0 cellspacing=0 rules="all"
				align="center" class="sortable">
				<tr id="head_1_ep"> 
				<c:if test="${(table=='view_store_dep_trans' || table =='store_c_trns_m') && transMapList[0].trns_code.storeTrnsDefSpec.is_contract == '0' && transMapList[0].trns_code.storeTrnsDefSpec.is_reservation == '0'}">
					<td nowrap class="helpTitle">&nbsp; <fmt:message
					key="stores.caption.transNumber" />&nbsp;</td>
					<td nowrap class="helpTitle">&nbsp; <fmt:message
					key="stores.caption.refrigerator" />&nbsp;</td>
					<td nowrap class="helpTitle">&nbsp; <fmt:message
					key="stores.caption.fromDestination" />&nbsp;</td>
					<td nowrap class="helpTitle">&nbsp; <fmt:message
					key="stores.caption.toDestination" />&nbsp;</td>
					</c:if>
					<c:if test="${table =='store_c_trns_m' && transMapList[0].trns_code.storeTrnsDefSpec.is_contract == '1' || transMapList[0].trns_code.storeTrnsDefSpec.is_reservation == '1'}">
					<td nowrap class="helpTitle">&nbsp; <fmt:message
					key="stores.caption.transNumber" />&nbsp;</td>
					<td nowrap class="helpTitle">&nbsp; <fmt:message
					key="commons.caption.transDate" />&nbsp;</td>
					<td nowrap class="helpTitle">&nbsp; <fmt:message
					key="stores.caption.refrigerator" />&nbsp;</td>
					<td nowrap class="helpTitle">&nbsp; <fmt:message
					key="stores.caption.destCustomer" />&nbsp;</td>
					</c:if>
					
					<c:if test="${table !='store_c_trns_m' && table!='view_store_dep_trans'}">
					<td class="helpTitle" nowrap align="center">&nbsp;<abc:i18n
						property="${firstKey}" /><fmt:message>${firstKey}</fmt:message>&nbsp;</td>

					<c:if test="${secondKey != null && secondKey != ''}">
						<td class="helpTitle" nowrap align="center">&nbsp;<abc:i18n
							property="${secondKey}" /><fmt:message>${secondKey}</fmt:message>&nbsp;</td>
					</c:if>
					</c:if>
					
				</tr>
				<c:if test="${(table=='view_store_dep_trans' || table =='store_c_trns_m') && transMapList[0].trns_code.storeTrnsDefSpec.is_contract == '0' && transMapList[0].trns_code.storeTrnsDefSpec.is_reservation == '0'}">
				
				<c:forEach items="${transMapList}" var="aResult">
	
					<tr>
						<td nowrap class="helpBod"
							style="text-align: right; font-size: 10.5pt;"><a
							href="javascript:selectResult('${aResult.id}','${aResult.trns_no}','${inputId}');${onlinkclick}">${aResult.trns_no}</a>
						</td>
						<td nowrap class="helpBod">&nbsp; ${aResult.branch.descr}</td>
						<c:if test="${aResult.extdestfrom==false}">
						<td nowrap class="helpBod">&nbsp; ${aResult.fromDestination.name_ar}</td>
						</c:if>
						<c:if test="${aResult.extdestfrom==true}">
						<td nowrap class="helpBod">&nbsp; ${aResult.extFromCode}</td>
						</c:if>
						<c:if test="${aResult.extdestto==false}">
						<td nowrap class="helpBod">&nbsp; ${aResult.toDestination.name_ar}</td>
						</c:if>
						<c:if test="${aResult.extdestto==true}">
						<td nowrap class="helpBod">&nbsp; ${aResult.extToCode}</td>
						</c:if>
						
						
						
					</tr>

				</c:forEach>
				</c:if>
				
				<c:if test="${table =='store_c_trns_m' && transMapList[0].trns_code.storeTrnsDefSpec.is_contract == '1' || transMapList[0].trns_code.storeTrnsDefSpec.is_reservation == '1'}">
				
				<c:forEach items="${transMapList}" var="aResult">

					<tr>
						<td nowrap class="helpBod"
							style="text-align: right; font-size: 10.5pt;"><a
							href="javascript:selectResult('${aResult.id}','${aResult.trns_no}','${inputId}');${onlinkclick}">${aResult.trns_no}</a>
						</td>
						<td nowrap class="helpBod">&nbsp; <_4s_:formatMiladiDate	value="${aResult.trns_date}" /></td>
						<td nowrap class="helpBod">&nbsp; ${aResult.branch.code} - ${aResult.branch.descr}</td>
						<td nowrap class="helpBod">&nbsp; ${aResult.fromDestination.name_ar}</td>
					</tr>

				</c:forEach>
				</c:if>
				
				<c:if test="${table !='store_c_trns_m' && table !='view_store_dep_trans'}">
				
				<c:forEach items="${results}" var="aResult">

					<tr>
						<td nowrap class="helpBod"
							style="text-align: right; font-size: 10.5pt;"><a
							href="javascript:selectResult('${aResult.identity}','${aResult.id}','${inputId}');${onlinkclick}">${aResult.id}</a>
						</td>
						<c:if test="${secondKey != null && secondKey != ''}">
							<td nowrap class="helpBod"
								style="text-align: right; font-size: 10.5pt;" id="n${aResult.identity}">*${aResult.description}</td>
						</c:if>
					</tr>

				</c:forEach>
				</c:if>
			</table>

			</td>
			</tr>
		</table>



		<%@ include file="/web/common/includes/popupfooter.jsp"%>