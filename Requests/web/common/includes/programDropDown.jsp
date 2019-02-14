<script language="JavaScript" type="text/javascript">
function changeProgram() {
	selectObj = document.getElementById("programList");
	if( selectObj != null ) {
		var newSelection = selectObj.options[selectObj.selectedIndex].value
		var destination = "";
		switch (newSelection) {
			case "STORE": application = "STORE";
				break;
			case "ADMINISTRATION": application = "ADMINISTRATION";
				break;
		}
		document.location="/Requests/common/changeApplication.html?application="+application;
	}
}
</script>

<%	
	String applicationName2="";
	if (session.getAttribute("appName") != null ) {
		applicationName2 = (String)session.getAttribute("appName");
	} 
	
	String sel1 = "";
	String sel2 = "";
	String sel3 = "";
	String sel4 = "";
	String sel5 = "";
	String sel6 = "";
	String sel7 = "";
	String sel8 = "";
	String sel9 = "";
	String sel10 = "";
	

		sel6 = ( applicationName2.equals( "ADMINISTRATION" ) ) ? " selected" : "";
	%>

	<select name="programList" id="programList" onChange="changeProgram()">

			<!--option value="REGISTRATION"<%= sel1%>><fmt:message key="registration.caption.applicationName"/></option>

		

			<option value="COMMUNICATION"<%= sel2%>><fmt:message key="communication.caption.applicationName"/></option>

		

			<option value="BANKS"<%= sel3%>><fmt:message key="banks.caption.applicationName"/></option>

	
			<option value="GL"<%= sel4%>><fmt:message key="gl.caption.applicationName"/></option> 


			<option value="CME"<%= sel5%>><fmt:message key="cme.caption.applicationName"/></option> 

			<option value="ATTENDANCE"<%= sel7%>><fmt:message key="attendance.caption.applicationName"/></option> 
			
			<option value="COMMITTEES"<%= sel8%>><fmt:message key="committees.caption.applicationName"/></option>
			
			<option value="TRAINING"<%= sel9%>><fmt:message key="training.caption.applicationName"/></option> 
			 
			<option value="GENERALEXAM"<%= sel10%>><fmt:message key="generalExam.caption.applicationName"/></option--> 

<!--			<option value="STORE"<%= sel1%>><fmt:message key="stores.caption.applicationName"/></option>-->
			<option value="ADMINISTRATION"<%= sel6%>><fmt:message key="administration.caption.applicationName"/></option>

	</select> 
