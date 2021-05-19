function displayDetails(obj){
	popup = obj;
	status = popup.getAttribute("data_status") ;
	
	switch (status) {
	
	case "Away" :
		/* Away */
		tip= "Was <b>away</b> from "
				+ popup.getAttribute("data_startTimeString") 
				+ " to "
				+ popup.getAttribute("data_endTimeString") ;
	break;
	
	case "NonAuthorizable" :
		/* Arrived and Left */
		tip= "<b>Arrived</b> at "
				+ popup.getAttribute("data_startTimeString") 
				+ " and <b>left</b> at "
				+ popup.getAttribute("data_endTimeString") ;
	break;
	
	case "AsPending" :
		/* Arrived and did not Leave and Did not arrive again to request */
		tip= "<b>Arrived</b> at "
				+ popup.getAttribute("data_startTimeString") 
				+ " and <b>did not leave</b>, and <b>did not request</b> setting a departure time";
	break;
	
	case "Pending" :
		/* Requested and pending */
		tip= "<b>Requested</b> adding the time from "
				+ popup.getAttribute("data_RequestedStartTimeString") 
				+ " to "
				+ popup.getAttribute("data_RequestedEndTimeString") 
				+ "<br>The request was made on " 
				+ popup.getAttribute("data_requestTime") 
				+ " and is still <b>waiting for a manager authorization</b>.";
	break;
	
	case "PendingToday" :
		/* to day */
		tip = "<b>Arrived</b> at "
				+ popup.getAttribute("data_startTimeString") 
				+ " (<b>today</b>) and <b>haven't left yet</b>";
	break;
	
	case "Authorized" :
		/* Requested and Authorized */
		tip= "<b>Requested</b> adding the time from "
				+ popup.getAttribute("data_RequestedStartTimeString") 
				+ " to "
				+ popup.getAttribute("data_RequestedEndTimeString") 
				+ "<br>The request was made on " 
				+ popup.getAttribute("data_requestTime") 
				+ "<br>" 
				+ popup.getAttribute("data_managerName") 
				+ " <b>authorized</b> the time from "
				+ popup.getAttribute("data_startTimeString") 
				+ " to "
				+ popup.getAttribute("data_endTimeString") ;
	break;
	
	}
	
	tip += "<br>Comments: " + popup.getAttribute("data_notes");
	
	return tip;
}
