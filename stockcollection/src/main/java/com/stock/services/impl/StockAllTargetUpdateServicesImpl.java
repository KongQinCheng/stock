package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockAllTargetUpdateServices;
import com.stock.util.MathCaclateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.stock.services.impl.StockInfoCciServicesImpl.*;
import static com.stock.services.impl.StockInfoKdjServicesImpl.*;
import static com.stock.services.impl.StockInfoMacdServicesImpl.*;


@Service
public class StockAllTargetUpdateServicesImpl implements IStockAllTargetUpdateServices {

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Override
    public void allTargetUpdate(String stockCode) throws Exception {

        //根据股票代码查询历史数据
        List<StockInfo> stockInfoListAll = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(), 80);

        //MACD的 初始化
        double lastDayEma12 = 0;
        double lastDayEma26 = 0;
        double lastDEAMACD = 0;
        double todayDif = 0;
        double todayBar = 0;


        //KDJ  初始化
        double[] lszgjArray = new double[9];
        double[] lszdjArray = new double[9];
        double maxValue = 0.0;
        double minVaule = 0.0;

        double kValue = 50.0;
        double dValue = 50.0;
        double jValue = 0.0;


        //CCI 初始化
        int dayNum = 14;
        double[] spjArray = new double[dayNum];

        //RSI初始化
        double pp_6;// 用于计算rsi数据
        double np_6;
        double pp_12;
        double np_12;
        double pp_24;
        double np_24;
        double prepp_6 = 0;// 用于计算rsi数据
        double prenp_6 = 0;
        double prepp_12 = 0;
        double prenp_12 = 0;
        double prepp_24 = 0;
        double prenp_24 = 0;
        double upsanddowns;

        int rsi1_n = 6;
        int rsi2_n = 12;
        int rsi3_n = 24;
        double n1 = Double.valueOf(new Integer(rsi1_n));// 标准值为6
        double n2 = Double.valueOf(new Integer(rsi2_n));// 标准值为12
        double n3 = Double.valueOf(new Integer(rsi3_n));// 标准值为24
        double num_100 = 100D;

        double RSI1 = 0;
        double RSI2 = 0;
        double RSI3 = 0;


        //将前面n天的数据保存的列表中
        if (stockInfoListAll.size() >= dayNum + 1) {
            for (int i = 0; i < dayNum; i++) {
                spjArray[i] = Double.valueOf(stockInfoListAll.get(i).getSpj());
            }
        }


        if (stockInfoListAll.size() > 8) {
            //将前面9天的数据保存的列表中
            for (int i = 0; i < 9; i++) {
                StockInfo stockInfo = stockInfoListAll.get(i);
                lszgjArray[i] = Double.valueOf(stockInfo.getZgj());
                lszdjArray[i] = Double.valueOf(stockInfo.getZdj());
            }
        }


