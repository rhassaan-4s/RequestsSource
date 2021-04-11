<%@ Page Language="vb" validateRequest="false" %>

<%@ Register TagPrefix="cr" Namespace="CrystalDecisions.Web" Assembly="CrystalDecisions.Web, Version=11.0.3300.0, Culture=neutral, PublicKeyToken=692fbea5521e1304" %>
<%@ Register TagPrefix="ctrls" Namespace="CrystalDecisions.ReportAppServer.Controllers" Assembly="CrystalDecisions.ReportAppServer.Controllers, Version=11.0.3300.0, Culture=neutral, PublicKeyToken=692fbea5521e1304" %>
<%@ Register TagPrefix="engine" Namespace="CrystalDecisions.CrystalReports.Engine" Assembly="CrystalDecisions.CrystalReports.Engine, Version=11.0.3300.0, Culture=neutral, PublicKeyToken=692fbea5521e1304" %>
<%@ Register TagPrefix="rptsrc" Namespace="CrystalDecisions.ReportSource" Assembly="CrystalDecisions.ReportSource, Version=11.0.3300.0, Culture=neutral, PublicKeyToken=692fbea5521e1304" %>
<%@ Register TagPrefix="ser" Namespace="CrystalDecisions.ReportAppServer.XmlSerialize" Assembly="CrystalDecisions.ReportAppServer.XmlSerialize, Version=11.0.3300.0, Culture=neutral, PublicKeyToken=692fbea5521e1304" %>
<%@ Register TagPrefix="objfact" Namespace="CrystalDecisions.ReportAppServer.ObjectFactory" Assembly="CrystalDecisions.ReportAppServer.ObjectFactory, Version=11.0.3300.0, Culture=neutral, PublicKeyToken=692fbea5521e1304" %>


<%@ Import Namespace="CrystalDecisions.ReportAppServer.Controllers" %>
<%@ Import Namespace="CrystalDecisions.CrystalReports.Engine"  %>
<%@ Import Namespace="CrystalDecisions.ReportSource" %>
<%@ Import Namespace="CrystalDecisions.Web" %>

<script runat="server" language="VB">
    Protected overrides Sub OnInit(ByVal e As System.EventArgs)
        MyBase.OnInit( e )
        Dim rptSrc As CrystalDecisions.ReportAppServer.Controllers.ReportSource


        'Response.ExpiresAbsolute = Now()
        Dim viewer As CrystalDecisions.Web.CrystalReportViewer
        viewer = CrystalReportViewer1
        'viewer = new CrystalDecisions.Web.CrystalReportViewer

        Const CLOSED_RPT_ID = "closedreportid"
        Const RPT_ID = "reportid"
        Const RPT_SOURCE = "reportsource"

        Dim requestMethod
        requestMethod = UCase(Request.ServerVariables("REQUEST_METHOD"))

        Dim serializedRptSrc As String
        Dim reportid
        Dim closedreportid As String

        If (requestMethod = "POST") Then
            serializedRptSrc = Request.Form(RPT_SOURCE)
            closedreportid = Request.Form(CLOSED_RPT_ID)
        Else
            serializedRptSrc = Request.QueryString(RPT_SOURCE)
            closedreportid = Request.QueryString(CLOSED_RPT_ID)
        End If

        ' reportid is always passed in the URL
        reportid = Request.QueryString(RPT_ID)

        If (Not (closedreportid = Nothing Or (Len(closedreportid) = 0))) Then
            ' Clear the report source in session with the given closedreportid
            dim rptSrcI as ISCRReportSource
            rptSrcI = Session(closedreportid)
            System.Runtime.InteropServices.Marshal.ReleaseComObject(rptSrcI)
            Session.Remove(closedreportid)
            rptSrcI = Nothing
        End If

        If (Not (reportid = Nothing Or (Len(reportid) = 0))) Then
            On Error Resume Next

            ' Try to load report source from session if it's there
            If (Not (serializedRptSrc = Nothing Or (Len(serializedRptSrc) = 0))) Then
                ' session doesn't contain report source
                ' need to deserialize the report source string and pass to viewer
                Dim XMLSer As New CrystalDecisions.ReportAppServer.XmlSerialize.XmlSerializer

                XMLSer.ObjectCreater = New CrystalDecisions.ReportAppServer.ObjectFactory.ObjectFactory

                Dim objXmlSer As Object = XMLSer.CreateObjectFromString(serializedRptSrc)
                Session(reportid) = objXmlSer


                viewer.ReportSource = CType(objXmlSer, ISCRReportSource)

            Else
                viewer.ReportSource = Session(reportid)
            End If

            if Err.number <> 0 then
                Response.Write (Err.Description)
                Err.Clear
            end if            

            On Error goto 0
        End If

    End Sub

</script>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
  <HEAD>
        <title>WebForm1</title>
</HEAD>
<body>
    <form id=Form1 method="post" runat="server">
                <CR:CrystalReportViewer 
                    id="CrystalReportViewer1" 
                    runat="server"
                    HasRefreshButton = false
                    HasExportButton = false
                    HasPrintButton = false
>
                </CR:CrystalReportViewer>
    </form>
</body>
</HTML>
