package com.stock.controller.collection;

import com.stock.bean.StockInfo;
import com.stock.bean.StockList;
import com.stock.mapper.StockInfoMapper;
import com.stock.util.HtmlUtil;
import com.stock.util.SpringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.stock.controller.collection.StockListCollection.getStockList;

public class StockInfoCollection {

    static HtmlUtil htmlUtil = SpringUtil.getBean( HtmlUtil.class);

    static StockInfoMapper stockInfoMapper = SpringUtil.getBean( StockInfoMapper.class);


    /***
     * 根据数据库中保存的 股票编号 获取股票的历史信息
     * @throws Exception
     */
    public static void getWycjSituationAll( ) throws Exception {

//        String tablename="";
//        List<String> resultlist = new ArrayList<>();
//        for (int i = 0; i <stockList.size(); i++) {
//            tablename= "stock_info_"+stockList.get(i).getStockCode().replaceAll("\t","");
//            if (!isTableExist(tablename)){
//                resultlist.add(stockList.get(i).getStockCode().replaceAll("\t",""));
//            }
//        }
//
//        System.out.println(resultlist);


//        List<StockList> stockList =new ArrayList<>();
//        String[] arr={"002408","002601","002603","002604","002606","002608","002668","002669","002670","002671","002869","002875","002902","002958","00604","00813","02454","02777","03383","101650","1102","1216","1477","1565","1569","2013","2027","2059","2105","2303","2324","2354","2357","2362","2376","2382","2393","2439","2454","2498","2884","2915","300124","300125","300126","300127","300128","300129","300130","300249","300458","300496","300497","300498","300499","300500","300501","300502","300503","3008","3034","3149","3211","3454","3474","3533","3598","3673","400016","430005","430037","430056","430065","430066","430120","430127","430140","430161","430174","430177","430208","430211","430222","430237","430240","430244","430289","430301","430309","430323","430335","430353","430403","430430","430432","430489","430493","430501","430515","430566","430591","430605","430609","430618","430642","430693","430725","430727","430763","5007","600012","600015","600016","600070","600071","600072","600208","600209","600210","600211","600212","600213","600215","600278","600279","600280","600354","600355","600356","600357","600358","600436","600438","600439","600444","600446","600519","600520","600521","600522","600523","600653","600764","600855","600973","600975","600976","600977","600978","601633","601636","601666","603179","603180","603181","603185","603186","603189","603196","603367","603368","603369","603377","603378","603385","603978","603979","603980","6147","6153","6176","6180","6230","6269","6271","6274","6278","6285","6505","751038","751405","751900","751936","8039","8069","8299","830783","830793","830828","830832","830879","830881","830899","830933","830972","830978","831049","831067","831083","831114","831140","831190","831247","831248","831259","831278","831329","831405","831417","831450","831527","831550","831604","831628","831836","831846","831885","831891","831940","832042","832097","832139","832149","832159","832317","832341","832397","832432","832522","832609","832684","832737","833039","833047","833074","833119","833132","833183","833186","833221","833331","833377","833390","833442","833444","833497","833503","833581","833656","833684","833694","833753","833755","833757","833788","833840","833932","833960","833999","834013","834028","834061","834082","834178","834258","834387","834476","834480","834507","834534","834708","834729","834731","834754","834863","834869","834915","835054","835185","835218","835268","835488","835670","835785","835879","835930","835955","836036","836053","836084","836188","836361","836859","836892","837009","837088","837160","837335","837375","837514","837567","837935","838006","838200","838237","838305","838373","838529","838601","838750","838830","838886","839264","839448","839473","839646","839720","839737","839744","839843","870023","870155","870169","870285","870447","870842","871018","871114","871134","871269","871831","872010","872242","872474","872492","872493","872682","873009","002602","002605","4938","833761"};
//        for (int i = 0; i < arr.length; i++) {
//            StockList stockList1 =new StockList();
//            stockList1.setStockCode(arr[i]);
//            stockList.add(stockList1) ;
//        }

        List<StockList> stockList = getStockList();

        double threadCount =100.0 ; //使用 20个线程处理

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

    /***
     *  开启多线程进行数据处理
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
                    getWycjSituation(listInput.get(i).getStockCode().replaceAll("\t","")+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /***
     * 获取 单个 股票的 历史信息
     */
    public static void getWycjSituation(String stockCode) throws Exception {

        createTableByTableName(stockCode);

        List<String> urlList = getURLbyShareCode(stockCode);
        for (int i = 0; i <urlList.size() ; i++) {

            String html= htmlUtil.getHtmlByURL(urlList.get(i),"UTF-8");

            //获取需要的正文
            html= htmlUtil.getHtmlByExpression("</thead[\\s\\S]*</tr>    </table>",html);

            html= htmlUtil.replaceHtml(" class='cGreen'",html);
            html= htmlUtil.replaceHtml(" class='cRed'",html);


            html= htmlUtil.getHtmlByExpression("<td>(.*?)</td>",html);
            String[] strings = htmlUtil.splitByExpression("</td>", html);

            if ("".equals(strings[0])){
                continue;
            }

            boolean returnResult = getBean(strings, stockCode);
            if (returnResult){
             break;
            }

        }
    }


    /***
     * 如果表不存在 创建表
     * @param stockCode
     */
    public static void createTableByTableName(String stockCode)   {
        String tableName="stock_info_" + stockCode;
        if(!isTableExist(tableName)){
                    stockInfoMapper.createTableByTableName(tableName);
        }
    }

    /**
     *  判断数据库表 是否存在
     * @param tableName
     * @return
     */
    public static boolean isTableExist(String tableName)   {
        boolean isExist=false;
        double tableExist = stockInfoMapper.isTableExist(tableName);
        if (tableExist==1){
            isExist= true;
        }
        return isExist;
    }


    /**
     * 根据 股票代码 获取 历史数据的 RUl
     * @param shareCode
     * @return
     * @throws ParseException
     */
    public static List<String>  getURLbyShareCode(String shareCode) throws ParseException {

        String tempURL="";
        List<String> urlList =new ArrayList<>();
        for (int year = 2019; year >2001 ; year--) {
            for (int season = 4; season >0 ; season--) {
                tempURL="http://quotes.money.163.com/trade/lsjysj_"+shareCode+".html?year="+year+"&season="+season;
                urlList.add(tempURL);
            }
        }
        return  urlList;
    }


    /**
     * 保存信息到数据库
     * @param stockInfo
     * @throws Exception
     */
    public static void insertDb(StockInfo stockInfo) throws Exception  {
        try {
            stockInfoMapper.addStockInfo(stockInfo);
        }catch (Exception e){
            throw e;
        }
    }


    /***
     * 解析 HTML 获取 历史信息 ，保存到数据库中
     * @param strArray
     * @param shareCode
     * @return
     * @throws Exception
     */
    public static boolean getBean(String[] strArray,String shareCode ) throws Exception {

        boolean returnResult=false;

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        StockInfo stockInfo =new StockInfo();

        double FailNum=0;
        for (int i = 0; i <strArray.length ; i++) {
            if (FailNum>10){
                returnResult =true;
                break;
            }

            String tempStr= strArray[i].replace("<td>","").replace("</td>","").replace("，","");
            int modInt =i%11;
            switch (modInt){
                case 0:
                    stockInfo.setStockDate(tempStr);
                    break;
                case 1:
                    stockInfo.setKpj(Double.valueOf(tempStr));
                    break;
                case 2:
                    stockInfo.setZgj(Double.valueOf(tempStr));
                    break;
                case 3:
                    stockInfo.setZdj(Double.valueOf(tempStr));
                    break;
                case 4:
                    stockInfo.setSpj(Double.valueOf(tempStr));
                    break;
                case 5:
                    stockInfo.setZde(Double.valueOf(tempStr));
                    break;
                case 6:
                    stockInfo.setZdf(Double.valueOf(tempStr));
                    break;
                case 7:
                    stockInfo.setCjl(Double.valueOf(tempStr.replace(",","")));
                    break;
                case 8:
                    stockInfo.setCjje(Double.valueOf(tempStr.replace(",","")));
                    break;
                case 9:
                    stockInfo.setZf(Double.valueOf(tempStr));
                    break;
                case 10:
                    stockInfo.setStockCode(shareCode);
                    stockInfo.setHsl(Double.valueOf(tempStr));
                    try {
                        stockInfo.setkValue(0);
                        stockInfo.setdValue(0);
                        stockInfo.setjValue(0);

                        //根据时间 判断数据 是否存在
                        boolean isExist=false;
                        double tableExist = stockInfoMapper.isRoweExist(stockInfo);
                        if (tableExist>=1){
                           throw  new Exception();
                        }else{
                            insertDb(stockInfo);
                        }
                    }catch (Exception e){
                        FailNum++;
                    }finally {
                        break;
                    }
            }
        }


        return returnResult;
    }


    /***
     * 删除 空表
     * @throws Exception
     */
    public static void delNullTable( ) throws Exception {
        //获取列表
        List<StockList> stockList = getStockList();

        StockInfo stockInfo =new StockInfo();

        List<String> resultlist =new ArrayList<>();
        List<String> resultlist222 = new ArrayList<>();

        for (int i = 0; i < stockList.size(); i++) {
            //循环列表
            StockList stockList1 =stockList.get(i);

            //判断表是否有数据
            stockInfo.setStockCode(stockList1.getStockCode().replaceAll("\t","")+"");

            try{
                List<StockInfo> stockInfoList = stockInfoMapper.getStockListByShareCode(stockInfo);
                if(stockInfoList==null||stockInfoList.size()<1){
                //没有数据--删除list
//                stockListMapper.delStockList(stockList1.getStockCode());
                //没有数据--删除大表
//                stockInfoMapper.delTableByStockCode(stockInfo);
                    resultlist222.add(stockInfo.getStockCode());

            }
            }
            catch (Exception e){
                resultlist.add(stockList1.getStockCode().replaceAll("\t","")+"");
            }

        }
        System.out.println(resultlist222);

    }


    /***
     * 查找数据为空的表
     * @throws Exception
     */
    public static void findTable( ) throws Exception {

        List<StockList> stockList =new ArrayList<>();
        StockInfo stockInfo =new StockInfo();

        String rrrr="";
        String[] arr={"000593","000594","000595","000596","000597","000598","000599","000600","000601","000602","000603","000605","000606","000607","000608","000609","000610","000611","000612","000613","000615","000617","000619","000620","000622","000623","000625","000626","000627","000628","000629","000630","000631","000632","000633","000635","000636","000637","000638","000639","000650","000651","000652","000655","000656","000657","000659","000661","000662","000663","000666","000667","000668","000669","000670","000671","000672","000673","000676","000677","000678","000679","000681","000682","000683","000685","000686","000687","000688","000690","000691","000692","000693","000697","000698","000700","000701","000702","000703","000705","000707","000708","000710","000711","000712","000713","000715","000716","000717","000718","000719","000720","000721","000722","000723","000725","000726","000727","000728","000729","000731","000732","000733","000735","000736","000737","000738","000739","000748","000750","000751","000752","000753","000755","000756","000757","000758","000759","000760","000761","000762","000766","000767","000768","000776","000777","000778","000779","000782","000783","000785","000786","000787","000788","000789","000790","000791","000792","000793","000795","000796","000797","000798","000799","000800","000801","000802","000806","000807","000809","000810","000811","000812","000813","000815","000816","000818","000819","000820","000822","000823","000825","000826","000827","000828","000829","000830","000831","000833","000835","000836","000837","000838","000839","000848","000850","000851","000852","000856","000858","000860","000861","000862","000863","000868","000869","000875","000876","000877","000878","000880","000881","000882","000883","000885","000886","000887","000888","000889","000890","000892","000893","000895","000897","000898","000899","000900","000901","000902","000903","000905","000906","000908","000909","000910","000911","000912","000913","000915","000916","000917","000918","000919","000920","000921","000922","000923","000925","000926","000927","000928","000929","000930","000931","000932","000933","000936","000937","000938","000939","000948","000949","000950","000951","000952","000953","000955","000957","000958","000959","000960","000961","000962","000963","000965","000966","000967","000968","000970","000971","000972","000973","000975","000976","000977","000978","000979","000980","000981","000982","000983","000985","000987","000988","000989","000990","000993","000995","000996","000997","000998","000999","001696","001896","001965","001979","002001","002002","002003","002004","002005","002006","002007","002008","002009","002010","002011","002012","002013","002014","002015","002016","002017","002018","002019","002020","002021","002022","002023","002024","002025","002026","002027","002028","002029","002030","002031","002032","002033","002034","002035","002036","002037","002038","002039","002040","002041","002042","002043","002044","002045","002046","002047","002048","002049","002050","002051","002052","002054","002055","002056","002057","002058","002059","002060","002061","002063","002064","002065","002066","002067","002068","002069","002070","002072","002073","002074","002075","002076","002077","002078","002079","002080","002081","002082","002083","002084","002085","002087","002088","002089","002090","002091","002093","002094","002095","002096","002097","002098","002099","002100","002101","002102","002103","002104","002105","002106","002107","002108","002109","002110","002111","002112","002113","002114","002115","002116","002117","002118","002119","002120","002121","002122","002123","002124","002125","002126","002127","002128","002129","002130","002131","002132"};
        for (int i = 0; i < arr.length; i++) {
            stockInfo.setStockCode(arr[i]);
            List<StockInfo> stockListByShareCode = stockInfoMapper.getStockListByShareCode(stockInfo);
            if (stockListByShareCode==null||stockListByShareCode.size()<1){
                rrrr=rrrr+arr[i]+",";
            }
        }

        System.out.println(rrrr);


    }




}
