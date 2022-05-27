package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.utils.FileUpAndDownloadUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @create: 2022-05-10 19:52
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/upload")
    @ResponseBody
    public ResponseResult<?> saveFile(HttpServletRequest request,
                                      @RequestParam("upload") MultipartFile uploadFile) {
        ResponseResult<?> result;
        //照片存放地址
        String savePathname = File.separator + "images" + File.separator + "headImages";
        try {
            String serverFilePath = FileUpAndDownloadUtil.fileUpload(savePathname, request, uploadFile);
            //TODO  上传到数据库中
            result = new ResponseResult<>(ResponseCode.SUCCESS, serverFilePath);
        } catch (IOException e) {
            result = new ResponseResult<>(ResponseCode.ERROR);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 下载文件临时
     *
     * @param session
     * @return
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> fileDownload(HttpSession session, @RequestParam(value = "filename", required = false) String filename) {
//        String filename = map.get("filename");
        String filePath = File.separator + "images" + File.separator + "headImages" + File.separator + filename;
        try {
            return FileUpAndDownloadUtil.fileDownload(session, filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



