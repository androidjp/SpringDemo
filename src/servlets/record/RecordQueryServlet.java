package servlets.record;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Record 的增删查改操作
 * Created by androidjp on 2017/2/8.
 */
@WebServlet("/servlets/record/RecordQueryServlet")
public class RecordQueryServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doJsonResponse(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req , resp);
    }




    private void doJsonResponse(HttpServletRequest req, HttpServletResponse resp) {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");


    }
}
