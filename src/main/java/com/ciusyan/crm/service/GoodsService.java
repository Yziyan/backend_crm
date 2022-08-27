package com.ciusyan.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.pojo.po.Goods;
import com.ciusyan.crm.pojo.vo.echart.EtAddressCountVo;
import com.ciusyan.crm.pojo.vo.echart.EtGoodsCountVo;
import com.ciusyan.crm.pojo.vo.request.page.GoodsPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.GoodsReqVo;
import com.ciusyan.crm.pojo.vo.response.GoodsVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional(readOnly = true)
public interface GoodsService extends IService<Goods> {

    // 分页查询商品数据
    PageVo<GoodsVo> list(GoodsPageReqVo query);

    // 保存 Or 更新
    @Transactional(readOnly = false)
    boolean saveOrUpdate(GoodsReqVo reqVo);

    // 单独上传文件
    @Transactional(readOnly = true)
    boolean upload(MultipartFile file);

    // 根据分类ID统计商品数据
    EtGoodsCountVo getGoodsStatByCatId(Short categoryId);

    // 根据地址统计销量
    List<EtAddressCountVo> listAddressStat();
}
