package com.ciusyan.crm.common.prop;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@ConfigurationProperties("crm")
@Component
@Data
public class CrmProperties implements ApplicationContextAware {

    private Cfg cfg;
    private Upload upload;

    // 因为如果没有放在Spring容器中的Bean，不可以直接使用 @Autowired 注入对象。给那些没有在Spring中的人用
    private static CrmProperties crmProperty;
    public static CrmProperties get() {
        return crmProperty;
    }

    // 将此配置放入Spring容器的时候【将属性赋值】
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        crmProperty = this;
    }

    // 文件上传
    @Data
    public static class Upload {
        private String basePath;
        private String uploadPath;
        private String imagePath;
        private String videoPath;

        // 获取图片相对目录
        public String getImageDir() {
            return uploadPath + imagePath;
        }

        // 获取视频相对目录
        public String getVideoDir() {
            return uploadPath + videoPath;
        }

    }

    // 跨域配置
    @Data
    public static class Cfg {
        private String[] corsOrigins;
    }

}
