
var lastValue = "";
var isIE = false;
var t = null;
var liveSearchField = null;
var liveSearchHiddenField = null;
var  eventKeyCode = null;
function liveSearchInit() {

	inputList = document.getElementsByTagName("input");
	for (i=0;i<inputList.length;i++){
		livesearch = inputList[i].getAttribute("livesearch");
		if (livesearch == "true"){
			if (navigator.userAgent.indexOf("Safari") > 0) {
				
				inputList[i].addEventListener("keydown",liveSearchKeyPress,false);
			} else if (navigator.product == "Gecko") {
				
				inputList[i].addEventListener("keypress",liveSearchKeyPress,false);
				inputList[i].addEventListener("blur",liveSearchHide,false);
			} else {
				
				inputList[i].attachEvent('onkeydown',liveSearchKeyPress);
				isIE = true;
			}
		}
	}
}

function liveSearchHide(table,firstParam,paramString) {
	//alert("hide");
	var lv = document.getElementById(liveSearchField+"div");
	if(lv != null ){
		lv.style.display = "none";
		var  sh = document.getElementById(liveSearchField+"ul");
		sh.innerHTML = "";
		var highlight = document.getElementById("LSHighlight");
		if (highlight) {
			highlight.removeAttribute("id");
		}
		liveSearchInput = document.getElementById(liveSearchField);
		var bindById = liveSearchInput.getAttribute("bindById");
		//alert("bindById "+bindById);
		var liveSearchHiddenInput = document.getElementById(liveSearchHiddenField);
		if (liveSearchInput.value == null || liveSearchInput.value == ''){
		//alert ("if");
			liveSearchHiddenInput.value = "";
		}
		else if (liveSearchHiddenInput.value == ""){
		//	alert("else");
			if (bindById == 'false'){
			//alert("false");
				liveSearchHiddenInput.value = liveSearchInput.value;
			}
			else{
			//	alert("before dwr");
				var val = liveSearchInput.value;
				//alert ("paramString "+paramString);
				if(paramString != undefined){
					if (paramString != null && paramString != ''){	
						paramString = paramString.replace(/%%/g, "'");
						//alert("here1");
						//qry.getObjectId(fillHidden,table,firstParam,val,paramString);
					}
					else{
						//alert("here2");
						//qry.getObjectId(fillHidden,table,firstParam,val,"");
					}
				}
			//	alert("after dwr");
			}
		}
	}
}

function fillHidden(data){
		//alert("data "+data);
		//alert(" data.length "+data.length);
		var liveSearchHiddenInput = document.getElementById(liveSearchHiddenField);
		if (data != null && data.length > 0){
		//alert("if");
	//	alert ("data[0] "+data[0]);
		//alert ("data[0].id "+data[0].id);
			liveSearchHiddenInput.value = data[0].id;
		}
		else{
	//	alert("else");
	// if addNewRecord == true 
	// then insert the record and return the new id 
	// else put the value = ""
			//var addNewRecord = liveSearchField.getAttribute('addnewRecord');
			//alert("addNewRecord "+addNewRecord);
			liveSearchHiddenInput.value = "";
		}
}

