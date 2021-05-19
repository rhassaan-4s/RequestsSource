
function validateTheDate(inpt){
	
	if(inpt.value != null && inpt.value != ''){
	
		if( dateCheck(inpt.value,'%d/%m/%yyyy')== false && dateCheck(inpt.value,'%d-%m-%yyyy') == false ){
			//alert("Error in Date : " + inpt.value);
			inpt.setAttribute('class','errorInField');
			return false;
		}else{
			inpt.setAttribute('class','');
		}
	
	}else{
		inpt.setAttribute('class','');
	
	}
	
	return true;
}


var tokPat=new RegExp("^month_strict|month|Month|MONTH|yyyy|YYYY|mins|MINS|mon_strict|ampm|AMPM|mon|Mon|MON|min|MIN|dd|DD|mm|MM|yy|YY|hh|HH|ss|SS|m|M|d|D|y|Y|h|H|s|S");

// lowerMonArr is used to map months to their numeric values.

var lowerMonArr={jan:1, feb:2, mar:3, apr:4, may:5, jun:6, jul:7, aug:8, sep:9, oct:10, nov:11, dec:12}

// monPatArr contains regular expressions used for matching abbreviated months
// in a date string.

var monPatArr=new Array();
monPatArr['mon_strict']=new RegExp(/jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec/);
monPatArr['Mon']=new RegExp(/Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec/);
monPatArr['MON']=new RegExp(/JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC/);
monPatArr['mon']=new RegExp("jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec",'i');

// monthPatArr contains regular expressions used for matching full months
// in a date string.

var monthPatArr=new Array();
monthPatArr['month']=new RegExp(/^january|february|march|april|may|june|july|august|september|october|november|december/i);
monthPatArr['Month']=new RegExp(/^January|February|March|April|May|June|July|August|September|October|November|December/);
monthPatArr['MONTH']=new RegExp(/^JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER/);
monthPatArr['month_strict']=new RegExp(/^january|february|march|april|may|june|july|august|september|october|november|december/);

// cutoffYear is the cut-off for assigning "19" or "20" as century.  Any
// two-digit year >= cutoffYear will get a century of "19", and everything
// else gets a century of "20".

var cutoffYear=50;

// FormatToken is a datatype we use for storing extracted tokens from the
// format string.

function FormatToken (token, type) {
this.token=token;
this.type=type;
}

function parseFormatString (formatStr) {
var tokArr=new Array;
var tokInd=0;
var strInd=0;
var foundTok=0;
    
while (strInd < formatStr.length) {
if (formatStr.charAt(strInd)=="%" &&
(matchArray=formatStr.substr(strInd+1).match(tokPat)) != null) {
strInd+=matchArray[0].length+1;
tokArr[tokInd++]=new FormatToken(matchArray[0],"symbolic");
} else {

// No token matched current position, so current character should 
// be saved as a required literal.

if (tokInd>0 && tokArr[tokInd-1].type=="literal") {

// Literal tokens can be combined.Just add to the last token.

tokArr[tokInd-1].token+=formatStr.charAt(strInd++);
}
else {
tokArr[tokInd++]=new FormatToken(formatStr.charAt(strInd++), "literal");
      }
   }
}
return tokArr;
}



