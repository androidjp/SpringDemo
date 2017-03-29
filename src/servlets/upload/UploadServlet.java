package servlets.upload;

import base.Constant;
import model.IRequestCallback;
import model.impl.user.UserManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import pojo.User;
import pojo.network.Result;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

/**
 * 图片上传处理APP
 * Created by androidjp on 2017/3/24.
 */
@WebServlet("/servlets/upload/UploadServlet")
public class UploadServlet extends HttpServlet {

    private UserManager userManager = new UserManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        handleForWeb(req, resp);
        handleForAndroid(req, resp);
//        handleForAndroidTest(req, resp);
    }

    private void handleForAndroidTest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");

        Result<User> result = new Result<>();

        PrintWriter out = resp.getWriter();

        // 创建文件项目工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // 设置文件上传路径
        String upload = this.getServletContext().getRealPath("./") + File.separator + Constant.USER_IMG_DIR;
        // 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
        String temp = System.getProperty("java.io.tmpdir");
        // 设置缓冲区大小为 5M
        factory.setSizeThreshold(1024 * 1024 * 5);
        // 设置临时文件夹为temp
        factory.setRepository(new File(temp));
        // 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

        // 解析结果放在List中
        try {
            List<FileItem> list = servletFileUpload.parseRequest(req);

            for (FileItem item : list) {
                String name = item.getFieldName();
                InputStream is = item.getInputStream();

                System.out.println("the current name is " + name);

                if (name.contains("file")) {
                    try {
                        inputStream2File(is, upload + "\\" + System.currentTimeMillis() + item.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String key = item.getName();
                    String value = item.getString();
                    out.println(key + "---" + value);
                }
            }


            out.println("success");
        } catch (FileUploadException e) {
            e.printStackTrace();
            out.println("failure");
        }

        out.flush();
        out.close();
    }


    // 流转化成字符串
    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    // 流转化成文件
    public static void inputStream2File(InputStream is, String savePath)
            throws Exception {
        System.out.println("the file path is  :" + savePath);
        File file = new File(savePath);
        InputStream inputSteam = is;
        BufferedInputStream fis = new BufferedInputStream(inputSteam);
        FileOutputStream fos = new FileOutputStream(file);
        int f;
        while ((f = fis.read()) != -1) {
            fos.write(f);
        }
        fos.flush();
        fos.close();
        fis.close();
        inputSteam.close();

    }


    private void handleForAndroid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");

        Result result = new Result();
        PrintWriter out = resp.getWriter();

        //检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(req)) {
            ///如果不是
//            out.println("Error: 表单必须包含enctype=multipart/form-data");
            result.code = 400;
            result.msg = "表单必须包含enctype=multipart/form-data";
            out.println(result.toJsonString());
            out.flush();
            out.close();
            return;
        }

        //创建Result<String> 对象

        //首先获取user_id
        String user_id = null;
        //TODO: 研究如何获取RequestBody
        StringBuilder sb = new StringBuilder();
        ServletInputStream sis = req.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(sis, "utf-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("user_id")) {
                for (int i = 0; i < 5; i++) {
                    line = reader.readLine();
                    if (line.trim().length() == 20)
                        user_id = line.trim();
                }
                break;
            }
        }
