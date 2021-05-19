/**
 * 
 */
package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.LevelsCommand;
import com._4s_.common.web.action.BaseSimpleFormController;

/**
 * @author hoda
 *
 */
public class TreeLevelsFormController   extends BaseSimpleFormController {

			protected HRManager hrManager = null;
			protected Class className=null;
	      
			public HRManager getHrManager() {
				return hrManager;
			}

			public void setHrManager(HRManager hrManager) {
				this.hrManager = hrManager;
			}
			

		
			protected Object formBackingObject(HttpServletRequest request)
					throws Exception {
				LevelsCommand levelsCommand=new LevelsCommand();
				
				
				return levelsCommand;
			}

			protected Map referenceData(HttpServletRequest request, Object command,Errors errors) throws Exception {
				
				Map model = new HashMap();
		
				return model;
			}

			
			protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
				// TODO Auto-generated method stub
			
				}
			
			protected ModelAndView onSubmit(HttpServletRequest request,
					HttpServletResponse response, Object command, BindException errors)
					throws Exception {
				LevelsCommand result = (LevelsCommand) command;
					
					for(int i=0;i<result.getLevels().size();i++)
					{Object level=null;
						className.cast(level);
						 level=result.getLevels().get(i);
					  hrManager.saveObject(level);
					}
					
			
				return new ModelAndView(new RedirectView("internalLevelForm.html"));
			}

			
			

			
			}
			




