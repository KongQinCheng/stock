package com.stock.controller.collection;

import com.alibaba.fastjson.JSON;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockIncreaseAnalyze;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockIncreaseAnalyzeServices;
import com.stock.services.IStockInfoServices;
import com.stock.services.IStockListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class StockIncreaseEffectCollection {

    @Autowired
    IStockListDao iStockListDao;

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockInfoServices iStockInfoServices;


    @Autowired
    IStockNewDataDao IStockNewDataDao;

    @Autowired
    IStockListServices iStockListServices;

    @Autowired
    IStockIncreaseAnalyzeServices iStockIncreaseAnalyzeServices;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /***
     * 根据数据库中保存的 股票编号 获取股票的历史信息
     * @throws Exception
     */
    public void getStockIncreaseEffectThread() throws Exception {

        List<StockList> stockList = iStockListServices.getStockList();

        double threadCount = 100.0; //使用 20个线程处理

        ExecutorService executor = Executors.newFixedThreadPool((int) threadCount);
        int listSize = stockList.size();
        //将总数分成 多个线程之后，每个线程需要处理的数据为： listSize/threadCount

        double divNumd = Math.ceil(listSize / threadCount);
        int divNum = (int) divNumd;

        if (listSize > 0) {
            int batch = listSize % divNum == 0 ? listSize / divNum : listSize / divNum + 1;
            for (int j = 0; j < batch; j++) {
                int end = (j + 1) * divNum;
                if (end > listSize) {
                    end = listSize;
                }
                List<StockList> subList = stockList.subList(j * divNum, end);
                ThreadRunnable threadRunnable = new ThreadRunnable(subList);
                executor.execute(threadRunnable);
            }
        }


    }

    /***
     *  开启多线程进行数据处理
     */
    class ThreadRunnable implements Runnable {
        private List<StockList> listInput;

        public ThreadRunnable(List<StockList> temp) {
            this.listInput = temp;
        }

        @Override
        public void run() {
            for (int i = 0; i < listInput.size(); i++) {
                try {
                    getStockIncreaseEffectInit(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    //上一天涨幅为
    // 10% 对今天 股票的涨幅的影响。
    // 8%-10% 对今天 股票的涨幅的影响。
    // 6%-8% 对今天 股票的涨幅的影响。
    // 4%-6% 对今天 股票的涨幅的影响。
    // 2%-4% 对今天 股票的涨幅的影响。
    // 0%-2%  对今天 股票的涨幅的影响。
    // -2%--4% 对今天 股票的涨幅的影响。
    // -4%--6% 对今天 股票的涨幅的影响。
    // -6%--8% 对今天 股票的涨幅的影响。
    // -8%--10% 对今天 股票的涨幅的影响。
    // -10% 对今天 股票的涨幅的影响。
    //截至到2019年-4-22日 统计数据为：
    public void getStockIncreaseEffectInit(String stockCode) {

        iStockIncreaseAnalyzeServices.delByStockCode(stockCode);

        int count_increase02 = 0;
        int count_increase24 = 0;
        int count_increase46 = 0;
        int count_increase68 = 0;
        int count_increase810 = 0;
        int count_increase10 = 0;
        int count_descend02 = 0;
        int count_descend24 = 0;
        int count_descend46 = 0;
        int count_descend68 = 0;
        int count_descend810 = 0;
        int count_descend10 = 0;

        double yestayZdf = 0.0;
        double zfDayCount = 0.0;

        List<StockInfo> stockListByStockCode = iStockInfoDao.getStockListByStockCode(stockCode, 999999999);


        if (stockListByStockCode.size() > 0) {
            for (int i = 0; i < stockListByStockCode.size(); i++) {
                double zdf = stockListByStockCode.get(i).getZdf();

                if (zdf >= 0) { //
                    zfDayCount += 1.0;

                    if (yestayZdf >= 10) {
                        count_increase10 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= 8) {
                        count_increase810 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= 6) {
                        count_increase68 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= 4) {
                        count_increase46 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= 2) {
                        count_increase24 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= 0) {
                        count_increase02 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= -2) {
                        count_descend02 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= -4) {
                        count_descend24 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= -6) {
                        count_descend46 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= -8) {
                        count_descend68 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                    if (yestayZdf >= -10) {
                        count_descend810 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }

                    if (yestayZdf >= -20) {
                        count_descend10 += 1.0;
                        yestayZdf = zdf;
                        continue;
                    }
                }

                yestayZdf = zdf;
            }

            StockIncreaseAnalyze stockIncreaseAnalyze = new StockIncreaseAnalyze();
            stockIncreaseAnalyze.setIncrease10(count_increase10 / zfDayCount);
            stockIncreaseAnalyze.setIncrease810(count_increase810 / zfDayCount);
            stockIncreaseAnalyze.setIncrease68(count_increase68 / zfDayCount);
            stockIncreaseAnalyze.setIncrease46(count_increase46 / zfDayCount);
            stockIncreaseAnalyze.setIncrease24(count_increase24 / zfDayCount);
            stockIncreaseAnalyze.setIncrease02(count_increase02 / zfDayCount);
            stockIncreaseAnalyze.setDescend02(count_descend02 / zfDayCount);
            stockIncreaseAnalyze.setDescend24(count_descend24 / zfDayCount);
            stockIncreaseAnalyze.setDescend46(count_descend46 / zfDayCount);
            stockIncreaseAnalyze.setDescend68(count_descend68 / zfDayCount);
            stockIncreaseAnalyze.setDescend810(count_descend810 / zfDayCount);
            stockIncreaseAnalyze.setDescend10(count_descend10 / zfDayCount);
            stockIncreaseAnalyze.setCount(stockListByStockCode.size());
            stockIncreaseAnalyze.setStockCode(stockCode);
            Date dt = new Date();
            String created_at=sdf.format(dt);
            stockIncreaseAnalyze.setStockDate(created_at);
            iStockIncreaseAnalyzeServices.insert(stockIncreaseAnalyze);
        }
        System.out.println("涨幅影响 更新完毕stockCode="+stockCode);
    }

    //统计所有股票的涨跌幅的影响。
    public void getStockIncreaseAnalyzeToTableAll() {

        double count_increase02 = 0.0;
        double count_increase24 = 0.0;
        double count_increase46 = 0.0;
        double count_increase68 = 0.0;
        double count_increase810 = 0.0;
        double count_increase10 = 0.0;
        double count_descend02 = 0.0;
        double count_descend24 = 0.0;
        double count_descend46 = 0.0;
        double count_descend68 = 0.0;
        double count_descend810 = 0.0;
        double count_descend10 = 0.0;
        double zfDayCount = 0.0;

        List<StockIncreaseAnalyze> listAll = iStockIncreaseAnalyzeServices.getListAll();
        for (int i = 0; i < listAll.size(); i++) {
            count_increase02 += listAll.get(i).getIncrease02() * listAll.get(i).getCount();
            count_increase24 += listAll.get(i).getIncrease24() * listAll.get(i).getCount();
            count_increase46 += listAll.get(i).getIncrease46() * listAll.get(i).getCount();
            count_increase68 += listAll.get(i).getIncrease68() * listAll.get(i).getCount();
            count_increase810 += listAll.get(i).getIncrease810() * listAll.get(i).getCount();
            count_increase10 += listAll.get(i).getIncrease10() * listAll.get(i).getCount();
            count_descend02 += listAll.get(i).getDescend02() * listAll.get(i).getCount();
            count_descend24 += listAll.get(i).getDescend24() * listAll.get(i).getCount();
            count_descend46 += listAll.get(i).getDescend46() * listAll.get(i).getCount();
            count_descend68 += listAll.get(i).getDescend68() * listAll.get(i).getCount();
            count_descend810 += listAll.get(i).getDescend810() * listAll.get(i).getCount();
            count_descend10 += listAll.get(i).getDescend10() * listAll.get(i).getCount();
            zfDayCount += listAll.get(i).getCount();

        }
        StockIncreaseAnalyze stockIncreaseAnalyze = new StockIncreaseAnalyze();
        stockIncreaseAnalyze.setIncrease10(count_increase10 / zfDayCount);
        stockIncreaseAnalyze.setIncrease810(count_increase810 / zfDayCount);
        stockIncreaseAnalyze.setIncrease68(count_increase68 / zfDayCount);
        stockIncreaseAnalyze.setIncrease46(count_increase46 / zfDayCount);
        stockIncreaseAnalyze.setIncrease24(count_increase24 / zfDayCount);
        stockIncreaseAnalyze.setIncrease02(count_increase02 / zfDayCount);
        stockIncreaseAnalyze.setDescend02(count_descend02 / zfDayCount);
        stockIncreaseAnalyze.setDescend24(count_descend24 / zfDayCount);
        stockIncreaseAnalyze.setDescend46(count_descend46 / zfDayCount);
        stockIncreaseAnalyze.setDescend68(count_descend68 / zfDayCount);
        stockIncreaseAnalyze.setDescend810(count_descend810 / zfDayCount);
        stockIncreaseAnalyze.setDescend10(count_descend10 / zfDayCount);
        stockIncreaseAnalyze.setCount(zfDayCount);
        stockIncreaseAnalyze.setStockCode("000000");

        Date dt = new Date();
        String created_at=sdf.format(dt);
        stockIncreaseAnalyze.setStockDate(created_at);
        iStockIncreaseAnalyzeServices.insert(stockIncreaseAnalyze);

    }

    //判断是否是今天最新的统计数据
    public String getStockIncreaseEffect(String stockCode){

        Date dt = new Date();
        String created_at=sdf.format(dt);
        Map<String,Object> resultMap= new HashMap<>();

        //查找昨日的涨跌幅
        List<StockInfo> newStockListByStockCode = iStockInfoServices.getNewStockListByStockCode(stockCode, SortType.DESC.toString(), 1);
        if (newStockListByStockCode.size()==1){
            resultMap.put("zdf",newStockListByStockCode.get(0).getZdf());
        }else {
            resultMap.put("zdf",0);
        }


        //查当前的的ID
        StockIncreaseAnalyze entryByStockCode = iStockIncreaseAnalyzeServices.getEntryByStockCode(stockCode, created_at);
        if (entryByStockCode==null){
            getStockIncreaseEffectInit(stockCode);
            entryByStockCode = iStockIncreaseAnalyzeServices.getEntryByStockCode(stockCode, created_at);
        }


        StockIncreaseAnalyze entryByStockCode_000000 = iStockIncreaseAnalyzeServices.getEntryByStockCode("000000", created_at);
        if (entryByStockCode_000000==null){
            iStockIncreaseAnalyzeServices.delByStockCode("000000");
            getStockIncreaseAnalyzeToTableAll();
            entryByStockCode_000000 = iStockIncreaseAnalyzeServices.getEntryByStockCode("000000", created_at);
        }

        Map<String,Object> resultMapOwn= new HashMap<>();
        resultMapOwn.put("increase02",entryByStockCode.getIncrease02());
        resultMapOwn.put("increase24",entryByStockCode.getIncrease24());
        resultMapOwn.put("increase46",entryByStockCode.getIncrease46());
        resultMapOwn.put("increase68",entryByStockCode.getIncrease68());
        resultMapOwn.put("increase810",entryByStockCode.getIncrease810());
        resultMapOwn.put("increase10",entryByStockCode.getIncrease10());

        resultMapOwn.put("descend02",entryByStockCode.getDescend02());
        resultMapOwn.put("descend24",entryByStockCode.getDescend24());
        resultMapOwn.put("descend46",entryByStockCode.getDescend46());
        resultMapOwn.put("descend68",entryByStockCode.getDescend68());
        resultMapOwn.put("descend810",entryByStockCode.getDescend810());
        resultMapOwn.put("descend10",entryByStockCode.getDescend10());

        Map<String,Object> resultMapAll= new HashMap<>();
        resultMapAll.put("increase02",entryByStockCode_000000.getIncrease02());
        resultMapAll.put("increase24",entryByStockCode_000000.getIncrease24());
        resultMapAll.put("increase46",entryByStockCode_000000.getIncrease46());
        resultMapAll.put("increase68",entryByStockCode_000000.getIncrease68());
        resultMapAll.put("increase810",entryByStockCode_000000.getIncrease810());
        resultMapAll.put("increase10",entryByStockCode_000000.getIncrease10());

        resultMapAll.put("descend02",entryByStockCode_000000.getDescend02());
        resultMapAll.put("descend24",entryByStockCode_000000.getDescend24());
        resultMapAll.put("descend46",entryByStockCode_000000.getDescend46());
        resultMapAll.put("descend68",entryByStockCode_000000.getDescend68());
        resultMapAll.put("descend810",entryByStockCode_000000.getDescend810());
        resultMapAll.put("descend10",entryByStockCode_000000.getDescend10());



        resultMap.put("resultMapOwn",resultMapOwn);
        resultMap.put("resultMapAll",resultMapAll);
        String jsonStr = JSON.toJSONString( resultMap );
//        System.out.println(jsonStr);
        return jsonStr;
    }



    //每一只股票 上市的的前几天情况


}
