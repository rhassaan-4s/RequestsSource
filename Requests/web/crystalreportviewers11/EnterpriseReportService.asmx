<%@ WebService language="C#" class="CrystalDecisions.Web.Services.CustomEnterpriseReportService" %>


namespace CrystalDecisions.Web.Services
{
    using System;
    using System.Web.Services;
    using CrystalDecisions.Shared;
    using CrystalDecisions.CrystalReports.Engine;
    using CrystalDecisions.ReportSource;
    using CrystalDecisions.Web.Services;
    using CrystalDecisions.Web.Services.Enterprise;

    
    [ WebService( Namespace="http://crystaldecisions.com/reportwebservice/9.1/" ) ]
    public class CustomEnterpriseReportService : CrystalDecisions.Web.Services.Enterprise.EnterpriseReportService
    {
        public CustomEnterpriseReportService()
        {
            // This timeout is the time in milliseconds that we will wait for a
            // response from the Web Component Server.  The default is 90000 (90s).
            // WCSClient.WebRequestTimeout = 120000;
        }
    }
}

