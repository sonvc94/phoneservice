package com.geardao.phoneservice.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.geardao.phoneservice")
public class FeignConfiguration {

}
