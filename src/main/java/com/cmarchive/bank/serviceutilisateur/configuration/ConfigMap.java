package com.cmarchive.bank.serviceutilisateur.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "configuration")
public class ConfigMap {

    private String checkTokenEndpointUrl;
    private String clientId;
    private String clientSecret;

    public String getCheckTokenEndpointUrl() {
        return checkTokenEndpointUrl;
    }

    public ConfigMap setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public ConfigMap setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public ConfigMap setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }
}
