package com.f4blog.model.utils;


import com.f4Blog.basic.exception.Assert;
import com.f4blog.model.base.BaseModel;
import org.springframework.util.StringUtils;

public class ModelUtil {
	 public static boolean isNew(BaseModel model) {
	        Assert.isTrue(model != null, "model 是空的 ", true);
	        Object id = model.getId();
	        if (model.getId() == null) {
	            return true;
	        }
			 //如果id的类型的int或者String
	        if(id instanceof Integer ){
	        	if(model.getId() != null ){
	        		return false;
	        	}
	        	return true;
	        }
	        if(id instanceof String ){
	        	if (model.getId() != null && !StringUtils.hasText(id.toString())) {
	                return false;
	            }
	            return true;
	        }
	        return false;
	    }
}
