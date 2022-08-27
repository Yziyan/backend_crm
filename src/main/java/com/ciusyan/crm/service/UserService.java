package com.ciusyan.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.pojo.dto.UserInfoDto;
import com.ciusyan.crm.pojo.po.User;
import com.ciusyan.crm.pojo.vo.request.LoginReqVo;
import com.ciusyan.crm.pojo.vo.request.page.UserPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserPasswordReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserUploadReqVo;
import com.ciusyan.crm.pojo.vo.response.LoginVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import com.ciusyan.crm.pojo.vo.response.UserVo;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface UserService extends IService<User> {

    // 登录
    LoginVo login(LoginReqVo reqVo);

    // 用户信息【部门、角色、个人信息】
    UserInfoDto getUserInfo(Integer id);

    // 用户列表【分页】
    PageVo<UserVo> list(UserPageReqVo query);

    // 保存 or 更新
    @Transactional(readOnly = false)
    Boolean saveOrUpdate(UserReqVo reqVo);

    // 上传头像
    @Transactional(readOnly = false)
    boolean updatePhoto(UserUploadReqVo reqVo) throws Exception;

    // 上传相册
    @Transactional(readOnly = false)
    boolean updateAlbum(UserUploadReqVo reqVo) throws Exception;

    // 修改密码
    @Transactional(readOnly = false)
    boolean updatePassword(UserPasswordReqVo reqVo);
}
