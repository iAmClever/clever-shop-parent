package com.clever.sdk;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by clever on 2019/9/22.
 */
@Component
public class AppYiLianPlugin  implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }
}
