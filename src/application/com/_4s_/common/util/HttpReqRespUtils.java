package com._4s_.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpReqRespUtils {

    private static final String[] IP_HEADER_CANDIDATES = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR",
        "X-Forwarded-Host", 
        "X-Forwarded-Port",
        "X-Forwarded-Proto", 
        "X-Forwarded-Ssl", 
        "X-Forwarded-Prefix"
    };

    public static String getClientIpAddressIfServletRequestExist(HttpServletRequest request) {

        if (RequestContextHolder.getRequestAttributes() == null) {
        	System.out.println("RequestContextHolder.getRequestAttributes() " + RequestContextHolder.getRequestAttributes());
        	System.out.println("null request attributes");
            return "0.0.0.0";
        }

//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println("request.getRemoteAddr() " + request.getRemoteAddr());
        String ipList = "";
        for (String header: IP_HEADER_CANDIDATES) {
//        	System.out.println("header " + header);
            ipList = request.getHeader(header);
//            System.out.println("iplist " + ipList);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return ip;
            }
        }
        
        return request.getRemoteAddr();
    }
}