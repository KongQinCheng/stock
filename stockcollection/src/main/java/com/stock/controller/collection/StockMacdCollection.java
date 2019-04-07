package com.stock.controller.collection;

import com.stock.bean.StockInfo;
import com.stock.bean.StockList;
import com.stock.mapper.StockInfoMapper;
import com.stock.util.SpringUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.stock.controller.collection.StockKdjCollection.getStockListByStockCode;
import static com.stock.controller.collection.StockKdjCollection.getStockListByStockCodeLimit10;
import static com.stock.controller.collection.StockListCollection.getStockList;

public class StockMacdCollection {

    static StockInfoMapper stockInfoMapper = SpringUtil.getBean( StockInfoMapper.class);


    public static void main(String[] args)  throws Exception{

        DecimalFormat df = new DecimalFormat("#.0000");
        System.out.println(  getEMA12(55.01,53.7));
        System.out.println(  getEMA12(55.01,53.7));
        System.out.println(getEMA26(55.01,53.7));
        System.out.println(getDIFF(getEMA12(55.01,53.7),getEMA26(55.01,53.7)));
        System.out.println(getDEAMACD(0,getDIFF(getEMA12(55.01,53.7),getEMA26(55.01,53.7))));
        System.out.println(getBAR(getDEAMACD(0,getDIFF(getEMA12(55.01,53.7),getEMA26(55.01,53.7))),getDIFF(getEMA12(55.01,53.7),getEMA26(55.01,53.7))));
    }


