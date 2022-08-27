package com.ciusyan.crm.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("【编辑】用户头像、用户相册")
public class UserUploadReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer id;

    @ApiModelProperty("用户头像")
    private MultipartFile photo;

    @ApiModelProperty("头像原地址")
    private String photoUrl;

    @ApiModelProperty("用户相册")
    private MultipartFile[] album;

    @ApiModelProperty("用户相册的原地址【多个之间用逗号隔开】")
    private String albumUrl;

    @ApiModelProperty("对标数组【多图片编辑时使用】")
    private List<Integer> matchIndex;

}
