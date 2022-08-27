package com.ciusyan.crm.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ciusyan.crm.common.enhance.MpPage;
import com.ciusyan.crm.pojo.po.User;
import com.ciusyan.crm.pojo.vo.response.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper extends BaseMapper<User> {

    MpPage<UserVo> selectPageVos(MpPage<UserVo> page,
                                 @Param(Constants.WRAPPER)Wrapper<UserVo> wrapper);

}