    /***
     * 计算所有 股票的  MACD值
     */
    public static  void stockMacdInitALL( ){
        List<StockList> stockList = getStockList();

//        List<StockList> stockList =new ArrayList<>();
//        String[] arr={"000001","000002","000004","000005","000006","000007","000008","000009","000010","000011","000012","000014","000016","000017","000018","000019","000020","000021","000022","000023","000024","000025","000026","000027","000028","000029","000030","000031","000032","000033","000034","000035","000036","000037","000038","000039","000040","000042","000043","000045","000046","000048","000049","000050","000055","000056","000058","000059","000060","000061","000062","000063","000065","000066","000068","000069","000070","000078","000088","000089","000090","000096","000099","000100","000150","000151","000153","000155","000156","000157","000158","000159","000166","000301","000338","000400","000401","000402","000403","000404","000407","000408","000409","000410","000411","000413","000415","000416","000417","000418","000419","000420","000421","000422","000423","000425","000426","000428","000429","000430","000488","000498","000501","000502","000503","000504","000505","000506","000507","000509","000510","000511","000513","000514","000516","000517","000518","000519","000520","000521","000522","000523","000524","000525","000526","000527","000528","000529","000530","000531","000532","000533","000534","000536","000537","000538","000539","000540","000541","000543","000544","000545","000546","000547","000548","000549","000550","000551","000552","000553","000554","000555","000557","000558","000559","000560","000561","000562","000563","000564","000565","000566","000567","000568","000570","000571","000572","000573","000576","000578","000581","000582","000584","000585","000586","000587","000589","000590","000591","000592","000593","000594","000595","000596","000597","000598","000599","000600","000601","000602","000603","000605","000606","000607","000608","000609","000610","000611","000612","000613","000615","000616","000617","000619","000620","000622","000623","000625","000626","000627","000628","000629","000630","000631","000632","000633","000635","000636","000637","000638","000639","000650","000651","000652","000655","000656","000657","000659","000661","000662","000663","000665","000666","000667","000668","000669","000670","000671","000672","000673","000676","000677","000678","000679","000680","000681","000682","000683","000685","000686","000687","000688","000690","000691","000692","000693","000695","000697","000698","000700","000701","000702","000703","000705","000707","000708","000709","000710","000711","000712","000713","000715","000716","000717","000718","000719","000720","000721","000722","000723","000725","000726","000727","000728","000729","000731","000732","000733","000735","000736","000737","000738","000739","000748","000750","000751","000752","000753","000755","000756","000757","000758","000759","000760","000761","000762","000766","000767","000768","000776","000777","000778","000779","000780","000782","000783","000785","000786","000787","000788","000789","000790","000791","000792","000793","000795","000796","000797","000798","000799","000800","000801","000802","000806","000807","000809","000810","000811","000812","000813","000815","000816","000818","000819","000820","000821","000822","000823","000825","000826","000827","000828","000829","000830","000831","000833","000835","000836","000837","000838","000839","000848","000850","000851","000852","000856","000858","000859","000860","000861","000862","000863","000868","000869","000875","000876","000877","000878","000880","000881","000882","000883","000885","000886","000887","000888","000889","000890","000892","000893","000895","000897","000898","000899","000900","000901","000902","000903","000905","000906","000908","000909","000910","000911","000912","000913","000915","000916","000917","000918","000919","000920","000921","000922","000923","000925","000926","000927","000928","000929","000930","000931","000932","000933","000935","000936","000937","000938","000939","000948","000949","000950","000951","000952","000953","000955","000957","000958","000959","000960","000961","000962","000963","000965","000966","000967","000968","000969","000970","000971","000972","000973","000975","000976","000977","000978","000979","000980","000981","000982","000983","000985","000987","000988","000989","000990","000993","000995","000996","000997","000998","000999","001696","001896","001965","001979","002001","002002","002003","002004","002005","002006","002007","002008","002009","002010","002011","002012","002013","002014","002015","002016","002017","002018","002019","002020","002021","002022","002023","002024","002025","002026","002027","002028","002029","002030","002031","002032","002033","002034","002035","002036","002037","002038","002039","002040","002041","002042","002043","002044","002045","002046","002047","002048","002049","002050","002051","002052","002053","002054","002055","002056","002057","002058","002059","002060","002061","002062","002063","002064","002065","002066","002067","002068","002069","002070","002071","002072","002073","002074","002075","002076","002077","002078","002079","002080","002081","002082","002083","002084","002085","002086","002087","002088","002089","002090","002091","002092","002093","002094","002095","002096","002097","002098","002099","002100","002101","002102","002103","002104","002105","002106","002107","002108","002109","002110","002111","002112","002113","002114","002115","002116","002117","002118","002119","002120","002121","002122","002123","002124","002125","002126","002127","002128","002129","002130","002131","002132","002133","002134","002135","002136","002137","002138","002139","002140","002141","002142","002143","002144","002145","002146","002147","002148","002149","002150","002151","002152","002153","002154","002155","002156","002157","002158","002159","002160","002161","002162","002163","002164","002165","002166","002167","002168","002169","002170","002171","002172","002173","002174","002175","002176","002177","002178","002179","002180","002181","002182","002183","002184","002185","002186","002187","002188","002189","002190","002191","002192","002193","002194","002195","002196","002197","002198","002199","002200","002201","002202","002203","002204","002205","002206","002207","002208","002209","002210","002211","002212","002213","002214","002215","002216","002217","002218","002219","002220","002221","002222","002223","002224","002225","002226","002227","002228","002229","002230","002231","002232","002233","002234","002235","002236","002237","002238","002239","002240","002241","002242","002243","002244","002245","002246","002247","002248","002249","002250","002251","002252","002253","002254","002255","002256","002258","002259","002260","002261","002262","002263","002264","002265","002266","002267","002268","002269","002270","002271","002272","002273","002274","002275","002276","002277","002278","002279","002280","002281","002282","002283","002284","002285","002286","002287","002288","002289","002290","002291","002292","002293","002294","002295","002296","002297","002298","002299","002300","002301","002302","002303","002304","002305","002306","002307","002308","002309","002310","002311","002312","002313","002314","002315","002316","002317","002318","002319","002320","002321","002322","002323","002324","002325","002326","002327","002328","002329","002330","002331","002332","002333","002334","002335","002336","002337","002338","002339","002340","002341","002342","002343","002344","002345","002346","002347","002348","002349","002350","002351","002352","002353","002354","002355","002356","002357","002358","002359","002360","002361","002362","002363","002364","002365","002366","002367","002368","002369","002370","002371","002372","002373","002374","002375","002376","002377","002378","002379","002380","002381","002382","002383","002384","002385","002386","002387","002388","002389","002390","002391","002392","002393","002394","002395","002396","002397","002398","002399","002400","002401","002402","002403","002404","002405","002406","002407","002408","002409","002410","002411","002412","002413","002414","002415","002416","002417","002418","002419","002420","002421","002422","002423","002424","002425","002426","002427","002428","002429","002430","002431","002432","002433","002434","002435","002436","002437","002438","002439","002440","002441","002442","002443","002444","002445","002446","002447","002448","002449","002450","002451","002452","002453","002454","002455","002456","002457","002458","002459","002460","002461","002462","002463","002464","002465","002466","002467","002468","002469","002470","002471","002472","002473","002474","002475","002476","002477","002478","002479","002480","002481","002482","002483","002484","002485","002486","002487","002488","002489","002490","002491","002492","002493","002494","002495","002496","002497","002498","002499","002500","002501","002502","002503","002504","002505","002506","002507","002508","002509","002510","002511","002512","002513","002514","002515","002516","002517","002518","002519","002520","002521","002522","002523","002524","002526","002527","002528","002529","002530","002531","002532","002533","002534","002535","002536","002537","002538","002539","002540","002541","002542","002543","002544","002545","002546","002547","002548","002549","002550","002551","002552","002553","002554","002555","002556","002557","002558","002559","002560","002561","002562","002563","002564","002565","002566","002567","002568","002569","002570","002571","002572","002573","002574","002575","002576","002577","002578","002579","002580","002581","002582","002583","002584","002585","002586","002587","002588","002589","002590","002591","002592","002593","002594","002595","002596","002597","002598","002599","002600","002601","002603","002604","002606","002607","002608","002609","002610","002611","002612","002613","002614","002615","002616","002617","002618","002619","002620","002621","002622","002623","002624","002625","002626","002627","002628","002629","002630","002631","002632","002633","002634","002635","002636","002637","002638","002639","002640","002641","002642","002643","002644","002645","002646","002647","002648","002649","002650","002651","002652","002653","002654","002655","002656","002657","002658","002659","002660","002661","002662","002663","002664","002665","002666","002667","002668","002669","002670","002671","002672","002673","002674","002675","002676","002677","002678","002679","002680","002681","002682","002683","002684","002685","002686","002687","002688","002689","002690","002691","002692","002693","002694","002695","002696","002697","002698","002699","002700","002701","002702","002703","002705","002706","002707","002708","002709","002711","002712","002713","002714","002716","002717","002718","002719","002721","002722","002723","002725","002726","002727","002730","002732","002733","002734","002735","002736","002737","002738","002739","002740","002741","002742","002743","002745","002746","002747","002748","002749","002750","002751","002752","002753","002755","002756","002757","002758","002759","002760","002761","002762","002763","002765","002766","002767","002768","002769","002770","002771","002772","002773","002775","002776","002777","002778","002779","002780","002781","002783","002785","002787","002788","002789","002790","002791","002792","002793","002795","002796","002798","002800","002801","002803","002805","002807","002809","002810","002811","002812","002813","002815","002816","002818","002820","002821","002822","002823","002824","002826","002828","002829","002830","002831","002832","002833","002837","002838","002839","002840","002841","002842","002843","002845","002847","002849","002850","002851","002853","002857","002858","002859","002860","002861","002862","002865","002866","002867","002869","002875","002876","002878","002880","002881","002883","002886","002887","002889","002896","002897","002898","002901","002902","002905","002906","002908","002909","002911","002912","002913","002916","002918","002920","002925","002926","002928","002930","002933","002938","002943","002946","000026","000028","000050","000159","000333","000338","000537","000571","000616","000637","000661","000665","000680","000695","000709","000715","000732","000780","000812","000821","000826","000859","000885","000901","000903","000935","000966","000969","000995","002053","002062","002071","002075","002086","002092","002132","002140","002143","002166","002168","002187","002223","002237","002252","002253","002256","002273","002292","002306","002311","002336","002368","002403","002415","002423","002434","002441","002453","002468","002490","002500","002530","002531","002553","002587","002588","002596","002598","002602","002605","002607","002615","002621","002638","002651","002657","002663","002680","002687","002726","002735","002763","002767","002768","002769","002809","002812","002815","002889"};
//
//        for (int i = 0; i < arr.length; i++) {
//            StockList stockList1 =new StockList();
//            stockList1.setStockCode(arr[i]);
//            stockList.add(stockList1) ;
//        }

        double threadCount =200.0 ; //使用 20个线程处理

        ExecutorService executor = Executors.newFixedThreadPool((int)threadCount);
        int listSize = stockList.size() ;
        //将总数分成 多个线程之后，每个线程需要处理的数据为： listSize/threadCount

        double divNumd  = Math.ceil(listSize/threadCount);
        int divNum  = (int)divNumd;

        if (listSize > 0) {
            int batch = listSize % divNum == 0 ? listSize / divNum : listSize / divNum + 1;
            for (int j=0; j<batch; j++) {
                int end = (j+1)*divNum;
                if (end > listSize) {
                    end = listSize;
                }
                List<StockList> subList = stockList.subList(j*divNum, end);
                ThreadRunnable threadRunnable = new ThreadRunnable(subList);
                executor.execute(threadRunnable);
            }
        }

    }


