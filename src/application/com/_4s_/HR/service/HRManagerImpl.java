package com._4s_.HR.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com._4s_.HR.dao.HRDAO;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HRGeographicalDivision;
import com._4s_.HR.model.HRGeographicalLevel;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRInternalLevel;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationRules;
import com._4s_.HR.model.HRViolationRules;
import com._4s_.HR.model.HRYearAllowVacationTransfer;
import com._4s_.common.service.BaseManagerImpl;
import com.jenkov.prizetags.tree.impl.Tree;
import com.jenkov.prizetags.tree.impl.TreeNode;
import com.jenkov.prizetags.tree.itf.ITree;
import com.jenkov.prizetags.tree.itf.ITreeNode;


public class HRManagerImpl extends BaseManagerImpl implements HRManager{


	private HRDAO hrDAO;

	public HRDAO getHrDAO() {
		return hrDAO;
	}

	public void setHrDAO(HRDAO hrDAO) {
		this.hrDAO = hrDAO;
	}
	
	
	public ITree createTreeIfNotFound(HttpServletRequest request,String className) {
			ITree result=null;
		log.debug("inside createTreeIfNotFound >>>>>>>>");
		
			if(className.equals("HRInternalDivision"))
			{
				result = (ITree) request.getSession().getAttribute("internalDivTree");
				if (result == null) {
					result = createTree(className);
					request.getSession().setAttribute("internalDivTree", result);
				}
			}
				
			else if(className.equals("HRQualificationDivision"))
			{
				result = (ITree) request.getSession().getAttribute("qualificationDivTree");
				if (result == null) {
					result = createTree(className);
					request.getSession().setAttribute("qualificationDivTree", result);
				}
			}
			
			else if(className.equals("HRGeographicalDivision"))
			{
				result = (ITree) request.getSession().getAttribute("geographicalDivTree");
				if (result == null) {
					result = createTree(className);
					request.getSession().setAttribute("geographicalDivTree", result);
				}
			}
			
			else if(className.equals("HRSpecialtyDivision"))
			{
				result = (ITree) request.getSession().getAttribute("specialtyDivTree");
				if (result == null) {
					result = createTree(className);
					request.getSession().setAttribute("specialtyDivTree", result);
				}
			}
			return result;
		}
	
