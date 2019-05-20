package com.cmarchive.bank.serviceutilisateur.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class ServiceUtilisateurConfiguration extends ResourceServerConfigurerAdapter {

    private ConfigMap configMap;

    public ServiceUtilisateurConfiguration(ConfigMap configMap) {
        this.configMap = configMap;
    }

    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(configMap.getCheckTokenEndpointUrl());
        remoteTokenServices.setClientId(configMap.getClientId());
        remoteTokenServices.setClientSecret(configMap.getClientSecret());

        return remoteTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(tokenServices());
    }
}
