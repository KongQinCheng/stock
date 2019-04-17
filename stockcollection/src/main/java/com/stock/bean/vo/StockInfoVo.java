package com.stock.bean.vo;

import lombok.Data;

@Data
public class StockInfoVo {
    private String  stockCode ;  //代码
    private String stockBeginTime;  //时间
    private String stockEndTime;  //时间
    private String searchType;
    private int limitNum;
}
