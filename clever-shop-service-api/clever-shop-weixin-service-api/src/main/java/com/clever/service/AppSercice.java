package com.clever.service;

import com.clever.entity.AppEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface AppSercice {

    @GetMapping("/getApp")
    public AppEntity get();
}
