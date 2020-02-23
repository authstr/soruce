package com.f4Blog.basic.cloud;

import cn.hutool.core.bean.BeanUtil;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.reqres.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * RestTemplate工具类
 * @author austhstr
 * @date 2020年2月19日14:32:13
 */
@Component
public class RestTemplateUtils {
    @Autowired
    RestTemplate restTemplate;

    public static MultiValueMap<String,Object> beanToMultiValueMap(Object o){
        MultiValueMap<String, Object> para=new LinkedMultiValueMap<> ();
        para.setAll(BeanUtil.beanToMap(o));
        return para;
    }
    public static   HttpEntity  getRequestHttpEntity(Object o){
        return new HttpEntity(beanToMultiValueMap(o),WebUtils.getHttpHeaders());
    }

    //设置Http的Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//
//        //设置访问参数
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("name", name);

    //        WebUtils.getRequest().getHeaderNames();
//        HttpHeaders headers = new HttpHeaders();
//        //设置访问的Entity
//        HttpEntity entity = new HttpEntity<>();
//    ResponseEntity<ResponseData> result = null;
//    //        map.put("username",new ArrayList().add("dd"));
//    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("username","ddd");
//        map.add("password","password");
//    HttpHeaders header =WebUtils.getHttpHeaders();
    // 需求需要传参为form-data格式
//        header.setContentType(MediaType.MULTIPART_FORM_DATA);

//    public  HttpEntity<MultiValueMap<String, String>>  getRequestHttpEntity(HttpServletRequest request,MultiValueMap<String,String> headers, MultiValueMap<String, String> para){
//        return null;
//    }

//    /**
//     * 在一个request的基础上执行http请求 (主方法)
//     * @param url 请求url
//     * @param request 基于的request
//     * @param method 请求方式
//     * @param increase_headers 在request增加的请求头信息
//     * @param increase_para 在request增加的请求参数信息
//     * @param uriVariables 路径参数,支持PathVariable类型的数据
//     * @return
//     */
////    requestExchange
//    public ResponseData  requestExchange(String url, HttpServletRequest request, HttpMethod method, MultiValueMap<String,String> increase_headers, MultiValueMap<String, String> increase_para, Map<String, String> uriVariables){
//        //获取request的请求头和参数
//        HttpHeaders headers= WebUtils.getHttpHeaders(request);
//        MultiValueMap<String, String> para=new LinkedMultiValueMap<> ();
//        para.setAll(WebUtils.getRequestParameters(request));
//        //增加参数
//        headers.addAll(increase_headers);
//        para.addAll(increase_para);
//
//        //封装HttpEntity
//        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(para, headers);
//
//        //获取请求方式
//        HttpMethod requestMethod=HttpMethod.resolve(request.getMethod());
//        if(method!=null){
//            requestMethod=method;
//        }
//        //请求
//        ResponseEntity<ResponseData> result = restTemplate.exchange(url,requestMethod,httpEntity, ResponseData.class);
//        return result.getBody();
//    }

}
