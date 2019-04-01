package com.stock.controller.collection;

import com.stock.bean.StockInfo;
import com.stock.mapper.StockInfoMapper;
import com.stock.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.List;



public class StockKdjCollection {


    static StockInfoMapper stockInfoMapper = SpringUtil.getBean( StockInfoMapper.class);



    public  static void getKDJValue(String stockCode) throws Exception {

        List<StockInfo> stockListByShareCode = getStockListByShareCode(stockCode);

        StockInfo stockInfo0  =stockListByShareCode.get(0);

        double kValue =0;
        double dValue =0;
        //判断是否有初始数据，没有就进行初始化赋值
        if (stockInfo0.getdValue()==0||stockInfo0.getkValue()==0){
            double rsv = getRsv((double) (stockInfo0.getSpj()), (double) (stockInfo0.getZgj()), (double) (stockInfo0.getZdj()));
                     kValue = getK(50, rsv);
                     dValue = getD(50, kValue);
            double jValue =getJ(kValue,dValue);
            stockInfo0.setkValue(kValue);
            stockInfo0.setdValue(dValue);
            stockInfo0.setjValue(jValue);
            stockInfoMapper.updateStockInfo(stockInfo0);
        }

        int dayNum= 9;  //9日RSV=（C－L9）÷（H9－L9）×100
        getRSVLase(stockListByShareCode,dayNum ,kValue,dValue);


    }



    public  static List<StockInfo> getStockListByShareCode(String stockCode)   {
        StockInfo stockInfo =new StockInfo();
        stockInfo.setStockCode(stockCode);
        List<StockInfo> stockListByShareCode = stockInfoMapper.getStockListByShareCode(stockInfo);
        return stockListByShareCode;

    }

    public  static List<StockInfo> getStockListByShareCodeLimit10(String stockCode)   {
        StockInfo stockInfo =new StockInfo();
        stockInfo.setStockCode(stockCode);
        List<StockInfo> stockListByShareCode = stockInfoMapper.getStockListByShareCodeLimit10(stockInfo);
        return stockListByShareCode;

    }




    //https://zhidao.baidu.com/question/575561429.html

    public  static int getRSVLase(List<StockInfo> list , int dayNum,double kValue,double dValue) throws Exception {

//        for (int i = 1; i <list.size() ; i++) {  //从第二个数据开始进行处理，第一个已经在最外层进行赋值了

        double[] doubles =new double[2];
        doubles[0]=kValue;
        doubles[1]=dValue;
        for (int i = 1; i <list.size() ; i++) {  //从第二个数据开始进行处理，第一个已经在最外层进行赋值了
            StockInfo  stockInfo = list.get(i);


            if (i>dayNum){
                 doubles = RSVInit(list, dayNum, i + 1,doubles[0],doubles[1]);
            }else {
                doubles =RSVInit(list,i+1,i+1,doubles[0],doubles[1]);
            }

        }

        return 0;
    }



    public static double[] RSVInit(List<StockInfo> list, int dayNum, int position,double kValueinput,double dValueinput) throws Exception {
        //根据时间排序获取最新的9条数据 按照时间倒序排列 最旧的数据排第一位
        //小于9条的 第一天需要设置为 50

        double[] lszgjArray =new double[dayNum];
        double[] lszdjArray =new double[dayNum];

        double lszgj =0;  //历史最高价格
        double lszdj =0;  //历史最低价格
        double jrspj =0;  //今日收盘价
        double rsv =0;  //今日rsv

        int beginIndex =position - dayNum ;
        int endIndex =position ;

        for (int i = beginIndex; i <endIndex ; i++) {

            //往数组里面插入数据 使用循环数组  i%dayNum
            int insertArrayIndex =i%dayNum;

            StockInfo stockInfo =list.get(i);
            lszgjArray[insertArrayIndex] =Double.valueOf(stockInfo.getZgj());
            lszdjArray[insertArrayIndex] =Double.valueOf(stockInfo.getZdj());
            jrspj= Double.valueOf(stockInfo.getSpj());
        }
        lszgj=  getValueByType(lszgjArray,1);
        lszdj=  getValueByType(lszdjArray,0);

        jrspj=list.get(dayNum-1).getSpj();

        rsv = getRsv(jrspj,lszgj,lszdj);
        double kValue = getK(kValueinput, rsv);
        double dValue = getD(dValueinput, kValue);
        double jValue =getJ(kValue,dValue);

        StockInfo stockInfoUpdate = list.get(position-1);
        stockInfoUpdate.setkValue(kValue);
        stockInfoUpdate.setdValue(dValue);
        stockInfoUpdate.setjValue(jValue);
        stockInfoMapper.updateStockInfo(stockInfoUpdate);

        double[] resultArr =new double[2];
        resultArr[0]=kValue;
        resultArr[1]=dValue;
        return resultArr;
    }

    /***
     *
     * @param array
     * @param type  0:查最小值  1：查最大值
     * @return
     */
    public static double getValueByType(double[] array,int type)   {
        double result=array[0];
        for (int i = 0; i <array.length ; i++) {
            if (type==0){
                if (result>array[i]){
                    result=array[i];
                }
            }
            if (type==1){
                if (result<array[i]){
                    result=array[i];
                }
            }
        }
        return result;
    }


    public static double getRsv(double spj,double zgj,double zdj) {
        //RSV=（收盘价－最低价）/（最高价－最低价）×100
        return  (spj-zdj)/(zgj-zdj)*100;
    }



    public static double getK(double beforeDayK,double todayRSV) {

        // K值=2/3×第8日K值+1/3×第9日RSV
        return (2/3.0000*beforeDayK) + (1/3.0000*todayRSV);
    }

    public static double getD(double beforeDayD,double todayK) throws Exception {

       // D值=2/3×第8日D值+1/3×第9日K值
        return 2/3.0000*beforeDayD + 1/3.0000*todayK ;
    }

    public static double getJ(double todayK,double todayD) throws Exception {

       // J值=3*第9日K值-2*第9日D值
        return  3*todayK - 2*todayD ;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(getK(50,40));
        System.out.println(getD(50,40));
        System.out.println(getJ(50,40));
    }

}
