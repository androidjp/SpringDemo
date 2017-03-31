package pojo;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * 理赔计算结果
 * Created by androidjp on 2016/12/27.
 */
public class RecordRes {


    //    @PrimaryKey
    private String result_id = null;//主键
    public float money_pay;///总花费（两位小数）
    public float money_hurt;//伤残赔偿金
    public float money_heart;//精神损失费
    public float money_nursing;//护理费
    public float money_tardy;//误工费
    public float money_medical;//医药费
    public float money_nutrition;//营养费
    public float money_hospital_allowance;//住院伙食补贴
    public float money_relatives;//需抚养人费用
    public float money_bury; //安葬费

    ///各个详情
    public String money_hurt_info;
    public String money_heart_info;
    public String money_nursing_info;//护理费
    public String money_tardy_info;//误工费
    public String money_medical_info;//医药费
    public String money_nutrition_info;//营养费
    public String money_hospital_allowance_info;//住院伙食补贴
    public String money_relatives_info;//需抚养人费用详情
    public String money_bury_info; //安葬费

    public RecordRes(){

    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }

    public String getResult_id() {

        return result_id;
    }

    //计算所有费用
    public void calculateAllPay(){
        money_pay = money_hurt
                + money_heart
                + money_hospital_allowance
                + money_nursing
                + money_nutrition
                + money_medical
                + money_tardy
                + money_bury;
    }

    public String toJsonString() {
        Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(this);
        return jsonStr;
    }
}
