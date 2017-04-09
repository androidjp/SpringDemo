package model.impl.record;

import base.Constant;
import model.IRecordManagement;
import model.IRequestCallback;
import model.RequestManager;
import model.connection_pool.ConPool;
import pojo.Location;
import pojo.Record;
import pojo.RecordRes;
import pojo.RelativeItemMsg;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 记录管理实现类
 * Created by androidjp on 2017/2/28.
 */
public class RecordManager extends RequestManager implements IRecordManagement {

    private static boolean isFinishSaveLocation = false;
    private static boolean isFinishSaveRecordRes = false;
    private static boolean isFinishSaveRelatives = false;

    private RecordRes mRecordRes = null;

    @Override
    public void getRefreshList(String user_id, long record_time) {
        String sql = "select * from record  left join location on record.location_id=location.location_id where record.user_id=?  AND record_time >? order by record_time desc";

        if (record_time == 0) {
            firstRefresh(user_id);
            return;
        }

        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user_id);
            preparedStatement.setTimestamp(2, new Timestamp(record_time));

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Record> list = new ArrayList<>();
            while (resultSet.next()) {
                //TODO: 获取数据，并返回列表
                Record record = new Record();
                record.setRecord_id(resultSet.getString(Constant.RECORD_ID));
                record.setUser_id(resultSet.getString(Constant.USER_ID));
                record.setLocation_id(resultSet.getString(Constant.LOCATION_ID));
                Location location = new Location();
                location.setLocation_id(resultSet.getString(Constant.LOCATION_ID));
                if (resultSet.getString(Constant.CITY)!=null)
                    location.city = resultSet.getString(Constant.CITY);
                if (resultSet.getString(Constant.PROVINCE)!=null)
                    location.province = resultSet.getString(Constant.PROVINCE);
                if (resultSet.getString(Constant.STREET)!=null)
                    location.street = resultSet.getString(Constant.STREET);
                if (resultSet.getString(Constant.LATITUDE)!=null)
                    location.latitude = Double.valueOf(resultSet.getString(Constant.LATITUDE));
                if (resultSet.getString(Constant.LONGITUDE)!=null)
                    location.longitude = Double.valueOf(resultSet.getString(Constant.LONGITUDE));
                record.setLocation(location);
                record.record_time = resultSet.getTimestamp(Constant.RECORD_TIME).getTime();
                record.hurt_level = resultSet.getInt(Constant.HURT_LEVEL);
                record.salary = resultSet.getFloat(Constant.SALARY);
                record.relatives_count = resultSet.getInt(Constant.RELATIVES_COUNT);
                record.has_spouse = (resultSet.getInt(Constant.HAS_SPOUSE) == 0 ? false : true);
                record.responsibility = resultSet.getInt(Constant.RESPONSIBILITY);
                record.driving_tools = resultSet.getInt(Constant.DRIVING_TOOLS);
                record.medical_free = resultSet.getFloat(Constant.MEDICAL_FREE);
                record.hospital_days = resultSet.getInt(Constant.HOSPITAL_DAYS);
                record.tardy_days = resultSet.getInt(Constant.TARDY_DAYS);
                record.nutrition_days = resultSet.getInt(Constant.NUTRITION_DAYS);
                record.nursing_days = resultSet.getInt(Constant.NURSING_DAYS);
                record.setResult_id(resultSet.getString(Constant.RESULT_ID));
                record.pay = resultSet.getFloat(Constant.PAY);
                list.add(record);
            }
//            if (mCallback != null) {
//                mCallback.finish(list);
//            }
            getLocationForRecord(list);

        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("获取理赔历史列表失败，" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 第一次加载记录列表的时候需要判断
     *
     * @param user_id
     */
    private void firstRefresh(String user_id) {
        String sql = "select * from record left join location on record.location_id=location.location_id where record.user_id=? order by record_time desc limit 8";
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Record> list = new ArrayList<>();
            while (resultSet.next()) {
                //TODO: 获取数据，并返回列表
                Record record = new Record();
                record.setRecord_id(resultSet.getString(Constant.RECORD_ID));
                record.setUser_id(resultSet.getString(Constant.USER_ID));
                record.setLocation_id(resultSet.getString(Constant.LOCATION_ID));
                Location location = new Location();
                location.setLocation_id(resultSet.getString(Constant.LOCATION_ID));
                if (resultSet.getString(Constant.CITY)!=null)
                    location.city = resultSet.getString(Constant.CITY);
                if (resultSet.getString(Constant.PROVINCE)!=null)
                    location.province = resultSet.getString(Constant.PROVINCE);
                if (resultSet.getString(Constant.STREET)!=null)
                    location.street = resultSet.getString(Constant.STREET);
                if (resultSet.getString(Constant.LATITUDE)!=null)
                    location.latitude = Double.valueOf(resultSet.getString(Constant.LATITUDE));
                if (resultSet.getString(Constant.LONGITUDE)!=null)
                    location.longitude = Double.valueOf(resultSet.getString(Constant.LONGITUDE));
                record.setLocation(location);
                record.record_time = resultSet.getTimestamp(Constant.RECORD_TIME).getTime();
                record.hurt_level = resultSet.getInt(Constant.HURT_LEVEL);
                record.salary = resultSet.getFloat(Constant.SALARY);
                record.relatives_count = resultSet.getInt(Constant.RELATIVES_COUNT);
                record.has_spouse = (resultSet.getInt(Constant.HAS_SPOUSE) == 0 ? false : true);
                record.responsibility = resultSet.getInt(Constant.RESPONSIBILITY);
                record.driving_tools = resultSet.getInt(Constant.DRIVING_TOOLS);
                record.medical_free = resultSet.getFloat(Constant.MEDICAL_FREE);
                record.hospital_days = resultSet.getInt(Constant.HOSPITAL_DAYS);
                record.tardy_days = resultSet.getInt(Constant.TARDY_DAYS);
                record.nutrition_days = resultSet.getInt(Constant.NUTRITION_DAYS);
                record.nursing_days = resultSet.getInt(Constant.NURSING_DAYS);
                record.setResult_id(resultSet.getString(Constant.RESULT_ID));
                record.pay = resultSet.getFloat(Constant.PAY);
                list.add(record);
            }
            if (mCallback != null) {
                mCallback.finish(list);
            }
//            getLocationForRecord(list);

        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("获取理赔历史列表失败，" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void getMoreList(String user_id, long record_time) {

        String sql = "select * from record left join location on record.location_id=location.location_id where record.user_id=? AND record_time <? order by record_time desc limit 8";

        if (record_time == 0)
            if (mCallback != null)
                mCallback.finish(null);

        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user_id);
            preparedStatement.setTimestamp(2, new Timestamp(record_time));

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Record> list = new ArrayList<>();
            while (resultSet.next()) {
                //TODO: 获取数据，并返回列表
                //TODO: 获取数据，并返回列表
                Record record = new Record();
                record.setRecord_id(resultSet.getString(Constant.RECORD_ID));
                record.setUser_id(resultSet.getString(Constant.USER_ID));
                record.setLocation_id(resultSet.getString(Constant.LOCATION_ID));
                Location location = new Location();
                location.setLocation_id(resultSet.getString(Constant.LOCATION_ID));
                if (resultSet.getString(Constant.CITY)!=null)
                    location.city = resultSet.getString(Constant.CITY);
                if (resultSet.getString(Constant.PROVINCE)!=null)
                    location.province = resultSet.getString(Constant.PROVINCE);
                if (resultSet.getString(Constant.STREET)!=null)
                    location.street = resultSet.getString(Constant.STREET);
                if (resultSet.getString(Constant.LATITUDE)!=null)
                    location.latitude = Double.valueOf(resultSet.getString(Constant.LATITUDE));
                if (resultSet.getString(Constant.LONGITUDE)!=null)
                    location.longitude = Double.valueOf(resultSet.getString(Constant.LONGITUDE));
                record.setLocation(location);
                record.record_time = resultSet.getTimestamp(Constant.RECORD_TIME).getTime();
                record.hurt_level = resultSet.getInt(Constant.HURT_LEVEL);
                record.salary = resultSet.getFloat(Constant.SALARY);
                record.relatives_count = resultSet.getInt(Constant.RELATIVES_COUNT);
                record.has_spouse = (resultSet.getInt(Constant.HAS_SPOUSE) == 0 ? false : true);
                record.responsibility = resultSet.getInt(Constant.RESPONSIBILITY);
                record.driving_tools = resultSet.getInt(Constant.DRIVING_TOOLS);
                record.medical_free = resultSet.getFloat(Constant.MEDICAL_FREE);
                record.hospital_days = resultSet.getInt(Constant.HOSPITAL_DAYS);
                record.tardy_days = resultSet.getInt(Constant.TARDY_DAYS);
                record.nutrition_days = resultSet.getInt(Constant.NUTRITION_DAYS);
                record.nursing_days = resultSet.getInt(Constant.NURSING_DAYS);
                record.setResult_id(resultSet.getString(Constant.RESULT_ID));
                record.pay = resultSet.getFloat(Constant.PAY);
                list.add(record);
            }
            if (mCallback!=null){
                mCallback.finish(list);
            }
//            getLocationForRecord(list);

        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("获取理赔历史列表失败，" + e.getMessage());
            e.printStackTrace();
        }
    }

    ///为每一个Record 项，给上其location成员
    private void getLocationForRecord(List<Record> recordList) {
        LocationManager locationManager = new LocationManager();
        for (Record item : recordList) {
            item.setLocation(locationManager.getLocation(item.getLocation_id()));
        }
        if (mCallback != null) {
            mCallback.finish(recordList);
        }
    }

    @Override
    public void addRecord(Record record) {
        ///TODO: 注意：此时进来的record是带着location内容 和 List<RelativeItemMsg>的！！！
        isFinishSaveLocation = false;
        isFinishSaveRecordRes = false;

        //TODO: 先计算理赔结果
        CalculateManager calculateManager = new CalculateManager();
        calculateManager.setRequestCallback(new IRequestCallback<RecordRes>() {
            @Override
            public void finish(RecordRes value) {
                ///成功计算，现在，保存RecordRes 并把 RecordRes.总赔偿金额 存入Record中
                mRecordRes = value;
                record.pay = value.money_pay;
                //准备保存record

                ///TODO: 如果Record没有user_id或者user_id为空时，直接返回finish(value)即可
                if(record.getUser_id() == null || record.getUser_id().length() ==0){
                    if (mCallback!=null)
                        mCallback.finish(mRecordRes);
                    return;
                }


                //设定一个通用返回Object（可强转String）的IRequestCallback
                //用于执行最终的record操作
                IRequestCallback callback = new IRequestCallback() {
                    @Override
                    public void finish(Object value) {
                        String resultStr = (String) value;
                        if ("add location success".equals(resultStr))
                            isFinishSaveLocation = true;
                        else if ("add record_res success".equals(resultStr))
                            isFinishSaveRecordRes = true;
                        //保存成功了
                        if (isFinishSaveLocation && isFinishSaveRecordRes)
                            addingRecord(record);
                    }

                    @Override
                    public void error(String msg) {
                        if (mCallback != null)
                            mCallback.error(msg);
                        return;
                    }
                };

                //1. 先存储location
                Location location = record.getLocation();
                if (location == null) {
                    if (mCallback != null)
                        mCallback.error("record不存在location！！");
                    return;
                }
                LocationManager locationManager = new LocationManager();
                locationManager.setRequestCallback(callback);
                locationManager.addLocation(location.getLocation_id()
                        , location.city, location.province, location.street
                        , location.latitude, location.longitude);

                //2. 后存储record_res
                RecordResManager recordResManager = new RecordResManager();
                recordResManager.setRequestCallback(callback);
                recordResManager.saveRecordRes(value);

            }

            @Override
            public void error(String msg) {
                if (mCallback != null)
                    mCallback.error(msg);
            }
        });
        calculateManager.calculateRecord(record);
    }


    private void addingRecord(Record record) {
        if (!(isFinishSaveLocation && isFinishSaveRecordRes))
            return;

//        String sql = "insert into location(location_id, city , province, street, latitude, longitude) values(?,?,?,?,?,?)";
        String sql = "insert into record(record_id,user_id,location_id,record_time" +
                ",hurt_level,salary,relatives_count,has_spouse" +
                ",id_type,responsibility,driving_tools,medical_free" +
                ",hospital_days,tardy_days,nutrition_days,nursing_days" +
                ",result_id,pay) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, record.getRecord_id());
            preparedStatement.setString(2, record.getUser_id());
            preparedStatement.setString(3, record.getLocation_id());
//            preparedStatement.setDate(4,new Date(record.record_time));
//            preparedStatement.setLong(4,record.record_time);
            preparedStatement.setTimestamp(4, new Timestamp(record.record_time));
//            preparedStatement.setDate(4,null);
            preparedStatement.setInt(5, record.hurt_level);
            preparedStatement.setFloat(6, record.salary);
            preparedStatement.setInt(7, record.relatives_count);
            preparedStatement.setInt(8, (record.has_spouse ? 1 : 0));
            preparedStatement.setInt(9, record.id_type);
            preparedStatement.setInt(10, record.responsibility);
            preparedStatement.setInt(11, record.driving_tools);
            preparedStatement.setFloat(12, record.medical_free);
            preparedStatement.setInt(13, record.hospital_days);
            preparedStatement.setInt(14, record.tardy_days);
            preparedStatement.setInt(15, record.nutrition_days);
            preparedStatement.setInt(16, record.nursing_days);
            preparedStatement.setString(17, record.getResult_id());
            preparedStatement.setFloat(18, record.pay);

            preparedStatement.execute();

            addingRelatives(record.getRelative_msg_list());
        } catch (SQLException e) {
            if (mCallback!=null)
                mCallback.error("添加record失败！"+e.getMessage());
            e.printStackTrace();
        }
    }

    private void addingRelatives(List<RelativeItemMsg> relative_msg_list) {

        //3. 再存储 relative_item_msg 列表
        RelativesItemMsgManager relativesItemMsgManager = new RelativesItemMsgManager();
        relativesItemMsgManager.setRequestCallback(new IRequestCallback<List<RelativeItemMsg>>() {
            @Override
            public void finish(List<RelativeItemMsg> value) {
                isFinishSaveRelatives = true;
                if (mCallback!=null)
                    mCallback.finish(mRecordRes);
            }

            @Override
            public void error(String msg) {
                if (mCallback !=null)
                    mCallback.error(msg);
            }
        });
        relativesItemMsgManager.saveRelatives(relative_msg_list);
    }
}