function liveSearchKeyPress(event) {
	eventKeyCode = event.keyCode;
	if (event.keyCode == 40 )
	//KEY DOWN
	{
		highlight = document.getElementById("LSHighlight");
		if (!highlight) {
			highlight = document.getElementById(liveSearchField+"ul").firstChild.firstChild;
		} else {
			highlight.removeAttribute("id");
			highlight = highlight.nextSibling;
		}
		if (highlight) {
			highlight.setAttribute("id","LSHighlight");
		} 
		if (!isIE) { event.preventDefault(); }
		liveSearchInput = document.getElementById(liveSearchField);
		liveSearchInput.value = highlight.innerHTML;
	} 
	//KEY UP
	else if (event.keyCode == 38 ) {
		highlight = document.getElementById("LSHighlight");
		if (!highlight) {
			highlight = document.getElementById(liveSearchField+"div").firstChild.firstChild.lastChild;
		} 
		else {
			highlight.removeAttribute("id");
			highlight = highlight.previousSibling;
		}
		if (highlight) {
				highlight.setAttribute("id","LSHighlight");
		}
		if (!isIE) { event.preventDefault(); }
		liveSearchInput = document.getElementById(liveSearchField);
		liveSearchInput.value = highlight.innerHTML;
	} 
	//ESC
	else if (event.keyCode == 27) {
		highlight = document.getElementById("LSHighlight");
		if (highlight) {
			highlight.removeAttribute("id");
			var idValue = highlight.getAttribute("idValue");
		}
		document.getElementById(liveSearchField+"div").style.display = "none";
		liveSearchInput = document.getElementById(liveSearchField);
		liveSearchInput.value = highlight.innerHTML;
		var liveSearchHiddenInput = document.getElementById(liveSearchHiddenField);
		var bindById = liveSearchInput.getAttribute("bindById");
		if (bindById == 'true'){
			liveSearchHiddenInput.value = idValue;
		}
		else{
			liveSearchHiddenInput.value = liveSearchInput.value;
		}
	} 
	

	
	//BACK SPACE
	else if (event.keyCode == 8){
		highlight = document.getElementById("LSHighlight");
		if (highlight) {
			highlight.removeAttribute("id");
			
		}
		 liveSearchInput = document.getElementById(liveSearchField);
		 length = liveSearchInput.value.length;
		 newValue = liveSearchInput.value.substring(0,length);
		 liveSearchInput.value = newValue;
		 
	}
	
	
	
}

function liveSearchMouseOver(liname,resultId){
		var res = resultId;
		highlight = document.getElementById("LSHighlight");
		if(!highlight){
			newhighlight = document.getElementsByName(liname);
			highlight = newhighlight[0];
		}
		else{
			highlight.removeAttribute("id");
			newhighlight = document.getElementsByName(liname);
			highlight = newhighlight[0];
		}
		if (highlight){
			highlight.setAttribute("id","LSHighlight");
		}
		liveSearchInput = document.getElementById(liveSearchField);
		liveSearchInput.value = highlight.innerHTML;
		var liveSearchHiddenInput = document.getElementById(liveSearchHiddenField);
		
		var bindById = liveSearchInput.getAttribute("bindById");
		if (bindById == 'true'){
			liveSearchHiddenInput.value = resultId;
		}
		else{
			liveSearchHiddenInput.value = liveSearchInput.value;
		}
}


function liveSearchStart(liveSearchFieldId,liveSearchHiddenFieldId) {
//alert("liveSearchFieldId "+liveSearchFieldId);
//alert("liveSearchHiddenFieldId "+liveSearchHiddenFieldId);
	var ele = liveSearchFieldId;
	//alert("ele "+ele);
	liveSearchField = ele;
	liveSearchHiddenField = liveSearchHiddenFieldId;
	
	var lsf = document.getElementById(liveSearchFieldId);
	var lshf = document.getElementById(liveSearchHiddenFieldId);
	lshf.value="";
	//alert("lsf "+lsf);
	actionString = lsf.getAttribute('actionString');
//	alert(actionString);
	tokens = actionString.tokenize(":", " ", true);
//	alert("tokens "+tokens);
//	alert("tokens.length "+tokens.length);
	i = 0;
	while (i<tokens.length){
		lsf.setAttribute(tokens[i],tokens[i+1]);
		i = i + 2;
	}
	
	if (t) {
		window.clearTimeout(t);
	}
	t = window.setTimeout("getSuggestions('"+ele+"')",200);
}


