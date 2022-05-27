package cn.jxufe.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @create: 2022-05-10 19:56
 * @author: lwz
 * @description:
 **/
public class FileUpAndDownloadUtil {
    /**
     * 文件上传
     *
     * @param savePathname 路径
     * @param request      httpServletRequest
     * @param uploadFile   文件名称
     * @throws IOException
     */
    public static String fileUpload(String savePathname, HttpServletRequest request, MultipartFile uploadFile) throws IOException {
        String fileName = uploadFile.getOriginalFilename();//获取上传的文件的文件名
        fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));//处理文件重名问题
        String realPath = request.getSession().getServletContext().getRealPath(savePathname); //获取服务器中目录的绝对路径
        File file = new File(realPath);
        //创建文件夹
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
        }
        String finalPath = realPath + File.separator + fileName; //服务器地址
        uploadFile.transferTo(new File(finalPath)); //上传文件
        return savePathname + File.separator + fileName;
    }

    public static ResponseEntity<byte[]> fileDownload(HttpSession session, @RequestParam("filePath") String filePath) throws IOException {
        InputStream is = null;
        try {
            String srcFile = session.getServletContext().getRealPath(filePath);//获取服务器中文件的真实路径
            is = Files.newInputStream(Paths.get(srcFile));//创建输入流
            byte[] bytes = new byte[is.available()];//创建字节数组
            int read = is.read(bytes);//将流读到字节数组中
            MultiValueMap<String, String> headers = new HttpHeaders();//创建HttpHeaders对象设置响应头信息
            headers.add("Content-Disposition", "attachment;filename=" + srcFile.substring(srcFile.lastIndexOf(File.separator) + 1));//设置要下载方式以及下载文件的名字
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } finally {
            if (is != null) {
                is.close();//关闭输入流
            }
        }
    }
}
