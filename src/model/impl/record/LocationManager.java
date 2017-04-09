package model.impl.record;

import model.ILocation;
import model.RequestManager;
import model.connection_pool.ConPool;
import pojo.Location;
import utils.ChineseUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Location 管理类（数据库操作相关 ）
 * Created by androidjp on 2017/3/1.
 */
public class LocationManager extends RequestManager implements ILocation {

//    private LocationManager() {
//
//    }
//
//    public static LocationManager getsInstance() {
//        return LocationManager.SingletonHolder.sInstance;
//    }
//
//    private static class SingletonHolder {
//        private static final LocationManager sInstance = new LocationManager();
//    }


    @Override
    public void addLocation(String location_id, String city, String province, String street, double latitude, double longitude) {
        String sql = "insert into location(location_id, city , province, street, latitude, longitude) values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon()
                    .getConnection()
                    .prepareStatement(sql);
            preparedStatement.setString(1, location_id);
//            preparedStatement.setString(2, ChineseUtil.toUTF8(city));
//            preparedStatement.setString(3, ChineseUtil.toUTF8(province));
//            preparedStatement.setString(4, ChineseUtil.toUTF8(street));
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, province);
            preparedStatement.setString(4, street);
            preparedStatement.setDouble(5, latitude);
            preparedStatement.setDouble(6, longitude);
            preparedStatement.execute();
            preparedStatement.close();


            if (mCallback != null) {
                mCallback.finish("add location success");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Location getLocation(String location_id) {
        String sql = "select * from location where location_id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon()
                    .getConnection()
                    .prepareStatement(sql);
            preparedStatement.setString(1, location_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Location location = new Location();
                location.setLocation_id(resultSet.getString("location_id"));
//                location.city = ChineseUtil.adjustMessCode(resultSet.getString("city"));
//                location.province = ChineseUtil.adjustMessCode(resultSet.getString("province"));
//                location.street = ChineseUtil.adjustMessCode(resultSet.getString("street"));
                location.city = resultSet.getString("city");
                location.province = resultSet.getString("province");
                location.street = resultSet.getString("street");
                location.latitude = resultSet.getDouble("latitude");
                location.longitude = resultSet.getDouble("longitude");
                if (mCallback != null) {
                    mCallback.finish(location);
                }
                return location;
            }

        } catch (SQLException e) {
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