function getSuggestions(liveSearchFieldId){
	
	if (document.getElementById(liveSearchFieldId).value == ""){
	liveSearchHide();
	}else if (lastValue != document.getElementById(liveSearchFieldId).value && eventKeyCode != 38 && eventKeyCode != 40){
		
		value = document.getElementById(liveSearchFieldId).value;
//		alert("value "+value);
		table = document.getElementById(liveSearchFieldId).getAttribute("table");
//		alert("table "+table);
		firstParam = document.getElementById(liveSearchFieldId).getAttribute("firstParam");
		secondParam = document.getElementById(liveSearchFieldId).getAttribute("secondParam");
//		alert("param "+param);
		paramString = document.getElementById(liveSearchFieldId).getAttribute("paramString");
//		var t=setTimeout("5+6",1500);
//		var occur= null;
//		occur=paramString.match(/--/g);
//		if(occur!= null){
//			paramString = paramString.replace(/--/g, "'");
//		}
//		var t=setTimeout("5+6",1500);
		paramString = paramString.replace(/%%/g, "'");
		
		paramString = paramString.split("@@");
		
		if (paramString.length==3){
			
			qry.getAutoCompleteSuggestionsByTypeAndBranch(generateSuggestionsHTML,value,table,firstParam,paramString[0],paramString[1],paramString[2]);
		}else if (paramString.length==2){
				qry.getAutoCompleteSuggestionsByBranch(generateSuggestionsHTML,value,table,firstParam,paramString[0],paramString[1]);
		} else{
			paramString = document.getElementById(liveSearchFieldId).getAttribute("paramString");
			var t=setTimeout("5+6",1500);
			var occur= null;
			occur=paramString.match(/--/g);
			if(occur!= null){
				paramString = paramString.replace(/--/g, "'");
			}
			var t=setTimeout("5+6",1500);
			paramString = paramString.replace(/%%/g, "'");
			
			if(firstParam.indexOf(",") > -1 && table =="destination"){
				qry.getAutoCompleteSuggestionsComplex(generateSuggestionsHTMLComplex,value,table,firstParam,paramString,secondParam);
			}else if(firstParam.indexOf(",") > -1 && table =="item_data"){
				
				qry.getAutoCompleteSuggestionsItemData(generateSuggestionsItemDataHTMLComplex,value,table,firstParam,paramString,secondParam);
				
			}
			else if (table =="store_c_trns_m,store_trns_def "){
				qry.getAutoCompleteSuggestionsWithoutId(generateSuggestionsHTML,value,table,firstParam,paramString);
			}else if(table =="dist_names" || table =="driver" || table=="cars"){
				var arrString = paramString.split(",");
				if(paramString !=''){
					if(arrString.length>1){
						qry.getExternalDestination(generateExternalCodeHTML,value,table,firstParam,secondParam,paramString);
					}else if(arrString.length==1){
						var arrFilter = paramString.split("=");
						if(arrFilter[1]!=''){
							qry.getExternalDestination(generateExternalCodeHTML,value,table,firstParam,secondParam,paramString);
						}
					}
				}
			}else if(table=="car_type" || table=="area"){
				qry.getExternalDestination(generateExternalCodeHTML,value,table,firstParam,secondParam,paramString);
			
			} else if(table == "store_trns_m") {
				qry.getExternalTrans(generateExternalCodeHTML,value,table,firstParam,secondParam,paramString);
				
			} else {
				
				paramString = paramString.replace(/%25/g, "%");
				qry.getAutoCompleteSuggestions(generateSuggestionsHTML,value,table,firstParam,paramString);
			}
		}
		
	}
	lastValue = document.getElementById(liveSearchFieldId).value;
}

function generateSuggestionsHTML(data){
	if (data.length > 0){
		//alert (" > 0");
		htmlString = "<ul class='LSRes' >";
		for (i=0;i<data.length;i++){
			//liname = liveSearchField + 'i';
			//alert(liname);
			htmlString = htmlString + "<li name ="+i+" idValue="+data[i].ID+" onmouseover='liveSearchMouseOver("+i+","+data[i].ID+")'>"+data[i].RESULT+"</li>";
		}
		htmlString = htmlString  + "</ul>";
		
	}
	else{
		htmlString = "";
	}
	fillSuggestionsDiv(htmlString);
}

function generateExternalCodeHTML(data){
	if (data.length > 0){
		//alert (" > 0");
		htmlString = "<ul class='LSRes' >";
		for (i=0;i<data.length;i++){
			//liname = liveSearchField + 'i';
			
			htmlString = htmlString + "<li name ="+i+" idValue="+data[i].ID+" onmouseover=\"liveSearchMouseOver("+i+",'"+data[i].ID+"')\">"+data[i].RESULT+"</li>";
		}
		htmlString = htmlString  + "</ul>";
		
	}
	else{
		htmlString = "";
	}
	fillSuggestionsDiv(htmlString);
}

