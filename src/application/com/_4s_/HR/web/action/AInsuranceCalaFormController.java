package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.AInsuranceCala;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeGroupBy;
import com._4s_.HR.model.HRInsurance;
import com._4s_.HR.model.HRMonth;
import com._4s_.HR.model.HRYear;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class AInsuranceCalaFormController extends BaseSimpleFormController{

	private final Log log = LogFactory.getLog(getClass());
	protected HRManager hrManager = null;
		
	public HRManager getHrManager(){
		return hrManager;
	}

	public void setHrManager(HRManager hrManager){
		this.hrManager = hrManager;
	}
		
	//**************************************** formBackingObject ***********************************************\\
	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		AInsuranceCala aic=null;
		String aicId=request.getParameter("aicId");
		log.debug("aicId>>>>>>>>>>>>"+aicId);
		if(aicId!=null && !aicId.equals("")){
			aic=(AInsuranceCala)hrManager.getObject(AInsuranceCala.class, new Long(aicId));
		}
		else{
		       aic=new AInsuranceCala();
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return aic;
	}
	
	List aInsuranceCalaList2;
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Map model=new HashMap();
		
		String aicId=request.getParameter("aicId");
		log.debug("aicId  "+aicId);
		model.put("aicId",aicId);

		List orderlist = new ArrayList();
		orderlist.add("emp_code");

		String search = request.getParameter("search");
		String copy = request.getParameter("copy");
		
		String month = request.getParameter("month");
		log.debug("Entered month: "+month);
//		if(errors.getErrorCount()==0){
//			if((month == null || month.equals("")) && ((search != null && search.equals("true")) || (copy != null && copy.equals("true")))){
//				errors.rejectValue("month", "commons.errors.requiredFields");
//			}
//		}
//		String year=request.getParameter("year");
//		log.debug("Entered year: "+year);
//		if(errors.getErrorCount()==0){
//			if((year == null || year.equals("")) && ((search != null && search.equals("true")) || (copy != null && copy.equals("true")))){
//				errors.rejectValue("year", "commons.errors.requiredFields");
//			}
//		}
		String insurance=request.getParameter("insuranceCode");
		log.debug("Entered insurance: "+insurance);
		String emp_code = request.getParameter("emp_code");
		log.debug("Entered emp_code: "+emp_code);
		String empName = request.getParameter("empName");
		if (empName != null && !empName.equals("")) {
            String[] parts = empName.split(",");
            empName = "";
            for (int i = 0; i < parts.length; i++) {
                char c = (char) new Integer(parts[i]).intValue();
                empName += c;
            }
        }
		log.debug("Entered empName: "+empName);
		String insuranceNo = request.getParameter("insuranceNo");
		log.debug("Entered insuranceNo: "+insuranceNo);
		String subscriptionDate = request.getParameter("subscriptionDate");
		log.debug("Entered subscriptionDate: "+subscriptionDate);
		String groupBy = request.getParameter("groupBy");
		log.debug("Entered groupBy: "+groupBy);

		//COPY DATA FROM PRE MONTH
		log.debug("Copy: "+copy);
//		if((month != null && month.length() > 0) && (year != null && year.length() > 0) && (copy != null && copy.equals("true"))){
//			log.debug("COPY DATA FROM PRE MONTH");
//			copyFromPreMonth(copy, month, year, insurance);
//		}
//		
		//SEARCH DATA FOR VIEW
		log.debug("SEARCH DATA FOR VIEW");
		aInsuranceCalaList2 = new ArrayList();
//		if((month == null || month.length() == 0) && (year == null || year.length() == 0) && (insurance == null || insurance.length() == 0)){
//			log.debug("All Null");
//		}
//		else if((month != null && month.length() > 0) && (year != null && year.length() > 0)){
//			List aInsuranceCalaList = hrManager.getFilteredGroupedAInsuranceCala(month, year, insurance, emp_code, empName, insuranceNo, subscriptionDate, groupBy);
//			for(int i=0; i<aInsuranceCalaList.size(); i++){
//				AInsuranceCala a =(AInsuranceCala) aInsuranceCalaList.get(i);
//				HREmployee e = (HREmployee) hrManager.getObjectByParameter(HREmployee.class, "empCode", a.getEmp_code());
//				a.setEmpName(e.getName());
//				log.debug("NAME>>"+e.getName());
//				a.setInsuranceNo(e.getInsurNo());
//				log.debug("INSU NO>>"+e.getInsurNo());
//				a.setSubscriptionDate(e.getSubscriptionDate().toString().split(" ")[0]+"");
//				log.debug("SUBSC DATE>>"+e.getSubscriptionDate()+"a SUBSC DATE>>"+a.getSubscriptionDate());
////				log.debug("a SUBSC DATE>>"+a.getSubscriptionDate());
//				a.setInsuranceCode(e.getInsurCode()+"");
//				log.debug("INSU CODE>>"+e.getInsurCode());
//				aInsuranceCalaList2.add(a);
//			}
//		}
		
		List monthList=hrManager.getObjects(HRMonth.class);
		model.put("monthList", monthList);
		
		List yearList=hrManager.getObjects(HRYear.class);
		model.put("yearList", yearList);
		
		List groupByList=hrManager.getObjects(HREmployeeGroupBy.class);
		model.put("groupByList", groupByList);
		
		List insuranceList=hrManager.getObjects(HRInsurance.class);
		model.put("insuranceList", insuranceList);
		
		model.put("results", aInsuranceCalaList2);
//		model.put("year", year);
		model.put("month", month);
		model.put("insuranceCode", insurance);
		model.put("employeeCode", emp_code);
		model.put("employeeName", empName);
		model.put("insuranceNo", insuranceNo);
		model.put("subscriptionDate", subscriptionDate);
		model.put("groupBy", groupBy);
		
//		log.debug("MAP: year "+year+" month "+month+" insuranceCode "+insurance+" employeeCode "+emp_code+" employeeName "+empName+" insuranceNo "+insuranceNo+" subscriptionDate "+subscriptionDate+" groupBy "+groupBy);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	//**************************************** onBind ***********************************************\\		
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	//**************************************** onBindAndValidate ***********************************************\\		
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AInsuranceCala aic;

		log.debug("aInsuranceCalaList.size()>>>>>>>>>>>"+aInsuranceCalaList2.size());
		
		for(int i=0;i<aInsuranceCalaList2.size();i++){
			aic = (AInsuranceCala) aInsuranceCalaList2.get(i);
			HREmployee emp = (HREmployee) hrManager.getObjectByParameter(HREmployee.class, "empCode", aic.getEmp_code());
    	
			String basic_sal = request.getParameter("basic_sal"+i); 
    		log.debug("basic_sal>>>>>>>"+basic_sal);
    		String tot_value = request.getParameter("tot_value"+i);
    		log.debug("tot_value>>>>>>>"+tot_value);
    		String basic_emp = request.getParameter("basic_emp"+i);
    		log.debug("basic_emp>>>>>>>"+basic_emp);
    		String var_emp = request.getParameter("var_emp"+i);
    		log.debug("var_emp>>>>>>>"+var_emp);
    		String basic_co = request.getParameter("basic_co"+i);
    		log.debug("basic_co>>>>>>>"+basic_co);
    		String var_co = request.getParameter("var_co"+i);
    		log.debug("var_co>>>>>>>"+var_co);	
    		
			if(aic != null){
    			log.debug("aic>>>>>>>>>>>"+aic);
    			aic.setEmpName(emp.getName());
    			aic.setInsuranceNo(emp.getInsurNo());
    			aic.setSubscriptionDate(emp.getSubscriptionDate()+"");
    			
    			if(basic_sal !=null && !basic_sal.equals("")){
    				aic.setBasic_sal(new Double(basic_sal));
    			}
    			if(tot_value !=null && !tot_value.equals("")){
    				aic.setTot_value(new Double(tot_value));
    			}
    			if(basic_emp !=null && !basic_emp.equals("")){
    				aic.setBasic_emp(new Double(basic_emp));
    			}
    			if(var_emp !=null && !var_emp.equals("")){
    				aic.setVar_emp(new Double(var_emp));
    			}
    			if(basic_co !=null && !basic_co.equals("")){
    				aic.setBasic_co(new Double(basic_co));
    			}
    			if(var_co !=null && !var_co.equals("")){
    				aic.setVar_co(new Double(var_co));
    			}
    			hrManager.saveObject(aic);
			}
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception {
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Map model=new HashMap(); 
		String month = request.getParameter("month");
		model.put("month", month);
		log.debug(">>>sMonth"+month);
		String year = request.getParameter("year");
		model.put("year", year);
		log.debug(">>>sYear"+year);
		String insurance=request.getParameter("insuranceCode");
		model.put("insuranceCode", insurance);
		log.debug(">>>sInsurance"+insurance);
	
		String emp_code = request.getParameter("emp_code");
		model.put("emp_code", emp_code);
		log.debug(">>>sEmployeeCode"+emp_code);
		String empName = request.getParameter("name");
		String res ="";
		for (int i=0;i< empName.length(); i++) {
			res = res+ empName.codePointAt(i)+ ',';
		}
		if(!res.equals("")){
			res = res.substring(0, res.length() - 1);
		}
		model.put("empName", res);
		log.debug(">>>sEmployeeName"+empName);
		
		String insuranceNo = request.getParameter("insuranceNo");
		model.put("insuranceNo", insuranceNo);
		log.debug(">>>sInsuranceNo"+insuranceNo);
		String subscriptionDate =  request.getParameter("subscriptionDate");
		model.put("subscriptionDate", subscriptionDate);
		log.debug(">>>sSubscriptionDate"+subscriptionDate);	
		
		String groupBy =  request.getParameter("groupBy");
		model.put("groupBy", groupBy);
		log.debug(">>>sGroupBy"+groupBy);	
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView("aInsuranceCalaForm.html"),model);
	}
	
	//**************************************** copyFromPreMonth ***********************************************\\
	private void copyFromPreMonth (String copy, String month, String year, String insurance){
		log.debug("month "+month+" year "+year+" insurance "+insurance);
		
		String pMonth;
		String pYear;
		
		List orderlist = new ArrayList();
		orderlist.add("emp_code");
		
		if(!month.equals("1") && !year.equals("1")){
			if(month.equals("1")){
				pMonth = "12";
				pYear = (Integer.parseInt(year)-1)+"";
			}else{
				pMonth = (Integer.parseInt(month)-1)+"";
				pYear = year;
			}
			log.debug("Month: "+month+", PreMonth: "+pMonth+", Year: "+year+", PreYear: "+pYear);
			List aInsuList = hrManager.getObjectsByTwoParametersOrderedByFieldList(AInsuranceCala.class, "month", pMonth, "year", pYear, orderlist);

			if(insurance != null && insurance.length() > 0){
				log.debug("insurance != null: "+insurance);
				aInsuList = new ArrayList();
				List aInList = hrManager.getObjectsByTwoParametersOrderedByFieldList(AInsuranceCala.class, "month", pMonth, "year", pYear, orderlist);
				for(int i=0; i<aInList.size(); i++){
					AInsuranceCala a = (AInsuranceCala) aInList.get(i);
					log.debug("pAIC: "+a);
					HREmployee e = (HREmployee) hrManager.getObjectByParameter(HREmployee.class, "empCode", a.getEmp_code());

					log.debug("pEMP: "+e);
					if(insurance.equals(e.getInsurCode()+"")){
						log.debug("ADD: "+a);
						aInsuList.add(a);
					}
				}
				log.debug("aInsuList: "+aInsuList.size());
			}
			if(aInsuList != null || aInsuList.size() != 0){
				for(int i=0; i<aInsuList.size(); i++){
					AInsuranceCala pAic= (AInsuranceCala) aInsuList.get(i);
					log.debug("PRE MONTH AIC: "+ pAic);
					List tAicList = hrManager.getObjectsByThreeParametersOrderedByFieldList(AInsuranceCala.class,"emp_code", pAic.getEmp_code(), "month", month, "year", year, orderlist);
					
					if(tAicList != null && tAicList.size() == 1){
						AInsuranceCala tAic = (AInsuranceCala) tAicList.get(0);
						if(tAic != null){
							log.debug("alredy exist");
						}
					}else{
						AInsuranceCala nAic = new AInsuranceCala();
						nAic.setEmp_code(pAic.getEmp_code());
						nAic.setBasic_sal(pAic.getBasic_sal());
						nAic.setTot_value(pAic.getTot_value());
						nAic.setBasic_co(pAic.getBasic_co());
						nAic.setBasic_emp(pAic.getBasic_emp());
						nAic.setVar_co(pAic.getVar_co());
						nAic.setVar_emp(pAic.getVar_emp());
						nAic.setMore_base_sal(pAic.getMore_base_sal());
						nAic.setMore_var_sal(pAic.getMore_var_sal());
						nAic.setMore_base(pAic.getMore_base());
						nAic.setMore_var(pAic.getMore_var());
						nAic.setMore_var_sal(pAic.getMore_var_sal());
						nAic.setMonth(month);
						nAic.setYear(year);
						log.debug("NEW AIC: "+nAic);
						hrManager.saveObject(nAic);
					}
				}
			}	
		}else{
			log.debug("First Month");
		}
	}
} 
