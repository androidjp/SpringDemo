package servlets.test;

import model.IRequestCallback;
import model.impl.record.LocationManager;
import pojo.Location;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by androidjp on 2017/4/10.
 */
@WebServlet("/servlets/test/TestLocation")
public class TestLocation extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();

        String city = req.getParameter("city");

        LocationManager locationManager = new LocationManager();

        locationManager.setRequestCallback(new IRequestCallback() {
            @Override
            public void finish(Object value) {

                if (value == null){
                    pw.println("成功存储location："+city);
                    locationManager.getLocation("123321");
                }
                else{
                    Location location = (Location) value;
                    pw.println("最终获取的location："+location.toJsonString());
                    pw.flush();
                    pw.close();
                }
            }

            @Override
            public void error(String msg) {
                pw.println(msg);
                pw.flush();
                pw.close();
            }
        });
        locationManager.addLocation("123321",city,"广东省","翠樱街1巷16号",111,-18);
    }
}
