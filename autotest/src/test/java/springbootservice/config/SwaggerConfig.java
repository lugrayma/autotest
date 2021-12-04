package springbootservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * swagger访问地址：http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apInfo())
				.pathMapping("/")
				.select()
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//不显示错误的接口地址
				.paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
				.build();
	}

	private ApiInfo apInfo() { 
		return new ApiInfoBuilder().title("我的接口文档")
				.contact(new Contact("lugreyma", "175263092@qq.com", ""))
				.description("这是我的swaggerui生成的接口文档")
				.version("1.0.0.1")
				.build();
	}
}
