package com.project_study.my.ocr_frs.controller;

import com.project_study.my.common.ResultBean;
import com.project_study.my.ocr_frs.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName OrcFrsController
 * @Description: 证件扫描、人脸识别
 * @Author zyl
 * @Date 2020/2/12
 * @Version V1.0
 **/
public class OrcFrsController {
     @Autowired
     private OcrService ocrService;
    @PostMapping("/ocr_id_card")
    public ResultBean queryUserData (MultipartFile file) {
        ocrService.tokenDemo(file);
        return  ResultBean.error();
    }

}
