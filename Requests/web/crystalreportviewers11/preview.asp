<%@ Language=VBScript codepage=65001%>

<% Option Explicit
Response.ExpiresAbsolute = Now() - 1
const CLOSED_RPT_ID = "closedreportid"
const RPT_ID = "reportid"
const RPT_SOURCE = "reportsource"

Dim requestMethod
requestMethod = UCase(Request.ServerVariables("REQUEST_METHOD"))

Dim serializedRptSrc, reportid, closedreportid
if (requestMethod = "POST") then
    serializedRptSrc = Request.Form(RPT_SOURCE)
    closedreportid = Request.Form(CLOSED_RPT_ID)
else
    serializedRptSrc = Request.QueryString(RPT_SOURCE)
    closedreportid = Request.QueryString(CLOSED_RPT_ID)
end if

' reportid is always passed in the URL
reportid = Request.QueryString(RPT_ID)

if (Not (IsEmpty(closedreportid) OR (Len(closedreportid) = 0))) then
    ' Clear the report source in session with the given closedreportid
    Session.Contents.Remove(closedreportid)
end if

if (Not (IsEmpty(reportid) OR (Len(reportid) = 0))) then
    On Error Resume Next
    
    Dim objectFactory
    ' Use the version dependent prog id for side by side support
    ' (i.e. to maintain consistency when multiple versions of the product is installed)
    Set objectFactory = CreateObject("CrystalReports11.ObjectFactory.1")
    
    Dim HTMLViewer

    Set HTMLViewer = ObjectFactory.CreateObject("CrystalReports.CrystalReportViewer")
    With HTMLViewer
        .Name = "htmlpreview"
        .IsOwnForm = true
        .IsOwnPage = true
        .HasRefreshButton = false
        .HasExportButton = false
        .HasPrintButton = false
    End With
        
    if (Not (IsEmpty(serializedRptSrc) OR (Len(serializedRptSrc) = 0))) then
        ' Got a seralizedRprtSrc string
        ' need to deserialize the report source string and pass to viewer
        Dim XMLSerializer
        Set XMLSerializer = ObjectFactory.CreateObject("CrystalReports.SAXXMLSerializer")
        
        XMLSerializer.SetObjectCreator ObjectFactory     
        HTMLViewer.ReportSource = XMLSerializer.CreateObjectFromString(serializedRptSrc)
        
        set Session.Contents(reportid) = HTMLViewer.ReportSource
    else
	' Try to load report source from session
        HTMLViewer.ReportSource = Session.Contents(reportid)
    end if

    HTMLViewer.URI = Request.ServerVariables("Path_Info") + "?" + RPT_ID + "=" + reportid
    
    call HTMLViewer.ProcessHttpRequest(Request, Response, Session)
    if Err.number <> 0 then
        Response.Write Err.Description
        Err.Clear
    end if          

end if 

%>
