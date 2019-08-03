package com.lu.j1993.controller;

import com.lu.j1993.pojo.Imgurl;
import com.lu.j1993.service.ImgService;
import com.lu.j1993.utils.AliyunOssUtils;
import com.lu.j1993.utils.DeleteFileUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Administrator on 2019/8/3.
 */
@Slf4j
@Controller
public class UpFileController {
     //Logger logger=LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImgService service;
    @GetMapping("/")
    public String yemian(){
        return "index";
    }
    @PostMapping("upload")
    @ResponseBody
    public String  uploadBlog(MultipartHttpServletRequest request) throws JSONException {
        String code="";
        String newFileName = null;
        AliyunOssUtils ossUtils = new AliyunOssUtils();
        Iterator<String> fileNames = request.getFileNames();

        try {
            while (fileNames.hasNext()) {

                //把fileNames集合中的值打出来
                String fileName=fileNames.next();
                System.out.println("fileName: "+fileName);
                log.info("filename2222=="+fileName);

                /*
                 * request.getFiles(fileName)方法即通过fileName这个Key, 得到对应的文件
                 * 集合列表. 只是在这个Map中, 文件被包装成MultipartFile类型
                 */
                List<MultipartFile> fileList=request.getFiles(fileName);

                if (fileList.size()>0) {

                    //遍历文件列表
                    Iterator<MultipartFile> fileIte=fileList.iterator();

                    while (fileIte.hasNext()) {

                        //获得每一个文件
                        MultipartFile multipartFile = fileIte.next();

                        if (multipartFile != null) {
                            String filename = multipartFile.getOriginalFilename();
                            if (!"".equals(filename.trim())) {

                                File newFile = new File(filename);
                                FileOutputStream os = new FileOutputStream(newFile);
                                os.write(multipartFile.getBytes());
                                os.close();
                                multipartFile.transferTo(newFile);
                                // 上传到OSS
                                String uploadUrl = ossUtils.upLoad(newFile);
                                newFileName= "http://lucien.oss-cn-beijing.aliyuncs.com/"+uploadUrl;
                                Imgurl imgurl = new Imgurl();
                                imgurl.setName(newFileName);
                                int add = service.add(imgurl);
                                if(add==1){
                                    code="0";
                                }else {
                                    code="1";
                                }
                                System.err.println(code);
                                System.err.println(uploadUrl);

                                // 删除上传的文件
                                File file1=new File("");
                                String s = file1.getAbsolutePath();
                                DeleteFileUtil.delete(s + "\\" + filename);
                            }

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;

    }
}
