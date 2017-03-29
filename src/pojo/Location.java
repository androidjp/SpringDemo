package pojo;


/**
 * 定位信息
 * Created by androidjp on 2017/1/4.
 */
public class Location{
    //    @PrimaryKey
    private String location_id;
    public String city;
    public String province;
    public String street;
    public double latitude;//纬度
    public double longitude;//经度

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }
}
