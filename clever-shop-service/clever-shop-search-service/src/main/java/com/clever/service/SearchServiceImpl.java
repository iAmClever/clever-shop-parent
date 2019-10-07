package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.dto.ProductDto;
import com.clever.entity.ProductEntity;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.esResposiory.ProductReposiory;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by clever on 2019/9/8.
 */
@RestController
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ProductReposiory productReposiory;

    @Override
    public BaseResponse<List<ProductDto>> search(String name) {


        //int a = 1/0;
        // 1.拼接查询条件
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 2.模糊查询name字段
        //builder.must(QueryBuilders.fuzzyQuery("name", name));
        // 2.模糊查询多个字段（含有 搜索关键字）
        builder.must(QueryBuilders.multiMatchQuery(name, "name", "subtitle", "detail"));
        Pageable pageable = new QPageRequest(0, 5);
        // 3.调用ES接口查询
        Page<ProductEntity> page = productReposiory.search(builder, pageable);
        // 4.获取集合数据
        List<ProductEntity> content = page.getContent();
        // 5.将entity转换dto
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        List<ProductDto> mapAsList = mapperFactory.getMapperFacade().mapAsList(content, ProductDto.class);
        return BaseResponseUtil.suc(mapAsList);
    }
}
