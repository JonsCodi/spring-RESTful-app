package org.restful.soccer_league.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setLimitParamName("limit");
    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
    }

}
