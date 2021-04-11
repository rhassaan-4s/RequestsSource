package com._4s_.HR.web.action;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.AInsuranceCala;
import com._4s_.HR.model.Degree;
import com._4s_.HR.model.HRBank;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeAddress;
import com._4s_.HR.model.HREmployeeCostCenter;
import com._4s_.HR.model.HREmployeeJob;
import com._4s_.HR.model.HREmployeeQualification;
import com._4s_.HR.model.HRGeographicalDivision;
import com._4s_.HR.model.HRInsurance;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRLocation;
import com._4s_.HR.model.HRMaritalStatus;
import com._4s_.HR.model.HRMilitaryService;
import com._4s_.HR.model.HRMonth;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRRegion;
import com._4s_.HR.model.HRReligion;
import com._4s_.HR.model.HRSyndicate;
import com._4s_.HR.model.HRTax;
import com._4s_.HR.model.HRTitle;
import com._4s_.HR.model.HRYear;
import com._4s_.HR.model.Sector;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com.crystaldecisions.enterprise.ocaframework.idl.OCA.OCAcdz.WICDZServer.Blob;
//import com._4s_.gl.model.Account;
//import com._4s_.gl.model.AnalysisLeafAccount;
import com.jenkov.prizetags.tree.itf.ITree;

public class EmployeeForm extends BaseSimpleFormController {

	HRManager hrManager=null;

	public HRManager getHrManager() {
		return hrManager;
	}

	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
	 
		String employeeId=request.getParameter("employeeId");
		log.debug("employeeId>>>>>>>>>>>>>>>>>>>."+employeeId);
		
		String geographicalDivisionId = request.getParameter("geographicalDivisionId");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> geographicalDivisionId "+geographicalDivisionId);
		
		HREmployee hrEmployee;
		if(employeeId!=null && !employeeId.equals("")){
			 hrEmployee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		}else{
			hrEmployee=new HREmployee();
			
			//to put code automatically
			List employees=hrManager.getObjects(HREmployee.class);
			List codesList=new ArrayList();
			Iterator itr=employees.iterator();
			while(itr.hasNext()){
				HREmployee emp=(HREmployee)itr.next();
				codesList.add(Integer.parseInt(emp.getEmpCode()));
			}
			String code = hrManager.zeroFill(codesList.toArray(),8);
			hrEmployee.setEmpCode(code);
		    HREmployeeAddress address=new HREmployeeAddress();
		    if(hrEmployee.getEmpAddress()==null){
		    	hrEmployee.setEmpAddress(address);
		    }
		    HRGeographicalDivision geographicalDivision=null;
		  
		    /* if(geographicalDivisionId!=null && !geographicalDivisionId.equals(""))
		    {
		      geographicalDivision = (HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class,new Long(geographicalDivisionId));
		    }
		    */
		    
		    if(hrEmployee.getEmpAddress().getGeographicalDivision()==null){
		    	geographicalDivision=new HRGeographicalDivision();
		    	hrEmployee.getEmpAddress().setGeographicalDivision(geographicalDivision);
		    	log.debug("hrEmployee.getEmpAddress().getGeographicalDivision>>>"+hrEmployee.getEmpAddress().getGeographicalDivision());
			}
			log.debug("hrEmployee after adding geographicalDivision"+hrEmployee.getEmpAddress().getGeographicalDivision());
		    HREmployeeQualification qual=new HREmployeeQualification();
		    if(hrEmployee.getEmpQualification()==null){
		    	hrEmployee.setEmpQualification(qual);
		    }
		    HRQualificationDivision qualDivision=null;
		   
		    /* if(hrEmployee.getEmpQualification().getQualificationDivision()==null)
			{
		    	qualDivision=new HRQualificationDivision();
			  hrEmployee.getEmpQualification().setQualificationDivision(qualDivision);
			  log.debug(" hrEmployee.getEmpQualification().getQualificationDivision>>>"+ hrEmployee.getEmpQualification().getQualificationDivision());
			}*/
		    
		    HREmployeeJob job=new HREmployeeJob();
		    if(hrEmployee.getCurrentEmpJob()==null){
		    	hrEmployee.setCurrentEmpJob(job);
		    }

		    HRInternalDivision intDivision=null;
		  
		    /* if(hrEmployee.getEmpJob().getInternalDivision()==null)
		    {
		    	intDivision=new HRInternalDivision();
		    	hrEmployee.getEmpJob().setInternalDivision(intDivision);
		    }*/
		}
		if(request.getMethod().equals("POST")){
			log.debug(">>>>>>>>>> Method post");
			String stringCounter=request.getParameter("counter");
			if(stringCounter!=null && !stringCounter.equals("")){
			  int counter=new Long(stringCounter).intValue();
			  log.debug(">>>>>>>>>> counter: "+counter);
			}

			log.debug(">>>>>>>>>>hrEmployee.getEmployeeRelatives().size(): "+hrEmployee.getEmployeeRelatives().size());
			List relatives=new ArrayList();
			
			// add new employeeRelatives if the user added new relatives
			/*for(int i=0;i<counter-hrEmployee.getEmployeeRelatives().size();i++)
			{
				HREmployeeRelative relative=new HREmployeeRelative();
				relative.setEmployee(hrEmployee);
				relatives.add(relative);
			}
			
			hrEmployee.getEmployeeRelatives().addAll(relatives);*/
			
			log.debug(">>>>>>>>>> hrEmployee.getEmployeeRelatives().size(): "+hrEmployee.getEmployeeRelatives().size());
		}
		log.debug("empAddress>>>>>>>>>>>>>>>>>>>>>"+hrEmployee.getEmpAddress());
		log.debug(">>>>>>>>>>>>>>>>>>>> end of formBackingObject.........................................");	
		return hrEmployee;
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>referenceData start>>>>>>>>>>>>>>>>>>>>>>>>>>");	
		HREmployee hrEmployee = (HREmployee) command;
		Map map = new HashMap();
		
