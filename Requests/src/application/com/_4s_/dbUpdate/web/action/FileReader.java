package com._4s_.dbUpdate.web.action;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.dbUpdate.service.SQLManager;

public class FileReader implements Controller {

	private final Log log = LogFactory.getLog(getClass());

	SQLManager mgr = null;

	public SQLManager getMgr() {
		return mgr;
	}

	public void setMgr(SQLManager mgr) {
		this.mgr = mgr;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HashMap model = new HashMap();

		String FileName = "/home/aali/Desktop/sofa.txt";

		File file = new File(FileName);
		java.io.FileReader reader = new java.io.FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String Line = "";
		String id = "";
		String Tokenz="";

		while (br.ready()) {
			
			Line = br.readLine();

			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Full string is "+Line);
			StringTokenizer tokenizer = new StringTokenizer(Line, ",");
			while (tokenizer.hasMoreTokens()) {

				id = (tokenizer.nextToken());	
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>string is "+id);
				Tokenz+=id+"\n";
			}
			
		}
		
		model.put("Tokenz", Tokenz);
		return new ModelAndView("fileReaderView", model);
	}
}
