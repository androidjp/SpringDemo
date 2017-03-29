package servlets.record;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jp.org.json.JSONObject;
import pojo.Record;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by androidjp on 2017/3/4.
 */
@WebServlet("/servlets/record/RecordAddServlet")
public class RecordAddServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doJsonResponse(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req , resp);
    }



    private void doHttpResponse(HttpServletRequest req, HttpServletResponse resp){

    }

    private void doJsonResponse(HttpServletRequest req, HttpServletResponse resp) {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        String jsonStr = null;
        try {
            ServletInputStream sis=  req.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len;
            while((len = sis.read(buffer))!=-1){
                sb.append(new String(buffer,0,len));
            }
            jsonStr = sb.toString();
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonStr!=null){
            Gson gson = new GsonBuilder().create();
            Record record = gson.fromJson(jsonStr,Record.class);

            System.out.print(record.toString());
            ///成功拿到 record对象，开始存储并计算


        }

    }

}
