package model;

/**
 * 加解密接口
 * Created by junpeng.wu on 1/9/2017.
 */
public interface IEntryption<X,Y> {
    ///加密
    Y entryption(X obj);
    ///解码
    X decode(Y obj);
}