		String employeeId=request.getParameter("employeeId");
		log.debug("employeeId>>>>>>>>>>>>>>>>>>>."+employeeId);
		
		String internalDivisionId = request.getParameter("internalDivisionId");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> internalDivisionId "+internalDivisionId);
	
		String geographicalDivisionId = request.getParameter("geographicalDivisionId");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> geographicalDivisionId "+geographicalDivisionId);

		String qualificationDivisionId = request.getParameter("qualificationDivisionId");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> qualificationDivisionId "+qualificationDivisionId);
		
		String tabNo=request.getParameter("tabNo");
		log.debug("tabNo>>>>>>>>>>>>>>>>>>>>>>>"+tabNo);
		
		String tabNo1=request.getParameter("tabNo1");
		log.debug("tabNo1>>>>>>>>>>>>>>>>>>>>>>>"+tabNo1);
		
		ITree internalDivisionTree = hrManager.createTreeIfNotFound(request,"HRInternalDivision");
		ITree geoDivisionTree = hrManager.createTreeIfNotFound(request,"HRGeographicalDivision");
		ITree qualDivisionTree = hrManager.createTreeIfNotFound(request,"HRQualificationDivision");
		
		if ((internalDivisionId != null) && (!internalDivisionId.equals(0)) && (internalDivisionId.length() != 0)){
			HRInternalDivision internalDivision = (HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(internalDivisionId));
			log.debug(">>>>>>>>>>>>>>>>>>>>>>internalDivision.getEndesc() "+internalDivision.getEndesc());
			map.put("internalDivision",internalDivision);
			map.put("isLeaf", "false");
				
			// can a new account be added under this category account
			
			List childs = internalDivision.getChilds();
			BigInteger bigInt = new BigInteger("10");
			
			/*// must get the length of the next level
			HRInternalLevel  nextLevel=hrManager.getLevelByLevelNo(internalDivision.getDivisionLevel().getLevelNo()+1);
			int maxCode = bigInt.pow(nextLevel.getLength().intValue()).intValue() - 1;
			
			if (childs.size() > maxCode || childs.size() > maxCode){
				map.put("canAddChild","false");
			}
			if (childs.size() < maxCode && childs.size() < maxCode){
				map.put("canAddChild","true");
			}*/
	
			/*	
			if(internalDivision.getParent()!=null && internalDivision.getDivisionLevel()!=null)
			{
					if(hrEmployee.getEmpJob().getInternalDivision()==null)
					{
					 hrEmployee.getEmpJob().setInternalDivision(internalDivision);
					}
					map.put("isLeaf", "true");
				
			}*/
		}
		
