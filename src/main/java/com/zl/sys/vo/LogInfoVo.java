package com.zl.sys.vo;

import com.zl.sys.domain.LogInfo;
import com.zl.sys.domain.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class LogInfoVo extends LogInfo {


    private static final long serialVersionUID = 9161644967983110759L;
    private Integer page=1;
    private Integer limit=10;

    private Integer[] ids;//接收多个ID

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
