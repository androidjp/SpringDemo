package model;

/**
 * Created by junpeng.wu on 1/9/2017.
 */
public class RequestManager<T>{
    protected IRequestCallback<T> mCallback;

    public void setRequestCallback(IRequestCallback<T> callback){
        this.mCallback = callback;
    }

}