		if ((qualificationDivisionId != null) && (!qualificationDivisionId.equals(0)) && (qualificationDivisionId.length() != 0)){
			HRQualificationDivision qualificationDivision = (HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(qualificationDivisionId));
			log.debug(">>>>>>>>>>>>>>>>>>>>>>qualificationDivision.getEndesc() "+qualificationDivision.getEndesc());
			map.put("qualificationDivision",qualificationDivision);
			map.put("isLeaf", "false");
			
			// can a new account be added under this category account
			
			List childs = qualificationDivision.getChilds();
			BigInteger bigInt = new BigInteger("10");
			
			/*// must get the length of the next level
			HRQualificationLevel  nextLevel=hrManager.getQualLevelByLevelNo(qualificationDivision.getDivisionLevel().getLevelNo()+1);
			int maxCode = bigInt.pow(nextLevel.getLength().intValue()).intValue() - 1;
			
			if (childs.size() > maxCode || childs.size() > maxCode){
				map.put("canAddChild","false");
			}
			if (childs.size() < maxCode && childs.size() < maxCode){
				map.put("canAddChild","true");
			}*/
					
			/*if(qualificationDivision.getParent()!=null && qualificationDivision.getDivisionLevel()!=null)
			{
					if(hrEmployee.getEmpQualification().getQualificationDivision()==null)
					{
				 	 hrEmployee.getEmpQualification().setQualificationDivision(qualificationDivision);
					}
					map.put("isLeaf", "true");
				
			}*/
		}
		
		if ((geographicalDivisionId != null) && (!geographicalDivisionId.equals(0)) && (geographicalDivisionId.length() != 0)){
			HRGeographicalDivision geographicalDivision = (HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class,new Long(geographicalDivisionId));
			log.debug(">>>>>>>>>>>>>>>>>>>>>>geographicalDivision.getEndesc() "+geographicalDivision.getEndesc());
			map.put("geographicalDivision",geographicalDivision);
			map.put("isLeaf", "false");
			
			// can a new account be added under this category account
			
			List childs = geographicalDivision.getChilds();
			BigInteger bigInt = new BigInteger("10");
			
			/*// must get the length of the next level
			HRGeographicalLevel  nextLevel=hrManager.getGeoLevelByLevelNo(geographicalDivision.getDivisionLevel().getLevelNo()+1);
			int maxCode = bigInt.pow(nextLevel.getLength().intValue()).intValue() - 1;
			
			if (childs.size() > maxCode || childs.size() > maxCode){
				map.put("canAddChild","false");
			}
			if (childs.size() < maxCode && childs.size() < maxCode){
				map.put("canAddChild","true");
			}
	
			if(geographicalDivision.getParent()!=null && geographicalDivision.getDivisionLevel()!=null)
			{
					log.debug(" inside if geographicalDivision.getParent().getIsLast().equals(new Boolean(true))");
					if(hrEmployee.getEmpAddress().getGeographicalDivision()==null)
					{
					  hrEmployee.getEmpAddress().setGeographicalDivision(geographicalDivision);
					}
					log.debug("hrEmployee after adding geographicalDivision"+hrEmployee.getEmpAddress().getGeographicalDivision());
					map.put("isLeaf", "true");
				
			}*/		
		}
		map.put("tabNo",tabNo);
		map.put("tabNo1",tabNo1);
	    map.put("internalDivisionTree",internalDivisionTree );
	    map.put("geoDivisionTree", geoDivisionTree);
	    map.put("qualDivisionTree",qualDivisionTree );
		map.put("employeeId", employeeId);
		map.put("internalDivisionId", internalDivisionId);
		map.put("geographicalDivisionId", geographicalDivisionId);
		map.put("qualificationDivisionId", qualificationDivisionId);
		map.put("religions", hrManager.getObjects(HRReligion.class));
		map.put("insuranceCodes", hrManager.getObjects(HRInsurance.class));
		map.put("militaryServices", hrManager.getObjects(HRMilitaryService.class));
		map.put("maritalStatuses", hrManager.getObjects(HRMaritalStatus.class));
		map.put("titles", hrManager.getObjects(HRTitle.class));
		map.put("nationCountryList", hrManager.getCountriesForNationality());
//		map.put("costCenters", hrManager.getObjects(AnalysisLeafAccount.class));
		map.put("taxList", hrManager.getObjects(HRTax.class));
		map.put("InsuCodesList", hrManager.getObjects(HRInsurance.class));
		map.put("startDatesList", hrManager.getObjects(HREmployeeJob.class));
		
