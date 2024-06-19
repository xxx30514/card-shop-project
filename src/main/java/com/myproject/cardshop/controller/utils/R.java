package com.myproject.cardshop.controller.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {

    /**
     * 成功
     */
    public static final String SUCCESS_CODE = "200";
    /**
     * 失敗
     */
    public static final String FAIL_CODE = "400";
    /**
     * 未登入
     */
    public static final String USER_NO_LOGIN = "401";


    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long   total;


    /**
     * 成功
     * @param msg
     * @param data
     * @return
     */
    public static R success(String msg,Object data,Long total){

        return new R(SUCCESS_CODE,msg,data,total);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static R success(String msg,Object data){

        return success(msg,data,null);
    }

    /**
     * 成功
     * @return
     */
    public static R success(String msg){

        return success(msg,null);
    }


    /**
     * 成功
     * @return
     */
    public static R success(Object data){

        return success(null,data);
    }



    /**
     * 失敗
     * @param msg
     * @param data
     * @return
     */
    public static R fail(String msg,Object data,Long total){

        return new R(FAIL_CODE,msg,data,total);
    }

    /**
     * 失敗
     * @param data
     * @return
     */
    public static R fail(String msg,Object data){

        return fail(msg,data,null);
    }

    /**
     * 失敗
     * @return
     */
    public static R fail(String msg){

        return fail(msg,null);
    }


    /**
     * 失敗
     * @return
     */
    public static R fail(Object data){

        return fail(null,data);
    }


    /**
     * 未登入
     * @return
     */
    public static R noLogin(){

        return fail(USER_NO_LOGIN,"使用者尚未登入!");
    }






}
