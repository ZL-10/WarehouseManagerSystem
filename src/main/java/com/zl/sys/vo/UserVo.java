package com.zl.sys.vo;

import com.zl.sys.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserVo extends User {

    private static final long serialVersionUID = -7714608731536352353L;
    private Integer page = 1;
    private Integer limit = 10;
}
