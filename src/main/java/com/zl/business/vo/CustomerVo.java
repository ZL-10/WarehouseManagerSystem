package com.zl.business.vo;

import com.zl.business.domain.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerVo extends Customer {

    private static final long serialVersionUID = -2689188073180215252L;

    private Integer page=1;
    private Integer limit=10;

    private Integer[] ids;
}
