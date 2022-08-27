package com.ciusyan.crm.test.service;


import com.ciusyan.crm.CrmApplication;
import com.ciusyan.crm.mapper.RoleResourceMapper;
import com.ciusyan.crm.pojo.po.RoleResource;
import com.ciusyan.crm.pojo.vo.echart.EtAddressCountVo;
import com.ciusyan.crm.pojo.vo.echart.EtGoodsCountVo;
import com.ciusyan.crm.service.CategoryService;
import com.ciusyan.crm.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = CrmApplication.class)
@RunWith(SpringRunner.class)
@Rollback
@AutoConfigureMockMvc
public class GoodsServiceTest {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Test
    public void listCount() {
        List<EtAddressCountVo> etAddressCountVos = goodsService.listAddressStat();

        System.out.println(etAddressCountVos);
    }

    @Test
    public void statCount() {
        List<EtGoodsCountVo> statGoods = categoryService.listStatGoods();
        System.out.println(statGoods);
    }

    @Test
    public void addRow() {

        for (int i = 10; i < 43; i++) {


            Short aShort = Short.valueOf("5");

            Short aShort1 = Short.valueOf(String.valueOf(i + 1));
            roleResourceMapper.insert(new RoleResource(aShort, aShort1));

        }

    }

}
