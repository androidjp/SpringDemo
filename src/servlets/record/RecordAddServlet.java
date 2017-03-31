package servlets.record;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.IRequestCallback;
import model.impl.record.RecordManager;
import pojo.Record;
import pojo.RecordRes;
import pojo.network.Result;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Record上传Servlet
 * Created by androidjp on 2017/3/4.
 */
@WebServlet("/servlets/record/RecordAddServlet")
public class RecordAddServlet extends HttpServlet {
    private RecordManager recordManager = new RecordManager();
    private Result<RecordRes> result = new Result<>();

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

        String jsonStr = null;
        try {
            ServletInputStream sis = req.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = sis.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len));
            }
            jsonStr = sb.toString();
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonStr != null) {
            Gson gson = new GsonBuilder().create();
            Record record = gson.fromJson(jsonStr, Record.class);

            ///成功拿到 record对象，开始存储并计算
//            out.println("计算前："+record.toJsonString());
            //TODO: 开始准备计算
            this.recordManager.setRequestCallback(new IRequestCallback<RecordRes>() {
                @Override
                public void finish(RecordRes value) {
//                    out.println("计算-> 存储Location  -> 存储RecordRes  -> 存储Record 过程成功！");
                    result.code = 200;
                    result.msg = "success";
                    result.data = value;
                    out.println(result.toJsonString());
                    out.flush();
                    out.close();
                }

                @Override
                public void error(String msg) {
//                    out.println("计算-> 存储Location  -> 存储RecordRes  -> 存储Record 过程失败！");
                    result.code = 400;
                    result.msg = msg;
                    out.println(result.toJsonString());
                    out.flush();
                    out.close();
                }
            });
            this.recordManager.addRecord(record);
        }

    }

}
