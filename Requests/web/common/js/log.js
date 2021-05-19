function logInfo(infoLine) {
	/* */
	document.getElementById('historyInfo').innerHTML = 
		document.getElementById('focusInfo').innerHTML +
		document.getElementById('historyInfo').innerHTML ;
	document.getElementById('focusInfo').innerHTML = infoLine+"<br>";		
	
}