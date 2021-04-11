package com._4s_.common.web.action;

import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.util.DBUtils;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.command.DateConverter;

public class DateConverterFormController extends BaseSimpleFormController{
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response,
			Object command, BindException errors)
	throws Exception{
	DateConverter dateConverter = (DateConverter)command;
	String convertedDate = null;
	
	MultiCalendarDate mCalDate = new MultiCalendarDate();
	try{
		mCalDate.setDateString(dateConverter.getDate());
	}catch (IllegalArgumentException e){
		errors.rejectValue("date","commons.errors.invalidDate","");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> errors "+errors);
	}
	
	convertedDate = mCalDate.getConvertedDateString();
	dateConverter.setConvertedDate(convertedDate);
//	generateUnKnownIslamicDates();
	return new ModelAndView("CommonAdminDateConverter","dateConverter",dateConverter);
}
	
	private void generateUnKnownIslamicDates() {
		
		Calendar currentDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		currentDate.set(Calendar.YEAR,1900);
		currentDate.set(Calendar.MONTH,0);
		currentDate.set(Calendar.DAY_OF_MONTH,1);
		currentDate.getTime();
		MultiCalendarDate miladiCal = new MultiCalendarDate();
		MultiCalendarDate hijriCal = new MultiCalendarDate();
		miladiCal.setCalendarType(MultiCalendarDate.MILADI);
		hijriCal.setCalendarType(MultiCalendarDate.HIJRI);
		int count =0;
		while(currentDate.before(endDate)){
//			 miladi >  hijri > milady
			String hijriStr = "";
			try{
				miladiCal.setDate(currentDate.getTime());
				hijriStr = miladiCal.getHijriString();
				hijriCal.setDateString(hijriStr);
			
			}catch (Exception e) {
				log.fatal("miladi date > " + currentDate.getTime().toString() + "   its hijri is : " + hijriStr);
				String year = currentDate.get(Calendar.YEAR) + "";
				String month = (currentDate.get(Calendar.MONTH) + 1) + "";
				String day = currentDate.get(Calendar.DAY_OF_MONTH) + "";
				if(month.length() == 1){
					month = "0"+month;
				}
				if(day.length() == 1){
					day = "0"+day;
				}
				String miladyStr = year+"-"+month+"-"+day;
				
				String arr[] = hijriStr.split("/");
				String yearSection = new Integer(arr[2]) +"";
				String monthSection = new Integer(arr[1]) +"";
				String daySection = new Integer(arr[0]) +"";
				if(monthSection.length() == 1){
					monthSection = "0"+monthSection;
				}
				if(daySection.length() == 1){
					daySection = "0"+daySection;
				}

				hijriStr = yearSection+"-"+monthSection+"-"+daySection;
				DBUtils.execute("insert into temp_date values('"+miladyStr+"','"+hijriStr+"')");
			}
			
			currentDate.add(Calendar.DAY_OF_YEAR , 1);
			currentDate.getTime();

//			count++;
//			if(count > 100){
//				break;
//			}
		}
	}

	protected Object formBackingObject (HttpServletRequest request)
	throws ServletException{
		DateConverter dateConverter;
//		if (request.getSession().getAttribute("dateConverter") != null){
//			dateConverter = (DateConverter)request.getSession().getAttribute("dateConverter");
//		}else{
			dateConverter = new DateConverter();
		//}
		return dateConverter;
	}

}
