package com.zl.sys.vo;

import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {


    private static final long serialVersionUID = 8437937940060078456L;
    private Integer page = 1;
    private Integer limit = 10;


}
