package servlets;

import base.Constant;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 上传处理容器
 * Created by junpeng.wu on 1/9/2017.
 */
@WebServlet("/servlets/UploadServlet")
public class UploadServlet extends HttpServlet{


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(req)){
            ///如果不是
            PrintWriter out = resp.getWriter();
            out.println("Error: 表单必须包含enctype=multipart/form-data");
            out.flush();
            return;
        }

        //配置上传参数
        DiskFileItemFactory factory  = new DiskFileItemFactory();
        //设置内存临界值 -- 超过后将产生临时文件并存储到临时目录中
        factory.setSizeThreshold(Constant.MEMORY_THRESHOLD);
        ///设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(Constant.MAX_FILE_SIZE);//最大文件上传数
        upload.setSizeMax(Constant.MAX_REQUEST_SIZE);//最大请求数
        ///构造临时路径来存储上传的文件
        String uploadPath = req.getServletContext().getRealPath("./") + File.separator + Constant.UPLOAD_DIR;
        System.out.println("uploadPath= "+ uploadPath);
        File uploadDir = new File(uploadPath);
        ///如果不存在此目录，就自动创建
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }
        try{
            List<FileItem> formItems = upload.parseRequest(req);
            if (formItems !=null && formItems.size() >0){
                for (FileItem item:formItems){
                    ///处理不在表单中的字段
                    if (!item.isFormField()){
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        ///控制台输出文件上传路径
                        System.out.println(filePath);
                        ///保存文件到硬盘
                        item.write(storeFile);
                        req.setAttribute("message", "文件上传成功，服务端文件实际绝对路径为："+ storeFile.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            req.setAttribute("message","错误信息："+ e.getMessage());
        }
        //跳转到upload_result.jsp
        req.getServletContext().getRequestDispatcher("/upload_result.jsp").forward(req,resp);
    }
}