		map.put("banksList", hrManager.getObjects(HRBank.class));
		map.put("regionsList", hrManager.getObjects(HRRegion.class));
		map.put("locationsList", hrManager.getObjects(HRLocation.class));
		map.put("degreesList", hrManager.getObjects(Degree.class));
		
		
		map.put("sectorsList", hrManager.getObjects(Sector.class));
//		map.put("titlesList", hrManager.getObjects(HRTitle.class));
		map.put("syndicatesList", hrManager.getObjects(HRSyndicate.class));
//		map.put("costCentersList", hrManager.getObjects(Degree.class));
		
		if(employeeId!=null && !employeeId.equals("")){
			HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
			map.put("employeeCostCenters", hrManager.getObjectsByParameter(HREmployeeCostCenter.class,"employee",employee));
		}
		
		log.debug("----size---"+hrManager.getObjects(HRLocation.class).size());
		if(hrEmployee.getLocation()!=null){
		log.debug("---- location---"+hrEmployee.getLocation().getId());
		}
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<end referenceData>>>>>>>>>>>>>>>>>>>>>");
		return map;
	}
	
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
		
//		HREmployee hrEmployee = (HREmployee) command;
		
		String insuranceNumber = request.getParameter("insurNo");
		String insuranceCode = request.getParameter("insurCode");
		String subscriptionDate = request.getParameter("subscriptionDate");
		
		if(insuranceNumber == null || insuranceNumber.equals("") &&
				(insuranceCode != null || !insuranceCode.equals("") ||
				 subscriptionDate != null || !subscriptionDate.equals(""))){
			log.debug("----entered here---");
			errors.rejectValue("insurNo", "commons.errors.requiredFields","insuranceNumber requiredField");
		}
		if(insuranceCode == null || insuranceCode.equals("") &&
				(insuranceNumber != null && !insuranceNumber.equals("") ||
				subscriptionDate != null && !subscriptionDate.equals(""))){
			log.debug("----entered here2---");
			errors.rejectValue("insurCode", "commons.errors.requiredFields","insuranceCode requiredField");
		}
