package model;

import pojo.Record;

/**
 * Record管理
 * Created by androidjp on 2017/2/28.
 */
public interface IRecordManagement {

    /// 获取第page页的理赔历史记录
     void getRecordHistory(String user_id , int page);
     /// 添加一条理赔记录（相当于请求，计算出结果，并同时保存和返回数据）
     void addRecord(Record record);
}
