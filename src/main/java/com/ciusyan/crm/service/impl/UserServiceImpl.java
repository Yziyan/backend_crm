package com.ciusyan.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.common.cache.Caches;
import com.ciusyan.crm.common.enhance.MpLambdaQueryWrapper;
import com.ciusyan.crm.common.enhance.MpPage;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.common.util.upload.UploadEditParam;
import com.ciusyan.crm.common.util.upload.UploadEditResult;
import com.ciusyan.crm.common.util.upload.Uploads;
import com.ciusyan.crm.mapper.UserMapper;
import com.ciusyan.crm.pojo.bo.UserInfoBo;
import com.ciusyan.crm.pojo.dto.UserInfoDto;
import com.ciusyan.crm.pojo.dto.UserPermissionDto;
import com.ciusyan.crm.pojo.po.*;
import com.ciusyan.crm.pojo.vo.request.LoginReqVo;
import com.ciusyan.crm.pojo.vo.request.page.UserPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserPasswordReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserUploadReqVo;
import com.ciusyan.crm.pojo.vo.response.LoginVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import com.ciusyan.crm.pojo.vo.response.UserVo;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ResourceService resourceService;

    /**
     * 登录
     * @param reqVo：登录信息
     * @return ：【简单的用户信息 + Token】
     */
    @Override
    public LoginVo login(LoginReqVo reqVo) {

        MpLambdaQueryWrapper<User> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(User::getName, reqVo.getName());
        User userPo = baseMapper.selectOne(wrapper);

        if (userPo == null) { // 没有此账号
            return JsonVos.raise(CodeMsg.WARNING_NAME);
        }

        if (!userPo.getPassword().equals(reqVo.getPassword())) { // 密码错误
            return JsonVos.raise(CodeMsg.WARNING_PASSWORD);
        }

        if (userPo.getEnable() == 0) { // 被禁用了
            return JsonVos.raise(CodeMsg.WARNING_UNABLE);
        }

        // 用户的角色信息
        List<Role> roles = roleService.listRoleByUserId(userPo.getId());

        // 构建 UserPermissionDto
        UserPermissionDto permissionDto = new UserPermissionDto();
        permissionDto.setUser(userPo);
        if (!CollectionUtils.isEmpty(roles)) {
            permissionDto.setRoles(roles);
            // 查询对应的资源ID
            List<Short> roleIds = Streams.map(roles, Role::getId);
            List<Resource> resources = resourceService.listResByRoleId(roleIds);
            permissionDto.setResources(resources);
        }

        // 保存Token
        String token = UUID.randomUUID().toString();
        Caches.putToken(token, permissionDto);

        // 将 【Po -> Vo】
        LoginVo vo = MapStructs.INSTANCE.po2loginVo(userPo);
        vo.setToken(token);

        return vo;
    }

    /**
     * 通过用户id获取用户信息
     *
     * @param id ：用户id
     * @return ：【部门、角色、基本信息】
     */
    @Override
    public UserInfoDto getUserInfo(Integer id) {

        // 用户的信息
        User userPo = baseMapper.selectById(id);

        // 用户的所有角色
        List<Role> roles = roleService.listRole(roleService.listRoleIds(id));

        Department department = departmentService.getByUserId(id);

        UserInfoBo userInfoBo = new UserInfoBo();
        userInfoBo.setUser(userPo);
        userInfoBo.setDepartment(department);
        userInfoBo.setRoles(roles);

        return MapStructs.INSTANCE.bo2dto(userInfoBo);

    }

    /**
     * 分页查询【用户】
     *
     * @param query ： 分页的条件【页数、大小】
     * @return ： 用户列表【部门id 用户id】
     */
    @Override
    public PageVo<UserVo> list(UserPageReqVo query) {

        // 查询条件
        MpLambdaQueryWrapper<User> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(query.getName(), User::getName).
                like(query.getNickname(), User::getNickname).
                like(query.getCellphone(), User::getCellphone).
                like(query.getEnable(), User::getEnable).
                between(query.getCreatedTime(), User::getCreatedTime);

        // 分页查询后的结果
        PageVo<UserVo> pageVo = baseMapper.
                selectPage(new MpPage<>(query), wrapper).
                buildVo(MapStructs.INSTANCE::po2vo);

        // 将查询的 UserVo 设置【roleId】
        Streams.map(pageVo.getData(), (userVo) -> {
            userVo.setRoleIds(roleService.listRoleIds(userVo.getId()));
            return userVo;
        });
        return pageVo;
    }

    /**
     * 保存 or 更新
     *
     * @param reqVo：客户端传过来的【用户信息 + 角色id】
     * @return ：是否成功
     */
    @Override
    public Boolean saveOrUpdate(UserReqVo reqVo) {

        // reqVo -> Po
        User po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 保存 & 更新 【用户信息】
        if (!saveOrUpdate(po)) return false;

        Integer id = reqVo.getId();
        if (id != null && id > 0) {

            // 将修改掉的用户踢下线【让其重新登录】
            Caches.removeToken(Caches.get(id));

            // 如果是编辑，去把所有角色先删除
            roleService.removeByUserId(id);
        }

        // 保存角色信息
        String roleIdsStr = reqVo.getRoleIds();
        if (StringUtils.isEmpty(roleIdsStr)) return true; // 说明没有传角色过来

        String[] roleIds = roleIdsStr.split(",");
        List<UserRole> userRoles = new ArrayList<>();

        Integer userId = po.getId();
        for (String roleId : roleIds) { // 构建 UserRole对象
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(Short.valueOf(roleId));
            userRoles.add(userRole);
        }

        return userRoleService.saveBatch(userRoles);
    }

    /**
     * 上传头像
     *
     * @param reqVo : 【头像 + 头像原地址】
     * @return ：是否成功
     */
    @Override
    public boolean updatePhoto(UserUploadReqVo reqVo) throws Exception {

        String filePath;
        MultipartFile photo = reqVo.getPhoto();
        User user = new User();
        user.setId(reqVo.getId());

        if (photo != null) { // 保存头像
            filePath = Uploads.uploadImage(photo);
            if (!StringUtils.isEmpty(filePath)) user.setPhoto(filePath);
        } else {
            return JsonVos.raise(CodeMsg.NO_FILE);
        }

        try {
            boolean res = baseMapper.updateById(user) > 0;
            if (res) {
                // 说明更新成功了【删除原先上传的文件】
                Uploads.deleteFile(reqVo.getPhotoUrl());
            }
            return res;
        } catch (Exception e) { // 在这里面出现异常，先把刚刚传的文件删除掉
            Uploads.deleteFile(filePath);
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 上传相册
     *
     * @param reqVo：【相册 + 相册原地址】
     * @return ：是否成功
     */
    @Override
    public boolean updateAlbum(UserUploadReqVo reqVo) throws Exception {

        // 查出需要编辑的用户
        User user = baseMapper.selectById(reqVo.getId());

        // 【新文件数据 + 对标索引 + 数据库以前存的路径】
        UploadEditParam editParam = new UploadEditParam(reqVo.getAlbum(),
                reqVo.getMatchIndex(), user.getAlbum());

        // 做编辑返回 【新上传的文件路径 + 需要删除的文件路径 + 保存数据库的路径】
        UploadEditResult editResult = Uploads.uploadEditImages(editParam);

        user.setAlbum(editResult.getFilePath());

        try {
            boolean res = baseMapper.updateById(user) > 0;
            // 保存成功，删除原先的图片
            if (res) {
                Uploads.deleteFiles(editResult.getNeedDeletePath());
            }
            return res;

        } catch (Exception e) {
            // 出现异常，那么将刚保存的照片删除掉
            Uploads.deleteFiles(editResult.getNewUploadFilePath());
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 修改密码
     * @param reqVo：【旧密码 + 新密码】
     * @return ：是否成功
     */
    @Override
    public boolean updatePassword(UserPasswordReqVo reqVo) {

        User user = baseMapper.selectById(reqVo.getId());

        if (!reqVo.getOldPassword().equals(user.getPassword())) {
            // 旧密码不正确
            return JsonVos.raise(CodeMsg.WARNING_OLD_PASSWORD);
        }

        if (reqVo.getNewPassword().equals(user.getPassword())) {
            return JsonVos.raise(CodeMsg.WARNING_OLD_NEW_PASSWORD);
        }

        user.setPassword(reqVo.getNewPassword());
        boolean res = baseMapper.updateById(user) > 0;
        if (res) {
            Caches.removeToken(reqVo.getToken());
        }
        return res;
    }
}



