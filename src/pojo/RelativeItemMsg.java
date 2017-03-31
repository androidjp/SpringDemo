package pojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 亲属抚养关系item
 * relation：0：子女 1：父母
 * age：[1,55] 的整数
 * Created by androidjp on 2017/1/17.
 */
public class RelativeItemMsg {
    //    @PrimaryKey
    private String relativeItemMsg_id;
    //    @Required
    private String  record_id;
    private int relation;
    private int age;


    public String getRelativeItemMsg_id() {
        return relativeItemMsg_id;
    }

    public void setRelativeItemMsg_id(String relativeItemMsg_id) {
        this.relativeItemMsg_id = relativeItemMsg_id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }



    public String toJsonString() {
        Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(this);
        return jsonStr;
    }
}
