package com.zl.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    public static final ResultObj LOGIN_SUCCESS=new ResultObj(Constant.OK,"登录成功");
    public static final ResultObj LOGIN_FAIL_PASS=new ResultObj(Constant.ERROR,"登录失败 用户名或密码不正确");
    public static final ResultObj LOGIN_FAIL_CODE=new ResultObj(Constant.ERROR,"登录失败，验证码不正确");

    public static final ResultObj UPDATE_SUCCESS=new ResultObj(Constant.OK,"更新成功");
    public static final ResultObj UPDATE_ERROR=new ResultObj(Constant.ERROR,"更新失败");

    public static final ResultObj ADD_SUCCESS=new ResultObj(Constant.OK,"添加成功");
    public static final ResultObj ADD_ERROR=new ResultObj(Constant.ERROR,"添加失败");

    public static final ResultObj DELETE_SUCCESS=new ResultObj(Constant.OK,"删除成功");
    public static final ResultObj DELETE_ERROR=new ResultObj(Constant.ERROR,"删除失败");

    public static final ResultObj RESET_SUCCESS=new ResultObj(Constant.OK,"重置成功");
    public static final ResultObj RESET_ERROR=new ResultObj(Constant.ERROR,"重置失败");

    public static final ResultObj DISPATCH_SUCCESS=new ResultObj(Constant.OK,"分配成功");
    public static final ResultObj DISPATCH_ERROR=new ResultObj(Constant.ERROR,"分配失败");

    private Integer code;
    private String message;
}
