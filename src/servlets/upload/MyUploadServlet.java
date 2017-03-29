package servlets.upload;

import base.Constant;
import model.IRequestCallback;
import model.impl.user.UserManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import pojo.User;
import pojo.network.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * 文件上传（测试）Servlet
 * Created by androidjp on 2017/3/28.
 */
@WebServlet("/servlets/upload/MyUploadServlet")
public class MyUploadServlet extends HttpServlet {

    String uploadPath = null;

    String user_id = null;
    String user_pic = null;
    UserManager userManager = new UserManager();
    Result<User> result = new Result<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        try {
            DiskFileItemFactory diskFactory = new DiskFileItemFactory();
            // threshold 极限、临界值，即硬盘缓存 1M
            diskFactory.setSizeThreshold(4 * 1024);
            // repository 贮藏室，即临时文件目录
            diskFactory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(diskFactory);
            // 设置允许上传的最大文件大小 4M
            upload.setSizeMax(4 * 1024 * 1024);
            this.uploadPath = req.getServletContext().getRealPath("./") + Constant.USER_IMG_DIR;
            File dir = new File(this.uploadPath);
            if (!dir.exists())
                dir.mkdir();
            // 解析HTTP请求消息头
            List fileItems = upload.parseRequest(req);
            Iterator iter = fileItems.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField())///可以读到@Part("user_id") RequestBody user_id 数据
                {
                    System.out.println("处理表单内容 ...");
//                    pw.println("处理表单内容");
                    processFormField(item, pw);
                } else {
                    System.out.println("处理上传的文件 ...");
//                    pw.println("处理上传的文件");
//                    pw.println("文件详情：getContentType() = "+item.getContentType());
//                    pw.println("文件详情：getFieldName() = "+item.getFieldName());
//                    pw.println("文件详情：getName() = "+item.getName());
//                    pw.println("文件详情：getSize() = "+item.getSize());
                    processUploadFile(item, pw);
                }
            }// end while()

//            pw.println("user_id = " + user_id);
//            pw.println("user_pic = " + user_pic);

            this.userManager
                    .setRequestCallback(new IRequestCallback<User>() {
                        @Override
                        public void finish(User value) {
                            result.code = 200;
                            result.msg = "success";
                            if (value!=null)
                                result.data = value;
                            pw.println(result.toJsonString());
                            pw.flush();
                            pw.close();
                        }

                        @Override
                        public void error(String msg) {
                            result.code = 500;
                            result.msg = msg;
                            pw.println(result.toJsonString());
                            pw.flush();
                            pw.close();
                        }
                    });
            userManager.updateUserPic(this.user_id, this.user_pic);
        } catch (Exception e) {
            System.out.println("使用 fileupload 包时发生异常 ...");
            e.printStackTrace();
        }

    }

    // 处理表单内容
    private void processFormField(FileItem item, PrintWriter pw)
            throws Exception {
        String name = item.getFieldName();
        String value = item.getString();
//        pw.println(name + " : " + value + "\r\n");
        if (name.trim().equals("user_id")) {
            this.user_id = value;
        }
    }

    // 处理上传的文件
    private void processUploadFile(FileItem item, PrintWriter pw)
            throws Exception {

        // 此时的文件名包含了完整的路径，得注意加工一下
        String filename = item.getName();
//        System.out.println("完整的文件名：" + filename);
//        int index = filename.lastIndexOf("\\");
//        filename = filename.substring(index + 1, filename.length());

        long fileSize = item.getSize();

        if ("".equals(filename) && fileSize == 0) {
            System.out.println("文件名为空 ...");
            return;
        }

//        pw.println("文件有数据");
        String savePath = uploadPath + File.separator + filename;
//        pw.println("准备将文件保存在："+ savePath);
        File uploadFile = new File(savePath);
        item.write(uploadFile);
//        pw.println(filename + " 文件保存完毕 ...");
//        pw.println("文件大小为 ：" + fileSize + "\r\n");
        this.user_pic = Constant.USER_IMG_DIR+ File.separator + filename;
//        if (this.user_id!=null){
//            ///成功
//            //1. 开始记录user_pic 到user表中
//            this.userManager.setRequestCallback(new IRequestCallback<User>() {
//                @Override
//                public void finish(User value) {
//                    Result<User> result = new Result<>();
//
//                    if (value==null){
//                        result.code = 500;
//                        result.msg = "update user_pic fail，没有返回User对象信息";
//                        pw.println(result.toJsonString());
//                        pw.flush();
//                        return;
//                    }
//                    result.code = 200;
//                    result.msg = "update user_pic success";
//                    result.data = value;
//                    pw.println(result.toJsonString());
//                }
//
//                @Override
//                public void error(String msg) {
//                    Result<User> result = new Result<>();
//                    result.code = 500;
//                    result.msg = "update user_pic fail，"+ msg;
//                    pw.println(result.toJsonString());
//                }
//            });
//            this.userManager.updateUserPic(this.user_id,this.user_pic);
//        }
    }
}
