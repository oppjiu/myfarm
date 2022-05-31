package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.User;
import cn.jxufe.serivce.UserService;
import cn.jxufe.utils.FileUpAndDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    UserService userService;

    @RequestMapping("/upload")
    @ResponseBody
    public ResponseResult<?> saveFile(@RequestParam("upload") MultipartFile uploadFile, HttpSession session) {
        ResponseResult<?> result;
        //照片存放地址
        String savePathname = SystemCode.DEFAULT_HEAD_IMAGE_SAVE_PATH;
        try {
            String serverFilePath = FileUpAndDownloadUtil.fileUpload(savePathname, session, uploadFile);
            //获取用户信息
            User curUser = (User) session.getAttribute(SystemCode.USER_SESSION_NAME);
            //获取用户数据
            User userByFind = userService.findByUsername(curUser.getUsername());
            userByFind.setHeadImgUrl(serverFilePath);
            userService.modify(userByFind);//保存数据
            result = new ResponseResult<>(ResponseCode.SUCCESS, serverFilePath);
        } catch (IOException e) {
            result = new ResponseResult<>(ResponseCode.ERROR);
            throw new RuntimeException(e);
        }
        return result;
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> fileDownload(@RequestParam(value = "filename", required = false) String filename, HttpSession session) {
//        String filename = map.get("filename");
        String filePath = SystemCode.DEFAULT_HEAD_IMAGE_SAVE_PATH + File.separator + filename;
        try {
            return FileUpAndDownloadUtil.fileDownload(session, filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



