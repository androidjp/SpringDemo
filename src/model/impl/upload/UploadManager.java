package model.impl.upload;

import model.RequestManager;

/**
 * 上传管理类
 * Created by junpeng.wu on 1/9/2017.
 */
public class UploadManager extends RequestManager{
    //TODO: 如何支持多线程异步多数据上传？？
    private int mMaxTaskCount = 1;///最大异步任务数

    private UploadManager(){
    }

    public static UploadManager getInstance(){
        return SingletonHolder.sInstance;
    }

    private static final class SingletonHolder{
        private static final UploadManager sInstance = new UploadManager();
    }

    public int getMaxTaskCount() {
        return mMaxTaskCount;
    }

    public void setMaxTaskCount(int mMaxTaskCount) {
        this.mMaxTaskCount = mMaxTaskCount;
    }

    ///===========================================

}