function buildDate(dateStr,formatStr) {
// parse the format string first.
var tokArr=parseFormatString(formatStr);
var strInd=0;
var tokInd=0;
var intMonth;
var intDay;
var intYear;
var intHour;
var intMin;
var intSec;
var ampm="";
var strOffset;

// Create a date object with the current date so that if the user only
// gives a month or day string, we can still return a valid date.

var curdate=new Date();
intMonth=curdate.getMonth()+1;
intDay=curdate.getDate();
intYear=curdate.getFullYear();

// Default time to midnight, so that if given just date info, we return
// a Date object for that date at midnight.

intHour=0;
intMin=0;
intSec=0;

// Walk across dateStr, matching the parsed formatStr until we find a 
// mismatch or succeed.

while (strInd < dateStr.length && tokInd < tokArr.length) {

// Start with the easy case of matching a literal.

if (tokArr[tokInd].type=="literal") {
if (dateStr.indexOf(tokArr[tokInd].token,strInd)==strInd) {

// The current position in the string does match the format 
// pattern.

strInd+=tokArr[tokInd++].token.length;
continue;
}
else {

// ACK! There was a mismatch; return error.

return "\"" + dateStr + "\" ?? ????? ????????: " ;
   }
}

// If we get here, we're matching to a symbolic token.
switch (tokArr[tokInd].token) {
case 'm':
case 'M':
case 'd':
case 'D':
case 'h':
case 'H':
case 'min':
case 'MIN':
case 's':
case 'S':

// Extract one or two characters from the date-time string and if 
// it's a number, save it as the month, day, hour, or minute, as
// appropriate.

curChar=dateStr.charAt(strInd);
nextChar=dateStr.charAt(strInd+1);
matchArr=dateStr.substr(strInd).match(/^\d{1,2}/);
if (matchArr==null) {

// First character isn't a number; there's a mismatch between
// the pattern and date string, so return error.

switch (tokArr[tokInd].token.toLowerCase()) {
case 'd': var unit="??? ??? ?????"; break;
case 'm': var unit="??? ??? ?????"; break;
case 'h': var unit="hour"; break;
case 'min': var unit="minute"; break;
case 's': var unit="second"; break;
}
return " " + unit + " \"" + curChar + "\" or \"" + curChar +
nextChar + "\".";
}
strOffset=matchArr[0].length;
switch (tokArr[tokInd].token.toLowerCase()) {
case 'd': intDay=parseInt(matchArr[0],10); break;
case 'm': intMonth=parseInt(matchArr[0],10); break;
case 'h': intHour=parseInt(matchArr[0],10); break;
case 'min': intMin=parseInt(matchArr[0],10); break;
case 's': intSec=parseInt(matchArr[0],10); break;
}
break;
case 'mm':
case 'MM':
case 'dd':
case 'DD':
case 'hh':
case 'HH':
case 'mins':
case 'MINS':
case 'ss':
case 'SS':

// Extract two characters from the date string and if it's a 
// number, save it as the month, day, or hour, as appropriate.

strOffset=2;
matchArr=dateStr.substr(strInd).match(/^\d{2}/);
if (matchArr==null) {

// The two characters aren't a number; there's a mismatch 
// between the pattern and date string, so return an error
// message.

switch (tokArr[tokInd].token.toLowerCase()) {
case 'dd': var unit="??? ??? ?????"; break;
case 'mm': var unit="??? ??? ?????"; break;
case 'hh': var unit="hour"; break;
case 'mins': var unit="minute"; break;
case 'ss': var unit="second"; break;
}
return " " + unit + " \"" + dateStr.substr(strInd,2) + 
"\".";
}
switch (tokArr[tokInd].token.toLowerCase()) {
case 'dd': intDay=parseInt(matchArr[0],10); break;
case 'mm': intMonth=parseInt(matchArr[0],10); break;
case 'hh': intHour=parseInt(matchArr[0],10); break;
case 'mins': intMin=parseInt(matchArr[0],10); break;
case 'ss': intSec=parseInt(matchArr[0],10); break;
}
break;
case 'y':
case 'Y':

// Extract two or four characters from the date string and if it's
// a number, save it as the year.Convert two-digit years to four
// digit years by assigning a century of '19' if the year is >= 
// cutoffYear, and '20' otherwise.

if (dateStr.substr(strInd,4).search(/\d{4}/) != -1) {

// Four digit year.

intYear=parseInt(dateStr.substr(strInd,4),10);
strOffset=4;
}
else {
if (dateStr.substr(strInd,2).search(/\d{2}/) != -1) {

// Two digit year.

intYear=parseInt(dateStr.substr(strInd,2),10);
if (intYear>=cutoffYear) {
intYear+=1900;
}
else {
intYear+=2000;
}
strOffset=2;
}
else {

// Bad year; return error.

return "Bad year \"" + dateStr.substr(strInd,2) + 
"\". Must be two or four digits.";
   }
}
break;
case 'yy':
case 'YY':

// Extract two characters from the date string and if it's a 
// number, save it as the year.Convert two-digit years to four 
// digit years by assigning a century of '19' if the year is >= 
// cutoffYear, and '20' otherwise.

if (dateStr.substr(strInd,2).search(/\d{2}/) != -1) {

// Two digit year.

intYear=parseInt(dateStr.substr(strInd,2),10);
if (intYear>=cutoffYear) {
intYear+=1900;
}
else {
intYear+=2000;
}
strOffset=2;
} else {
// Bad year; return error
return "Bad year \"" + dateStr.substr(strInd,2) + 
"\". Must be two digits.";
}
break;
case 'yyyy':
case 'YYYY':

// Extract four characters from the date string and if it's a 
// number, save it as the year.

if (dateStr.substr(strInd,4).search(/\d{4}/) != -1) {

// Four digit year.

intYear=parseInt(dateStr.substr(strInd,4),10);
strOffset=4;
}
else {

// Bad year; return error.

return "??? ??? ?????? \"" + dateStr.substr(strInd,4) + 
"\".";
}
break;
case 'mon':
case 'Mon':
case 'MON':
case 'mon_strict':

// Extract three characters from dateStr and parse them as 
// lower-case, mixed-case, or upper-case abbreviated months,
// as appropriate.

monPat=monPatArr[tokArr[tokInd].token];
if (dateStr.substr(strInd,3).search(monPat) != -1) {
intMonth=lowerMonArr[dateStr.substr(strInd,3).toLowerCase()];
}
else {

// Bad month, return error.

switch (tokArr[tokInd].token) {
case 'mon_strict': caseStat="lower-case"; break;
case 'Mon': caseStat="mixed-case"; break;
case 'MON': caseStat="upper-case"; break;
case 'mon': caseStat="between Jan and Dec"; break;
}
return "Bad month \"" + dateStr.substr(strInd,3) + 
"\". Must be " + caseStat + ".";

}
strOffset=3;
break;
case 'month':
case 'Month':
case 'MONTH':
case 'month_strict':

// Extract a full month name at strInd from dateStr if possible.

monPat=monthPatArr[tokArr[tokInd].token];
matchArray=dateStr.substr(strInd).match(monPat);
if (matchArray==null) {

// Bad month, return error.

return "Can't find a month beginning at \"" +
dateStr.substr(strInd) + "\".";
}

// It's a good month.

intMonth=lowerMonArr[matchArray[0].substr(0,3).toLowerCase()];
strOffset=matchArray[0].length;
break;
case 'ampm':
case 'AMPM':
matchArr=dateStr.substr(strInd).match(/^(am|pm|AM|PM|a\.m\.|p\.m\.|A\.M\.|P\.M\.)/);
if (matchArr==null) {

// There's no am/pm in the string.Return error msg.

return "Missing am/pm designation.";
}

// Store am/pm value for later (as just am or pm, to make things
// easier later).

if (matchArr[0].substr(0,1).toLowerCase() == "a") {

// This is am.

ampm = "am";
}
else {
ampm = "pm";
}
strOffset = matchArr[0].length;
break;
}
strInd += strOffset;
tokInd++;
}
if (tokInd != tokArr.length || strInd != dateStr.length) {

/* We got through the whole date string or format string, but there's 
 more data in the other, so there's a mismatch. */

return "\"" + dateStr + "\" is either missing desired information or has more information than the expected format: " + formatStr;
}

// Make sure all components are in the right ranges.

if (intMonth < 1 || intMonth > 12) {
return "????? ??? ?? ???? ??? 01 ? 12";
}
if (intDay < 1 || intDay > 31) {
return "????? ??? ?? ???? ??? 01 ? 31.";
}

// Make sure user doesn't put 31 for a month that only has 30 days

if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && intDay == 31) {
return " ???"+intMonth+" ?????? ?? 31";
}

