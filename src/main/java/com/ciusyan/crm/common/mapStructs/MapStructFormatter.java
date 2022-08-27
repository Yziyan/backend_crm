package com.ciusyan.crm.common.mapStructs;

import com.ciusyan.crm.pojo.po.User;
import com.ciusyan.crm.pojo.vo.response.UserVo;
import org.mapstruct.Qualifier;
import org.mapstruct.TargetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

public class MapStructFormatter {


    // 必须使用的规定格式：自定义转换类型 【日期 -> 时间戳】

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface Date2Millis{}

    /**
     * Date -> 毫秒数
     * @param date: 日期
     * @return : 时间戳
     */
    @Date2Millis
    public static Long date2millis(Date date) {
        if (date == null) return null;
        return date.getTime();
    }

    // 毫秒数 -> 日期
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface Mills2Date{}


    /**
     * 毫秒数 -> 日期
     * @param miles：毫秒数【时间戳】
     * @return ： 日期
     */
    @Mills2Date
    public static Date mills2date(Long miles) {
        if (miles == null) return null;
        return new Date(miles);
    }

    // User -> UserVo
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface User2UserVo{}

    /**
     * User -> UserVo
     * @param user: 未删减
     * @return ： 删减版的 User
     */
    @User2UserVo
    public static UserVo user2userVo(User user) {
        return MapStructs.INSTANCE.po2vo(user);
    }

}