//		if(subscriptionDate == null || subscriptionDate.equals("") &&
//				(insuranceNumber != null && !insuranceNumber.equals("") ||
//				insuranceCode != null && !insuranceCode.equals(""))){
//			errors.rejectValue("subscriptionDate", "commons.errors.requiredFields","subscriptionDate requiredField");
//		}
	}
	
	@Override
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		HREmployee hrEmployee = (HREmployee) command;
		
		String militaryService=request.getParameter("militaryService");
		log.debug(" militaryService>>>>>>>>>>>>>>>>>>>>>>"+militaryService);
		
		String internalDivisionId = request.getParameter("internalDivisionId");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> internalDivisionId "+internalDivisionId);
		
		String geographicalDivisionId = request.getParameter("geographicalDivisionId");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> geographicalDivisionId "+geographicalDivisionId);
		
		if(geographicalDivisionId!=null && !geographicalDivisionId.equals("")){
			HRGeographicalDivision geographicalDivision=(HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class, new Long(geographicalDivisionId));
			hrEmployee.getEmpAddress().setGeographicalDivision(geographicalDivision);
		}
		
		String qualificationDivisionId = request.getParameter("qualificationDivisionId");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> qualificationDivisionId "+qualificationDivisionId);

		/*if ((internalDivisionId != null) && (!internalDivisionId.equals(0)) && (internalDivisionId.length() != 0)){
			HRInternalDivision internalDivision = (HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(internalDivisionId));
			log.debug(">>>>>>>>>>>>>>>>>>>>>>internalDivision.getEndesc() "+internalDivision.getEndesc());
			
			if(internalDivision.getParent()!=null)
			{
				if (internalDivision.getParent().getIsLast().equals(new Boolean(true))){
					if(hrEmployee.getEmpJob().getInternalDivision()==null)
					{
					 hrEmployee.getEmpJob().setInternalDivision(internalDivision);
					}
					
				}
			}
		
		}
		if ((qualificationDivisionId != null) && (!qualificationDivisionId.equals(0)) && (qualificationDivisionId.length() != 0)){
			HRQualificationDivision qualificationDivision = (HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(qualificationDivisionId));
			log.debug(">>>>>>>>>>>>>>>>>>>>>>qualificationDivision.getEndesc() "+qualificationDivision.getEndesc());
					
			if(qualificationDivision.getParent()!=null)
			{
				if (qualificationDivision.getParent().getIsLast().equals(new Boolean(true))){
					if(hrEmployee.getEmpQualification().getQualificationDivision()==null)
					{
				 	 hrEmployee.getEmpQualification().setQualificationDivision(qualificationDivision);
					}
					
				}
			}
		
		}
		
		if ((geographicalDivisionId != null) && (!geographicalDivisionId.equals(0)) && (geographicalDivisionId.length() != 0)){
			HRGeographicalDivision geographicalDivision = (HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class,new Long(geographicalDivisionId));
			log.debug(">>>>>>>>>>>>>>>>>>>>>>geographicalDivision.getEndesc() "+geographicalDivision.getEndesc());
							
			if(geographicalDivision.getParent()!=null)
			{
				if (geographicalDivision.getParent().getIsLast().equals(new Boolean(true))){
					log.debug(" inside if geographicalDivision.getParent().getIsLast().equals(new Boolean(true))");
					if(hrEmployee.getEmpAddress().getGeographicalDivision()==null)
					{
					  hrEmployee.getEmpAddress().setGeographicalDivision(geographicalDivision);
					}
					log.debug("hrEmployee after adding geographicalDivision"+hrEmployee.getEmpAddress().getGeographicalDivision());
					
				}
			}
		
				
		}*/
		///log.debug("ardesc>>>>>>>>>>>>"+ardesc);
		//log.debug("hrEmployee.getEmpAddress()>>>>>>>>>>>"+hrEmployee.getEmpAddress());
//		log.debug("hrEmployee.getEmpAddress().getGeographicalDivision()>>>>>>>>>"+hrEmployee.getEmpAddress().getGeographicalDivision());
		
//		 attach employee photo

		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		
		if (req.getFile("employeePhotoFile")!=null  &&  !req.getFile("employeePhotoFile").isEmpty()){
			MultipartFile newFile =  req.getFile("employeePhotoFile");
//			hrEmployee.setEmployeePhoto(Hibernate.createBlob(newFile.getInputStream()));
//			byte[] bFile = newFile.getInputStream();
//			Blob file = Hibernate.getLobCreator(session).createBlob(bFile);

			hrEmployee.setEmployeePhotoName(newFile.getOriginalFilename());
			log.debug(">>>>>>>>>>>>>>photo name: "+hrEmployee.getEmployeePhotoName());
		}	
		
		String insuranceNumber = request.getParameter("insurNo");
		String insuranceCode = request.getParameter("insurCode");
		String subscriptionDate = request.getParameter("subscriptionDate");
		
		log.debug(">>>>>>>>>>>>>>insuranceNumber: "+insuranceNumber);
		log.debug(">>>>>>>>>>>>>>insuranceCode: "+insuranceCode);
		log.debug(">>>>>>>>>>>>>>subscriptionDate: "+subscriptionDate);
		
		if(insuranceNumber != null && !insuranceNumber.equals("") &&
				insuranceCode != null && !insuranceCode.equals("") &&
				subscriptionDate != null && !subscriptionDate.equals("") ){
			
			log.debug(">>>>>>>>>>>>>>To create AInsuranceCala");
			AInsuranceCala aic = new AInsuranceCala();
			
			log.debug(">>>>>>>>>>>>>>empCode: "+request.getParameter("empCode"));
			aic.setEmp_code(request.getParameter("empCode"));
	
			String sub[] = subscriptionDate.split("/");
			log.debug(">>>>>>>>>>>>>>date: "+sub[1]+"-"+sub[2].substring(0, 4));
			
			//HRMonth m = (HRMonth) hrManager.getObjectByParameter(HRMonth.class, "month", new Integer(sub[1]));
			aic.setMonth(new Integer(sub[1])+"");
			
			//HRYear y = (HRYear) hrManager.getObjectByParameter(HRYear.class, "year", new Integer(sub[2].substring(0, 4)));
			aic.setYear(new Integer(sub[2].substring(0, 4))+"");	
			
			aic.setBasic_sal(0.0);
			aic.setTot_value(0.0);
			aic.setBasic_emp(0.0);
			aic.setVar_emp(0.0);
			aic.setBasic_co(0.0);
			aic.setVar_co(0.0);
			hrManager.saveObject(aic);
		}

	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		HREmployee hrEmployee = (HREmployee) command;
		
		hrManager.saveObject(hrEmployee.getEmpAddress());
		hrManager.saveObject(hrEmployee.getEmpAddress().getGeographicalDivision());
		hrManager.saveObject(hrEmployee.getCurrentEmpJob());
		hrManager.saveObject(hrEmployee.getEmpQualification());
		

		
