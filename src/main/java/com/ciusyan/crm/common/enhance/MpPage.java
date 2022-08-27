package com.ciusyan.crm.common.enhance;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.pojo.vo.request.page.PageReqVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;

import java.util.List;
import java.util.function.Function;

public class MpPage<T> extends Page<T> {

    private final PageReqVo reqVo;

    public MpPage(PageReqVo reqVo) {
        // 将分页参数传递给Mybatis plus 的 Page
        super(reqVo.getPage(), reqVo.getSize());
        this.reqVo = reqVo;
    }

    private <N> PageVo<N> commonBuildVo(List<N> data) {
        // 用Mybatis plus 返回的结果，【给自己的PageReqVo赋值】：因为前端传过来的可能是错误的
        reqVo.setPage(getCurrent());
        reqVo.setSize(getSize());

        // 用Mybatis plus 返回的结果【给分页结果赋值】
        PageVo<N> pageVo = new PageVo<>();
        pageVo.setCount(getTotal());
        pageVo.setPages(getPages());
        pageVo.setData(data);

        return pageVo;
    }

    public PageVo<T> buildVo() {
        return commonBuildVo(getRecords());
    }

    // 将 T -> R
    public <R> PageVo<R> buildVo(Function<T, R> function) {
        return commonBuildVo(Streams.map(getRecords(), function));
    }


}
