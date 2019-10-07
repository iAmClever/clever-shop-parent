package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by clever on 2019/9/8.
 */
public interface SearchService {

    @GetMapping("/search")
    public BaseResponse<List<ProductDto>> search(String name);
}
