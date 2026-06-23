package com._4s_.HR.web.action;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.HR.model.HREmployeeDocuments;
import com._4s_.HR.service.HRManager;

public class ViewDocumentPhoto implements Controller{
	protected final Log log = LogFactory.getLog(getClass());

	HRManager hrManager=null;
   
	public HRManager getHrManager() {
		return hrManager;
	}


	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	////////**************************************************************************************//

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hrEmployeeDocumentId = request.getParameter("hrEmployeeDocumentId");
		if(hrEmployeeDocumentId!=null && !hrEmployeeDocumentId.equals(""))
		{
		HREmployeeDocuments hrEmployeeDocuments = (HREmployeeDocuments)hrManager.getObject(HREmployeeDocuments.class,new Long(hrEmployeeDocumentId));
		
         log.debug("hrEmployee>>>>>>>>>>>>>>>>>>>>>>>>>>>handlereq"+hrEmployeeDocuments);
		
		
		if(hrEmployeeDocuments.getDocumentPhotoName()!=null && !hrEmployeeDocuments.getDocumentPhotoName().equals(""))
		{
			ServletContext servletContext = request.getSession().getServletContext();
			String documentName = hrEmployeeDocuments.getDocumentPhotoName();
			log.debug("<<<<<<<<<<< documentName: "+documentName);
			
			
			java.sql.Blob blob = hrEmployeeDocuments.getDocumentPhoto();
			//java.sql.Blob blob1 = center.getGeneralValidationLetter();
			
			InputStream is = blob.getBinaryStream();
			//InputStream inputStream=blob1.getBinaryStream();
			byte[] bytes = blob.getBytes(1,(int)hrEmployeeDocuments.getDocumentPhoto().length());
			//byte[] bytes1 = blob1.getBytes(1,(int)center.getGeneralValidationLetter().length());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>.."+is.read());
			
			if ( is.read() > 0)
			{
				InputStreamReader inn = new InputStreamReader(is);
				
				inn.read();
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>read "+inn.read());
				String mimetype = servletContext.getMimeType(documentName);
				
				response.setBufferSize((int)hrEmployeeDocuments.getDocumentPhoto().length());
				response.setContentType(mimetype);
				response.setHeader("Content-Disposition", "attachment; filename=\""
				+ documentName + "\"");
				response.setContentLength((int)hrEmployeeDocuments.getDocumentPhotoName().length());
				
				FileCopyUtils.copy(bytes,response.getOutputStream());
				inn.close();
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
		 log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>. end of view attachment controller");
		
	}
		return new ModelAndView();
	}
}


	

	