        for (int i = 0; i < stockInfoListAll.size(); i++) {

            boolean macdBoolean = false;
            boolean kdjBoolean = false;
            boolean cciBoolean = false;
            boolean rsiBoolean = false;

            StockInfo stockInfo = stockInfoListAll.get(i);
            stockInfo.setStockCode(stockCode);

            //判断MACD值是否存在，如果不存在则进行更新--开始

            if ((stockInfo.getEMA12() == 0) && (stockInfo.getEMA26() == 0)) { //当天的数据未进行计算
                lastDayEma12 = getEMA12(lastDayEma12, stockInfo.getSpj());
                lastDayEma26 = getEMA26(lastDayEma26, stockInfo.getSpj());
                todayDif = getDIFF(lastDayEma12, lastDayEma26);
                lastDEAMACD = getDEAMACD(lastDEAMACD, todayDif);
                todayBar = getBAR(lastDEAMACD, todayDif);

                stockInfo.setEMA12(lastDayEma12);
                stockInfo.setEMA26(lastDayEma26);
                stockInfo.setEMAMACD(lastDEAMACD);
                stockInfo.setDIF(todayDif);
                stockInfo.setBAR(todayBar);

                macdBoolean = true;
            } else {
                lastDayEma12 = stockInfo.getEMA12();
                lastDayEma26 = stockInfo.getEMA26();
                lastDEAMACD = stockInfo.getEMAMACD();
            }
//            System.out.println("MACD 更新完毕stockCode=" + stockCode + " stockDate=" + stockInfo.getStockDate());
            //判断MACD值是否存在，如果不存在则进行更新--结束


            //判断KDJ值是否存在，如果不存在则进行更新--开始
            int insertArrayIndex = i % 9;
            lszgjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZgj());
            lszdjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZdj());

            if (stockInfo.getKValue() == 0.0 && stockInfo.getDValue() == 0.0 && stockInfo.getJValue() == 0.0) {//当天的数据未进行计算
                maxValue = getValueByType(lszgjArray, 1);
                minVaule = getValueByType(lszdjArray, 0);
                if (maxValue != minVaule) {
                    double rsv = getRsv((double) (stockInfo.getSpj()), (double) maxValue, minVaule);
                    kValue = getK(kValue, rsv);
                    dValue = getD(dValue, kValue);
                    jValue = getJ(kValue, dValue);
                }
                stockInfo.setKValue(kValue);
                stockInfo.setDValue(dValue);
                stockInfo.setJValue(jValue);
                kdjBoolean = true;
            } else {
                kValue = stockInfo.getKValue();
                dValue = stockInfo.getDValue();
            }
//            System.out.println("KDJ  更新完毕stockCode=" + stockCode + " stockDate=" + stockInfo.getStockDate());
            //判断KDJ值是否存在，如果不存在则进行更新--结束
//
//
//
//            //判断CCI值是否存在，如果不存在则进行更新--开始
            spjArray[insertArrayIndex] = Double.valueOf(stockInfo.getSpj());
            if (stockInfoListAll.size() >= dayNum + 1) {
                if (stockInfo.getCci() == 0.0) {    //当天的数据未进行计算
                    double tpy = getTPY(Double.valueOf(stockInfo.getZgj()), Double.valueOf(stockInfo.getZdj()), Double.valueOf(stockInfo.getSpj()));
                    double ma = getMA(spjArray);
                    double md = getMD(ma, spjArray);
                    double cci = getCci(tpy, ma, md);
                    stockInfo.setCci(cci);
                    cciBoolean = true;
                }
            }