    /**
     * 查找数据库中 MACD值 为空的表
     */
    public static  void findMACDNullTable(){

        //获取列表
        List<StockList> stockList = getStockList();

        StockInfo stockInfo =new StockInfo();

        List<String> resultlist =new ArrayList<>();
        List<String> resultlist222 = new ArrayList<>();
        List<String> resultlist222333 = new ArrayList<>();

        for (int i = 0; i < stockList.size(); i++) {
            //循环列表
            StockList stockList1 =stockList.get(i);

            //判断表是否有数据
            stockInfo.setStockCode(stockList1.getStockCode().replaceAll("\t","")+"");

            try{
                List<StockInfo> stockInfoList = stockInfoMapper.getStockListByStockCode(stockInfo.getStockCode() ,999999999);

                    if(stockInfoList.get(0).getEMA12()==0){
                        resultlist222.add(stockInfo.getStockCode());
                    }
            }
            catch (Exception e){
                resultlist222333.add(stockInfo.getStockCode());
            }
        }
        System.out.println(resultlist222);
        System.out.println(resultlist222333);
    }


    /**
     * 开启多线程 计算MACD值
     */
    static class ThreadRunnable implements Runnable{
        private List<StockList> listInput;
        public ThreadRunnable(List<StockList> temp){
            this.listInput= temp;
        }

