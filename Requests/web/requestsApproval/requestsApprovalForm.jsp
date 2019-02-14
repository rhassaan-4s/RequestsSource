<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<abc:security property="1034"/>
<script type="text/javascript">
function cancelApproval(){
	alert('request_id===='+request_id.value);
	alert('99999');
	//var reqId=document.getElementById("reqId").value;
	//var URL='requestsApprovalForm.html?reqId='+reqId+'&cancelApproval='+true;
	//window.location.href=URL;
}

function cancelApp(){
	//alert('--------x--------');
	var requestId=document.getElementById("requestType").value;
	//alert('----requestType----'+requestId);
	var reqId=document.getElementById("reqId").value;
	var URL='requestsApprovalForm.html?reqId='+reqId+'&cancelApproval='+true+'&done='+true+'&requestType='+requestId;
	//document.getElementById("done").value='true';	
	window.location.href=URL;
	//document.getElementById("done").value='true';
	//alert('------');

}
</script>


<style type="text/css">
#MM_table td {
	border: 1px solid #99CCFF;
}
</style>

<c:if test="${data==1}"><form id="requestsApprovalForm" name="requestsApprovalForm" method="POST"
			action="<c:url value="/requestsApproval/requestsApprovalForm.html?reqId="/>${requestInfo.id}">
	<table width="100%" border="0" cellspacing="0" cellpadding="7"
		style="padding-right: 10px">
		
		<tr>
			<td colspan="2">
<!--			<input type="text" name="done" id="done" value="" >-->
			<input type="hidden" name="reqId" id="reqId" value="${requestInfo.id}" >
			<input type="hidden" name="last" value="${last}" >
			<input type="hidden" name="requestType" id="requestType" value="${requestType}" >
			<input type="hidden"  id="errand" name="errand" value="${errand}"/>
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if></td>
		</tr>
		<tr>
		
		</tr>
		<tr>
			<td>
			<table align="center" width="90%" class="sofT">
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="7" nowrap><abc:i18n
						property="requestsApproval.header.requestsApprovalForm" /><fmt:message
						key="requestsApproval.header.requestsApprovalForm" /></td>
				</tr>
	
				<tr>
					<td nowrap class="formBodControl" ><abc:i18n
						property="requestsApproval.caption.userName" /> <fmt:message
						key="requestsApproval.caption.userName" /></td>
					<td class="formBodControl" colspan="6">${requestInfo.login_user.name}</td>
				</tr>
	
				<tr id="head_1_ep">
					<td class="helpTitle" colspan="7" align="center"
						style="text-align: center;"><abc:i18n
						property="requestsApproval.requestsApprovalForm.reqInfo" /><fmt:message
						key="requestsApproval.requestsApprovalForm.reqInfo" /></td>
				</tr>
	
	
				<tr>
					<td nowrap class="formBodControl" bgcolor="white"><abc:i18n
						property="requestsApproval.caption.requestName" /> <fmt:message
						key="requestsApproval.caption.requestName" /></td>
	
					<td nowrap class="formBodControl" ><abc:i18n
						property="requestsApproval.caption.requestType" /> <fmt:message
						key="requestsApproval.caption.requestType" /></td>
	
					<td nowrap class="formBodControl" ><abc:i18n
						property="requestsApproval.caption.requestDate" /> <fmt:message
						key="requestsApproval.caption.requestDate" /></td>
	
					<td nowrap class="formBodControl" ><abc:i18n
						property="commons.caption.from" />
					<fmt:message
						key="commons.caption.from" /></td>
	
					<td nowrap class="formBodControl" align="center">
					<abc:i18n
						property="commons.caption.to" /> <fmt:message
						key="commons.caption.to" /></td>

