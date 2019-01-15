package com.lz.snappy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerApp {

	/**
	 * 创建一个api应用
	 * apiInfo用于提供api信息
	 * 通过select方法提供一个ApiSelectorBuilder()，实例，来确定哪些接口暴露给swagger
	 * 通过指定包路径来定义要创建的目录
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lz.snappy.demo.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * 用于定义展示页面中的一些信息
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("spring boot用于测试swagger构建restful Apis")
				.description("更多内容请关注https://blog.csdn.net/sanyaoxu_2/article/details/80555328")
				.termsOfServiceUrl("https://blog.csdn.net/sanyaoxu_2/article/details/80555328")
				.contact("sunf")
				.version("1.0").build();
	}

}
