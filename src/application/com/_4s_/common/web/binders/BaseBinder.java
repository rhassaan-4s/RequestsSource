package com._4s_.common.web.binders;

import java.beans.PropertyEditor;

public interface BaseBinder extends PropertyEditor{

	public abstract Class getBindedClass();

}