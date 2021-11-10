package com._4s_.common.web.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Types;
import com._4s_.common.model.TypesData;
import com._4s_.common.service.BaseManager;

public class TypesDataFormController extends BaseSimpleFormController {
	private BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		TypesData typesData = (TypesData) command;
		String typeId = request.getParameter("typeId");
		log.debug(">>>>>>>>>>>>>>>>typeId "+typeId);
		Types type = (Types)baseManager.getObject(Types.class,new Long(typeId));
		log.debug(">>>>>>>>>>>>>>>>>>>>>>type "+type.toString());
		baseManager.saveObject(typesData);
		typesData.setType(type);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		return new ModelAndView(new RedirectView("typesDataController.html?typeId="+typeId));
	}

	
	protected Map referenceData(HttpServletRequest request, Object arg1, Errors arg2) throws Exception {
		// TODO Auto-generated method stub
		Map model = new HashMap();
		String typeId = request.getParameter("typeId");
		Types type = (Types)baseManager.getObject(Types.class,new Long(typeId));
		String typeDataId = request.getParameter("typeDataId");
		model.put("typeDataId",typeDataId);
		model.put("typeId",typeId);
		model.put("typeDescription",type.getDescription());
		model.put("arDesc",type.getArDesc());
		return model;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		String typeDataId = request.getParameter("typeDataId");
		
		TypesData typesData=null;
		
		log.debug(">>>>>>>>>>>>typeDataId: "+typeDataId);
		
		if(typeDataId == null || typeDataId.equals("")){
			
			typesData=new TypesData();
			log.debug(">>>>>>>>>>>>inside if: "+typesData);
		}
		else{
			typesData = (TypesData)baseManager.getObject(TypesData.class,new Long(typeDataId));
			log.debug("typesData: "+ typesData.toString());
		}
		
		
		return typesData;
	}

}
