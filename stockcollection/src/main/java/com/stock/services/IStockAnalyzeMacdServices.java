package com.stock.services;


import java.util.Map;

public interface IStockAnalyzeMacdServices {


    /***
     * 初始化各个股票出现金叉之后对后续的影响
     * @param stockCode
     * @param type  预留给之后的 涨幅和跌幅
     */
    void crossEffectInit(String stockCode, String type);


    /***
     * 统计所有数据的 出现金叉之后对后续的影响
     * @param type 预留给之后的 涨幅和跌幅
     */
    void crossEffectAllInit(String type);

    /***
     * 统计所有数据的 出现金叉之后对后续的影响 --新的统计方法
     * @param type 预留给之后的 涨幅和跌幅
     */
    void crossEffectInitNew(String stockCode, String type);

    /***
     * 统计所有数据的 出现金叉之后对后续的影响
     * @param stockCode
     * @return
     */
    Map<String, Object> getCrossEffect(String stockCode);

    /***
     * 统计所有数据的 出现金叉之后对后续的影响
     * @param stockCode
     * @return
     */
    Map<String, Object> getCrossEffectNew(String stockCode,int effectType);

    /***
     * 统计所有数据的 出现金叉之后对后续的影响
     * @param stockCode
     * @return
     */
    Map<String, Object> getStockCrossEffectNewFinal(String stockCode,String crossType, String searchType);

    /***
     * 统计所有数据的 出现金叉之后对后续的影响
     * 统计所有数据的 出现金叉之后最高价涨幅
     * @param stockCode
     * @return
     */
     void crossEffectInitNewFinal(String stockCode);

    /***
     * 统计所有数据的 出现金叉之后对后续的影响
     * 统计所有数据的 出现金叉之后最高价涨幅
     * @param stockCode
     * @return
     */
    void crossEffectInitNewFinalMaxValue(String stockCode);




    /***
     * 统计所有数据的 出现金叉之后对 MACD值 对后续的影响
     * @param stockCode
     * @return
     */
    String crossEffectMacdValueInit(String stockCode);

    /***
     * 统计所有数据的 出现金叉之后对后续的影响
     * @param type 预留给之后的 涨幅和跌幅
     */
    void crossEffectMacdValueAllInit(String type);


    /***
     *
     * 统计所有数据的 出现金叉之后对后续的影响
     * @param stockCode
     * @return
     */
    String getCrossEffectMacdValue(String stockCode);


}
