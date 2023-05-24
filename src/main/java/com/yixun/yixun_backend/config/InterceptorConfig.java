package com.yixun.yixun_backend.config;

import com.yixun.yixun_backend.utils.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/api/LogIn/**", "/api/MainPage/**", "/api/News/**", "/api/RelatedDp/**", "/api/SearchInfo/**",
                        "/api/VolAct/**", "/api/Vol/ShowTenVolunteer","/api/Administrator/**","/api/Alipay/**")//放行的接口，应该填入无需登录也可以用的接口
                .excludePathPatterns( "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.woff", "/**/*.ttf","/swagger-ui")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","doc.html","/error") // 放行静态文件
                .excludePathPatterns( "/**");
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

}