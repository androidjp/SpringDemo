package servlets.record;

import base.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.IRequestCallback;
import model.impl.record.RecordManager;
import pojo.Record;
import pojo.network.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Record 的增删查改操作
 * Created by androidjp on 2017/2/8.
 */
@WebServlet("/servlets/record/RecordQueryServlet")
public class RecordQueryServlet extends HttpServlet {
    RecordManager recordManager = new RecordManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doJsonResponse(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private void doJsonResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();

        ///获取 Attribute
        String user_id = req.getParameter(Constant.USER_ID);
        String pageStr = req.getParameter(Constant.PAGE);
        String record_time_str = req.getParameter(Constant.RECORD_TIME);
        int page = Integer.valueOf(pageStr);
        long record_time = Long.valueOf(record_time_str);

//        out.println("user_id = "+user_id +", page = "+ page +", record_time = "+ record_time);
//        out.println("读到数据了，开始请求数据库获取数据！");

        recordManager.setRequestCallback(new IRequestCallback() {
            @Override
            public void finish(Object value) {
                if (value == null){
                    Result<List<Record>> result = new Result<>();
                    result.code = 200;
                    result.msg = "empty list";
                    result.data = null;
                    out.println(result.toJsonString());
                    out.flush();
                    out.close();
                    return;
                }
                List<Record> list = (List<Record>) value;
                if (list!=null){
                    Result<List<Record>> result = new Result<>();
                    result.code = 200;
                    result.msg = "success";
                    result.data = list;
                    out.println(result.toJsonString());
                    out.flush();
                    out.close();
                }
            }

            @Override
            public void error(String msg) {
                out.println(msg);
                out.flush();
                out.close();
            }
        });
        if (page == 0){
                ///加载全新的一页
                recordManager.getRefreshList(user_id,record_time);
        }else{
                recordManager.getMoreList(user_id,record_time);
        }
    }
}