// Check for February date validity (including leap years) 

if (intMonth == 2) {

// figure out if "year" is a leap year; don't forget that
// century years are only leap years if divisible by 400

var isleap=(intYear%4==0 && (intYear%100!=0 || intYear%400==0));
if (intDay > 29 || (intDay == 29 && !isleap)) {
return "?????? " + intYear + " ?? ???? ??  " + intDay + 
" ";
   }
}

// Check that if am/pm is not provided, hours are between 0 and 23.

if (ampm == "") {
if (intHour < 0 || intHour > 23) {
return "Hour must be between 0 and 23 for military time.";
   }
}
else {

// non-military time, so make sure it's between 1 and 12.

if (intHour < 1|| intHour > 12) {
return "Hour must be between 1 and 12 for standard time.";
   }
}

// If user specified amor pm, convert intHour to military.

if (ampm=="am" && intHour==12) {
intHour=0;
}
if (ampm=="pm" && intHour < 12) {
intHour += 12;
}
if (intMin < 0 || intMin > 59) {
return "Minute must be between 0 and 59.";
}
if (intSec < 0 || intSec > 59) {
return "Second must be between 0 and 59.";
}
return new Date(intYear,intMonth-1,intDay,intHour,intMin,intSec);
}
function dateCheck(dateStr,formatStr) {
//alert('inside dateCheck')
//alert(dateStr)
//alert(formatStr)
var myObj = buildDate(dateStr,formatStr);
if (typeof myObj == "object") {

// We got a Date object, so good.

return true;
}
else {

// We got an error string.

//alert(myObj); // alert from here
return false;
   }
}
////////////////////////////////////////////////////////////


	
	