//		log.debug("----getBasic---"+hrEmployee.getBasic());
//		log.debug("----getFirst_employment---"+hrEmployee.getFirst_employment());
//		log.debug("----getBasichour---"+hrEmployee.getBasichour());
//		log.debug("----getNo_govern_raise---"+hrEmployee.getNo_govern_raise());
//		log.debug("----getNo_var_insu---"+hrEmployee.getNo_var_insu());
		log.debug("----Permenant---"+hrEmployee.getPermenant());
		log.debug("----members---"+hrEmployee.getMembers());
		
		log.debug("hrEmployee.getEmpAddress().getGeographicalDivision().getArdesc()"+hrEmployee.getEmpAddress().getGeographicalDivision().getArdesc());
		hrManager.saveObject(hrEmployee);
	   
		List employeeCostCenterList=hrManager.getObjectsByParameter(HREmployeeCostCenter.class,"employee",hrEmployee);
		String  costCenter= request.getParameter("costCenter");
		String anotherCostCenter = request.getParameter("costCenter2");
		String  fromDate= request.getParameter("fromDate");
		String  fromDate2= request.getParameter("fromDate2");
		String toDate = request.getParameter("toDate");
		String  toDate2= request.getParameter("toDate2");
		log.debug("fromDate>>>>>>>>>>>>"+fromDate);
		log.debug("toDate>>>>>>>>>"+toDate);
		log.debug("fromDate2??????"+fromDate2);
		log.debug("todate2>>>>>>>>>"+toDate2);
