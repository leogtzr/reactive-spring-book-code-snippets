package com.reactive.rsb.utils;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

public abstract class SpringUtils {

    public static ConfigurableApplicationContext run(final Class<?> sources, final String profile) {

        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        if (StringUtils.hasText(profile)) {
            applicationContext.getEnvironment().setActiveProfiles(profile);
        }

        applicationContext.register(sources);
        applicationContext.refresh();
        applicationContext.start();

        return applicationContext;
    }

}
