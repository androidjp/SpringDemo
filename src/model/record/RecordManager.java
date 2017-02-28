package model.record;

import model.IRecordManagement;
import model.RequestManager;
import model.impl.connection_pool.ConPool;
import pojo.Record;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 记录管理实现类
 * Created by androidjp on 2017/2/28.
 */
public class RecordManager extends RequestManager<Record> implements IRecordManagement{


    private RecordManager(){

    }

    public static RecordManager getsInstance(){
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final RecordManager sInstance = new RecordManager();
    }



    @Override
    public void getRecordHistory(String user_id, int page) {
        String sql = "select * from record where user_id = ? limit ?,8 order by record_time desc";
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.setString(1,user_id);
            preparedStatement.setInt(2,page);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                //TODO: 获取数据，并返回列表
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addRecord(Record record) {
        //TODO: 先计算理赔结果
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
}
