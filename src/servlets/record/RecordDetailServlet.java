package servlets.record;

import base.Constant;
import model.IRequestCallback;
import model.impl.record.RecordResManager;
import pojo.RecordRes;
import pojo.network.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 获取RecordRes
 * Created by androidjp on 2017/3/30.
 */
@WebServlet("/servlets/record/RecordDetailServlet")
public class RecordDetailServlet extends HttpServlet {

    private RecordResManager recordResManager = new RecordResManager();
    private Result<RecordRes> result = new Result<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();

        String result_id = req.getParameter(Constant.RESULT_ID);

        if (result_id == null){
            out.println("result_id 为空！！");
            out.flush();
            out.close();
            return;
        }

        ///TODO: 获取RecordRes
        recordResManager.setRequestCallback(new IRequestCallback() {
            @Override
            public void finish(Object value) {
                if (value!=null){
                    RecordRes recordRes = (RecordRes) value;
                    result.code = 200;
                    result.msg = "success";
                    result.data = recordRes;
                    out.println(result.toJsonString());
                    out.flush();
                    out.close();
                }
            }
            @Override
            public void error(String msg) {
                result.code = 500;
                result.msg = msg;
                out.println(result.toJsonString());
                out.flush();
                out.close();
            }
        });
        recordResManager.getRecordRes(result_id);
    }
}