<c:choose>
					<c:when test="${requestType==3}"><td nowrap class="formBodControl" >
					<abc:i18n
						property="requestsApproval.caption.leaveEffect" /> <fmt:message
						key="requestsApproval.caption.leaveEffect" /></td>
					</c:when>
					
					<c:otherwise><td nowrap class="formBodControl" >
					<abc:i18n
						property="requestsApproval.requestsApprovalForm.reqPeriod" /> <fmt:message
						key="requestsApproval.requestsApprovalForm.reqPeriod" /></td>
					</c:otherwise>
					</c:choose>	 	
						 						
					<td nowrap class="formBodControl"><abc:i18n
						property="commons.caption.notes" /> <fmt:message
						key="commons.caption.notes" /></td>
						
				</tr>
	
	
				<tr>
					<c:choose>
					<c:when test="${errand=='true'}">
						<td nowrap class="formBodControl" >مأموريه</td>
		
						<td nowrap class="formBodControl" >
						مأموريه </td>				
					
					</c:when>
					<c:otherwise>
						<td nowrap class="formBodControl" >${requestInfo.request_id.description}</td>
		
						<td nowrap class="formBodControl" >
						${requestInfo.request_id.description}</td>
					</c:otherwise>
					</c:choose>
					<td nowrap class="formBodControl" ><_4s_:formatMiladiDate value="${requestInfo.request_date}"/></td>
	
					<td nowrap class="formBodControl" >
					<c:if test="${requestType==3}">
					${requestInfo.period_from}</c:if>
					
					<c:if test="${requestType!=3}">
					<_4s_:formatMiladiDate value="${requestInfo.from_date}"/></c:if>
					
					</td>	
	
					<td class="formBodControl">
						<c:if test="${requestType==3}">
					${requestInfo.period_to}</c:if>
						<c:if test="${requestType!=3}">
					<_4s_:formatMiladiDate value="${requestInfo.to_date}"/></c:if>
					</td>
					
					<c:if test="${requestType!=3}"><td class="formBodControl">
						${requestInfo.withdrawDays}
					</td></c:if>
					
					<td nowrap class="formBodControl" >${requestInfo.notes}</td>

				</tr>
				
				<tr>
					<td class="formBodControl" colspan="7" align="center">
	
					<table id="MM_table" border="0" cellspacing="3" width="100%">
						<tr id="head_1_ep" style="text-align: center;">
							<td class="helpTitle" colspan="7" nowrap
								style="text-align: center;"><abc:i18n
								property="commons.button.getVacCredit" /><fmt:message
								key="commons.button.getVacCredit" /></td>
						</tr>
						<tr>
	
							<td  class="formReq"><abc:i18n
								property="requestsApproval.caption.vacLimit" />
							<fmt:message
								key="requestsApproval.caption.vacLimit" /></td>

							<td  class="formReq"><abc:i18n
								property="requestsApproval.caption.vacConsumed" /> <fmt:message
								key="requestsApproval.caption.vacConsumed" /></td>
							<td class="formReq"><abc:i18n
								property="requestsApproval.caption.vacCredit" /> <fmt:message
								key="requestsApproval.caption.vacCredit" /></td>
							
	
						</tr>
							
						<tr>
							<td  class="formBodControl">${vacLimit}</td>
							<td  class="formBodControl">${vacConsumed}</td>
							<td  class="formBodControl">${vacCredit}</td>
						</tr>
				</table>
				</td>
				</tr>
	
				<tr>
					<td class="formBodControl" colspan="7" align="center">
	
					<table id="MM_table" border="0" cellspacing="3" width="100%">
						<tr id="head_1_ep" style="text-align: center;">
							<td class="helpTitle" colspan="7" nowrap
								style="text-align: center;"><abc:i18n
								property="requestsApproval.header.requestsApprovalForm" /><fmt:message
								key="requestsApproval.header.requestsApprovalForm" /></td>
						</tr>
						<tr>
	
							<td width="130" class="helpTitle"><abc:i18n
								property="requestsApproval.requestsApprovalForm.reqResTitle" />
							<fmt:message
								key="requestsApproval.requestsApprovalForm.reqResTitle" /></td>

							<td colspan="2" class="formReq"><abc:i18n
								property="requestsApproval.requestsApprovalForm.reqStatus" /> <fmt:message
								key="requestsApproval.requestsApprovalForm.reqStatus" /></td>
							<td class="formReq"><abc:i18n
								property="requestsApproval.requestsApprovalForm.reqResName" /> <fmt:message
								key="requestsApproval.requestsApprovalForm.reqResName" /></td>
							<td class="helpTitle"><abc:i18n
								property="commons.caption.notes" /> <fmt:message
								key="commons.caption.notes" /></td>

	
	
						</tr>
						<c:forEach items="${approvalList}" var="record">
							
						<tr>
							<td width="120" class="formBodControl">${record.title}</td>
									<c:if test="${record.status==2 && posted!=1}">
										<td width="130"><abc:i18n
											property="requestsApproval.requestsApprovalForm.reqApproval" />
										<fmt:message
											key="requestsApproval.requestsApprovalForm.reqApproval" /> <input
											type="radio" checked="checked" name="status" value="1" /><input type="hidden"
											name="accId" value="${record.id}"></td>
										<td width="140"><abc:i18n
											property="requestsApproval.requestsApprovalForm.reqRejected" />
										<fmt:message
											key="requestsApproval.requestsApprovalForm.reqRejected" /> <input
											type="radio" name="status" value="0" /></td>
										<td class="formBod" >${record.user}</td>
										<td class="formBod" >${record.notes}</td>
									</c:if>
									
									<c:choose>
										<c:when test="${record.status==0 && record.user==emp && done!='true' && posted!=1 && approvedBy==operator && approvedBy!='' &&operator!='' && approvedBy!=null &&operator!=null}">
											<td colspan="2"><abc:i18n
												property="requestsApproval.requestsApprovalForm.reqRejected" />
											<fmt:message
												key="requestsApproval.requestsApprovalForm.reqRejected" /></td>
											<td>${record.user}</td>
											<td>									
											<abc:i18n property="requestsApproval.button.cancelApproval" />
											<input type="button" name="cancelApproval" onclick="cancelApp()" value=<fmt:message key="requestsApproval.button.cancelApproval"/>
												class="button" />
											</td> 	
											<td>${record.note}</td>
										</c:when>
										
										<c:when test="${record.status==0  && posted!=1 }">
											<c:if test="${approvedBy!=null && operator!=null}">
												<c:if test="${approvedBy!=operator}">
													<td colspan="2"><abc:i18n
														property="requestsApproval.requestsApprovalForm.reqRejected" />
													<fmt:message
														key="requestsApproval.requestsApprovalForm.reqRejected" /></td>
													<td>${record.user}</td><td></td>
													<td>${record.note}</td>
												</c:if>
											</c:if>
											
											
										</c:when>
									</c:choose>	
									
									<c:choose>			
										<c:when test="${record.status==1 && lastOne=='true'&& record.user==emp && done!='true' && posted!=1 && approvedBy==operator && approvedBy!='' &&operator!='' && approvedBy!=null &&operator!=null}">
											<td colspan="2"><abc:i18n
												property="requestsApproval.requestsApprovalForm.reqApproval" />
											<fmt:message
												key="requestsApproval.requestsApprovalForm.reqApproval" />
												</td>
											<td>${record.user}</td>
											
											<td>									
											<abc:i18n property="requestsApproval.button.cancelApproval" />
											<input type="button" name="cancelApproval" onclick="cancelApp()" value=<fmt:message key="requestsApproval.button.cancelApproval"/>
												class="button" />
											</td>	
											<td>${record.note}</td>
										</c:when>
																																	
										<c:when test="${record.status==1}" >
											<td colspan="2"><abc:i18n
												property="requestsApproval.requestsApprovalForm.reqApproval" />
											<fmt:message
												key="requestsApproval.requestsApprovalForm.reqApproval" />
												</td>
											<td>${record.user}</td><td></td>
											<td>${record.note}</td>
										</c:when>			
													

									</c:choose>
									
									<c:choose>
										<c:when test="${record.status==3 && done!='true' && posted!=1 && record.user==emp && approvedBy==operator && approvedBy!='' &&operator!='' && approvedBy!=null &&operator!=null}">
											<td colspan="2"></td><td></td><td></td><td></td>
										<tr>
											<td colspan="2"></td>
											<td ><abc:i18n
													property="requestsApproval.requestsApprovalForm.reqApproval" />
												<fmt:message
													key="requestsApproval.requestsApprovalForm.reqApproval" />
													</td>
											<td>${record.user}</td>
												
											<td>
												<abc:i18n property="requestsApproval.button.cancelApproval" />
												<input type="button" name="cancelApproval" onclick="cancelApp()" value=<fmt:message key="requestsApproval.button.cancelApproval"/>
													class="button" />
											</td>
											<td>${record.note}</td>
										</tr>			
										</c:when>
										
										<c:when test="${record.status==3  && posted!=1 }">
										
										<td></td><td></td>
										<tr><td></td>
											<c:if test="${approvedBy!=null && operator!=null}">
												<c:if test="${approvedBy!=operator}">
													<td colspan="2"><abc:i18n
														property="requestsApproval.requestsApprovalForm.Approvaldenied" />
													<fmt:message
														key="requestsApproval.requestsApprovalForm.Approvaldenied" />
														</td>
													<td>${record.user}</td><td></td>
													<td>${record.note}</td>
												</c:if>
											</c:if>
											
											<c:if test="${approvedBy==null && operator==null}">
												<td colspan="2"><abc:i18n
													property="requestsApproval.requestsApprovalForm.Approvaldenied" />
												<fmt:message
													key="requestsApproval.requestsApprovalForm.Approvaldenied" />
													</td>
												<td>${record.user}</td><td></td>
												<td>${record.note}</td>
											</c:if>
										</tr>
										</c:when>
									</c:choose>
							
						</tr>
						</c:forEach> 	
						<c:if test="${showbSubmit==1}">
							<tr>
								<td width="130" class="formBodControl"><abc:i18n
									property="commons.caption.notes" /> <fmt:message
									key="commons.caption.notes" /></td>
								<td colspan="6"><textarea name="note" rows="5" cols="40"></textarea></td>
							</tr>
						</c:if>
						</table>
						</td>
					</tr>
					<tr>
						<td colspan="7">&nbsp;</td>
					</tr>
					<tr>
						<c:choose>
		
							<c:when test="${showbSubmit==1}">
								<td colspan="7"><abc:i18n property="commons.button.save" /><input
									type="submit" name="save"
									value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;
								<abc:i18n property="commons.button.cancel" /><input type="reset"
									name="cancel" value=<fmt:message key="commons.button.cancel"/>
									class="button" /></td>
							</c:when>
							
							<c:when test="${showbSubmit==0}">
							
								<td colspan="6">
								<font size="+2" color="green">
								<abc:i18n
									property="requestsApproval.requestsApprovalForm.reqStatus" /> <fmt:message
									key="requestsApproval.requestsApprovalForm.reqStatus" /> : 
								<c:if test="${requestInfo.approved==1}">					
								
								<abc:i18n
									property="requestsApproval.requestsApprovalForm.reqApproval" />
								<fmt:message
									key="requestsApproval.requestsApprovalForm.reqApproval" />
								</c:if>
								<c:if test="${requestInfo.approved==99}">					
								
								<abc:i18n
									property="requestsApproval.requestsApprovalForm.reqRejected" />
								<fmt:message
									key="requestsApproval.requestsApprovalForm.reqRejected" />
								</c:if>
								
								<c:if test="${requestInfo.approved==0}">					
									لم يكتمل	
									في انتظار موافقة الموظف المسئول
								</c:if>
								
								
									</font>
									&nbsp;
									</td>
							
							</c:when>
							<c:otherwise>
							
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
			</td>
		</tr>

	</table>	</form>
</c:if>


<%@ include file="/web/common/includes/footer.jsp"%>