function generateSuggestionsHTMLComplex(data){
		if (data.length > 0){
			//alert (" > 0");
			htmlString = "<ul class='LSRes' >";
			for (i=0;i<data.length;i++){
				//liname = liveSearchField + 'i';
				//alert(data[i].RESULT);
				htmlString = htmlString + "<li name ="+i+" idValue="+data[i].CODE1+"_"+data[i].CODE2+" onmouseover=\"liveSearchMouseOver("+i+",'"+data[i].CODE1+"_"+data[i].CODE2+"')\">"+data[i].RESULT+"</li>";
			}
			htmlString = htmlString  + "</ul>";
			
		}
		else{
			htmlString = "";
		}
		fillSuggestionsDiv(htmlString);
}
function generateSuggestionsItemDataHTMLComplex(data){
	if (data.length > 0){
		//alert (" > 0");
		htmlString = "<ul class='LSRes' >";
		for (i=0;i<data.length;i++){
			//liname = liveSearchField + 'i';
			
		
			htmlString = htmlString + "<li name ="+i+" idValue="+data[i].CODE1+"_"+data[i].CODE2+" onmouseover=\"liveSearchMouseOver("+i+",'"+data[i].CODE1+"_"+data[i].CODE2+"')\">"+data[i].RESULT+"</li>";
		}
		htmlString = htmlString  + "</ul>";
		
	}
	else{
		htmlString = "";
	}
	fillSuggestionsDiv(htmlString);
}

function fillSuggestionsDiv(htmlString){
		var  res = document.getElementById(liveSearchField+"div");
		res.style.display = "block";
		var  sh = document.getElementById(liveSearchField+"ul");
		sh.innerHTML = htmlString;
}

function disableSubmit(){
	 if (eventKeyCode == 13){
	 	//alert("enter");
	 	//window.event.keyCode = 0;
	 	window.eventKeyCode = 0;
		//return false;
	}
	//else{
		//alert("else");
	//	return true;
	//}
}


function selectResult(id,value,liveSearchField){

	//alert("value "+value);
	//alert("liveSearchField "+liveSearchField);
	//alert("opener "+opener);
	//alert("opener.document "+opener.document);
	//alert("opener.document.getElementById(liveSearchField) "+opener.document.getElementById(liveSearchField));
	liveSearchInput = opener.document.getElementById(liveSearchField);

	var name = opener.document.getElementById("mm_name");
	
	if(name){
		
		var x=document.getElementById("n"+id);
		opener.document.getElementById("mm_name").innerHTML=x.textContent;
		if (opener.document.getElementById("mm_input")) {
			opener.document.getElementById("mm_input").value=x.textContent;			
		}

	}
	//alert("liveSearchInput id ",liveSearchInput.getAttribute("id"));
	//alert("liveSearchInput name ",liveSearchInput.getAttribute("name"));
	var openerLocation = opener.location.href;
	//alert(openerLocation.match('/equationForm.html'));
	if(openerLocation.match('/equationForm.html') != null) {
		liveSearchInput.value +=  value;
	}
	else {
		liveSearchInput.value = value;
	}
	//alert("liveSearchInput.value "+liveSearchInput.value);
	var hiddenField = opener.document.getElementById(liveSearchField+'hidden');
	//alert("hiddenField "+hiddenField);
	var bindById = liveSearchInput.getAttribute("bindById");
	//alert("bindById "+bindById);
	if(openerLocation.match('/equationForm.html') == null) {
		if (bindById == 'true'){
			//alert("true");
			hiddenField.value = id;
		}
		else{
			//alert("false");
			hiddenField.value = value;
		}
	}
	//alert("hiddenField.value "+hiddenField.value);
	//alert(liveSearchInput);
	liveSearchInput.focus();
	window.close();
}

function selectTransactionResult(value,liveSearchField){
	liveSearchInput = opener.document.getElementById(liveSearchField);
	var openerLocation = opener.location.href;
	liveSearchInput.value = value;
	liveSearchInput.focus();
	window.close();
}

function changeHiddenValues(inputId,table,firstKey,secondKey,firstParam,secondParam,paramString){
	inp =  document.getElementById(inputId);
	sb = document.getElementById(inputId+"sb");
	hiddenUrl = document.getElementById(inputId+"hiddenUrl");
	hiddenInput = document.getElementById(inputId+"hidden");
	hiddenInput.value = "";
	inp.value="sss";
	inp.setAttribute('table',table);
	inp.setAttribute('firstParam',firstParam);
	inp.setAttribute('secondParam',secondParam);
	
	inp.setAttribute('paramString',paramString);
	inp.setAttribute('onkeypress','liveSearchStart("'+inputId+'","'+inputId+'hidden" );');
	url = hiddenUrl.value+"table="+table+"&firstKey="+firstKey+"&secondKey="+secondKey+"&firstParam="+firstParam+"&secondParam="+secondParam+"&inputId="+inputId+"&paramString="+paramString;
	if (paramString != null && paramString != '' && paramString != 'null'){
		inp.setAttribute('onBlur','liveSearchHide("'+table+'","'+firstParam+'","'+paramString+'");');
	}
	else{
		inp.setAttribute('onBlur','liveSearchHide("'+table+'","'+firstParam+'","null" );');
	}
	sb.setAttribute('onclick','javascript:createWindow("'+url+'",420,700)');
}

