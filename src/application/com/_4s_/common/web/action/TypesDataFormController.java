package com._4s_.common.web.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Company;
import com._4s_.common.model.Types;
import com._4s_.common.model.TypesData;
import com._4s_.common.service.BaseManager;

@Controller
@RequestMapping("/typesDataForm.html")
public class TypesDataFormController extends BaseSimpleFormController {
	@Autowired
	private BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("typesData") TypesData command,
			BindingResult result, SessionStatus status,Model model) {
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

	
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
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

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
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
		
		model.addAttribute("typesData", typesData);
		return "typesDataFormController";
	}

}