	public ITree createTreeIfNotFoundForCopy(HttpServletRequest request,String className,Object parent) {
		ITree result=null;
	log.debug("inside createTreeIfNotFoundForCopy >>>>>>>>");
	
		if(className.equals("HRInternalDivision"))
		{
			result = (ITree) request.getSession().getAttribute("internalDivTreeForCopy");
			if (result == null) {
				result = createTreeForCopy(className, parent);
				request.getSession().setAttribute("internalDivTreeForCopy", result);
			}
		}
			
		else if(className.equals("HRQualificationDivision"))
		{
			result = (ITree) request.getSession().getAttribute("qualificationDivTreeForCopy");
			if (result == null) {
				result = createTreeForCopy(className, parent);
				request.getSession().setAttribute("qualificationDivTreeForCopy", result);
			}
		}
		
		else if(className.equals("HRGeographicalDivision"))
		{
			result = (ITree) request.getSession().getAttribute("geographicalDivTreeForCopy");
			if (result == null) {
				result = createTreeForCopy(className, parent);
				request.getSession().setAttribute("geographicalDivTreeForCopy", result);
			}
		}
		
		else if(className.equals("HRSpecialtyDivision"))
		{
			result = (ITree) request.getSession().getAttribute("specialtyDivTreeForCopy");
			if (result == null) {
				result =createTreeForCopy(className, parent);
				request.getSession().setAttribute("specialtyDivTreeForCopy", result);
			}
		}
		return result;
	}
	
	
	
	
	public ITree createTree(String className) {
		log.debug("inside createTree");

		ITree divTree = new Tree();
		ITreeNode rootNode = new TreeNode("0", "root");
		divTree.setRoot(rootNode);
		List rootParents;
		

			rootParents = hrDAO.getRoot(className);
			log.debug("rootParents.size()>>>>>>>>>>>>>>"+rootParents.size());
			Iterator itr = rootParents.iterator();
			while (itr.hasNext()) {
				if(className.equals("HRInternalDivision"))
				{
					HRInternalDivision rootCategory = (HRInternalDivision) itr.next();
				ITreeNode a_rootNode = new TreeNode(rootCategory.getId().toString(),rootCategory.getArdesc());
				a_rootNode.setToolTip(rootCategory.getArdesc());
			log.debug("a_rootNode.getToolTip()>>>>>>>>>>>>>>"+a_rootNode.getToolTip());
				rootNode.addChild(a_rootNode);
				log.debug("after adding child to rootNode"+rootNode.getChildren().size());
				createNode(rootCategory, a_rootNode, className);
				
				List allLeafs = hrDAO.getAllLeafs(HRInternalDivision.class );
				log.debug("allLeefs.size()***********"+allLeafs.size());
				Iterator itr2 = allLeafs.iterator();
				while (itr2.hasNext()) {
					HRInternalDivision childLeaf = (HRInternalDivision) itr2.next();
					ITreeNode childNode = new TreeNode(childLeaf.getId().toString(), childLeaf.getArdesc());
					if(childLeaf.getParent()!=null)
					{
					String parentId = childLeaf.getParent().getId().toString();
					log.debug("parentId>>>>>>>>>>>>>>>>>>>>>>"+parentId);
					log.debug("divTree.findNode(parentId)>>>>>>>>>>>>>"+divTree.findNode(parentId));
					childNode.setToolTip(childLeaf.getParent().getArdesc());
					log.debug("a_childNode.getToolTip()>>>>>>>>>>>>>>"+childNode.getToolTip());
					divTree.findNode(parentId).addChild(childNode);
					}
				}
				}
				
				else if(className.equals("HRGeographicalDivision"))
				{
					HRGeographicalDivision rootCategory = (HRGeographicalDivision) itr.next();
				ITreeNode a_rootNode = new TreeNode(rootCategory.getId().toString(),rootCategory.getArdesc());
				rootNode.addChild(a_rootNode);
				createNode(rootCategory, a_rootNode, className);
				
				List allLeafs = hrDAO.getAllLeafs(HRGeographicalDivision.class );
				Iterator itr2 = allLeafs.iterator();
				
				while (itr2.hasNext()) {
					HRGeographicalDivision childLeaf = (HRGeographicalDivision) itr2.next();
					ITreeNode childNode = new TreeNode(childLeaf.getId().toString(), childLeaf.getArdesc());
					if(childLeaf.getParent()!=null)
					{
						String parentId = childLeaf.getParent().getId().toString();
						divTree.findNode(parentId).addChild(childNode);
						}
				}
				}
				
				else if(className.equals("HRQualificationDivision"))
				{
					HRQualificationDivision rootCategory = (HRQualificationDivision) itr.next();
				ITreeNode a_rootNode = new TreeNode(rootCategory.getId().toString(),rootCategory.getArdesc());
				rootNode.addChild(a_rootNode);
				createNode(rootCategory, a_rootNode, className);
				
				List allLeafs = hrDAO.getAllLeafs(HRQualificationDivision.class );
				Iterator itr2 = allLeafs.iterator();
				
				while (itr2.hasNext()) {
					HRQualificationDivision childLeaf = (HRQualificationDivision) itr2.next();
					ITreeNode childNode = new TreeNode(childLeaf.getId().toString(), childLeaf.getArdesc());
					if(childLeaf.getParent()!=null)
					{
						String parentId = childLeaf.getParent().getId().toString();
						divTree.findNode(parentId).addChild(childNode);
					}
				}
				}
				
				else if(className.equals("HRSpecialtyDivision"))
				{
					HRSpecialtyDivision rootCategory = (HRSpecialtyDivision) itr.next();
				ITreeNode a_rootNode = new TreeNode(rootCategory.getId().toString(),rootCategory.getArdesc());
				rootNode.addChild(a_rootNode);
				createNode(rootCategory, a_rootNode, className);
				
				List allLeafs = hrDAO.getAllLeafs(HRSpecialtyDivision.class );
				Iterator itr2 = allLeafs.iterator();
				
				while (itr2.hasNext()) {
					HRSpecialtyDivision childLeaf = (HRSpecialtyDivision) itr2.next();
					ITreeNode childNode = new TreeNode(childLeaf.getId().toString(), childLeaf.getArdesc());
					if(childLeaf.getParent()!=null)
					{
						String parentId = childLeaf.getParent().getId().toString();
						divTree.findNode(parentId).addChild(childNode);
					}
				}
				}
			}

			

		
		return divTree;
	}
	
	
	public ITree createTreeForCopy(String className,Object parent) {
		log.debug("inside createTree");

		ITree divTree = new Tree();
		ITreeNode rootNode = new TreeNode("0", "root");
		divTree.setRoot(rootNode);
		List rootParents;
		

			rootParents = hrDAO.getRoot(className);
			log.debug("rootParents.size()>>>>>>>>>>>>>>"+rootParents.size());
			Iterator itr = rootParents.iterator();
			while (itr.hasNext()) {
				if(className.equals("HRInternalDivision"))
				{
					HRInternalDivision rootCategory = (HRInternalDivision) itr.next();
				ITreeNode a_rootNode = new TreeNode(rootCategory.getId().toString(),rootCategory.getArdesc());
				a_rootNode.setToolTip(rootCategory.getArdesc());
			log.debug("a_rootNode.getToolTip()>>>>>>>>>>>>>>"+a_rootNode.getToolTip());
				rootNode.addChild(a_rootNode);
				log.debug("after adding child to rootNode"+rootNode.getChildren().size());
				createNodeForCopy(rootCategory, a_rootNode, className,parent);
				
				
				}
				
				else if(className.equals("HRGeographicalDivision"))
				{
					HRGeographicalDivision rootCategory = (HRGeographicalDivision) itr.next();
				ITreeNode a_rootNode = new TreeNode(rootCategory.getId().toString(),rootCategory.getArdesc());
				rootNode.addChild(a_rootNode);
				createNode(rootCategory, a_rootNode, className);
					
			
				}
				
				else if(className.equals("HRQualificationDivision"))
				{
					HRQualificationDivision rootCategory = (HRQualificationDivision) itr.next();
				ITreeNode a_rootNode = new TreeNode(rootCategory.getId().toString(),rootCategory.getArdesc());
				rootNode.addChild(a_rootNode);
				createNode(rootCategory, a_rootNode, className);
				
				
				}
				
				else if(className.equals("HRSpecialtyDivision"))
				{
					HRSpecialtyDivision rootCategory = (HRSpecialtyDivision) itr.next();
				ITreeNode a_rootNode = new TreeNode(rootCategory.getId().toString(),rootCategory.getArdesc());
				rootNode.addChild(a_rootNode);
				createNode(rootCategory, a_rootNode, className);
				
				
				}
			}

			

		
		return divTree;
	}


	
	public void createNode(Object higherCategory, ITreeNode treeNode,String className) {

		log.debug("create node");
		if (className.equals("HRInternalDivision")) {

			HRInternalDivision internalDivision = (HRInternalDivision) higherCategory;
			List childCategories = internalDivision.getChilds();
			//			sortChildren(childCategories);
			if(childCategories!=null)
			{
				log.debug("childCategories.size()>>>>>>>>>>>"+childCategories.size());
				for (int m = 0; m <childCategories.size() - 1; m++) {
					HRInternalDivision division=(HRInternalDivision)childCategories.get(m);
					for (int n = 0; n < childCategories.size() - m - 1; n++) {
						HRInternalDivision div=(HRInternalDivision)childCategories.get(n);
						HRInternalDivision div1=(HRInternalDivision)childCategories.get(n+1);
						if (new Long(div1.getLongCode())< new Long(div.getLongCode())) {
							HRInternalDivision tempDiv = (HRInternalDivision)childCategories.get(n);
							childCategories.set(n, childCategories.get(n + 1));
							childCategories.set(n + 1, tempDiv);
						}
					}
				}
				Iterator itr1 = childCategories.iterator();
				while (itr1.hasNext()) {
					log.debug("inside itrator for add child");
					HRInternalDivision lowerCategory = (HRInternalDivision) itr1.next();
					ITreeNode lowerNode = new TreeNode(lowerCategory.getId().toString(),lowerCategory.getArdesc());
					treeNode.addChild(lowerNode);
					createNode(lowerCategory, lowerNode, className);// in recursion the lowerCategory will be higher for its childs
				}
				
			
				
			}

		} 
		
		else if (className.equals("HRGeographicalDivision")) {

			HRGeographicalDivision geographicalDivision = (HRGeographicalDivision) higherCategory;
			List childCategories = geographicalDivision.getChilds();
			//			sortAccounts(childCategories);
			if(childCategories!=null)
			{
				
				log.debug("childCategories.size()>>>>>>>>>>>"+childCategories.size());
				for (int m = 0; m <childCategories.size() - 1; m++) {
					HRGeographicalDivision division=(HRGeographicalDivision)childCategories.get(m);
					for (int n = 0; n < childCategories.size() - m - 1; n++) {
						HRGeographicalDivision div=(HRGeographicalDivision)childCategories.get(n);
						HRGeographicalDivision div1=(HRGeographicalDivision)childCategories.get(n+1);
						if (new Long(div1.getLongCode())< new Long(div.getLongCode())) {
							HRGeographicalDivision tempDiv = (HRGeographicalDivision)childCategories.get(n);
							childCategories.set(n, childCategories.get(n + 1));
							childCategories.set(n + 1, tempDiv);
						}
					}
				}
				Iterator itr1 = childCategories.iterator();
				while (itr1.hasNext()) {
					HRGeographicalDivision lowerCategory = (HRGeographicalDivision) itr1.next();
					ITreeNode lowerNode = new TreeNode(lowerCategory.getId().toString(),lowerCategory.getArdesc());
					treeNode.addChild(lowerNode);
					createNode(lowerCategory, lowerNode, className);// in recursion the lowerCategory will be higher for its childs
				}
			}

		}
		
		else if (className.equals("HRQualificationDivision")) {

			HRQualificationDivision qualificationDivision = (HRQualificationDivision) higherCategory;
			List childCategories = qualificationDivision.getChilds();
			//			sortAccounts(childCategories);
			if(childCategories!=null)
			{
				log.debug("childCategories.size()>>>>>>>>>>>"+childCategories.size());
				for (int m = 0; m <childCategories.size() - 1; m++) {
					HRQualificationDivision division=(HRQualificationDivision)childCategories.get(m);
					for (int n = 0; n < childCategories.size() - m - 1; n++) {
						HRQualificationDivision div=(HRQualificationDivision)childCategories.get(n);
						HRQualificationDivision div1=(HRQualificationDivision)childCategories.get(n+1);
						if (new Long(div1.getLongCode())< new Long(div.getLongCode())) {
							HRQualificationDivision tempDiv = (HRQualificationDivision)childCategories.get(n);
							childCategories.set(n, childCategories.get(n + 1));
							childCategories.set(n + 1, tempDiv);
						}
					}
				}
					
				Iterator itr1 = childCategories.iterator();
				
				while (itr1.hasNext()) {
					HRQualificationDivision lowerCategory = (HRQualificationDivision) itr1.next();
					ITreeNode lowerNode = new TreeNode(lowerCategory.getId().toString(),lowerCategory.getArdesc());
					treeNode.addChild(lowerNode);
					createNode(lowerCategory, lowerNode, className);// in recursion the lowerCategory will be higher for its childs
				}
			}
		}
		
		else if (className.equals("HRSpecialtyDivision")) {

			HRSpecialtyDivision specialtyDivision = (HRSpecialtyDivision) higherCategory;
			List childCategories = specialtyDivision.getChilds();
			//			sortAccounts(childCategories);
			if(childCategories!=null)
			{
				
				log.debug("childCategories.size()>>>>>>>>>>>"+childCategories.size());
				for (int m = 0; m <childCategories.size() - 1; m++) {
					HRSpecialtyDivision division=(HRSpecialtyDivision)childCategories.get(m);
					for (int n = 0; n < childCategories.size() - m - 1; n++) {
						HRSpecialtyDivision div=(HRSpecialtyDivision)childCategories.get(n);
						HRSpecialtyDivision div1=(HRSpecialtyDivision)childCategories.get(n+1);
						if (new Long(div1.getLongCode())< new Long(div.getLongCode())) {
							HRSpecialtyDivision tempDiv = (HRSpecialtyDivision)childCategories.get(n);
							childCategories.set(n, childCategories.get(n + 1));
							childCategories.set(n + 1, tempDiv);
						}
					}
				}
					
				Iterator itr1 = childCategories.iterator();
				
				while (itr1.hasNext()) {
					HRSpecialtyDivision lowerCategory = (HRSpecialtyDivision) itr1.next();
					ITreeNode lowerNode = new TreeNode(lowerCategory.getId().toString(),lowerCategory.getArdesc());
					treeNode.addChild(lowerNode);
					createNode(lowerCategory, lowerNode, className);// in recursion the lowerCategory will be higher for its childs
				}
			}
		}
	}
	
