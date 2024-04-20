package com.amazon.product.configs.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "file:${app.config.dir}/application.yml", factory = YamlPropertySourceFactory.class)
public class ApplicationProperties {

    @Autowired
    private Environment environment;

    public void printEnvironment() {
        String property = environment.getProperty("app.config.name");
        System.out.println(property);
    }
}
