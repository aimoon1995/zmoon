package com.project_study.my.for_my.controller;

import com.project_study.my.common.ResultBean;
import com.project_study.my.common.param.BaseParam;
import com.project_study.my.common.utils.DateUtil;
import com.project_study.my.for_my.entity.ActivityUser;
import com.project_study.my.for_my.service.ActivityUserService;
import com.project_study.my.my_annotation.annotation.CustomValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import  com.project_study.my.common.group.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ActivityUserController
 * @Description: TODO
 * @Author zyl
 * @Date 2019/12/25
 * @Version V1.0
 **/
@RestController
@RequestMapping("/act/user")
@Slf4j
public class ActivityUserController {



     @Autowired
    private ActivityUserService activityUserService;

      @PostMapping("/query_user")
     public ResultBean queryUserData (MultipartFile m) {
          List<ActivityUser> activityUser = activityUserService.queryUserData();
         log.debug("------------------------------------------activityUser");
         return  ResultBean.error();
     }

    @PostMapping("/se_user")
    public ResultBean se_user () {
        Date lastMonth = this.parse(this.minusMonth(new Date(), DateUtil.DATE_FORMAT_DATE_M_0, 1l), DateUtil.DATE_FORMAT_DATE_M_0);
       ActivityUser activityUser = activityUserService.selectByUuid();


        log.error("------------------------------------------activityUser");
        return  ResultBean.error();
    }

    @PostMapping("/sel_user_page")
    @CustomValidate(groups =  AddGroup.class, showAll = true)
    public ResultBean queryUserByPage (BaseParam baseParam) {
        ResultBean resultBean = activityUserService.queryUserByPage(baseParam);
        log.debug("------------------------------------------activityUser");
        return  resultBean;
    }
    /**
     * 从日期字符转换为日期类型
     *
     * @param dateStr
     *            日期字符
     * @param format
     *            日期格式化模板
     * @return 日期
     */
    public static Date parse(String dateStr, String format) {
        Date date = null;
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = new SimpleDateFormat(format).parse(dateStr);
            } catch (ParseException ex) {
                log.error("DateParseError", ex);
            }
        }
        return date;
    }

    /*
     * @MethodName: minusMinutes
     * @Description: 减月
     * @Param: []
     * @Return: java.util.Date
     * @Author: zyl
     * @Date: 2019/12/2
     **/
    public  static  String  minusMonth (Date date, String format, Long amount) {
        // 时间  当前时间和  10分钟之前的时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime =  dateToLocalDateTime(date);
        LocalDateTime localDateTimeNew = localDateTime.minusMonths(amount);
        String localDateTimeNewStr  = formatter.format(localDateTimeNew);
        return  localDateTimeNewStr;
    }


    /**
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }
}
