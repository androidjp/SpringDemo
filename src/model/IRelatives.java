package model;

import pojo.RelativeItemMsg;

import java.util.List;

/**
 * Created by androidjp on 2017/4/1.
 */
public interface IRelatives {

    ///保存需抚养人列表
    void saveRelatives(List<RelativeItemMsg> dataList);

    //获取需抚养人列表
    void getRelatives(String record_id);

}
