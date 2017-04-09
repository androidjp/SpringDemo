package model;

import pojo.Record;

/**
 * Record管理
 * Created by androidjp on 2017/2/28.
 */
public interface IRecordManagement {

    /// 下拉刷新
    void getRefreshList(String user_id, long record_time);

    /// 上拉加载更多
    void getMoreList(String user_id, long record_time);

    /// 添加一条理赔记录（相当于请求，计算出结果，并同时保存和返回数据）
    void addRecord(Record record);
}