function changeHiddenValues(inputId,table,firstKey,secondKey,firstParam,secondParam,paramString,value){
	inp =  document.getElementById(inputId);
	sb = document.getElementById(inputId+"sb");
	hiddenUrl = document.getElementById(inputId+"hiddenUrl");
	hiddenInput = document.getElementById(inputId+"hidden");
	hiddenInput.value = "";
	if (value == null || value == ""){
		inp.setAttribute('value','');
	}
	else{
		inp.setAttribute('value',value);
	}
	inp.setAttribute('table',table);
	inp.setAttribute('firstParam',firstParam);
	inp.setAttribute('secondParam',secondParam);
	
	inp.setAttribute('paramString',paramString);
	inp.setAttribute('onkeypress','liveSearchStart("'+inputId+'","'+inputId+'hidden" );');
	url = hiddenUrl.value+"table="+table+"&firstKey="+firstKey+"&secondKey="+secondKey+"&firstParam="+firstParam+"&secondParam="+secondParam+"&inputId="+inputId+"&paramString="+paramString;
	if (paramString != null && paramString != '' && paramString != 'null'){
		inp.setAttribute('onBlur','liveSearchHide("'+table+'","'+firstParam+'","'+paramString+'");');
	}
	else{
		inp.setAttribute('onBlur','liveSearchHide("'+table+'","'+firstParam+'","null" );');
	}
	sb.setAttribute('onclick','javascript:createWindow("'+url+'",420,700)');
}

function onAutoCompleteDelete(oldInputId,newInputId,newInputName,onblur,onlinkclick){
	var input = document.getElementById(oldInputId);
	input.setAttribute('onblur',"liveSearchHide(); "+onblur);
	input.setAttribute('myonblur',onblur);
	input.setAttribute('onlinkclick',onlinkclick);

	var table = input.getAttribute('table');
	var firstKey = input.getAttribute('firstKey');
	var secondKey = input.getAttribute('secondKey');
	var firstParam = input.getAttribute('firstParam');
	var secondParam = input.getAttribute('secondParam');
	var paramString = input.getAttribute('paramString');
	//alert("firstParam : "+firstParam+"secondParam :" + secondParam );
	
	//alert('paramString '+paramString);
	var jsIncludes = input.getAttribute('jsIncludes');
	
	input.setAttribute('id',newInputId);
	input.setAttribute('name',newInputId);
	input.setAttribute('onkeypress',"liveSearchStart('"+newInputId+"','"+newInputId+"hidden');");
	
	var div = document.getElementById(oldInputId+"div");
	div.setAttribute('id',newInputId+'div');
	
	var ul = document.getElementById(oldInputId+"ul");
	ul.setAttribute('id',newInputId+'ul');
	
	var hiddenInput = document.getElementById(oldInputId+"hidden");
	hiddenInput.setAttribute('id',newInputId+'hidden');
	hiddenInput.setAttribute('name',newInputName);
	
	var hiddenUrl = document.getElementById(oldInputId+"hiddenUrl");
	hiddenUrl.setAttribute('id',newInputId+'hiddenUrl');
	
	var inputSb = document.getElementById(oldInputId+"sb");
	inputSb.setAttribute('id',newInputId+'sb');
	inputSb.setAttribute('onclick','javascript:createWindow("<c:url value=\'/../../common/searchForm.html?table='+table+'&firstKey='+firstKey+'&secondKey='+secondKey+'&firstParam='+firstParam+'&secondParam='+secondParam+'&inputId='+newInputId+'&paramString='+paramString+'&onblur='+onblur+'&onlinkclick='+onlinkclick+'&jsIncludes='+jsIncludes+'\'/>",420,700)');
}