//            System.out.println("CCI  更新完毕stockCode=" + stockCode + " stockDate=" + stockInfo.getStockDate());
//            //判断CCI值是否存在，如果不存在则进行更新--结束


            //判断RSI值是否存在，如果不存在则进行更新--开始
            if (i > 0) {
                upsanddowns = stockInfo.getSpj() - stockInfoListAll.get(i - 1).getSpj();
                pp_6 = MathCaclateUtil.add(MathCaclateUtil.divide(MathCaclateUtil.multiply(prepp_6, n1 - 1, BigDecimal.ROUND_HALF_UP), n1, BigDecimal.ROUND_UNNECESSARY), MathCaclateUtil.divide(upsanddowns >= 0 ? upsanddowns : 0, n1, BigDecimal.ROUND_UNNECESSARY), BigDecimal.ROUND_HALF_UP);// prepp_6*(6-1)/6+(upsanddowns>=0?upsanddowns:0)/6
                np_6 = MathCaclateUtil.add(MathCaclateUtil.divide(MathCaclateUtil.multiply(prenp_6, n1 - 1, BigDecimal.ROUND_HALF_UP), n1, BigDecimal.ROUND_UNNECESSARY), MathCaclateUtil.divide(upsanddowns >= 0 ? 0 : upsanddowns, n1, BigDecimal.ROUND_UNNECESSARY), BigDecimal.ROUND_HALF_UP);// prenp_6*(6-1)/6+(upsanddowns>=0?0:upsanddowns)/6
                RSI1 = MathCaclateUtil.divide(MathCaclateUtil.multiply(num_100, pp_6, BigDecimal.ROUND_HALF_UP), MathCaclateUtil.add(pp_6, -np_6, BigDecimal.ROUND_HALF_UP), BigDecimal.ROUND_UNNECESSARY);// 100*pp_6/(pp_6-np_6)

                prepp_6 = pp_6;
                prenp_6 = np_6;

                pp_12 = MathCaclateUtil.add(MathCaclateUtil.divide(MathCaclateUtil.multiply(prepp_12, n2 - 1, BigDecimal.ROUND_HALF_UP), n2, BigDecimal.ROUND_UNNECESSARY), MathCaclateUtil.divide(upsanddowns >= 0 ? upsanddowns : 0, n2, BigDecimal.ROUND_UNNECESSARY), BigDecimal.ROUND_HALF_UP);// prepp_12*(12-1)/12+(upsanddowns>=0?upsanddowns:0)/12;
                np_12 = MathCaclateUtil.add(MathCaclateUtil.divide(MathCaclateUtil.multiply(prenp_12, n2 - 1, BigDecimal.ROUND_HALF_UP), n2, BigDecimal.ROUND_UNNECESSARY), MathCaclateUtil.divide(upsanddowns >= 0 ? 0 : upsanddowns, n2, BigDecimal.ROUND_UNNECESSARY), BigDecimal.ROUND_HALF_UP);// prenp_12*(12-1)/12+(upsanddowns>=0?0:upsanddowns)/12;
                RSI2 = MathCaclateUtil.divide(MathCaclateUtil.multiply(num_100, pp_12, BigDecimal.ROUND_HALF_UP), MathCaclateUtil.add(pp_12, -np_12, BigDecimal.ROUND_HALF_UP), BigDecimal.ROUND_UNNECESSARY);// 100*pp_12/(pp_12-np_12);

                prepp_12 = pp_12;
                prenp_12 = np_12;

                pp_24 = MathCaclateUtil.add(MathCaclateUtil.divide(MathCaclateUtil.multiply(prepp_24, n3 - 1, BigDecimal.ROUND_HALF_UP), n3, BigDecimal.ROUND_UNNECESSARY), MathCaclateUtil.divide(upsanddowns >= 0 ? upsanddowns : 0, n3, BigDecimal.ROUND_UNNECESSARY), BigDecimal.ROUND_HALF_UP);// prepp_24*(24-1)/24+(upsanddowns>=0?upsanddowns:0)/24;
                np_24 = MathCaclateUtil.add(MathCaclateUtil.divide(MathCaclateUtil.multiply(prenp_24, n3 - 1, BigDecimal.ROUND_HALF_UP), n3, BigDecimal.ROUND_UNNECESSARY), MathCaclateUtil.divide(upsanddowns >= 0 ? 0 : upsanddowns, n3, BigDecimal.ROUND_UNNECESSARY), BigDecimal.ROUND_HALF_UP);// prenp_24*(24-1)/24+(upsanddowns>=0?0:upsanddowns)/24;
                RSI3 = MathCaclateUtil.divide(MathCaclateUtil.multiply(num_100, pp_24, BigDecimal.ROUND_HALF_UP), MathCaclateUtil.add(pp_24, -np_24, BigDecimal.ROUND_HALF_UP), BigDecimal.ROUND_UNNECESSARY);// 100*pp_24/(pp_24-np_24);

                prepp_24 = pp_24;
                prenp_24 = np_24;

                if ((stockInfo.getRSI06() == 0) && (stockInfo.getRSI12() == 0) && (stockInfo.getRSI24() == 0)) { //当天的数据未进行计算
                    stockInfo.setRSI06(RSI1);
                    stockInfo.setRSI12(RSI2);
                    stockInfo.setRSI24(RSI3);
                    rsiBoolean=true;
                }
            }
//            System.out.println("RSI  更新完毕stockCode=" + stockCode + " stockDate=" + stockInfo.getStockDate());
            //判断RSI值是否存在，如果不存在则进行更新--结束


            if (macdBoolean || kdjBoolean || cciBoolean||rsiBoolean) {
                iStockInfoDao.updateStockInfoAll(stockInfo);
            }

        }

        System.out.println("所有指标  更新完毕stockCode=" + stockCode );
    }
}
