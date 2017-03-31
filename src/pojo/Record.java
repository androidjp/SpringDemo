package pojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jp.org.json.JSONException;
import jp.org.json.JSONObject;

import java.util.List;

/**
 * 理赔记录：
 * <p>
 * Created by androidjp on 2016/12/25.
 */
public class Record implements Cloneable {


    //    @PrimaryKey
    private String record_id;//（主键）
    //    @Required
    private String user_id;///用户ID（外键）
    private String location_id;///定位（外键）
    //    @Ignore
    public Location location;//实际定位信息
    //    @Required
    public long record_time;
    public int hurt_level;
    public float salary;//月薪
    public int relatives_count;//兄弟姐妹数量
    public boolean has_spouse;//有无配偶（false：无， true：有）
    public int id_type;///户口性质（城镇、农村）
    public int responsibility;//责任：全责、次要责任、同等责任、主要责任、无责
    public int driving_tools;///交通工具
    public float medical_free;//医药费
    public int hospital_days;
    public int tardy_days;///误工天数
    public int nutrition_days;//营养期
    public int nursing_days;///护理期
    private String result_id;///结果（外键）
    public float pay;//总赔偿金额
    //    @Ignore
    private RecordRes result;///结果
    //    private RealmList<RelativeItemMsg> relative_msg_list;

    private List<RelativeItemMsg> relative_msg_list;


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        this.location_id = this.location.getLocation_id();
    }

    public RecordRes getResult() {
        return result;
    }

    public void setResult(RecordRes result) {
        this.result = result;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public List<RelativeItemMsg> getRelative_msg_list() {
        return relative_msg_list;
    }

    public void setRelative_msg_list(List<RelativeItemMsg> relative_msg_list) {
        this.relative_msg_list = relative_msg_list;
    }

    public String getResult_id() {
        return result_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }



    @Override
    protected Record clone() throws CloneNotSupportedException {
        return (Record) super.clone();
    }

    @Override
    public String toString() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("record_id", record_id);
//        jsonObject.put("user_id", user_id);
//        jsonObject.put("location_id", location_id);
//        jsonObject.put("location", location);
//        jsonObject.put("record_time", record_time);
//        jsonObject.put("hurt_level", hurt_level);
//        jsonObject.put("salary", salary);
//        jsonObject.put("relatives_count", relatives_count);
//        jsonObject.put("has_spouse", has_spouse);
//        jsonObject.put("id_type", id_type);
//        jsonObject.put("responsibility", responsibility);
//        jsonObject.put("driving_tools", driving_tools);
//        jsonObject.put("medical_free", medical_free);
//        jsonObject.put("hospital_days", hospital_days);
//        jsonObject.put("tardy_days", tardy_days);
//        jsonObject.put("nutrition_days", nutrition_days);
//        jsonObject.put("result_id", result_id);
//        jsonObject.put("result", result);
//        jsonObject.put("relative_msg_list", relative_msg_list);
//
//        return jsonObject.toString();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("record_id", record_id);
            jsonObject.put("user_id", user_id);
            jsonObject.put("location_id", location_id);
            jsonObject.put("location", location);
            jsonObject.put("record_time", record_time);
            jsonObject.put("hurt_level", hurt_level);
            jsonObject.put("salary", salary);
            jsonObject.put("relatives_count", relatives_count);
            jsonObject.put("has_spouse", has_spouse);
            jsonObject.put("id_type", id_type);
            jsonObject.put("responsibility", responsibility);
            jsonObject.put("driving_tools", driving_tools);
            jsonObject.put("medical_free", medical_free);
            jsonObject.put("hospital_days", hospital_days);
            jsonObject.put("tardy_days", tardy_days);
            jsonObject.put("nutrition_days", nutrition_days);
            jsonObject.put("nursing_days", nursing_days);
            jsonObject.put("result_id", result_id);
            jsonObject.put("result", result);
            jsonObject.put("relative_msg_list", relative_msg_list);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String toJsonString() {
        Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(this);
        return jsonStr;
    }
}
