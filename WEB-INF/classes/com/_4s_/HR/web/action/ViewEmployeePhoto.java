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

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.service.HRManager;



public class ViewEmployeePhoto implements Controller{
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
		String hrEmployeeId = request.getParameter("hrEmployeeId");
		if(hrEmployeeId!=null && !hrEmployeeId.equals(""))
		{
		HREmployee hrEmployee = (HREmployee)hrManager.getObject(HREmployee.class,new Long(hrEmployeeId));
		
         log.debug("hrEmployee>>>>>>>>>>>>>>>>>>>>>>>>>>>handlereq"+hrEmployee);
		 ServletContext servletContext = request.getSession().getServletContext();
		
		if(hrEmployee.getEmployeePhotoName()!=null && !hrEmployee.getEmployeePhotoName().equals(""))
		{
		 String fileName2 = hrEmployee.getEmployeePhotoName();
		 log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< fileName2"+fileName2);
		 java.sql.Blob blob = hrEmployee.getEmployeePhoto();
		 log.debug(">>>>>>>>>>>>>>>>>>>>>>blob "+blob);
		 if (blob != null){
			 InputStream is = blob.getBinaryStream();
			 byte[] bytes = blob.getBytes(1,(int)hrEmployee.getEmployeePhoto().length());
			 log.debug(">>>>>>>>>>>>>>>>>>>>>>>.."+is.read());
			 if ( is.read() > 0) {
				 //BufferedInputStream in = new BufferedInputStream(is);
				 InputStreamReader inn = new InputStreamReader(is);
				 
				 inn.read();
				 log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>read "+inn.read());
				 String mimetype = servletContext.getMimeType(fileName2);
		
				 response.setBufferSize((int)hrEmployee.getEmployeePhoto().length());
				 response.setContentType(mimetype);
				 response.setHeader("Content-Disposition", "attachment; filename=\""
				 + fileName2 + "\"");
				 response.setContentLength((int)hrEmployee.getEmployeePhoto().length());
		
				 FileCopyUtils.copy(bytes,response.getOutputStream());
				 inn.close();
				 response.getOutputStream().flush();
				 response.getOutputStream().close();
			 }
		 }
		}
		}
		 log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>. end of view attachment controller");
		return new ModelAndView();
	}


	
}
	


