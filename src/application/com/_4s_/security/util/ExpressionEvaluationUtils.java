package com._4s_.security.util;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

public class ExpressionEvaluationUtils {


    public static Object evaluate(String exp, Class<?> resultClass, PageContext pageContext)
        throws JspException {

        if (pageContext == null){
            return exp;
        }
        ELContext elContext =  pageContext.getELContext();
        JspFactory jf = JspFactory.getDefaultFactory();
        JspApplicationContext jac = jf.getJspApplicationContext(pageContext.getServletContext());
        ExpressionFactory ef = jac.getExpressionFactory();
        ValueExpression val = ef.createValueExpression(elContext, exp, resultClass);
        return val.getValue(elContext);
    }

}