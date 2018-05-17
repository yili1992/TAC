package com.lee.tac.controller;


import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.lee.tac.utils.EncryptionTool.encryptBASE64;

/**
 * Created by zhaoli on 2018/4/8
 */

@RestController
public class UploadController {
    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value(("${uploadPath}"))
    private String uploadPath;

    /**
     * 本地上传文件接口
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @CrossOrigin
    @PostMapping("/upload")
    public JSONObject upload(MultipartFile[] file, HttpServletRequest request) throws IOException {
        logger.info("文件上传开始.....");
        JSONObject jsonObject = new JSONObject();
        if (null != file && file.length > 0) {
            //遍历并保存文件
            for (MultipartFile files : file) {
                if (file != null) {
                    //取得当前上传文件的文件名称
                    String fileName = files.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    //本地上传文件方式
                    String suffix = files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf("."));
                    fileName = System.currentTimeMillis()+suffix;
                    String filePath = uploadPath+fileName;
                    try {
                        File serverFile = new File(filePath);
                        files.transferTo(serverFile);
                    } catch (Exception e) {
                        jsonObject.put("msg","上传失败");
                        jsonObject.put("code","99999");
                        return jsonObject;
                    }
                    logger.info(">>>>>>>>>>>>>本地上传路径 " + filePath);
                    jsonObject.put("code","0");
                    jsonObject.put("msg","上传成功");
                    jsonObject.put("imgURL",encryptBASE64(filePath));
                    return jsonObject;
                }
            }
        } else {
            jsonObject.put("msg","上传失败");
            jsonObject.put("code","99999");
            return jsonObject;
        }
        return jsonObject;
    }

    /**
     * 本地文件下载接口
     * @param fileName
     * @param request
     * @return
     * @throws IOException
     */
    @CrossOrigin
    @GetMapping("/download/{fileName}&{type}")
    public void download(@PathVariable("fileName") String fileName,@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response){
        fileName = fileName+"."+type;
        logger.info("<<<<<<<<<<<<<<<<<<下载文件 " + fileName);
        try(InputStream inputStream = new FileInputStream(new File(uploadPath,fileName));
            OutputStream outputStream = response.getOutputStream()) {

            //设置内容类型为下载类型
            response.setContentType("application/x-download");
            //设置请求头 和 文件下载名称
            response.addHeader("Content-Disposition","attachment;filename="+fileName);
            //用 common-io 工具 将输入流拷贝到输出流
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}