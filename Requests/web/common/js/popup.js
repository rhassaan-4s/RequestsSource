function createWindow(what,height,width) {
	var pHeight = height;
	var pWidth = width;
	

   var URL = what;
   
   var windowName = 'popup';
  
   var scrollbars = 'yes';
   
   	if(pWidth == null && pHeight==null)
	{
		pHeight = 300;
		pWidth= 700;
	}
  
   var features = 'width='+pWidth+',height='+pHeight+',Resizable=1,status=yes,location=no,left=50,scrollbars='+scrollbars;
   newWindow = window.open (URL, windowName, features);
  
   newWindow.focus();
  
return;
}

function createWindow2(what,height,width,popupName) {
	var pHeight = height;
	var pWidth = width;
	

   var URL = what;
   
   var windowName = popupName;
  
   var scrollbars = 'yes';
   
   	if(pWidth == null && pHeight==null)
	{
		pHeight = 300;
		pWidth= 700;
	}
  
   var features = 'width='+pWidth+',height='+pHeight+',Resizable=1,status=yes,location=no,left=50,scrollbars='+scrollbars;
   newWindow = window.open (URL, windowName, features);
  
   newWindow.focus();
  
return;
}