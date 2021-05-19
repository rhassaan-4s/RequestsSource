document.writeln ('<TABLE ALIGN="left" border="0" cellspacing="0" cellpadding="10">');
document.writeln ('<TR>');
document.write ('<TD ALIGN="left">');

if (prev != "advancedviewerTOC.htm") {
   document.writeln ('<A HREF="' + prev + '">');
   document.writeln ('<IMG SRC="prevpage.gif" HEIGHT=17 BORDER=0 ALT="Previous page"></A>');
}

if (next != "advancedviewerIX.htm") {
   document.writeln ('<A HREF="' + next + '">');
   document.writeln ('<IMG SRC="nextpage.gif" HEIGHT=17 BORDER=0 ALT="Next page"></A>');
}

// Only display the ShowTOC buttons in uncompiled HTML and if the navigation 
// pane is not visible

if ( (self.location.href == top.window.location.href) && (document.URL.search("MSITStore") == -1 ) )
{
      document.writeln ('</TD><TD ALIGN="left">');
      document.writeln ('<A HREF="default.htm' + current + '#TOC" TARGET="_top">');
      document.writeln ('<IMG SRC="showtoc.gif" HEIGHT=17 BORDER=0 ALT="Click to show the Table of Contents"></A></TD>');
      document.writeln ('<TD ALIGN="left">');
      document.writeln ('<A HREF="default.htm' + current + '#IDX" TARGET="_top">');
      document.writeln ('<IMG SRC="showix.gif" HEIGHT=17 BORDER=0 ALT="Click to show the Index"></A></TD>');
}

document.writeln ('</TD>');
document.writeln ('</TR>');
document.writeln ('</TABLE>');

