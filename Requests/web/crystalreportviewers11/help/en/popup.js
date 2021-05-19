var x,y;

if (document.all) 
{
	document.onmousemove = IECaptureCoords;
}
else if (document.getElementById) 
{
	document.onmousemove = NS6CaptureCoords;
}
else if (document.layers) 
{
	document.captureEvents(Event.MOUSEMOVE);
    document.onmousemove = NSCaptureCoords;
    document.captureEvents(Event.CLICK);
    document.onclick = HidePopup;
}


function IECaptureCoords(e) 
{
	x = event.x+5 + document.body.scrollLeft;
	y = event.y+5 + document.body.scrollTop;
}

function NSCaptureCoords(e) 
{
    if (e.screenX != x && e.screenY != y) {
        x=e.x;
        y=e.y;
    } 
}

function NS6CaptureCoords(e) 
{
	x = e.pageX;
    y = e.pageY;
} 


function popup(title, content)
{
	var contents, k
	contents= content
	for (k=2;k < arguments.length;k++)
	{
		contents= contents + "<BR>" + arguments[k] 
	}
	popup_ (title,contents);	
}

function popup_(titleText,bodyText)
{
	if (document.all)
	{
		var popupText = 
		"<TABLE class='PopupTable'><Tr><td><font face=verdana size=2><B>"+titleText+"</B></td><td align=right><a href='javascript:void(0)' onClick='HidePopup'><img border=0 src='x.gif' onClick='HidePopup'></a></font></td></tr>" +
			"<tr><td><font face=verdana size=2>"+bodyText+"</font></td></tr></TABLE>";
		document.all["Popup"].style.visibility = "visible";
		document.all["Popup"].innerHTML = popupText;	
		document.all["Popup"].style.left = x;
		document.all["Popup"].style.top = y;
		
	} else if(document.getElementById)
	{
		var popupText = 
		"<TABLE Border=1 cellspacing=0 cellpadding=0 bgcolor=#fafad2><TR><TD>" +
		"<TABLE border=0 bgcolor=#fafad2 bordercolor=#fafad2><Tr><td><font face=verdana size=2><B>"
		+titleText+"</B></td><td align=right><a href='javascript:void(0)' onClick='HidePopup(event)'><img border=5 src='x.gif' onClick='HidePopup(event)'></a></font></td></tr>" +
			"<tr><td><font face=verdana size=2>"+bodyText+"</font></td></tr></TABLE>"+"</td></tr></table>";
		
		var obj = document.getElementById("Popup");
        obj.innerHTML = popupText;
        obj.style.left = x;
        obj.style.top = y;
        obj.style.visibility = "visible";

        
	} else if(document.layers)
	{
		var popupText = 
		"<TABLE Border=1 cellspacing=0 cellpadding=0 bgcolor=#fafad2><TR><TD>" +
		"<TABLE border=0 bgcolor=#fafad2 bordercolor=#fafad2><Tr><td><font face=verdana size=2><B>"
		+titleText+"</B></td><td align=right><a href='javascript:void(0)' onClick='HidePopup'><img border=0 src='x.gif' onClick='HidePopup'></a></font></td></tr>" +
			"<tr><td><font face=verdana size=2>"+bodyText+"</font></td></tr></TABLE>"
		+"</td></tr></table>";
		document.layers["PopupLayer"].document.open();
		document.layers["PopupLayer"].document.write(popupText);
		document.layers["PopupLayer"].document.close();
		document.layers["PopupLayer"].pageX = x;
		document.layers["PopupLayer"].pageY = y;
		document.layers["PopupLayer"].visibility = "show";	
	}
}
	
function HidePopup(e) {
	if (document.all)
	{
		if (window.event)
		{
			if (window.event.srcElement.tagName!="A")
			{
				document.all["Popup"].style.visibility = "hidden";
				popupVisible=0;
			}
		}
	} else if(document.getElementById)
	{	
		tmpTarget = e.target;
		while (tmpTarget.tagName == null)
			tmpTarget = tmpTarget.parentNode;
        
		if (tmpTarget.tagName != "A")
		{
			document.getElementById("Popup").style.visibility = "hidden";
		}
	} else if(document.layers)
	{
		document.layers["PopupLayer"].visibility = "hide";
		routeEvent(e);	
	}
	
	return true;
}