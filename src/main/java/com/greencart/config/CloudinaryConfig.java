package com.greencart.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dbmiyxijh",
                "api_key", "883768889317189",
                "api_secret", "7uyEaq1QErKD-pvahiGFNZdBKLw"
        ));
    }
}
