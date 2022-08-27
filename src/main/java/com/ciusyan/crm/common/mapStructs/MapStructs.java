package com.ciusyan.crm.common.mapStructs;


import com.ciusyan.crm.pojo.bo.UserInfoBo;
import com.ciusyan.crm.pojo.dto.ResourceDto;
import com.ciusyan.crm.pojo.dto.UserInfoDto;
import com.ciusyan.crm.pojo.po.*;
import com.ciusyan.crm.pojo.vo.request.save.*;
import com.ciusyan.crm.pojo.vo.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {
    MapStructFormatter.class
})
public interface MapStructs {

    // 拿到实例对象
    MapStructs INSTANCE = Mappers.getMapper(MapStructs.class);


    // PO -> VO
    @Mapping(
            source = "createdTime",
            target = "createdTime",
            qualifiedBy = MapStructFormatter.Date2Millis.class
    )
    @Mapping(
            source = "updateTime",
            target = "updateTime",
            qualifiedBy = MapStructFormatter.Date2Millis.class
    )
    UserVo po2vo(User po);
    LoginVo po2loginVo(User po);
    RoleVo po2vo(Role po);
    @Mapping(
            source = "createdTime",
            target = "createdTime",
            qualifiedBy = MapStructFormatter.Date2Millis.class
    )
    GoodsVo po2vo(Goods po);
    DepartmentVo po2vo(Department po);
    CategoryVo po2vo(Category po);

    // VO -> PO

    User reqVo2po(UserReqVo vo);
    Resource reqVo2po(ResourceReqVo vo);
    Role reqVo2po(RoleReqVo vo);
    Goods reqVo2po(GoodsReqVo vo);
    Department reqVo2po(DepartmentReqVo vo);
    Category reqVo2po(CategoryReqVo vo);
    User infoReqVo2Po(UserInfoReqVo vo);


    // Bo -> Dto
    @Mapping(
            source = "user",
            target = "user",
            qualifiedBy = MapStructFormatter.User2UserVo.class
    )
    UserInfoDto bo2dto(UserInfoBo bo);

    // PO -> DTO

    ResourceDto po2dto(Resource po);


}
