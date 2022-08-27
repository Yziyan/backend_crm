package com.ciusyan.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.common.enhance.MpLambdaQueryWrapper;
import com.ciusyan.crm.common.enhance.MpPage;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.common.util.upload.Uploads;
import com.ciusyan.crm.mapper.GoodsMapper;
import com.ciusyan.crm.pojo.po.Goods;
import com.ciusyan.crm.pojo.vo.echart.EtAddressCountVo;
import com.ciusyan.crm.pojo.vo.echart.EtGoodsCountVo;
import com.ciusyan.crm.pojo.vo.request.page.GoodsPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.GoodsReqVo;
import com.ciusyan.crm.pojo.vo.response.GoodsVo;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import com.ciusyan.crm.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GoodsServiceImpl
        extends ServiceImpl<GoodsMapper, Goods>
        implements GoodsService {

    /**
     * / 分页查询商品数据
     * @param query :查询信息
     * @return :商品列表
     */
    @Override
    public PageVo<GoodsVo> list(GoodsPageReqVo query) {

        // 查询条件
        MpLambdaQueryWrapper<Goods> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(query.getName(), Goods::getName).
                like(query.getDescription(), Goods::getDescription).
                like(query.getAddress(), Goods::getAddress).
                like(query.getState(), Goods::getState).
                like(query.getCategoryId(), Goods::getCategoryId).
                between(query.getCreatedTime(), Goods::getCreatedTime);

        // 分页查询
        return baseMapper.
                selectPage(new MpPage<>(query), wrapper).
                buildVo(MapStructs.INSTANCE::po2vo);
    }


    /**
     * / 保存 Or 更新
     * @param reqVo：客户端传过来的商品数据
     * @return ：成功 or 失败
     */
    @Override
    public boolean saveOrUpdate(GoodsReqVo reqVo) {
        try {
            // 将其转换为 Po 对象
            Goods po = MapStructs.INSTANCE.reqVo2po(reqVo);
            // 上传图片
            String imgPath = Uploads.uploadImage(reqVo.getImgFile());
            // 说明图片上传成功了
            if (imgPath != null) {
                po.setImgUrl(imgPath);
            }

            // 保存或更新数据
            boolean ret = saveOrUpdate(po);
            // 更新成功 并且传了新的图片
            if (ret && imgPath != null) {
                // 删除以前的图片
                Uploads.deleteFile(reqVo.getImgUrl());
            }
            return ret;

        } catch (Exception e) {
            e.printStackTrace();
            return JsonVos.raise(CodeMsg.UPLOAD_IMG_ERROR);
        }
    }

    @Override
    public boolean upload(MultipartFile file) {
       try {
           // 图片上传
           String imgFilePath = Uploads.uploadImage(file);

           return StringUtils.isEmpty(imgFilePath);

       } catch (Exception e) {
           return false;
       }


    }


    /**
     * 根据分类ID统计商品信息
     * @param parentCateId：商品的分类
     * @return ：改分类的统计信息
     */
    @Override
    public EtGoodsCountVo getGoodsStatByCatId(Short parentCateId) {

        MpLambdaQueryWrapper<Goods> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Goods::getCategoryId, parentCateId);

        // 商品统计
        EtGoodsCountVo etGoodsCountVo = new EtGoodsCountVo();
        Long goodsCount = baseMapper.selectCount(wrapper);
        Integer goodsSaleCount = 0;
        Integer goodsFavorCount = 0;

        List<Goods> goodsPos = baseMapper.selectList(wrapper);
        for (Goods goods : goodsPos) {
            goodsFavorCount += goods.getFavorCount();
            goodsSaleCount += goods.getSaleCount();
        }

        etGoodsCountVo.setId(parentCateId);
        etGoodsCountVo.setGoodsCount(goodsCount);
        etGoodsCountVo.setGoodsSaleCount(goodsSaleCount);
        etGoodsCountVo.setGoodsFavorCount(goodsFavorCount);

        return etGoodsCountVo;

    }


    /**
     * 根据生产地址统计销量
     * @param address：商品出产地
     * @return ：统计结果
     */
    private EtAddressCountVo getGoodsStatByAddress(String address) {

        MpLambdaQueryWrapper<Goods> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Goods::getAddress, address).select(Goods::getSaleCount);

        List<Object> list = baseMapper.selectObjs(wrapper);
        List<Integer> listSaleCount = Streams.map(list, (item) -> Integer.parseInt(item.toString()));

        EtAddressCountVo addressCountVo = new EtAddressCountVo();
        Integer totalSaleCount = 0;
        for (Integer saleCount : listSaleCount) {
            totalSaleCount += saleCount;
        }

        addressCountVo.setAddress(address);
        addressCountVo.setAddressSaleCount(totalSaleCount);

        return addressCountVo;
    }


    /**
     * / 根据地址统计销量
     * @return ：统计结果
     */
    @Override
    public List<EtAddressCountVo> listAddressStat() {

        // 查出所有的城市
        MpLambdaQueryWrapper<Goods> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(Goods::getAddress);

        Set<String> listCity = new HashSet<>(Streams.
                map(baseMapper.selectObjs(wrapper), (item) -> (String) item));

        List<EtAddressCountVo> result = new ArrayList<>();
        for (String city : listCity) {
            result.add(getGoodsStatByAddress(city));
        }

        return result;
    }
}
