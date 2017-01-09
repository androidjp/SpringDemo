package model;

/**
 * Created by junpeng.wu on 1/9/2017.
 */
public class RequestManager {
    protected IRequestCallback mCallback;

    public void setRequestCallback(IRequestCallback callback){
        this.mCallback = callback;
    }

}
