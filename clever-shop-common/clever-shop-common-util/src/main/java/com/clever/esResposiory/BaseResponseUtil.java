package com.clever.esResposiory;

import com.clever.Constants.BaseResponse;
import com.clever.Constants.Constants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by clever on 2019/9/1.
 */
@Slf4j
@Data
public class BaseResponseUtil<T> {
    public static<T> BaseResponse<T> error(Integer code, String msg) {
        return setResult(code, msg, null);
    }

    // 返回错误，可以传msg
    public static<T> BaseResponse<T> error(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null);
    }

    // 返回成功，可以传data值
    public static<T> BaseResponse<T> suc(T data) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    // 返回成功，沒有data值
    public static<T> BaseResponse<T> suc() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    // 返回成功，沒有data值
    public static<T> BaseResponse<T> suc(String msg) {
        return setResult(Constants.HTTP_RES_CODE_200, msg, null);
    }

    // 通用封装
    public static<T> BaseResponse<T> setResult(Integer code, String msg, T data) {
        return new BaseResponse<T>(code, msg, data);
    }

}
