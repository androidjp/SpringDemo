package model.impl.record;

import base.Constant;
import model.IRelatives;
import model.RequestManager;
import model.connection_pool.ConPool;
import pojo.RelativeItemMsg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 需抚养人列表管理类
 * Created by androidjp on 2017/4/1.
 */
public class RelativesItemMsgManager extends RequestManager<List<RelativeItemMsg>> implements IRelatives{


    @Override
    public void saveRelatives(List<RelativeItemMsg> dataList) {
        if (dataList==null || dataList.size()==0){
            if (mCallback!=null)
                mCallback.finish(null);
        }
        String sql = "insert into relative_item_msg(relative_item_msg_id,record_id,age,relation) values(?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);

            for (RelativeItemMsg item:dataList){
                preparedStatement.setString(1,item.getRelativeItemMsg_id());
                preparedStatement.setString(2,item.getRecord_id());
                preparedStatement.setInt(3,item.getAge());
                preparedStatement.setInt(4,item.getRelation());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            if (mCallback!=null)
                mCallback.finish(new ArrayList<RelativeItemMsg>());
        } catch (SQLException e) {
            if (mCallback!=null)
                mCallback.error("保存需抚养人列表失败,"+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void getRelatives(String record_id) {
        String sql = "select * from relative_item_msg where record_id = ?";
        PreparedStatement preparedStatement = null;
        List<RelativeItemMsg> dataList = null;
        try {
            preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.setString(1,record_id);
            ResultSet resultSet =  preparedStatement.executeQuery();
            dataList = new ArrayList<>();
            while(resultSet.next()){
                RelativeItemMsg data = new RelativeItemMsg();
                data.setRecord_id(resultSet.getString(Constant.RECORD_ID));
                data.setRelativeItemMsg_id(resultSet.getString(Constant.RELATIVE_ITEM_MSG_ID));
                data.setRecord_id(resultSet.getString(Constant.RECORD_ID));
                data.setRecord_id(resultSet.getString(Constant.RECORD_ID));
                dataList.add(data);
            }
            if (mCallback!=null)
                mCallback.finish(dataList);
        } catch (SQLException e) {
            if (mCallback!=null)
                mCallback.error("获取需抚养人列表失败");
            e.printStackTrace();
        }
    }
}
