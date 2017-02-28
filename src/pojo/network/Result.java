package pojo.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jp.org.json.JSONObject;
import pojo.User;

import java.io.Serializable;

/**
 * 请求响应类（通用）
 * Created by androidjp on 2017/2/18.
 */

public class Result<T> implements Serializable{

    public int code;
    public String msg;
    public T data;
    public long count;
    public long page;

    @Override
    public String toString() {
//        return "Result{"+
//                "code=" + code +
//                ", msg='" + msg +
//                "\', data=" + data +
//                ", count=" + count +
//                ", page=" + page+
//                "}";
        return toJsonString();
    }


    public String toJsonString(){

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", code);
//        jsonObject.put("msg", msg);
//        jsonObject.put("count", count);
//        jsonObject.put("page", page);
//        jsonObject.put("data", data);
//        return jsonObject.toString();
        Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(this);
        return jsonStr;
    }
}