package model;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by junpeng.wu on 1/9/2017.
 */
public interface IRequestCallback<T> {
    public void finish(T value);
    public void error(String msg);
}
