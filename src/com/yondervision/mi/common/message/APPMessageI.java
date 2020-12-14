package com.yondervision.mi.common.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface APPMessageI {
    public String send(HttpServletRequest request,HttpServletResponse response)throws Exception;
}
