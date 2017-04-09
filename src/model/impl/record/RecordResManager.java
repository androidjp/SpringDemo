package model.impl.record;

import base.Constant;
import model.RequestManager;
import model.connection_pool.ConPool;
import pojo.RecordRes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Created by androidjp on 2017/3/1.
 */
public class RecordResManager extends RequestManager{

    private String saveResultSQL = "INSERT INTO record_res(" +
            "result_id,money_pay,money_hurt,money_heart," +
            "money_tardy,money_medical,money_nursing,money_nutrition,money_hospital_allowance," +
            "money_relatives,money_bury,money_hurt_info,money_heart_info," +
            "money_nursing_info,money_nutrition_info,money_tardy_info," +
            "money_medical_info,money_hospital_allowance_info, money_relatives_info," +
            "money_bury_info) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private String getResultSQL = "SELECT * FROM record_res where result_id = ?";

    /**
     * 保存理赔结果
     * @param recordRes 理赔结果对象
     */
    public void saveRecordRes(RecordRes recordRes){
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon()
                    .getConnection()
                    .prepareStatement(saveResultSQL);

            preparedStatement.setString(1,recordRes.getResult_id());
            preparedStatement.setFloat(2,recordRes.money_pay);
            preparedStatement.setFloat(3,recordRes.money_hurt);
            preparedStatement.setFloat(4,recordRes.money_heart);
            preparedStatement.setFloat(5,recordRes.money_tardy);
            preparedStatement.setFloat(6,recordRes.money_medical);
            preparedStatement.setFloat(7,recordRes.money_nursing);
            preparedStatement.setFloat(8,recordRes.money_nutrition);
            preparedStatement.setFloat(9,recordRes.money_hospital_allowance);
            preparedStatement.setFloat(10,recordRes.money_relatives);
            preparedStatement.setFloat(11,recordRes.money_bury);
            preparedStatement.setString(12,recordRes.money_hurt_info);
            preparedStatement.setString(13,recordRes.money_heart_info);
            preparedStatement.setString(14,recordRes.money_nursing_info);
            preparedStatement.setString(15,recordRes.money_nutrition_info);
            preparedStatement.setString(16,recordRes.money_tardy_info);
            preparedStatement.setString(17,recordRes.money_medical_info);
            preparedStatement.setString(18,recordRes.money_hospital_allowance_info);
            preparedStatement.setString(19,recordRes.money_relatives_info);
            preparedStatement.setString(20,recordRes.money_bury_info);

            preparedStatement.execute();

            ///成功插入一个理赔结果数据，然后，将结果
            if (mCallback!=null){
                mCallback.finish("add record_res success");
            }

        } catch (SQLException e) {
            if (mCallback!=null)
            {
                mCallback.error("inserting recordRes error！");
            }
            e.printStackTrace();
        }
    }

    /**
     * 获取理赔结果
     * @param resultID 理赔结果ID
     */
    public void getRecordRes(String resultID){
        String sql = "select * from record_res where result_id=?";

        try {
            PreparedStatement preparedStatement  = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, resultID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                RecordRes result = new RecordRes();
                result.setResult_id(resultSet.getString(Constant.RESULT_ID));
                result.money_pay = resultSet.getFloat(Constant.MONEY_PAY);
                result.money_hurt = resultSet.getFloat(Constant.MONEY_HURT);
                result.money_heart = resultSet.getFloat(Constant.MONEY_HEART);
                result.money_nursing = resultSet.getFloat(Constant.MONEY_NURSING);
                result.money_tardy = resultSet.getFloat(Constant.MONEY_TARDY);
                result.money_medical = resultSet.getFloat(Constant.MONEY_MEDICAL);
                result.money_nutrition = resultSet.getFloat(Constant.MONEY_NUTRITION);
                result.money_hospital_allowance = resultSet.getFloat(Constant.MONEY_HOSPITAL_ALLOWANCE);
                result.money_relatives = resultSet.getFloat(Constant.MONEY_RELATIVES);
                result.money_bury = resultSet.getFloat(Constant.MONEY_BURY);
                result.money_hurt_info = resultSet.getString(Constant.MONEY_HURT_INFO);
                result.money_heart_info = resultSet.getString(Constant.MONEY_HEART_INFO);
                result.money_nursing_info = resultSet.getString(Constant.MONEY_NURSING_INFO);
                result.money_nutrition_info = resultSet.getString(Constant.MONEY_NUTRITION_INFO);
                result.money_tardy_info = resultSet.getString(Constant.MONEY_TARDY_INFO);
                result.money_medical_info = resultSet.getString(Constant.MONEY_MEDICAL_INFO);
                result.money_hospital_allowance_info = resultSet.getString(Constant.MONEY_HOSPITAL_ALLOWANCE_INFO);
                result.money_relatives_info = resultSet.getString(Constant.MONEY_RELATIVES_INFO);
                result.money_bury_info = resultSet.getString(Constant.MONEY_BURY_INFO);

                if (mCallback!=null)
                    mCallback.finish(result);
            }
        } catch (SQLException e) {
            if (mCallback!=null)
                mCallback.error("获取ID为 "+ resultID + " 的RecordRes过程错误！SQLState="+ e.getSQLState());
            e.printStackTrace();
        }
    }


}