//		if(costCenter!=null && !costCenter.equals("")){
//			log.debug("costCenter>>>>>>>>>>>>>"+costCenter);
////			Account costAccount=(Account)hrManager.getObject(Account.class, new Long(costCenter));
//			if(employeeCostCenterList.size()>0 && !employeeCostCenterList.isEmpty()){
//				log.debug("employeeCostCenterList.size()>>>>>>>>>>"+employeeCostCenterList.size());
//				HREmployeeCostCenter emplyeeCostCenter=(HREmployeeCostCenter)employeeCostCenterList.get(0);
//				emplyeeCostCenter.setCostCenter(costAccount);
//				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//				
//				try{
//	        		if(fromDate!=null && !fromDate.equals("")){
//	        			Date fromDateParsed = df.parse(fromDate);
//	        			System.out.println("Today = " + df.format(fromDateParsed));
//	        			emplyeeCostCenter.setFromDate(fromDateParsed);
//	        		}
//				}catch(ParseException e){
//					e.printStackTrace();
//				}
//	          
//				try{
//	        		if(toDate!=null && !toDate.equals("")){
//		            	Date toDateParsed = df.parse(toDate);
//		            	System.out.println("Today = " + df.format(toDateParsed));
//		            	emplyeeCostCenter.setToDate(toDateParsed);
//	        		}
//				}catch (ParseException e){
//					e.printStackTrace();
//		        }
//		        hrManager.saveObject(emplyeeCostCenter);
//			}
//			else{
//				log.debug("employeeCostCenterList.size()>>>>>>>>>"+employeeCostCenterList.size());
//				log.debug("hrEmployee>>>>>>>>"+hrEmployee);
//				HREmployeeCostCenter emplyeeCostCenter=new HREmployeeCostCenter();
//				emplyeeCostCenter.setEmployee(hrEmployee);
//				emplyeeCostCenter.setCostCenter(costAccount);
//				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//				try{
//					if(fromDate!=null && !fromDate.equals("")){
//		            	Date fromDateParsed = df.parse(fromDate);
//		            	System.out.println("Today = " + df.format(fromDateParsed));
//		            	emplyeeCostCenter.setFromDate(fromDateParsed);
//		        	}
//		        }catch (ParseException e) {
//		        	e.printStackTrace();
//		        }
//		          
//		        try {
//		        	if(toDate!=null && !toDate.equals("")){
//		        		Date toDateParsed = df.parse(toDate);
//		        		System.out.println("Today = " + df.format(toDateParsed));
//		        		emplyeeCostCenter.setToDate(toDateParsed);
//		        	}
//		        }catch (ParseException e) {
//		        	e.printStackTrace();
//		        }
//		        hrManager.saveObject(emplyeeCostCenter);
//			}
//		}
	  
//		if(anotherCostCenter!=null && !anotherCostCenter.equals("")){
//			log.debug("anotherCostCenter>>>>>>>>>>>>>"+anotherCostCenter);
//			Account costAccount=(Account)hrManager.getObject(Account.class, new Long(anotherCostCenter));
//		  
//			if(employeeCostCenterList.size()>0 && !employeeCostCenterList.isEmpty()) {
//				HREmployeeCostCenter emplyeeCostCenter=(HREmployeeCostCenter)employeeCostCenterList.get(1);
//				emplyeeCostCenter.setCostCenter(costAccount);
//				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//
//				try {
//			    	if(fromDate2!=null && !fromDate2.equals("")){
//		            	Date fromDateParsed = df.parse(fromDate2);
//		            	System.out.println("Today = " + df.format(fromDateParsed));
//		            	emplyeeCostCenter.setFromDate(fromDateParsed);
//		        	}
//			    }catch (ParseException e) {
//			    	e.printStackTrace();
//			    }
//			          
//			    try {
//			    	if(toDate2!=null && !toDate2.equals("")){
//				    	Date toDateParsed = df.parse(toDate2);
//				        System.out.println("Today = " + df.format(toDateParsed));
//				    	emplyeeCostCenter.setToDate(toDateParsed);
//			        }
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				hrManager.saveObject(emplyeeCostCenter);
//			}else{
//				HREmployeeCostCenter emplyeeCostCenter=new HREmployeeCostCenter();
//				emplyeeCostCenter.setEmployee(hrEmployee);
//				emplyeeCostCenter.setCostCenter(costAccount);
//				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//				
//				try {
//		        	if(fromDate2!=null && !fromDate2.equals("")){
//		            	Date fromDateParsed = df.parse(fromDate2);
//		            	System.out.println("Today = " + df.format(fromDateParsed));
//		            	emplyeeCostCenter.setFromDate(fromDateParsed);
//		        	}
//				} catch (ParseException e) {
//		        	 e.printStackTrace();
//				}
//		         
//				try {
//		        	if(toDate2!=null && !toDate2.equals("")){
//			        	Date toDateParsed = df.parse(toDate2);
//			         	System.out.println("Today = " + df.format(toDateParsed));
//			         	emplyeeCostCenter.setToDate(toDateParsed);
//		        	}
//				} catch (ParseException e) {
//			     	e.printStackTrace();
//			   	}
//				hrManager.saveObject(emplyeeCostCenter);
//			}
//		}
		return new ModelAndView(new RedirectView("employeesView.html"));
	}
}