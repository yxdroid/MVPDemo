package com.yxfang.mvpdemo.model;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 15:34
 * ------------- Description -------------
 * ---------------------------------------
 */
public interface IBaseModel
{
    // 服务器地址
    String SERVER_ADDRESS = "http://192.168.31.243:8080";

    /*--------------------------user api sets start--------------------------------*/

    // 用户登录
    String USER_LOGIN = "/api/user/login";

    // 获取用户列表
    String GET_USER_LIST = "/api/user/getUserList";

    /*--------------------------user api sets end--------------------------------*/
}
