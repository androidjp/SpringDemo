package model.impl.record;

import model.IRecordManagement;
import model.IRequestCallback;
import model.RequestManager;
import model.connection_pool.ConPool;
import pojo.Location;
import pojo.Record;
import pojo.RecordRes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 记录管理实现类
 * Created by androidjp on 2017/2/28.
 */
public class RecordManager extends RequestManager implements IRecordManagement{

    private static boolean isFinishSaveLocation  = false;
    private static boolean isFinishSaveRecordRes = false;

//    private RecordManager(){
//
//    }
//
//    public static RecordManager getsInstance(){
//        return SingletonHolder.sInstance;
//    }
//
//    private static class SingletonHolder {
//        private static final RecordManager sInstance = new RecordManager();
//    }



    @Override
    public void getRecordHistory(String user_id, int page) {
        String sql = "select * from record where user_id = ? limit ?,8 order by record_time desc";
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.setString(1,user_id);
            preparedStatement.setInt(2,page);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Record> list = new ArrayList<>();
            while(resultSet.next()){
                //TODO: 获取数据，并返回列表
                Record record = new Record();
                record.setRecord_id(resultSet.getString("record_id"));
                resultSet.getString("user_id");
                resultSet.getString("location_id");
                record.record_time = resultSet.getDate("record_time").getTime();
                resultSet.getInt("hurt_level");
                resultSet.getFloat("salary");
                resultSet.getInt("relatives_count");
                resultSet.getInt("has_spouse");
                resultSet.getInt("responsibility");
                resultSet.getInt("driving_tools");
                resultSet.getFloat("medical_free");
                resultSet.getInt("hospital_days");
                resultSet.getInt("tardy_days");
                resultSet.getInt("nutrition_days");
                resultSet.getInt("nursing_days");
                resultSet.getString("result_id");
                resultSet.getFloat("pay");
                list.add(record);
            }
            if (mCallback!=null){
                mCallback.finish(list);
            }

        } catch (SQLException e) {
            if (mCallback!=null)
                mCallback.error("获取理赔历史列表失败，"+ e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void addRecord(Record record) {
        ///TODO: 注意：此时进来的record是带着location内容的！！！
        isFinishSaveLocation = false;
        isFinishSaveRecordRes = false;

        //TODO: 先计算理赔结果
        CalculateManager calculateManager = new CalculateManager();
        calculateManager.setRequestCallback(new IRequestCallback<RecordRes>() {
            @Override
            public void finish(RecordRes value) {
                ///成功计算，现在，保存RecordRes 并把 RecordRes.总赔偿金额 存入Record中
                RecordRes myRecordRes = value;

                record.pay = value.money_pay;
                //准备保存record

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
                            if (mCallback!=null)
                                addingRecord(record);
                    }

                    @Override
                    public void error(String msg) {
                        if (mCallback!=null)
                            mCallback.error(msg);
                    }
                };

                //1. 先存储location
                Location location = record.getLocation();
                if (location==null) {
                    if (mCallback != null)
                        mCallback.error("record不存在location！！");
                    return;
                }

                LocationManager locationManager = new LocationManager();
                locationManager.setRequestCallback(callback);
                locationManager.addLocation(location.getLocation_id()
                        ,location.city,location.province,location.street
                ,location.latitude,location.longitude);

                //2. 后存储record_res
                RecordResManager recordResManager = new RecordResManager();
                recordResManager.setRequestCallback(callback);
                recordResManager.saveRecordRes(value);
            }

            @Override
            public void error(String msg) {
                if (mCallback!=null)
                    mCallback.error(msg);
            }
        });
        calculateManager.calculateRecord(record);

        //TODO： 后进行数据库存储操作
//        String sql = "insert into record(" +
//                "user_id,location_id, record_time" +
//                ",hurt_level, salary, relatives_count" +
//                ",has_spouse, id_type, responsibility" +
//                ",driving_tools, medical_free, hosptial_days" +
//                ",tardy_days, nutrition_days, nursing_days" +
//                ",result_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        try {
//            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
//                    .getCon().getConnection().prepareStatement(sql);
//            preparedStatement.setString(1,user_id);
//            preparedStatement.setInt(2,page);
//            preparedStatement.execute();
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }


    private void addingRecord(Record record){

        String saveSQL = "insert into record(record_id,user_id,location_id" +
                ",record_time, hurt_level,salary,relatives_count,has_spouse" +
                "id_type,responsibility,driving_tools,medical_free,hospital_days" +
                ",tardy_days,nutrition_days,nursing_days,result_id,pay) values(" +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(saveSQL);
            preparedStatement.setString(1,record.getRecord_id());
            preparedStatement.setString(2,record.getUser_id());
            preparedStatement.setString(3,record.getLocation_id());
            preparedStatement.setDate(4,new Date(record.record_time));
            preparedStatement.setInt(5,record.hurt_level);
            preparedStatement.setFloat(6,record.salary);
            preparedStatement.setInt(7, record.relatives_count);
            preparedStatement.setInt(8,(record.has_spouse?1:0));
            preparedStatement.setInt(9,record.id_type);
            preparedStatement.setInt(10,record.responsibility);
            preparedStatement.setInt(11,record.driving_tools);
            preparedStatement.setFloat(12,record.medical_free);
            preparedStatement.setInt(13,record.hospital_days);
            preparedStatement.setInt(14,record.tardy_days);
            preparedStatement.setInt(15,record.nutrition_days);
            preparedStatement.setInt(16,record.nursing_days);
            preparedStatement.setString(17,record.getResult_id());
            preparedStatement.setFloat(18,record.pay);

            preparedStatement.execute();

            if (mCallback!=null)
                mCallback.finish(record);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