//        如果user_id为空，则返回失败的结果
        if (user_id == null) {
            result.code = 400;
            result.msg = "user_id 为空！！！";
            out.print(result.toJsonString());
            return;
        }

        //配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置内存临界值 -- 超过后将产生临时文件并存储到临时目录中
        factory.setSizeThreshold(Constant.MEMORY_THRESHOLD);
        ///设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        // 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(Constant.MAX_FILE_SIZE);//最大文件上传数
        upload.setSizeMax(Constant.MAX_REQUEST_SIZE);//最大请求数
        ///实际文件保存路径
        String uploadPath = req.getServletContext().getRealPath("./") + File.separator + Constant.USER_IMG_DIR;
        System.out.println("uploadPath= " + uploadPath);
        File uploadDir = new File(uploadPath);
        ///如果不存在此目录，就自动创建
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        out.println("uploadPath="+uploadPath+", 准备查找有没有文件数据");
        try{
            FileItemIterator iter = upload.getItemIterator(req);
            InputStream is = null;
            while(iter.hasNext()){
                out.println("有数据");
                FileItemStream item = iter.next();///获取文件流
                String name = item.getFieldName();
                is = item.openStream();
                if (!item.isFormField()){
                    if (is.available()>0){
                        String fname = item.getName();///获取文件名
                        Streams.copy(is, new FileOutputStream(uploadPath+File.separator+fname),true);
                        out.println("收到的文件名为："+ fname);
                    }
                }
            }
            out.flush();
            out.close();
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

//        try {
//            List<FileItem> formItems = upload.parseRequest(req);
//            if (formItems.size()==0)
//                out.println("parseRequest(req)得到的List<FileItem>为空！！");
//            if (formItems != null && formItems.size() > 0) {
//                out.write("存在文件！");
//                for (FileItem item : formItems) {
//                    String name = item.getFieldName();
//                    InputStream is = item.getInputStream();
//                    System.out.println("the current name is " + name);
//                    if (name.contains("user_pic")) {
//                        try {
//                            inputStream2File(is, uploadPath + File.separator + item.getName());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        String key = item.getName();
//                        String value = item.getString();
//                        System.out.println(key + "---" + value);
//                    }
//                    ///处理不在表单中的字段
//                    if (!item.isFormField()) {
//                        String fileName = new File(item.getName()).getName();
//                        String filePath = uploadPath + File.separator + fileName;
//                        File storeFile = new File(filePath);
//                        out.println("上传的文件存储在：" + filePath);
//                        ///保存文件到硬盘
//                        item.write(storeFile);
//                        ///控制台输出文件上传路径
//                        ///TODO: 还需要将此头像文件路径保存到数据库.user表中
//                        userManager.setRequestCallback(new IRequestCallback<User>() {
//                            @Override
//                            public void finish(User value) {
//                                ///成功保存上传文件路径到数据库
//                                System.out.println(filePath);
//
//                                try {
//                                    result.msg = "文件上传成功,路径在：" + filePath;
//                                    result.code = resp.getStatus();
//                                    if (value != null)
//                                        result.data = value;
//                                    out.println(result.toJsonString());
//                                    out.flush();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void error(String msg) {
//                                result.msg = "上传失败（保存文件路径到数据库失败）";
//                                result.code = 500;
//                                out.println(result.toJsonString());
//                                out.flush();
//                            }
//                        });
//                        userManager.updateUserPic(user_id, filePath);
//
//                    }
//                }
//            }
//        } catch (Exception e) {
////            req.setAttribute("message","错误信息："+ e.getMessage());
//            result.code = resp.getStatus();
//            result.msg = "错误信息：" + e.getMessage();
//            out.println(result.toJsonString());
//            out.flush();
//        } finally {
//            if (out != null)
//                out.close();
//        }
        //跳转到upload_result.jsp
//        req.getServletContext().getRequestDispatcher("/upload_result.jsp").forward(req,resp);

    }


    private void handleForWeb(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(req)) {
            ///如果不是
            PrintWriter out = resp.getWriter();
            out.println("Error: 表单必须包含enctype=multipart/form-data");
            out.flush();
            return;
        }

        //配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置内存临界值 -- 超过后将产生临时文件并存储到临时目录中
        factory.setSizeThreshold(Constant.MEMORY_THRESHOLD);
        ///设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(Constant.MAX_FILE_SIZE);//最大文件上传数
        upload.setSizeMax(Constant.MAX_REQUEST_SIZE);//最大请求数
        ///构造临时路径来存储上传的文件
        String uploadPath = req.getServletContext().getRealPath("./") + File.separator + Constant.UPLOAD_DIR;
        System.out.println("uploadPath= " + uploadPath);
        File uploadDir = new File(uploadPath);
        ///如果不存在此目录，就自动创建
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            List<FileItem> formItems = upload.parseRequest(req);
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    ///处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        ///控制台输出文件上传路径
                        System.out.println(filePath);
                        ///保存文件到硬盘
                        item.write(storeFile);
                        req.setAttribute("message", "文件上传成功");
                    }
                }
            }
        } catch (Exception e) {
            req.setAttribute("message", "错误信息：" + e.getMessage());
        }
        //跳转到upload_result.jsp
        req.getServletContext().getRequestDispatcher("/upload_result.jsp").forward(req, resp);

    }


}
