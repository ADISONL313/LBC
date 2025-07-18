package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.Help;
import com.cqcskj.lbc.rs.ResultDeal;

public interface HelpService extends IService<Help> {
    ResultDeal addHelpService(Help help);

    ResultDeal getHelpService(Help help);

    ResultDeal cancelHelpService(String helpId);

    ResultDeal updateHelpService(Help help);

    ResultDeal finishHelpService(String helpId);
}
