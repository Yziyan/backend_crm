package com.ciusyan.crm.controller;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.common.cache.Caches;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.shiro.TokenFilter;
import com.ciusyan.crm.common.util.Constants;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.pojo.dto.UserInfoDto;
import com.ciusyan.crm.pojo.po.User;
import com.ciusyan.crm.pojo.vo.request.LoginReqVo;
import com.ciusyan.crm.pojo.vo.request.page.UserPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserInfoReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserPasswordReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserReqVo;
import com.ciusyan.crm.pojo.vo.request.save.UserUploadReqVo;
import com.ciusyan.crm.pojo.vo.response.LoginVo;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.pojo.vo.result.JsonVo;
import com.ciusyan.crm.pojo.vo.result.PageJsonVo;
import com.ciusyan.crm.pojo.vo.response.UserVo;
import com.ciusyan.crm.pojo.vo.result.DataJsonVo;
import com.ciusyan.crm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/users")
public class UserController extends BaseController<User, UserReqVo> {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public DataJsonVo<LoginVo> login(@RequestBody LoginReqVo reqVo) {
        return JsonVos.ok(userService.login(reqVo));
    }

    @PostMapping("/uploadPhoto")
    @ApiOperation("上传头像")
    public JsonVo uploadPhoto(UserUploadReqVo reqVo) throws Exception {

        if (userService.updatePhoto(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/uploadAlbum")
    @ApiOperation("编辑相册")
    public JsonVo uploadAlbum(UserUploadReqVo reqVo) throws Exception {
        if (userService.updateAlbum(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public JsonVo logout(@RequestHeader(TokenFilter.HEADER_TOKEN) String token) {
        // 清除缓存里的 Token
        Caches.removeToken(token);
        return JsonVos.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation("通过Id获取用户信息【角色 + 部门 + 个人信息】")
    public DataJsonVo<UserInfoDto> getUserInfo(@PathVariable Integer id) {
        return JsonVos.ok(userService.getUserInfo(id));
    }

    @GetMapping("/getUser")
    @ApiOperation("根据用户ID获取用户信息")
    public DataJsonVo<UserVo> getUserById(@RequestParam @NotNull(message = "ID不能为空") Integer userId) {
        return JsonVos.ok(MapStructs.INSTANCE.po2vo(userService.getById(userId)));
    }

    @PostMapping("/list")
    @ApiOperation("用户列表")
    @RequiresPermissions(Constants.Permission.SYS_USER_QUERY)
    public PageJsonVo<UserVo> list(@RequestBody UserPageReqVo query) {
        return JsonVos.ok(userService.list(query));
    }

    @PostMapping("/password")
    @ApiOperation("修改密码")
    public JsonVo updatePassword(@RequestBody UserPasswordReqVo reqVo,
                                 HttpServletRequest request) {
        String token = request.getHeader(TokenFilter.HEADER_TOKEN);
        reqVo.setToken(token);
        if (userService.updatePassword(reqVo)) {
            Caches.removeToken(token);
            return JsonVos.ok(CodeMsg.UPDATE_PASSWORD_OK);
        } else {
            return JsonVos.error(CodeMsg.UPDATE_PASSWORD_ERROR);
        }
    }

    @PostMapping("updateInfo")
    @ApiOperation("修改用户个人信息")
    public JsonVo save(@RequestBody UserInfoReqVo reqVo) {
        User user = MapStructs.INSTANCE.infoReqVo2Po(reqVo);
        if (userService.updateById(user)) {
            return JsonVos.ok(CodeMsg.UPDATE_USERINFO_OK);
        } else {
            return JsonVos.error(CodeMsg.UPDATE_USERINFO_ERROR);
        }
    }

    // 【重写父类的保存和更新方法】
    @Override
    @RequiresPermissions(value = {
            Constants.Permission.SYS_USER_CREATE,
            Constants.Permission.SYS_USER_UPDATE
    }, logical = Logical.OR)
    public JsonVo save(@RequestBody UserReqVo userReqVo) {
        if (userService.saveOrUpdate(userReqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_USER_DELETE)
    public JsonVo remove(String ids) {
        return super.remove(ids);
    }

    @Override
    protected IService<User> getService() {
        return userService;
    }

    @Override
    protected Function<UserReqVo, User> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }
}
