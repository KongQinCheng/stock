package com.stock.dao;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;

import java.util.List;

public interface IStockNewDataDao {

    /***
     * 新增数据初入到数据库
     * @param stockInfo
     */
     void insert(StockInfo stockInfo);

    /***
     * 清空数据库表中的数据
     */
     void deleteAll();


    /***
     * 清空数据库表中的数据
     */
    void deleteByStockCode(String stockCode);

    /**
     * 获取stock_new_data数据列表
     * @param stockNewDataVo
     * @return
     */
     List<StockNewData>  getStockNewDataListByVo(StockNewDataVo stockNewDataVo);


    /***
     * 新增数据初入到数据库
     * @param stockInfo
     */
    public List<StockNewData> getStockListByStockCode(String stockCode, int limitNum);

}