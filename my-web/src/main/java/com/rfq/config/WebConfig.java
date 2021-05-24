package com.rfq.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rfq.interceptor.SetPublicAttributesInterceptor;
import com.rfq.interceptor.WatchHandlerInterceptor;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//启动Swagger2生成接口文档要用注解
//@EnableSwagger2
public class WebConfig implements WebMvcConfigurer{
	
	private Logger logger = LogManager.getLogger(WebConfig.class);
	
	 static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };
	
	@Autowired
	Config config;
	
	/**
     *  <!--swagger资源配置-->	
     * 访问如下查看接口，可以直接测试
     * http://localhost:8080/swagger-ui.html#/
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
                */
        
        registry.addResourceHandler("/favicon.ico")
        	.addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/favicon_one.ico")
    		.addResourceLocations("classpath:/static/img/");
        
        String pyPath=config.getImgPhyPath();
        pyPath=pyPath.replace("\\", "/");
        if(!pyPath.endsWith("/"))
        	pyPath+="/";
        if(!pyPath.startsWith("/"))
        	pyPath="/"+pyPath;
        
        pyPath+="uploadFiles/";
        
        registry.addResourceHandler("/uploadFiles/**")
        		.addResourceLocations("file://"+pyPath);
        		//.addResourceLocations("file:///data/uploadfiles/");

        logger.debug("addResourceLocations:"+"file://"+pyPath);
    }

	@Autowired
    private WatchHandlerInterceptor watchHandlerInterceptor;
	
	@Autowired
	private SetPublicAttributesInterceptor setPublicAttributesInterceptor;
	
//	@Autowired
//	CheckVersionInterceptor checkVersionInterceptor;
//	
	/**
	 * 添加拦截器
	 */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(watchHandlerInterceptor);
        registry.addInterceptor(setPublicAttributesInterceptor);
    }

    /**
     * 添加跨域调用
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
              .allowedOrigins("https://open.weixin.qq.com");// 允许访问微信扫码登录
        //解决跨域问题
        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods(ORIGINS)
        .maxAge(3600);
    }
}
