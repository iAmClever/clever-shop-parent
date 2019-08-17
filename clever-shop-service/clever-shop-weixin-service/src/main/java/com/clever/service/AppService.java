package com.clever.service;

import com.clever.entity.AppEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppService implements AppSercice {

    @Override
    public AppEntity get() {
        return new AppEntity("1","2");
    }
}
