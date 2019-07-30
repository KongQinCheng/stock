package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockAnalyzeKDJservices;
import com.stock.services.IStockAnalyzeRSIservices;
import com.stock.util.MathCaclateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



@Service
public class StockInfoRSIServicesImpl implements IStockAnalyzeRSIservices {

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Override
    public void getRSI(String stockCode ) {

        List<StockInfo> list =iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(),40); //40条信息 基本上就是 准确的数据

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


        double[] rsi1 =new double[list.size()];
        double[] rsi2=new double[list.size()];
        double[] rsi3=new double[list.size()];

        int rsi1_n=6; int rsi2_n=12; int rsi3_n=24;
        double n1 = Double.valueOf(new Integer(rsi1_n));// 标准值为6
        double n2 = Double.valueOf(new Integer(rsi2_n));// 标准值为12
        double n3 = Double.valueOf(new Integer(rsi3_n));// 标准值为24
        double num_100 = 100D;

        double RSI1 = 0;
        double RSI2 = 0;
        double RSI3 = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                continue;
            }
            upsanddowns = list.get(i).getSpj() - list.get(i-1).getSpj();

            pp_6 = MathCaclateUtil.add(
                    MathCaclateUtil.divide(MathCaclateUtil.multiply(prepp_6, n1 - 1, BigDecimal.ROUND_HALF_UP), n1,
                            BigDecimal.ROUND_UNNECESSARY),
                    MathCaclateUtil.divide(upsanddowns >= 0 ? upsanddowns : 0, n1, BigDecimal.ROUND_UNNECESSARY),
                    BigDecimal.ROUND_HALF_UP);// prepp_6*(6-1)/6+(upsanddowns>=0?upsanddowns:0)/6
            np_6 = MathCaclateUtil.add(
                    MathCaclateUtil.divide(MathCaclateUtil.multiply(prenp_6, n1 - 1, BigDecimal.ROUND_HALF_UP), n1,
                            BigDecimal.ROUND_UNNECESSARY),
                    MathCaclateUtil.divide(upsanddowns >= 0 ? 0 : upsanddowns, n1, BigDecimal.ROUND_UNNECESSARY),
                    BigDecimal.ROUND_HALF_UP);// prenp_6*(6-1)/6+(upsanddowns>=0?0:upsanddowns)/6
            RSI1 = MathCaclateUtil.divide(MathCaclateUtil.multiply(num_100, pp_6, BigDecimal.ROUND_HALF_UP),
                    MathCaclateUtil.add(pp_6, -np_6, BigDecimal.ROUND_HALF_UP), BigDecimal.ROUND_UNNECESSARY);// 100*pp_6/(pp_6-np_6)

            rsi1[i] = RSI1;
            prepp_6 = pp_6;
            prenp_6 = np_6;

            pp_12 = MathCaclateUtil.add(
                    MathCaclateUtil.divide(MathCaclateUtil.multiply(prepp_12, n2 - 1, BigDecimal.ROUND_HALF_UP), n2,
                            BigDecimal.ROUND_UNNECESSARY),
                    MathCaclateUtil.divide(upsanddowns >= 0 ? upsanddowns : 0, n2, BigDecimal.ROUND_UNNECESSARY),
                    BigDecimal.ROUND_HALF_UP);// prepp_12*(12-1)/12+(upsanddowns>=0?upsanddowns:0)/12;
            np_12 = MathCaclateUtil.add(
                    MathCaclateUtil.divide(MathCaclateUtil.multiply(prenp_12, n2 - 1, BigDecimal.ROUND_HALF_UP), n2,
                            BigDecimal.ROUND_UNNECESSARY),
                    MathCaclateUtil.divide(upsanddowns >= 0 ? 0 : upsanddowns, n2, BigDecimal.ROUND_UNNECESSARY),
                    BigDecimal.ROUND_HALF_UP);// prenp_12*(12-1)/12+(upsanddowns>=0?0:upsanddowns)/12;
            RSI2 = MathCaclateUtil.divide(MathCaclateUtil.multiply(num_100, pp_12, BigDecimal.ROUND_HALF_UP),
                    MathCaclateUtil.add(pp_12, -np_12, BigDecimal.ROUND_HALF_UP), BigDecimal.ROUND_UNNECESSARY);// 100*pp_12/(pp_12-np_12);

            rsi2[i] = RSI2;
            prepp_12 = pp_12;
            prenp_12 = np_12;

            pp_24 = MathCaclateUtil.add(
                    MathCaclateUtil.divide(MathCaclateUtil.multiply(prepp_24, n3 - 1, BigDecimal.ROUND_HALF_UP), n3,
                            BigDecimal.ROUND_UNNECESSARY),
                    MathCaclateUtil.divide(upsanddowns >= 0 ? upsanddowns : 0, n3, BigDecimal.ROUND_UNNECESSARY),
                    BigDecimal.ROUND_HALF_UP);// prepp_24*(24-1)/24+(upsanddowns>=0?upsanddowns:0)/24;
            np_24 = MathCaclateUtil.add(
                    MathCaclateUtil.divide(MathCaclateUtil.multiply(prenp_24, n3 - 1, BigDecimal.ROUND_HALF_UP), n3,
                            BigDecimal.ROUND_UNNECESSARY),
                    MathCaclateUtil.divide(upsanddowns >= 0 ? 0 : upsanddowns, n3, BigDecimal.ROUND_UNNECESSARY),
                    BigDecimal.ROUND_HALF_UP);// prenp_24*(24-1)/24+(upsanddowns>=0?0:upsanddowns)/24;
            RSI3 = MathCaclateUtil.divide(MathCaclateUtil.multiply(num_100, pp_24, BigDecimal.ROUND_HALF_UP),
                    MathCaclateUtil.add(pp_24, -np_24, BigDecimal.ROUND_HALF_UP), BigDecimal.ROUND_UNNECESSARY);// 100*pp_24/(pp_24-np_24);

            rsi3[i] = RSI3;
            prepp_24 = pp_24;
            prenp_24 = np_24;
        }

        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).getRSI06()==0.0){
                list.get(i).setStockCode(stockCode);
                list.get(i).setRSI06(rsi1[i]);
                list.get(i).setRSI12(rsi2[i]);
                list.get(i).setRSI24(rsi3[i]);
                iStockInfoDao.updateStockInfoRSI(list.get(i));
            }
        }
        System.out.println("RSI 更新完毕stockCode=" + stockCode);

    }

    @Override
    public void alterTable(String stockCode) {
        iStockInfoDao.alterTable(stockCode);
    }


}
