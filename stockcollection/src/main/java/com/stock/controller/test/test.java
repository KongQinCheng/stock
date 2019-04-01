package com.stock.controller.test;

public class test {



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

    public static double getRsv(double spj,double zgj,double zdj) {
        //RSV=（收盘价－最低价）/（最高价－最低价）×100
        return  (spj-zdj)/(zgj-zdj)*100;
    }


    public static void main(String[] args) throws Exception {




        double rsv =getRsv(25.39,29.07,25.25);
        System.out.println(rsv);

        double k=getK(50,rsv);
        System.out.println(k);

        double d=getD(50,k);
        System.out.println(d);

        double j=getJ(k,d);
        System.out.println(j);
    }



}
