package dev.mvc.team6_v2sbm3c;

import dev.mvc.petprofile.PetProfile;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.communityattachment.Communityattachment;
import dev.mvc.culturefile.Culturefile;
import dev.mvc.memberprofile.Memberprofile;
import dev.mvc.notice.Notice;
import dev.mvc.tool.Tool;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Windows: path = "C:/kd/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:///C:/kd/deploy/resort_v2sbm3c_blog/contents/storage
      
        // Ubuntu: path = "/home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:////home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage
      
        // JSP 인식되는 경로: http://localhost:9091/contents/storage";
        registry.addResourceHandler("/member/storage/**").addResourceLocations("file:///" +  Memberprofile.getUploadDir());
        
        // JSP 인식되는 경로: http://localhost:9091/contents/storage";
        registry.addResourceHandler("/culturefile/storage/**").addResourceLocations("file:///" +  Culturefile.getUploadDir());

        // 반려동물 이미지 경로
        registry.addResourceHandler("/petprofile/storage/**").addResourceLocations("file:///" +  PetProfile.getUploadDir());
        
        // JSP 인식되는 경로: http://localhost:9091/attachfile/storage";
        registry.addResourceHandler("/communityattachment/storage/**").addResourceLocations("file:///" +  Communityattachment.getUploadDir());
        
        registry.addResourceHandler("/notice/storage/**").addResourceLocations("file:///" +  Notice.getUploadDir());
        
        // JSP 인식되는 경로: http://localhost:9091/member/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(3000);
    }
}
