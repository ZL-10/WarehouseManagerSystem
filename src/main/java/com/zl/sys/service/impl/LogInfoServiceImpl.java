package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.domain.LogInfo;
import com.zl.sys.mapper.LogInfoMapper;
import com.zl.sys.service.LogInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("logInfoService")
@Transactional
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {
}
