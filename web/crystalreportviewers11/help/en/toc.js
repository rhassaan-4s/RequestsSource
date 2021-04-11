function onsync()
{
	var toc;
	var topic;

	if (this.name == "frmControl")
	{
		toc = this;
		topic = this.parent.frmTopic;
	}
	else if (this.name == "frmTopic")
	{
		toc = this.parent.frmControl;
		topic = this;
	}
	else return;

	// Return if the active left-hand page is the Index.
	if (toc.location.toString().search(/IX\.htm$/) != -1) return;
	
	// Get the page ID from the topic page. This call relies on our
	// <a id="tocid" name="unique"></a> tag written into every new page.
	var tocid = topic.document.getElementById("tocid");
	if (tocid == null) return
	else var myId = tocid.name;
	
	var aLink = toc.document.getElementById("a" + myId);
	// Return if there is no link in the TOC with an ID that matches.
	if (aLink == null) return;

	var what = aLink;
	var parentDivs = [];
	while (what.parentNode.tagName != 'BODY')
	{
 		what = what.parentNode;
		if (what.tagName == "DIV")
		{
			var divId = what.id;
			var imgId = 'i' + what.id.substring(1,what.id.length);
			var img = toc.document.getElementById(imgId);
			if (what.style.visibility=='hidden' || what.style.display=='none')
			{
				what.style.display='';
				what.style.visibility='visible';
				if (img != null) img.src='open.gif';
			}
			parentDivs.push(what);
		}
	}
	
	// Highlight the link.
	redden(toc,myId);
	
	//// Scroll to the parent.
	//var topParent = parentDivs.pop();
	//if (topParent != null) scroll(toc,topParent.id);

}

function unsync()
{
	var toc;
	var topic;

	if (this.name == "frmControl")
	{
		toc = this;
		topic = this.parent.frmTopic;
	}
	else if (this.name == "frmTopic")
	{
		toc = this.parent.frmControl;
		topic = this;
	}
	else return;

	// Return if the active left-hand page is the Index.
	if (toc.location.toString().search(/IX\.htm$/) != -1) return;

	// Get the page ID from the topic page. This call relies on our
	// <a id="tocid" name="unique"></a> tag written into every new page.
	var tocid = topic.document.getElementById("tocid");
	if (tocid == null) return
	else var myId = tocid.name;
	
	// Blacken its link.
	blacken(toc,myId);

}

function browsebook(id){

	var img = document.getElementById("i" + id);
	var div = document.getElementById("d" + id);

	if (div.style.visibility=='hidden' || div.style.display=='none')
	{
		div.style.display='';
		div.style.visibility='visible';
		img.src="open.gif";
	}
	else
	{
		div.style.display='none';
		div.style.visibility='hidden';
		img.src="closed.gif";
	}

	// Netscape and IE return different data in .childNodes.
	// So get the position of the first child HTML element (.nodeType = 1).
	var index = 0;
	while (div.childNodes[index].nodeType != 1) index++;
	var child = div.childNodes[index];

	// In case multiple DIV tags open sequentially in the TOC.htm file,
	// recurse while the first HTML element in the child.childNode array is a DIV tag.
	while (child.tagName == "DIV")
	{

		// var index = 0;
		// while (div.childNodes[index].nodeType != 1) index++;
		// subdiv = div.childNodes[index];

		if (child.style.visibility=='hidden' || child.style.display=='none')
		{
			child.style.display='';
			child.style.visibility='visible';
		}
		else
		{
			child.style.display='none';
			child.style.visibility='hidden';
		}

		index = 0;
		while (child.childNodes[index].nodeType != 1) index++;
		child = child.childNodes[index];
	}
		
}

function scroll(toc,tagId)
{
	// Hidden, because it causes weird behavior.
	// var aId  = 'a' + tagId.substring(1,tagId.length);
	// toc.location.hash = aId;
}

function redden(toc,myId)
{
	// Get the link by its ID, and toggle its color between black and red (color changed to green to match LVL).
	var myLink = toc.document.getElementById("a" + myId);
	if (myLink == null) return;
	myLink.style.color = "#007f00";
}

function blacken(toc,myId)
{
	// Get the link by its ID, and change its color to black.
	var myLink = toc.document.getElementById("a" + myId);
	if (myLink == null) return;
	myLink.style.color = "#000000";
}

