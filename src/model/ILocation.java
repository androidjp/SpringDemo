package model;

import pojo.Location;

/**
 * Created by androidjp on 2017/3/1.
 */
public interface ILocation {
    ///添加 location
    void addLocation(String location_id, String city,String province , String street, double latitude , double longitude);
    ///获取 理赔记录 对应的 地理位置
    Location getLocation(String location_id);
}
