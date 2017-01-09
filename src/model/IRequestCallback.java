package model;

/**
 * Created by junpeng.wu on 1/9/2017.
 */
public interface IRequestCallback {
    public void finish();
    public void error(String msg);
}