	public void createNodeForCopy(Object higherCategory, ITreeNode treeNode,String className,Object parent) {

		log.debug("create nodeForCopy");
		if (className.equals("HRInternalDivision")) {

			HRInternalDivision internalDivision = (HRInternalDivision) higherCategory;
			List childCategories = internalDivision.getChilds();
			//			sortChildren(childCategories);
			if(childCategories!=null)
			{
				log.debug("childCategories.size()>>>>>>>>>>>"+childCategories.size());
				for (int m = 0; m <childCategories.size() - 1; m++) {
					HRInternalDivision division=(HRInternalDivision)childCategories.get(m);
					for (int n = 0; n < childCategories.size() - m - 1; n++) {
						HRInternalDivision div=(HRInternalDivision)childCategories.get(n);
						HRInternalDivision div1=(HRInternalDivision)childCategories.get(n+1);
						if (new Long(div1.getLongCode())< new Long(div.getLongCode())) {
							HRInternalDivision tempDiv = (HRInternalDivision)childCategories.get(n);
							childCategories.set(n, childCategories.get(n + 1));
							childCategories.set(n + 1, tempDiv);
						}
					}
				}
				Iterator itr1 = childCategories.iterator();
				HRInternalDivision parentDiv=(HRInternalDivision)parent;
				while (itr1.hasNext()) {
					log.debug("inside itrator for add child");
					HRInternalDivision lowerCategory = (HRInternalDivision) itr1.next();
					ITreeNode lowerNode = new TreeNode(lowerCategory.getId().toString(),lowerCategory.getArdesc());
					if(parentDiv.getDivisionLevel()!=null)
					{
						if(lowerCategory.getDivisionLevel()!=null)
						{
							if(lowerCategory.getDivisionLevel().getLevelNo()<parentDiv.getDivisionLevel().getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
						else
						{
							HRInternalLevel levelForLower=(HRInternalLevel)hrDAO.getObject(HRInternalLevel.class, new Long(lowerCategory.getCode()));
							if(levelForLower.getLevelNo()<parentDiv.getDivisionLevel().getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
					}
					else
					{
						HRInternalLevel level=(HRInternalLevel)hrDAO.getObject(HRInternalLevel.class, new Long(parentDiv.getCode()));
						log.debug("lowerCategory.getDivisionLevel()>>>>>>>>>>>"+lowerCategory.getDivisionLevel());
						log.debug("level>>>>>>>>>>>>>>>>>"+level);
						if(lowerCategory.getDivisionLevel()!=null)
						{
							if(lowerCategory.getDivisionLevel().getLevelNo()<level.getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
						else
						{
							HRInternalLevel levelForLower=(HRInternalLevel)hrDAO.getObject(HRInternalLevel.class, new Long(lowerCategory.getCode()));
							if(levelForLower.getLevelNo()<level.getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
					}
					
					createNodeForCopy(lowerCategory, lowerNode, className,parentDiv);// in recursion the lowerCategory will be higher for its childs
				}
				
			
				
			}

		} 
		
		else if (className.equals("HRGeographicalDivision")) {

			HRGeographicalDivision geographicalDivision = (HRGeographicalDivision) higherCategory;
			List childCategories = geographicalDivision.getChilds();
			//			sortAccounts(childCategories);
			if(childCategories!=null)
			{
				
				log.debug("childCategories.size()>>>>>>>>>>>"+childCategories.size());
				for (int m = 0; m <childCategories.size() - 1; m++) {
					HRGeographicalDivision division=(HRGeographicalDivision)childCategories.get(m);
					for (int n = 0; n < childCategories.size() - m - 1; n++) {
						HRGeographicalDivision div=(HRGeographicalDivision)childCategories.get(n);
						HRGeographicalDivision div1=(HRGeographicalDivision)childCategories.get(n+1);
						if (new Long(div1.getLongCode())< new Long(div.getLongCode())) {
							HRGeographicalDivision tempDiv = (HRGeographicalDivision)childCategories.get(n);
							childCategories.set(n, childCategories.get(n + 1));
							childCategories.set(n + 1, tempDiv);
						}
					}
				}
				Iterator itr1 = childCategories.iterator();
				HRGeographicalDivision parentDiv=(HRGeographicalDivision)parent;
				while (itr1.hasNext()) {
					log.debug("inside itrator for add child");
					HRGeographicalDivision lowerCategory = (HRGeographicalDivision) itr1.next();
					ITreeNode lowerNode = new TreeNode(lowerCategory.getId().toString(),lowerCategory.getArdesc());
					if(parentDiv.getDivisionLevel()!=null)
					{
						if(lowerCategory.getDivisionLevel()!=null)
						{
							if(lowerCategory.getDivisionLevel().getLevelNo()<parentDiv.getDivisionLevel().getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
						else
						{
							HRGeographicalLevel levelForLower=(HRGeographicalLevel)hrDAO.getObject(HRGeographicalLevel.class, new Long(lowerCategory.getCode()));
							if(levelForLower.getLevelNo()<parentDiv.getDivisionLevel().getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
					}
					else
					{
						HRGeographicalLevel level=(HRGeographicalLevel)hrDAO.getObject(HRGeographicalLevel.class, new Long(parentDiv.getCode()));
						log.debug("lowerCategory.getDivisionLevel()>>>>>>>>>>>"+lowerCategory.getDivisionLevel());
						log.debug("level>>>>>>>>>>>>>>>>>"+level);
						if(lowerCategory.getDivisionLevel()!=null)
						{
							if(lowerCategory.getDivisionLevel().getLevelNo()<level.getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
						else
						{
							HRGeographicalLevel levelForLower=(HRGeographicalLevel)hrDAO.getObject(HRGeographicalLevel.class, new Long(lowerCategory.getCode()));
							if(levelForLower.getLevelNo()<level.getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
					}
					
					createNodeForCopy(lowerCategory, lowerNode, className,parentDiv);// in recursion the lowerCategory will be higher for its childs
				}
				
			
			}

		}
		
		else if (className.equals("HRQualificationDivision")) {

			HRQualificationDivision qualificationDivision = (HRQualificationDivision) higherCategory;
			List childCategories = qualificationDivision.getChilds();
			//			sortAccounts(childCategories);
			if(childCategories!=null)
			{
				log.debug("childCategories.size()>>>>>>>>>>>"+childCategories.size());
				for (int m = 0; m <childCategories.size() - 1; m++) {
					HRQualificationDivision division=(HRQualificationDivision)childCategories.get(m);
					for (int n = 0; n < childCategories.size() - m - 1; n++) {
						HRQualificationDivision div=(HRQualificationDivision)childCategories.get(n);
						HRQualificationDivision div1=(HRQualificationDivision)childCategories.get(n+1);
						if (new Long(div1.getLongCode())< new Long(div.getLongCode())) {
							HRQualificationDivision tempDiv = (HRQualificationDivision)childCategories.get(n);
							childCategories.set(n, childCategories.get(n + 1));
							childCategories.set(n + 1, tempDiv);
						}
					}
				}
					
				Iterator itr1 = childCategories.iterator();
				
				HRQualificationDivision parentDiv=(HRQualificationDivision)parent;
				while (itr1.hasNext()) {
					log.debug("inside itrator for add child");
					HRQualificationDivision lowerCategory = (HRQualificationDivision) itr1.next();
					ITreeNode lowerNode = new TreeNode(lowerCategory.getId().toString(),lowerCategory.getArdesc());
					if(parentDiv.getDivisionLevel()!=null)
					{
						if(lowerCategory.getDivisionLevel()!=null)
						{
							if(lowerCategory.getDivisionLevel().getLevelNo()<parentDiv.getDivisionLevel().getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
						else
						{
							HRQualificationLevel levelForLower=(HRQualificationLevel)hrDAO.getObject(HRQualificationLevel.class, new Long(lowerCategory.getCode()));
							if(levelForLower.getLevelNo()<parentDiv.getDivisionLevel().getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
					}
					else
					{
						HRQualificationLevel level=(HRQualificationLevel)hrDAO.getObject(HRQualificationLevel.class, new Long(parentDiv.getCode()));
						log.debug("lowerCategory.getDivisionLevel()>>>>>>>>>>>"+lowerCategory.getDivisionLevel());
						log.debug("level>>>>>>>>>>>>>>>>>"+level);
						if(lowerCategory.getDivisionLevel()!=null)
						{
							if(lowerCategory.getDivisionLevel().getLevelNo()<level.getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
						else
						{
							HRQualificationLevel levelForLower=(HRQualificationLevel)hrDAO.getObject(HRQualificationLevel.class, new Long(lowerCategory.getCode()));
							if(levelForLower.getLevelNo()<level.getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
					}
					
					createNodeForCopy(lowerCategory, lowerNode, className,parentDiv);// in recursion the lowerCategory will be higher for its childs
				}
			}
		}
		
		else if (className.equals("HRSpecialtyDivision")) {

			HRSpecialtyDivision specialtyDivision = (HRSpecialtyDivision) higherCategory;
			List childCategories = specialtyDivision.getChilds();
			//			sortAccounts(childCategories);
			if(childCategories!=null)
			{
				
				log.debug("childCategories.size()>>>>>>>>>>>"+childCategories.size());
				for (int m = 0; m <childCategories.size() - 1; m++) {
					HRSpecialtyDivision division=(HRSpecialtyDivision)childCategories.get(m);
					for (int n = 0; n < childCategories.size() - m - 1; n++) {
						HRSpecialtyDivision div=(HRSpecialtyDivision)childCategories.get(n);
						HRSpecialtyDivision div1=(HRSpecialtyDivision)childCategories.get(n+1);
						if (new Long(div1.getLongCode())< new Long(div.getLongCode())) {
							HRSpecialtyDivision tempDiv = (HRSpecialtyDivision)childCategories.get(n);
							childCategories.set(n, childCategories.get(n + 1));
							childCategories.set(n + 1, tempDiv);
						}
					}
				}
					
				Iterator itr1 = childCategories.iterator();
				
				HRSpecialtyDivision parentDiv=(HRSpecialtyDivision)parent;
				while (itr1.hasNext()) {
					log.debug("inside itrator for add child");
					HRSpecialtyDivision lowerCategory = (HRSpecialtyDivision) itr1.next();
					ITreeNode lowerNode = new TreeNode(lowerCategory.getId().toString(),lowerCategory.getArdesc());
					if(parentDiv.getDivisionLevel()!=null)
					{
						if(lowerCategory.getDivisionLevel()!=null)
						{
							if(lowerCategory.getDivisionLevel().getLevelNo()<parentDiv.getDivisionLevel().getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
						else
						{
							HRSpecialtyLevel levelForLower=(HRSpecialtyLevel)hrDAO.getObject(HRSpecialtyLevel.class, new Long(lowerCategory.getCode()));
							if(levelForLower.getLevelNo()<parentDiv.getDivisionLevel().getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
					}
					else
					{
						HRSpecialtyLevel level=(HRSpecialtyLevel)hrDAO.getObject(HRSpecialtyLevel.class, new Long(parentDiv.getCode()));
						log.debug("lowerCategory.getDivisionLevel()>>>>>>>>>>>"+lowerCategory.getDivisionLevel());
						log.debug("level>>>>>>>>>>>>>>>>>"+level);
						if(lowerCategory.getDivisionLevel()!=null)
						{
							if(lowerCategory.getDivisionLevel().getLevelNo()<level.getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
						else
						{
							HRSpecialtyLevel levelForLower=(HRSpecialtyLevel)hrDAO.getObject(HRSpecialtyLevel.class, new Long(lowerCategory.getCode()));
							if(levelForLower.getLevelNo()<level.getLevelNo())
							{
								treeNode.addChild(lowerNode);
							}
						}
					}
					
					createNodeForCopy(lowerCategory, lowerNode, className,parentDiv);// in recursion the lowerCategory will be higher for its childs
				}
			}
		}
	}

	public void deleteNodeFromTree(ITree tree, String deleteId) {
		log.debug("************************start deleteNodeFromTree()");
		ITreeNode deletedNode = tree.findNode(deleteId);
		log.debug("***************************parent "+ deletedNode.getParent().getId());
		tree.findNode(deletedNode.getParent().getId()).removeChild(deletedNode);
		log.debug("*******************deleteId " + deleteId);
		log.debug("************************end deleteNodeFromTree()");
	}

	
	public List getRoot(final String className)
	{
		return hrDAO.getRoot(className);
	}
	public List getAllLeafs(final Class clazz)
	{
		return hrDAO.getAllLeafs(clazz);
	}
	
	
	public List generateCodesList(List list,String className){
		List codesList = new ArrayList();
		Iterator itr=null;
		if(list!=null)
		{
		  itr = list.iterator();
		}
		log.debug("list.size\\\\\\\\\\\\\\\\......."+list);
		if(itr!=null)
		{
		while(itr.hasNext()){
			if(className.equals("HRInternalDivision"))
			{
				HRInternalDivision intDivision = (HRInternalDivision)itr.next();
				log.debug("intDivision.getCode()>>>>>>>>>>>>>>>"+intDivision.getCode());
				if(intDivision.getCode()!=null && !intDivision.getCode().equals(""))
				{
					Integer i = Integer.parseInt(intDivision.getCode());
					codesList.add(i);
				}
			}
			else if(className.equals("HRGeographicalDivision"))
			{
				HRGeographicalDivision intDivision = (HRGeographicalDivision)itr.next();
				if(intDivision.getCode()!=null && !intDivision.getCode().equals(""))
				{
					Integer i = Integer.parseInt(intDivision.getCode());
					codesList.add(i);
				}
			}
			else if(className.equals("HRQualificationDivision"))
			{
				HRQualificationDivision intDivision = (HRQualificationDivision)itr.next();
				if(intDivision.getCode()!=null && !intDivision.getCode().equals(""))
				{
					Integer i = Integer.parseInt(intDivision.getCode());
					codesList.add(i);
				}
			}
			
			else if(className.equals("HRSpecialtyDivision"))
			{
				HRSpecialtyDivision intDivision = (HRSpecialtyDivision)itr.next();
				if(intDivision.getCode()!=null && !intDivision.getCode().equals(""))
				{
					Integer i = Integer.parseInt(intDivision.getCode());
					codesList.add(i);
				}
			}
		}
		}
		return codesList;
	}
	
	
	public String zeroFill(Object[] codes, int codeLength) {
		Arrays.sort(codes);
		Integer nextCode;
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> codes.length "+codes.length);
		if (codes.length > 0){
			nextCode = (Integer) codes[codes.length - 1];
			nextCode++;
		}
		else{
			nextCode = new Integer(1);
		}
		String zeroFill = StringUtils.leftPad(nextCode.toString(), codeLength,
		"0");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.zeroFill" + zeroFill);
		return zeroFill;
	}
	
	public void addNode(String nodeId, String nodeArDesc,
			String parentId, ITree tree) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>Starting addNode ");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>nodeId "+nodeId);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>nodeArDesc "+nodeArDesc);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>parentId "+parentId);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>tree "+tree);
		ITreeNode resultNode = new TreeNode(nodeId, nodeArDesc);
		if (parentId != null && parentId.length() > 0) {
			log.debug(">>>>>>>>>>>>>>>>>>>>> inside if");
			ITreeNode parentNode = tree.findNode(parentId);
			parentNode.addChild(resultNode);
		} else {
			log.debug(">>>>>>>>>>>>>>>>>>>>> inside else");
			tree.getRoot().addChild(resultNode);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>Ending addNode ");
	}

	public void updateNodeDescription(String nodeId, String nodeArDesc,ITree tree) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>updateNodeDescription ");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>nodeId "+nodeId);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>nodeArDesc "+nodeArDesc);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>tree "+tree);

		ITreeNode node = tree.findNode(nodeId);
		node.setName(nodeArDesc);

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>updateNodeDescription");
	}
	
	// update the child Long code and save it.
	public void updateChildLongCode(String updatedParentCode,HRInternalDivision child)
	{
		String newCode = updatedParentCode;
		for (int i = updatedParentCode.length(); i< child.getLongCode().length() ; i++)
		{
			newCode += child.getLongCode().charAt(i);
		}
		log.debug("**updatedParentCode" + updatedParentCode +"||Child.getLongCode()"+child.getLongCode());
		log.debug("*******************newCode"+newCode);
		child.setLongCode(newCode);		
		saveObject(child);
	}
	
	public void updateChildLongCode(String updatedParentCode,HRGeographicalDivision child)
	{
		String newCode = updatedParentCode;
		for (int i = updatedParentCode.length(); i< child.getLongCode().length() ; i++)
		{
			newCode += child.getLongCode().charAt(i);
		}
		log.debug("**updatedParentCode" + updatedParentCode +"||Child.getLongCode()"+child.getLongCode());
		log.debug("*******************newCode"+newCode);
		child.setLongCode(newCode);		
		saveObject(child);
	}
	
	public void updateChildLongCode(String updatedParentCode,HRQualificationDivision child)
	{
		String newCode = updatedParentCode;
		for (int i = updatedParentCode.length(); i< child.getLongCode().length() ; i++)
		{
			newCode += child.getLongCode().charAt(i);
		}
		log.debug("**updatedParentCode" + updatedParentCode +"||Child.getLongCode()"+child.getLongCode());
		log.debug("*******************newCode"+newCode);
		child.setLongCode(newCode);		
		saveObject(child);
	}
	
	public void updateChildLongCode(String updatedParentCode,HRSpecialtyDivision child)
	{
		String newCode = updatedParentCode;
		for (int i = updatedParentCode.length(); i< child.getLongCode().length() ; i++)
		{
			newCode += child.getLongCode().charAt(i);
		}
		log.debug("**updatedParentCode" + updatedParentCode +"||Child.getLongCode()"+child.getLongCode());
		log.debug("*******************newCode"+newCode);
		child.setLongCode(newCode);		
		saveObject(child);
	}
	
	// change the CategoryAccountCode and change all childs long Code
	public void changeCode(HRInternalDivision changedInternalDivision,String newCode,String oldLongCode)
	{
		List children = hrDAO.getChildrenAndGrandChildrenByParentLongCode(oldLongCode,HRInternalDivision.class);

		
			Iterator itr = children.iterator();
			while (itr.hasNext())	// for every children & grandChilren & grandgrandChilren ...
			{
				HRInternalDivision childCategory =(HRInternalDivision) itr.next();

				if(childCategory.getChilds() != null)	// contains leafs.
				{
					Iterator leaf_itr = childCategory.getChilds().iterator();
					while(leaf_itr.hasNext())
					{
						HRInternalDivision intDivision = (HRInternalDivision)leaf_itr.next();
						updateChildLongCode(changedInternalDivision.getLongCode(),intDivision);
					}
				}
				if(childCategory.getId() != changedInternalDivision.getId())
					updateChildLongCode(changedInternalDivision.getLongCode(),childCategory);
				else log.debug("#######it was the changedinternalDivivsion########");
			}
		
	


	}
	
	public void changeCode(HRGeographicalDivision changedGeographicalDivision,String newCode,String oldLongCode)
	{
		List children = hrDAO.getChildrenAndGrandChildrenByParentLongCode(oldLongCode,HRGeographicalDivision.class);

		
			Iterator itr = children.iterator();
			while (itr.hasNext())	// for every children & grandChilren & grandgrandChilren ...
			{
				HRGeographicalDivision childCategory =(HRGeographicalDivision) itr.next();

				if(childCategory.getChilds() != null)	// contains leafs.
				{
					Iterator leaf_itr = childCategory.getChilds().iterator();
					while(leaf_itr.hasNext())
					{
						HRGeographicalDivision intDivision = (HRGeographicalDivision)leaf_itr.next();
						updateChildLongCode(changedGeographicalDivision.getLongCode(),intDivision);
					}
				}
				if(childCategory.getId() != changedGeographicalDivision.getId())
					updateChildLongCode(changedGeographicalDivision.getLongCode(),childCategory);
				else log.debug("#######it was the changedinternalDivivsion########");
			}
		
	


	}
	
	
	public void changeCode(HRQualificationDivision changedQualificationDivision,String newCode,String oldLongCode)
	{
		List children = hrDAO.getChildrenAndGrandChildrenByParentLongCode(oldLongCode,HRQualificationDivision.class);

		
			Iterator itr = children.iterator();
			while (itr.hasNext())	// for every children & grandChilren & grandgrandChilren ...
			{
				HRQualificationDivision childCategory =(HRQualificationDivision) itr.next();

				if(childCategory.getChilds() != null)	// contains leafs.
				{
					Iterator leaf_itr = childCategory.getChilds().iterator();
					while(leaf_itr.hasNext())
					{
						HRQualificationDivision intDivision = (HRQualificationDivision)leaf_itr.next();
						updateChildLongCode(changedQualificationDivision.getLongCode(),intDivision);
					}
				}
				if(childCategory.getId() != changedQualificationDivision.getId())
					updateChildLongCode(changedQualificationDivision.getLongCode(),childCategory);
				else log.debug("#######it was the changedinternalDivivsion########");
			}
		
	


	}
	
	public void changeCode(HRSpecialtyDivision changedSpecialtyDivision,String newCode,String oldLongCode)
	{
		List children = hrDAO.getChildrenAndGrandChildrenByParentLongCode(oldLongCode,HRSpecialtyDivision.class);

		
			Iterator itr = children.iterator();
			while (itr.hasNext())	// for every children & grandChilren & grandgrandChilren ...
			{
				HRSpecialtyDivision childCategory =(HRSpecialtyDivision) itr.next();

				if(childCategory.getChilds() != null)	// contains leafs.
				{
					Iterator leaf_itr = childCategory.getChilds().iterator();
					while(leaf_itr.hasNext())
					{
						HRSpecialtyDivision intDivision = (HRSpecialtyDivision)leaf_itr.next();
						updateChildLongCode(changedSpecialtyDivision.getLongCode(),intDivision);
					}
				}
				if(childCategory.getId() != changedSpecialtyDivision.getId())
					updateChildLongCode(changedSpecialtyDivision.getLongCode(),childCategory);
				else log.debug("#######it was the changedinternalDivivsion########");
			}
		
	


	}
	
	public Boolean deleteLevel(final Long id,final String className)
	{
		log.debug("inside deleteLevel>>>>>>>>>>>>>>>>>> ");
		if(className.equals("HRInternalLevel"))
		{
			log.debug("inside HRInterLevel>>>>>>>>>>>>>>>>>> ");
			HRInternalLevel lev=(HRInternalLevel)hrDAO.getObject(HRInternalLevel.class, id);
			List divList=hrDAO.getObjectsByParameter(HRInternalDivision.class, "divisionLevel",lev);
			if(divList==null || divList.isEmpty()|| divList.size()==0)
			{
				hrDAO.removeObject(lev);
				return true;
			}
			
		}
		
		else if(className.equals("HRGeographicalLevel"))
		{
			log.debug("inside HRGeoLevel>>>>>>>>>>>>>>>>>> ");
			HRGeographicalLevel lev=(HRGeographicalLevel)hrDAO.getObject(HRGeographicalLevel.class, id);
			log.debug("lev.getid:::::::::::"+lev.getId());
			List divList=hrDAO.getObjectsByParameter(HRGeographicalDivision.class, "divisionLevel",lev);
			if(divList==null || divList.isEmpty())
			{
				hrDAO.removeObject(lev);
				log.debug("lev:::::::::::"+lev);
				return true;
			}
			
		}
		else if(className.equals("HRQualificationLevel"))
		{
			log.debug("inside HRQualLevel>>>>>>>>>>>>>>>>>> ");
			HRQualificationLevel lev=(HRQualificationLevel)hrDAO.getObject(HRQualificationLevel.class, id);
			List divList=hrDAO.getObjectsByParameter(HRQualificationDivision.class, "divisionLevel",lev);
			if(divList==null || divList.isEmpty())
			{
				hrDAO.removeObject(lev);
				return true;
			}
			
			
		}
		else if(className.equals("HRSpecialtyLevel"))
		{
			log.debug("inside HRSpecialtyLevel>>>>>>>>>>>>>>>>>> ");
			HRSpecialtyLevel lev=(HRSpecialtyLevel)hrDAO.getObject(HRSpecialtyLevel.class, id);
			List divList=hrDAO.getObjectsByParameter(HRSpecialtyDivision.class, "divisionLevel",lev);
			if(divList==null || divList.isEmpty())
			{
				hrDAO.removeObject(lev);
				return true;
			}
		}
		
		return false;
	}
	
	
	public Boolean deleteYear(final Long id)
	{
		log.debug("inside deleteYear>>>>>>>>>>>>>>>>>> ");
		
			log.debug("inside HRInterLevel>>>>>>>>>>>>>>>>>> ");
			if(id!=null && !id.equals(""))
			{
			HRYearAllowVacationTransfer year=(HRYearAllowVacationTransfer)hrDAO.getObject(HRYearAllowVacationTransfer.class, id);
			
				hrDAO.removeObject(year);
			}
				return true;
			
		}
	
	public Boolean deleteVacationRules(final Long id)
	{
		log.debug("inside deletevacationRules>>>>>>>>>>>>>>>>>> ");	
		if(id!=null && !id.equals(""))
		{
		HRVacationRules vacationRules=(HRVacationRules)hrDAO.getObject(HRVacationRules.class, id);
		  hrDAO.removeObject(vacationRules);
		}
		return true;
	}
	
	public Boolean deleteViolationRules(final Long id)
	{
		log.debug("inside deleteviolationRules>>>>>>>>>>>>>>>>>> ");	
		if(id!=null && !id.equals(""))
		{
		HRViolationRules violationRules=(HRViolationRules)hrDAO.getObject(HRViolationRules.class, id);
		  hrDAO.removeObject(violationRules);
		}
		return true;
	}
	
	public List getChildrenAndGrandChildrenByParentLongCode(final String parentLongCode,final Class clazz)
	{
		return hrDAO.getChildrenAndGrandChildrenByParentLongCode(parentLongCode,clazz);
	}
	public HRInternalLevel getLastLevel()
	{
		return hrDAO.getLastLevel();
	}
	
	public HRInternalLevel getLevelByLevelNo(final Integer levelNo)
	{
		return hrDAO.getLevelByLevelNo(levelNo);
	}
	
	public List getCategoryAccountsByParentCategory(final Long parentId,final Class clazz)
	{
		return hrDAO.getChilderenByParent(parentId,clazz);
	}
	
	public List getParents(final Class clazz)
	{
		return hrDAO.getParents(clazz);
	}
	
	public HRQualificationLevel getQualLastLevel()
	{
		return hrDAO.getQualLastLevel();
	}
	
	public HRQualificationLevel getQualLevelByLevelNo(final Integer levelNo)
	{
		return hrDAO.getQualLevelByLevelNo(levelNo);
	}
	public HRGeographicalLevel getGeoLastLevel()
	{
		return hrDAO.getGeoLastLevel();
	}
	
	public HRGeographicalLevel getGeoLevelByLevelNo(final Integer levelNo)
	{
		return hrDAO.getGeoLevelByLevelNo(levelNo);
	}
	
	public List getAllByCode(final String code,final Class clazz)
	{
		return hrDAO.getAllByCode(code, clazz);
	}

	public List getLeafsByParentId(final Long parentId,final String className)
	{
		return hrDAO.getLeafsByParentId(parentId, className);
	}
	
	public HRSpecialtyLevel getSpecialtyLastLevel()
	{
		return hrDAO.getSpecialtyLastLevel();
	}
	public HRSpecialtyLevel getSpecialtyLevelByLevelNo(final Integer levelNo)
	{
		return hrDAO.getSpecialtyLevelByLevelNo(levelNo);
	}
	
	public List getLevelsByDivisionParentId(final Integer levelNo,final Class clazz)
	{
		return hrDAO.getLevelsByDivisionParentId(levelNo,clazz);
	}
	
	public List getExistingDivisionsForparent(final Class clazz,final String parentCode){
	   
		return hrDAO.getExistingDivisionsForparent( clazz, parentCode);
	}
	
	public List getHigherDivisions(final Class clazz,final Long id,final Integer levelNo)
	{
		return hrDAO.getHigherDivisions(clazz, id,levelNo);
	}
	
	public List getCountriesForNationality()
	{
		return hrDAO.getCountriesForNationality();
	}
	
	public List getDivisionChildren(final Class clazz,final String longCode)
	{
		return hrDAO.getDivisionChildren(clazz, longCode);
	}
	
	public List getExistingDivisionsForparent(final Class clazz,final Long id,final String code)
	{
		return hrDAO.getExistingDivisionsForparent(clazz, id, code);
	}
	public List getExistingDivisionsForParentForCopy(final Class clazz,final Long parentId,final String code)
	{  
		return hrDAO.getExistingDivisionsForParentForCopy(clazz, parentId, code);
	}
	
	public HRQualificationDivision getQualificationDivisionForTransaction()
	{
		return hrDAO.getQualificationDivisionForTransaction();
	}
	
	public HRSpecialtyDivision getSpecialtyDivisionForTransaction()
	{
		return hrDAO.getSpecialtyDivisionForTransaction();
	}
	
	public HRInternalDivision getInternalDivisionForTransaction()
	{
		return hrDAO.getInternalDivisionForTransaction();
	}
	
	public List getExistingDivisionsForNullDivLevelParent(final Class clazz,final Long id,final String code){
		return hrDAO.getExistingDivisionsForNullDivLevelParent(clazz, id, code);
	}
	
	public HRVacation getVacation(Long id)
	{
		HRVacation vacation=(HRVacation)hrDAO.getObject(HRVacation.class, id);
		return vacation;
	}
	
	public List getEmployeesForEmployeeVacationAtInstallation(final String empCode,final String empName,final HRInternalDivision division)
	{
		return hrDAO.getEmployeesForEmployeeVacationAtInstallation(empCode, empName, division);
	}
	
	public List getEmployeeVacationAtInstall(final HREmployee employee,final HRVacation vacation)
	{
		return hrDAO.getEmployeeVacationAtInstall(employee, vacation);
	}
	
	public List getEmployeesForEmployeeServiceLength(final String empCode,final String empName)
	{
		return hrDAO.getEmployeesForEmployeeServiceLength(empCode, empName);
	}
	public List getEmployeeServiceLength(final HREmployee employee)
	{
		return hrDAO.getEmployeeServiceLength(employee);
	}
	
	public Map getDateContents(Date serviceDate)
	{
		 Calendar cal=Calendar.getInstance();
		 if(serviceDate!=null && !serviceDate.equals(""))
		 {
         cal.setTime(serviceDate);
         cal.add(Calendar.HOUR_OF_DAY,0);
         cal.add(Calendar.MINUTE, 0);
         cal.add(Calendar.SECOND, 0);
         cal.add(Calendar.MILLISECOND,0);
		 }
        
        Calendar cal2=Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.add(Calendar.HOUR_OF_DAY,0);
        cal2.add(Calendar.MINUTE, 0);
        cal2.add(Calendar.SECOND, 0);
        cal2.add(Calendar.MILLISECOND,0);
  
     Integer  yearDiff=cal2.get(Calendar.YEAR)-cal.get(Calendar.YEAR);
     log.debug("yearDiff>>>>>>>"+yearDiff);
     Integer dayDiff=cal2.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_MONTH);
     log.debug("dayDiff>>>>>>>>>"+dayDiff);
     Integer monthDiff=(cal2.get(Calendar.MONTH))-(cal.get(Calendar.MONTH)); 
     int sign=monthDiff.signum(monthDiff);
     if(sign==-1)
     {
    	Integer month=(yearDiff*12)+monthDiff ;
    	log.debug("yearDiff>>>>>>>>>>>>>"+yearDiff);
    	yearDiff=0;
    	while(month>12)
    	{
    		log.debug("month>>>>>>"+month);
    		
    		yearDiff=yearDiff+1;
    		month=month-12;
    		log.debug("month>>>>>>>>>>after"+month);
    	}


    		monthDiff=month;
    	
     }
     
    /* else
     {
    	 Integer month=(yearDiff*12)-monthDiff ;
    	 log.debug("yearDiff>>>>>>>>>>>>>"+yearDiff);
    	 yearDiff=0;
    		while(month>12)
        	{
        		yearDiff=yearDiff+1;
        		month=month-12;
        	}
        	
        		monthDiff=month;
        	
     }*/
     log.debug("(cal2.get(Calendar.MONTH))>>>>>>>>"+(cal2.get(Calendar.MONTH)));
     log.debug("(cal.get(Calendar.MONTH))>>>>>>>>"+(cal.get(Calendar.MONTH)));
     log.debug("monthDiff>>>>"+monthDiff);  
     
     Map model=new HashMap();
     model.put("year", yearDiff);
     model.put("month", monthDiff);
     model.put("day",dayDiff );
     
     return model;
	}
	
	public Date getDateFromContents(Integer year,Integer month,Integer day)
	{
	     Calendar cal2=Calendar.getInstance();
	        cal2.setTime(new Date());
	        cal2.add(Calendar.HOUR_OF_DAY,0);
	        cal2.add(Calendar.MINUTE, 0);
	        cal2.add(Calendar.SECOND, 0);
	        cal2.add(Calendar.MILLISECOND,0);
	        cal2.add(Calendar.YEAR, (-year));
	        cal2.add(Calendar.DAY_OF_YEAR, (-day));
	        cal2.add(Calendar.MONTH, (-month));
	        Integer  yearDiff=cal2.get(Calendar.YEAR)-year;
	        log.debug("yearDiff>>>>>>>"+yearDiff);
	        Integer dayDiff=cal2.get(Calendar.DAY_OF_YEAR)-day;
	        log.debug("dayDiff>>>>>>>>>"+dayDiff);
	        Integer monthDiff=(cal2.get(Calendar.MONTH))-month;
	        log.debug("cal2.get(Calendar.MONTH)>>>>>>>>"+cal2.get(Calendar.MONTH));
	        log.debug("cal2.getTime()"+cal2.getTime());
	        
	      
		        return cal2.getTime();
	       
	}
	
	public List getEmployeesForEmployeeVacation(final String empCode,final String empName,final HRInternalDivision division)
	{
		return hrDAO.getEmployeesForEmployeeVacation(empCode, empName, division);
	}
	public List getEmployeeVacation(final HREmployee employee,final HRVacation vacation)
	{
		return hrDAO.getEmployeeVacation(employee, vacation);
	}
	public void changeFlag()
	{
		log.debug("inside changeFlag()");
		hrDAO.changeFlag();
	}
	
	public List getEmployeesByCodeAndName(final String codeFrom,final String codeTo,final String empName)
	{
		return  hrDAO.getEmployeesByCodeAndName(codeFrom, codeTo, empName);
	}
	
	public List getFilteredGroupedAInsuranceCala(final String month, final String year, final String insurance, final String emp_code, final String empName, final String insuranceNo, final String subscriptionDate, final String groupBy){
		return hrDAO.getFilteredGroupedAInsuranceCala(month, year, insurance, emp_code, empName, insuranceNo, subscriptionDate, groupBy);
	}

}
