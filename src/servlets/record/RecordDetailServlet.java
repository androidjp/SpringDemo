package servlets.record;

import base.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by androidjp on 2017/3/30.
 */
public class RecordDetailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();

        String user_id = req.getParameter(Constant.USER_ID);
        String record_id=req.getParameter(Constant.RECORD_ID);


    }
}
