package com.clever.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clever on 2019/8/18.
 */



// 添加文档来源
@Slf4j
@Component
@Primary
class DocumentationConfig implements SwaggerResourcesProvider {

    @Value("${clever.shop.zuul.swagger.document}")
    private String document;

    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        JSONArray array = JSONObject.parseArray(document);
        log.info("{}",array);

        for(int i=0;i<array.size();i++){
            JSONObject o = (JSONObject) array.get(i);
            resources.add(swaggerResource(o.getString("name"),o.getString("location"),o.getString("version")));
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }


}
