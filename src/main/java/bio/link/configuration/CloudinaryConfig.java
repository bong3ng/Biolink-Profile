package bio.link.configuration;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class CloudinaryConfig {


//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//
//        resolver.setDefaultEncoding("UTF-8");
//        return resolver;
//    }


    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloud = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "biocloudsieunhan",
                "api_key" , "122658443616462",
                "api_secret" , "s6XRCdM7fX3rBQpa49wAxdMcdT4",
                "secure" , true
        ));

        return cloud;
    }
}
