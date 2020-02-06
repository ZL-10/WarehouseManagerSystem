package com.zl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.Notice;
import com.zl.sys.vo.NoticeVo;

public interface NoticeService extends IService<Notice> {
    //获取所有公告
    DataGridView loadAllNotice(NoticeVo noticeVo);
}
