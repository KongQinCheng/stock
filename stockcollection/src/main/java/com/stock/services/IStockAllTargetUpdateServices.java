package com.stock.services;

import com.stock.bean.po.AnalyzeIncreaseDay;

import java.util.List;
import java.util.Map;

/***
 * 更新股票的所有信息
 */

public interface IStockAllTargetUpdateServices {

    void allTargetUpdate(String stockCode) throws Exception;

}
