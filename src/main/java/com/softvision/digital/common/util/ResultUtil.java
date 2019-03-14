package com.softvision.digital.common.util;

import com.softvision.digital.common.model.ResultDto;
import org.apache.commons.lang.exception.ExceptionUtils;


public class ResultUtil {

    private ResultUtil() {}

    public static ResultDto getSuccess(String message) {
        return getSuccess(message, null);
    }

    public static ResultDto getSuccess() {
        return getSuccess(null, null);
    }

    public static ResultDto getSuccess(Object content) {
        return getSuccess(null, content);
    }

    public static ResultDto getSuccess(String message, Object content) {
        ResultDto result = new ResultDto();
        result.setCode(ResultDto.SUCCESS);
        result.setMessage(message);
        result.setContent(content);
        return result;
    }

    public static ResultDto getFailure(String message) {
        return getFailure(message, null);
    }

    public static ResultDto getFailure(Exception exception) {
        return getFailure(exception.getMessage(), exception);
    }

    public static ResultDto getFailure(String message, Exception exception) {
        ResultDto result = new ResultDto();
        result.setCode(ResultDto.FAILURE);
        result.setMessage(message);
        result.setContent(ExceptionUtils.getFullStackTrace(exception));
        return result;
    }

}