        @Override
        public void run() {
            for (int i = 0; i <listInput.size() ; i++) {
                try {

                    //初始化
//                    stockMacdInit(listInput.get(i).getStockCode().replaceAll("\t","")+"",0);

                    //每次更新新数据
                    stockMacdInit(listInput.get(i).getStockCode().replaceAll("\t","")+"",1);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    /***
     *
     * @param stockCode
     * @param type  0: 初始化， 1： 有新数据进行更新
     */

    public static  void stockMacdInit(String stockCode ,int type){

        List<StockInfo> stockListByStockCode = new ArrayList<>();
        if (type==0){
            stockListByStockCode = getStockListByStockCode(stockCode,999999999);
        }
        if (type==1){
            stockListByStockCode = getStockListByStockCodeLimit10(stockCode);
        }

        double lastDayEma12 =0;
        double lastDayEma26 =0;
        double lastDEAMACD =0;
        double todayDif=0;
        double todayBar=0;

        for (int i = 0; i <stockListByStockCode.size() ; i++) {
            StockInfo stockInfo =stockListByStockCode.get(i);

            if (type ==1){
                if (stockInfo.getEMA12()!=0) {
                    lastDayEma12 = stockInfo.getEMA12();
                    lastDayEma26 = stockInfo.getEMA26();
                    lastDEAMACD = stockInfo.getEMAMACD();
                    continue;
                }
            }


            lastDayEma12 =getEMA12(lastDayEma12,stockInfo.getSpj());
            lastDayEma26 =getEMA26(lastDayEma26,stockInfo.getSpj());
            todayDif= getDIFF(lastDayEma12,lastDayEma26);
            lastDEAMACD=getDEAMACD(lastDEAMACD,todayDif);
            todayBar=getBAR(lastDEAMACD,todayDif);

            stockInfo.setStockCode(stockCode);
            stockInfo.setEMA12(lastDayEma12);
            stockInfo.setEMA26(lastDayEma26);
            stockInfo.setEMAMACD(lastDEAMACD);
            stockInfo.setDIF(todayDif);
            stockInfo.setBAR(todayBar);
            stockInfoMapper.updateStockInfoMacd(stockInfo);
        }

    }






    //    EMA（12）= 前一日EMA（12）×11/13＋今日收盘价×2/13
    public static  double getEMA12(double lastDayEMA12,double todaySpj){
        double result=0;
        result=lastDayEMA12*11/13.0+todaySpj*2/13.0;
        return  result;
    }
    //    EMA（26）= 前一日EMA（26）×25/27＋今日收盘价×2/27
    public static  double getEMA26(double lastDayEMA26,double todaySpj){
        double result=0;
        result=lastDayEMA26*25/27.0+todaySpj*2/27.0;
        return  result;
    }
    //    DIFF=今日EMA（12）- 今日EMA（26）
    public static  double getDIFF(double lastDayEMA12,double lastDayEMA26){
        double result=0;
        result=lastDayEMA12 -lastDayEMA26;
        return  result;
    }
    //    DEA（MACD）= 前一日DEA×8/10＋今日DIF×2/10
    public static  double getDEAMACD(double lastDEA,double todayDIFF){
        double result=0;
        result=lastDEA*8/10.0 + todayDIFF*2/10.0;
        return  result;
    }
    //    BAR=2×(DIFF－DEA)
    public static  double getBAR(double DEA,double todayDIFF){
        double result=0;
        result=2*(todayDIFF-DEA);
        return  result;
    }

}
