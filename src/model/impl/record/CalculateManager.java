package model.impl.record;

import model.ICalculate;
import model.RequestManager;
import pojo.Record;
import pojo.RecordRes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 计算管理类
 * Created by androidjp on 2017/3/21.
 */
public class CalculateManager extends RequestManager<RecordRes> implements ICalculate{


//    private CalculateManager(){
//
//    }
//
//    private static final class SingletonHolder{
//        private static final CalculateManager sInstance = new CalculateManager();
//    }
//
//    public static CalculateManager getInstance(){
//        return SingletonHolder.sInstance;
//    }
    //-----------------------------------------------------------------------------------


    @Override
    public void calculateRecord(Record record) {


        RecordRes recordRes = new RecordRes();
        recordRes.setResult_id(record.getResult_id());

        int dead = 11;
        int fen_mu = 10;
        recordRes.money_hurt = (float) (34757 * ((record.hurt_level * 1.0) / fen_mu % 1) * 20);
        recordRes.money_bury = (record.hurt_level == 11) ? (float) (2472.71 / 12 * 6) : 0;
        recordRes.money_heart = (float) (5000 * 6.00);
        recordRes.money_nursing = record.nursing_days * 60;
        recordRes.money_tardy = record.tardy_days * record.salary;
        recordRes.money_medical = record.medical_free;
        recordRes.money_nutrition = record.nutrition_days * 50;
        recordRes.money_hospital_allowance = record.hospital_days * 100;
        recordRes.calculateAllPay();

        ///计算完毕，需要返回RecordRes，并将总赔偿金额给到record表中
        if (mCallback!=null){
            mCallback.finish(recordRes);
        }
    }
}
