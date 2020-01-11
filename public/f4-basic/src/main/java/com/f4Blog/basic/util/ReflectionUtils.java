package com.f4Blog.basic.util;

import com.baomidou.mybatisplus.annotation.TableName;
import com.f4Blog.basic.exception.ErrorException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射操作的相关工具类
 * @author authstr
 *
 */
public class ReflectionUtils {

	/**
	 * 获取一个实体对象所映射的表名,该对象需要使用了@TableName注解
	 * @param obj 要获取的对象
	 * @return 返回映射的表名,如果无法获取,返回null
	 * @time 2018年9月17日14:58:52
	 * @author authstr
	 */
	public static String getEntityTableName(Object obj){
		return getEntityTableName(obj.getClass());
	}
	/**
	 * 获取一个Class对象所映射的表名,该Class需要使用了@TableName注解
	 * @param clazz 要获取的类型
	 * @return 返回映射的表名,如果无法获取,返回null
	 * @time 2018年9月17日14:59:54
	 * @author authstr
	 */
	public static String getEntityTableName(Class clazz){
		try {
			return ((TableName)clazz.getAnnotation(TableName.class)).value();
		} catch (Exception e) {
			return null;
		}
	}
	


	/**
	 * 获取指定对象的所有属性名称和属性值,包括为从父类继承来的属性.可以设置为使用属性的get方法获取值
	 * @param obj 要获取的对象
	 * @param byMethod 是否使用属性的get方法获取属性值
	 * @return
	 */
	public static Map<String,Object>  getAllParentFieldAndValue(Object obj,Boolean byMethod){
		Map<String,Object> res=new HashMap<String, Object>();
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					Object value=null;
					if(byMethod){
						value=executeGetMethod(obj,name,true);
					}else{
						value=propertyUtilsBean.getNestedProperty(obj, name);
					}
					res.put(name, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 获取指定对象的所有属性名称和属性值.可以设置为使用属性的get方法获取值
	 * @param obj  要获取的对象
	 * @param byMethod 是否使用属性的get方法获取属性值
	 * @return
	 */
	public static Map<String,Object>  getAllFieldAndValue(Object obj,Boolean byMethod){
		Map<String,Object> res=new HashMap<String, Object>();
			for (Field field : obj.getClass().getDeclaredFields()) {
				Object value=null;
				String name=field.getName();
				if(byMethod){
					value=executeGetMethod(obj,name,true);
				}else{
					try {
						field.setAccessible(true);
						value= field.get(obj);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new ErrorException("无法执行["+name+"]的字段的取值");
					}
				}
				res.put(name, value);
			}
		return res;
	}

	/**
	 *  <p> 通过get方法获取对象的所有 属性值不为null的  属性和属性值 </p>
	 * <p><b> 注意!</b> </p>
	 * <p> 对于基本数据类型,int,long,float,double,short如果属性值为0,视为值不存在,不会返回 </p>
	 *  <p>short,char,boolean会返回实际的值         </p>
	 * @param obj 要操作的对象
	 * @return Map,key为属性名称,value为属性的值
	 * @time 2018年9月17日15:59:46
	 * @author authstr
	 */
	public static Map<String,Object>  getFieldAndValue(Object obj){
		Class<? extends Object> clazz=obj.getClass();
		Field[] field= clazz.getDeclaredFields();//获取所有属性
		Map<String,Object> res=new HashMap<String, Object>();
		for(int i=0;i<field.length;i++){
			Object getResult=executeGetMethod(obj,field[i].getName());//获取该属性的get方法执行结果
			if(getResult instanceof Integer&&(int)getResult==0)getResult=null;//如果该结果是int,且值0,说明这个属性的值是初始值,实际没有赋值的,置为null
			if(getResult instanceof Long&&(long)getResult==0L)getResult=null;//类同上
			if(getResult instanceof Float&&(float)getResult==0f)getResult=null;//类同上
			if(getResult instanceof Double&&(double)getResult==0d)getResult=null;//类同上
			if(getResult instanceof Short&&(short)getResult==(short)0)getResult=null;//类同上
			if(getResult!=null)res.put(field[i].getName(), getResult);//如果通过这个属性拿到了结果,保存
		}
		return res;
	}




	
	/**
	 * 方法是否存在(仅判断公共方法)
	 * @param clazz 要判断的类
	 * @param methodName 要判断的方法名称
	 * @return true 存在 false 不存在
	 * @time 2018年10月8日14:03:21
	 * @author authstr
	 */
	public static boolean isMethodExist(Class clazz,String methodName){
		  Method[] method = clazz.getDeclaredMethods();//获取所有方法
		  for(int i=0;i<method.length;i++){
			  if(methodName.equals(method[i].getName())){
			  	  return true;//名字一样,返回true;
			  }
          }
		  return false;
	}	
	
	/**
	 * 属性是否存在
	 * @param clazz 要判断的类
	 * @param fieldName 要判断的字段名称
	 * @return true 存在
	 * @time 2018年10月8日14:12:45
	 * @author authstr
	 */
	public static boolean isFieldExist(Class clazz,String fieldName){
		//获取所有属性
		Field[] field= clazz.getDeclaredFields();
		for(int i=0;i<field.length;i++){
			//名字一样,返回true;
			if(fieldName.equals(field[i].getName())){
				return true;
			}
        }
		return false;
	}
	
    /**
     * 获取对象里指定属性的值,获取失败返回null
     * @param target 要获取的对象
     * @param name	属性名称
     * @return
     * @time 2018年11月1日 上午10:30:47
     * @author authstr
     */
    public static Object getProperty(Object target, String name) {
        try {
            return PropertyUtils.getProperty(target, name);
        }
        catch (Exception e) {
            return null;
        }
    }
	
	/**
	 * 调用对象中一个属性的get方法
	 * @param obj 要操作的对象
	 * @param fieldName 属性名称
	 * @return 该get方法的执行结果
	 * @time 2018年9月17日15:27:43
	 * @author authstr
	 */
	public static Object executeGetMethod(Object obj,String fieldName){
		return executeGetMethod(obj,fieldName,false);
	}

	/**
	 * 调用对象中一个属性的get方法
	 * @param obj  要操作的对象
	 * @param fieldName 属性名称
	 * @param isException 获取失败,是否抛出异常
	 * @return 该get方法的执行结果
	 * @time 2019年12月12日11:57:17
	 * @author authstr
	 */
	public static Object executeGetMethod(Object obj,String fieldName,Boolean isException){
		// 构造get方法名称
		Object value=null;
		String methodName ="get"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		try {
			Method m = obj.getClass().getMethod(methodName);
			//执行
			value=m.invoke(obj);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			if(isException){
				throw new ErrorException("未发现字段["+fieldName+"]的get方法");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(isException){
				throw new ErrorException("无法执行["+fieldName+"]的get方法");
			}
		}
		return value;
	}
	
	/**
	 * 调用对象中一个属性的set方法
	 * @param obj 要操作的对象
	 * @param fieldName 属性名称
	 * @param value 要set的值
	 * @throws Exception  异常信息
	 * @time 2018年10月8日14:27:27
	 * @author authstr
	 */
	public static <T> void executeSetMethod(Object obj,String fieldName,T value) throws Exception {
		//将属性的首字符大写，方便构造get，set方法
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method m=null;
    	 try {
    	 	//获取set方法
			m = obj.getClass().getMethod("set" + fieldName,value.getClass());
		    m.invoke(obj,value);//执行
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new Exception("未发现字段["+fieldName+"]的set方法");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("无法执行["+fieldName+"]的set方法");
		}
	}
	
}
