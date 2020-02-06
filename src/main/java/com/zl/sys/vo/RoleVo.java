package com.zl.sys.vo;

import com.zl.sys.domain.Notice;
import com.zl.sys.domain.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends Role {


    private static final long serialVersionUID = 1744101506695117262L;
    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;//接收多个ID


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
