package com.stock.services.impl;

import com.stock.Enum.CrossType;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockInfoActualtime;
import com.stock.bean.po.StockList;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.dao.IStockInfoActualtimeDao;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockInfoMacdServices;
import com.stock.services.IStockInfoServices;
import com.stock.services.IStockListServices;
import com.stock.services.IStockNewDataServices;
import com.stock.util.HtmlUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StockInfoServicesImpl implements IStockInfoServices {

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    HtmlUtil htmlUtil;


    @Autowired
    IStockListDao iStockListDao;


    @Autowired
    IStockNewDataDao iStockNewDataDao;

    @Autowired
    IStockInfoActualtimeDao iStockInfoActualtimeDao;

    @Autowired
    IStockInfoMacdServices iStockInfoMacdServices;

    @Autowired
    IStockInfoServices iStockInfoServices;

    @Autowired
    IStockNewDataServices iStockNewDataServices;


    @Override
    public List<StockInfo> getNewStockListByStockCode(String stockCode, String sortType, int limitNum) {
        return iStockInfoDao.getNewStockListByStockCode(stockCode, sortType, limitNum);
    }

    @Override
    public List<StockInfo> getStockListByStockCode(String stockCode, int limitNum) {
        return iStockInfoDao.getStockListByStockCode(stockCode, limitNum);
    }

//    @Override
//    public void createTableByTableName(String tableName) {
//        iStockInfoDao.createTableByTableName(tableName);
//    }


    @Override
    public void updateStockInfoMacd(StockInfo stockInfo) {
        iStockInfoDao.updateStockInfoMacd(stockInfo);
    }


    /***
     * 获取 单个 股票的 历史信息
     */
    @Override
    public void getStockInfoHistory(String stockCode) throws Exception {

        createTableByTableName(stockCode);
        List<String> urlList = getURLbyStockCode(stockCode);

        for (int i = 0; i < urlList.size(); i++) {
            String html = htmlUtil.getHtmlByURL(urlList.get(i), "UTF-8");
            //获取需要的正文
            html = htmlUtil.getHtmlByExpression("</thead[\\s\\S]*</tr>    </table>", html);
            html = htmlUtil.replaceHtml(" class='cGreen'", html);
            html = htmlUtil.replaceHtml(" class='cRed'", html);
            html = htmlUtil.getHtmlByExpression("<td>(.*?)</td>", html);
            String[] strings = htmlUtil.splitByExpression("</td>", html);
            if ("".equals(strings[0])) {
                continue;
            }
            boolean returnResult = getBean(strings, stockCode);
            if (returnResult) {
                break;
            }
        }
        System.out.println("获取数据成功，股票编号为：" + stockCode);
    }

    @Override
    public void delStockInfo(String stockCode, String stockDate) {
        iStockInfoDao.delStockInfo(stockCode, stockDate);
    }


    /***
     * 获取 单个 股票的 实时信息
     */
    @Override
    public void getStockInfoActualTime(String stockCode) throws Exception {

        String url = "http://www.baidu.com/s?wd=" + stockCode;
        String html = htmlUtil.getHtmlByURL(url, "UTF-8");
        html = htmlUtil.getHtmlByExpression("<span class=\"op-stockdynamic-moretab-cur-num c-gap-right-small\">(.*?)</span>", html);
        String[] strings = htmlUtil.splitByExpression("</span>", html);
        html = strings[0];
        html = html.replaceAll("<span class=\"op-stockdynamic-moretab-cur-num c-gap-right-small\">", "");

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date day = new Date();
        String stockDate = sdf2.format(day);

        // 进行相关处理
        String crossStockCode = insertStockInfoActualTime(stockCode, html);
        if (!"".equals(crossStockCode)) {
            //删除 推荐表中的数据
            iStockInfoActualtimeDao.deleteByStockCodeAndStockDate(stockCode, stockDate);
            //插入新数据
            StockInfoActualtime stockInfoActualtime = new StockInfoActualtime();
            stockInfoActualtime.setSpj(html);
            stockInfoActualtime.setStockCode(stockCode);
            stockInfoActualtime.setStockDate(stockDate);
            iStockInfoActualtimeDao.insert(stockInfoActualtime);
        } else {
            //删除 推荐表中的数据
            iStockInfoActualtimeDao.deleteByStockCodeAndStockDate(stockCode, stockDate);
        }
        System.out.println("实时金叉查询 查询完成  stockCode=" + stockCode);
    }


    public String insertStockInfoActualTime(String stockCode, String price) {

        try {
            Double.parseDouble(price);
        } catch (Exception e) {
            System.out.println("Double.parseDouble(price)= " + stockCode);
            return "";
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date day = new Date();
        String stockDate = sdf2.format(day);


        //删除今天的数据，现在的数据到保存到数据库中。
        iStockInfoDao.delStockInfo(stockCode, stockDate);
        iStockNewDataDao.deleteByStockCodeAndStockDate(stockCode, stockDate);

        //添加数据到数据库
        StockInfo stockInfo = new StockInfo();
        stockInfo.setStockCode(stockCode);
        stockInfo.setStockDate(stockDate);
        stockInfo.setSpj(Double.parseDouble(price));
        iStockInfoDao.addStockInfo(stockInfo);

        //计算MACD值
        iStockInfoMacdServices.getStockInfoMacd(stockCode, 1);

        //拷贝到新数据表中
        List<StockInfo> newStockListByStockCode = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(), 1);
        iStockNewDataDao.insert(newStockListByStockCode.get(0));

        //判断是否存在交叉
        List<StockInfo> newStockListByStockCodelist = iStockInfoServices.getNewStockListByStockCode(stockCode, SortType.ASC.toString(), 2);

        Map<String, Object> checkResult = iStockInfoMacdServices.isExistCross(newStockListByStockCodelist, 2, "11");
        if (!checkResult.isEmpty()) {
            if ((boolean) checkResult.get("result")) {
                return stockCode;
            }
        }
        return "";
    }


    /***
     * 如果表不存在 创建表
     * @param stockCode
     */
    public void createTableByTableName(String stockCode) {
        String tableName = "stock_info_" + stockCode;
        if (!iStockInfoDao.isTableExist(tableName)) {
            iStockInfoDao.createTableByTableName(tableName);
        }
    }

    /**
     * 根据 股票代码 获取 历史数据的 RUl
     *
     * @param StockCode
     * @return
     * @throws ParseException
     */
    public static List<String> getURLbyStockCode(String StockCode) throws ParseException {

        String tempURL = "";
        List<String> urlList = new ArrayList<>();
        for (int year = 2019; year > 2001; year--) {
            for (int season = 4; season > 0; season--) {
                tempURL = "http://quotes.money.163.com/trade/lsjysj_" + StockCode + ".html?year=" + year + "&season=" + season;
                urlList.add(tempURL);
            }
        }
        return urlList;
    }


    /***
     * 解析 HTML 获取 历史信息 ，保存到数据库中
     * @param strArray
     * @param StockCode
     * @return
     * @throws Exception
     */
    public boolean getBean(String[] strArray, String StockCode) throws Exception {

        boolean returnResult = false;

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        StockInfo stockInfo = new StockInfo();

        double FailNum = 0;
        for (int i = 0; i < strArray.length; i++) {
            if (FailNum > 10) {
                returnResult = true;
                break;
            }
            String tempStr = strArray[i].replace("<td>", "").replace("</td>", "").replace("，", "");
            int modInt = i % 11;
            switch (modInt) {
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
                    stockInfo.setCjl(Double.valueOf(tempStr.replace(",", "")));
                    break;
                case 8:
                    stockInfo.setCjje(Double.valueOf(tempStr.replace(",", "")));
                    break;
                case 9:
                    stockInfo.setZf(Double.valueOf(tempStr));
                    break;
                case 10:
                    stockInfo.setStockCode(StockCode);
                    stockInfo.setHsl(Double.valueOf(tempStr));

                    stockInfo.setKValue(0);
                    stockInfo.setDValue(0);
                    stockInfo.setJValue(0);

                    //根据时间 判断数据 是否存在
                    boolean isExist = false;
                    boolean tableExist = iStockInfoDao.isRoweExist(stockInfo);
                    if (tableExist) {
                        FailNum++;
                    } else {
                        iStockInfoDao.addStockInfo(stockInfo);
                    }
            }
        }
        return returnResult;
    }


    /***
     * 删除 空表
     * @throws Exception
     */

    @Override
    public void delNullTable() throws Exception {
        //获取列表
        List<StockList> stockList = iStockListDao.getStockList();
        StockInfo stockInfo = new StockInfo();
        List<String> resultlist = new ArrayList<>();
        List<String> resultlist222 = new ArrayList<>();

        for (int i = 0; i < stockList.size(); i++) {
            //循环列表
            StockList stockList1 = stockList.get(i);

            //判断表是否有数据
            stockInfo.setStockCode(stockList1.getStockCode().replaceAll("\t", "") + "");

            try {
                List<StockInfo> stockInfoList = iStockInfoDao.getStockListByStockCode(stockInfo.getStockCode(), 999999999);
                if (stockInfoList == null || stockInfoList.size() < 1) {
                    //没有数据--删除list
//                stockListMapper.delStockList(stockList1.getStockCode());
                    //没有数据--删除大表
//                stockInfoMapper.delTableByStockCode(stockInfo);
                    resultlist222.add(stockInfo.getStockCode());
                }
            } catch (Exception e) {
                resultlist.add(stockList1.getStockCode().replaceAll("\t", "") + "");
            }

        }

    }


    /***
     * 查找数据为空的表
     * @throws Exception
     */
    @Override
    public void findTable() throws Exception {
        List<StockList> stockList = new ArrayList<>();
        StockInfo stockInfo = new StockInfo();

        String rrrr = "";
        String[] arr = {"000593", "000594", "000595", "000596", "000597", "000598", "000599", "000600", "000601", "000602", "000603", "000605", "000606", "000607", "000608", "000609", "000610", "000611", "000612", "000613", "000615", "000617", "000619", "000620", "000622", "000623", "000625", "000626", "000627", "000628", "000629", "000630", "000631", "000632", "000633", "000635", "000636", "000637", "000638", "000639", "000650", "000651", "000652", "000655", "000656", "000657", "000659", "000661", "000662", "000663", "000666", "000667", "000668", "000669", "000670", "000671", "000672", "000673", "000676", "000677", "000678", "000679", "000681", "000682", "000683", "000685", "000686", "000687", "000688", "000690", "000691", "000692", "000693", "000697", "000698", "000700", "000701", "000702", "000703", "000705", "000707", "000708", "000710", "000711", "000712", "000713", "000715", "000716", "000717", "000718", "000719", "000720", "000721", "000722", "000723", "000725", "000726", "000727", "000728", "000729", "000731", "000732", "000733", "000735", "000736", "000737", "000738", "000739", "000748", "000750", "000751", "000752", "000753", "000755", "000756", "000757", "000758", "000759", "000760", "000761", "000762", "000766", "000767", "000768", "000776", "000777", "000778", "000779", "000782", "000783", "000785", "000786", "000787", "000788", "000789", "000790", "000791", "000792", "000793", "000795", "000796", "000797", "000798", "000799", "000800", "000801", "000802", "000806", "000807", "000809", "000810", "000811", "000812", "000813", "000815", "000816", "000818", "000819", "000820", "000822", "000823", "000825", "000826", "000827", "000828", "000829", "000830", "000831", "000833", "000835", "000836", "000837", "000838", "000839", "000848", "000850", "000851", "000852", "000856", "000858", "000860", "000861", "000862", "000863", "000868", "000869", "000875", "000876", "000877", "000878", "000880", "000881", "000882", "000883", "000885", "000886", "000887", "000888", "000889", "000890", "000892", "000893", "000895", "000897", "000898", "000899", "000900", "000901", "000902", "000903", "000905", "000906", "000908", "000909", "000910", "000911", "000912", "000913", "000915", "000916", "000917", "000918", "000919", "000920", "000921", "000922", "000923", "000925", "000926", "000927", "000928", "000929", "000930", "000931", "000932", "000933", "000936", "000937", "000938", "000939", "000948", "000949", "000950", "000951", "000952", "000953", "000955", "000957", "000958", "000959", "000960", "000961", "000962", "000963", "000965", "000966", "000967", "000968", "000970", "000971", "000972", "000973", "000975", "000976", "000977", "000978", "000979", "000980", "000981", "000982", "000983", "000985", "000987", "000988", "000989", "000990", "000993", "000995", "000996", "000997", "000998", "000999", "001696", "001896", "001965", "001979", "002001", "002002", "002003", "002004", "002005", "002006", "002007", "002008", "002009", "002010", "002011", "002012", "002013", "002014", "002015", "002016", "002017", "002018", "002019", "002020", "002021", "002022", "002023", "002024", "002025", "002026", "002027", "002028", "002029", "002030", "002031", "002032", "002033", "002034", "002035", "002036", "002037", "002038", "002039", "002040", "002041", "002042", "002043", "002044", "002045", "002046", "002047", "002048", "002049", "002050", "002051", "002052", "002054", "002055", "002056", "002057", "002058", "002059", "002060", "002061", "002063", "002064", "002065", "002066", "002067", "002068", "002069", "002070", "002072", "002073", "002074", "002075", "002076", "002077", "002078", "002079", "002080", "002081", "002082", "002083", "002084", "002085", "002087", "002088", "002089", "002090", "002091", "002093", "002094", "002095", "002096", "002097", "002098", "002099", "002100", "002101", "002102", "002103", "002104", "002105", "002106", "002107", "002108", "002109", "002110", "002111", "002112", "002113", "002114", "002115", "002116", "002117", "002118", "002119", "002120", "002121", "002122", "002123", "002124", "002125", "002126", "002127", "002128", "002129", "002130", "002131", "002132"};
        for (int i = 0; i < arr.length; i++) {
            stockInfo.setStockCode(arr[i]);
            List<StockInfo> stockListByStockCode = iStockInfoDao.getStockListByStockCode(stockInfo.getStockCode(), 999999999);
            if (stockListByStockCode == null || stockListByStockCode.size() < 1) {
                rrrr = rrrr + arr[i] + ",";
            }
        }
        //System.out.println(rrrr);
    }

}
