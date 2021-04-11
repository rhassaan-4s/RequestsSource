function setFocus(fld) {
//	logInfo("     setFocus: fld.name = "+fld.name);
	setTimeout(function(){fld.focus();fld.select();},5);
}
function setFocusB(fld) {
//	logInfo("     setFocus: fld.name = "+fld.name);
	disableForm(fld.form);
	setTimeout(function(){enableForm(fld.form);fld.focus();fld.select();},5);
}
function disableForm(aForm){
	for( i=0 ; i< aForm.elements.length ; i++ ){
		aForm.elements[i].disabled=true;
	}
}
function enableForm(aForm){
	for( i=0 ; i< aForm.elements.length ; i++ ){
		aForm.elements[i].disabled=false;
	}
}

function initFormValidValue(aForm){
	if(aForm)for( i=0 ; i< aForm.elements.length ; i++ ){
		aForm.elements[i].lastValidValue=aForm.elements[i].value;
	}
}
function delay(gap){ /* gap is in millisecs */
	var then,now; 
	then=new Date().getTime();
	now=then;
	while( (now-then) < gap ) {
		now=new Date().getTime();
	}
}

var inValidation=false;

if (window.XMLHttpRequest) {   		// branch for non IE/Windows ActiveX version 
	liveValidationReq = new XMLHttpRequest();
}

function validateForm(formField){
	if(inValidation){ return false; }
	
	if(formField.lastValidValue==formField.value){ return true; }
	
	inValidation=true;

	if (window.XMLHttpRequest) {
	} else if (window.ActiveXObject) {   		// branch for IE/Windows ActiveX version 
		liveValidationReq = new ActiveXObject( "Microsoft.XMLHTTP" );
	}
	
	if(!liveValidationReq) {
		alert("No Request Object");
	}

	/**
	 *  Make a Request
	 */
	theForm = formField.form ;
	url="/invoice/onLineValidator.html?formName="+theForm.getAttribute("name")+"&formId="+theForm.getAttribute("id")+"&fieldName="+formField.getAttribute("name")+"&fieldValue="+formField.value;
	liveValidationReq.open("GET", url,false);

	liveValidationReq.send(null);
	
	isValid=(liveValidationReq.responseText.length==0)?true:false;

	if(!isValid){
		disableForm(formField.form);
		alert(liveValidationReq.responseText);
		enableForm(formField.form);
		setFocus(formField);
	} else {
		formField.lastValidValue=formField.value;
	}

	inValidation=false;
	return isValid;
}
