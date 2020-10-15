package com.hyq.robot.config;

import com.google.gson.GsonBuilder;
import freemarker.template.Configuration;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ElasticsearchVersion;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.boot.autoconfigure.elasticsearch.jest.JestProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;

@org.springframework.context.annotation.Configuration
public class JestConfig {

    @Resource
    private JestProperties jestProperties;

    @Bean
    public JestClient jestClient(){

        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(jestProperties.getUris())
                .elasticsearchVersion(ElasticsearchVersion.V55)
                .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create())
                .connTimeout(jestProperties.getConnectionTimeout().getNano())
                .readTimeout(jestProperties.getReadTimeout().getNano())
                .multiThreaded(jestProperties.isMultiThreaded())
                .build());

        return factory.getObject();
    }

    @Bean("ftlConfig")
    public Configuration getFreeMarkerConfiguration(){
        freemarker.template.Configuration freeMarkerCfg = new Configuration(Configuration.VERSION_2_3_23);
        freeMarkerCfg.setClassForTemplateLoading(this.getClass(), "/ftl");
        freeMarkerCfg.setNumberFormat("#");
        freeMarkerCfg.setSharedVariable("ojb2Json",new Obj2JsonMethod());
        return freeMarkerCfg;
    }
}
