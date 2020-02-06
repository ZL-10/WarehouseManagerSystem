package com.zl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.LogInfo;
import com.zl.sys.vo.LogInfoVo;
import org.springframework.stereotype.Service;

@Service("logInfoService")
public interface LogInfoService  extends IService<LogInfo> {
    //查询全部登录日志
    DataGridView loadAllLoginInfo(LogInfoVo logInfoVo);
}
