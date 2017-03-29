package servlets.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by androidjp on 2017/3/25.
 */
@WebServlet("/servlets/test/TestXiaoMing")
public class TestXiaoMing extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        out = resp.getWriter();
        BufferedReader br = new BufferedReader(new FileReader("/Users/androidjp/Downloads/123.html"));
        String line;
        while ((line = br.readLine())!=null){
            System.out.println(line);
            out.print(line);
            out.flush();
        }
        br.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
