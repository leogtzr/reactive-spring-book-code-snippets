package com.endpoints.config;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "client")
@Component
public class ClientProperties {

    private Http http = new Http();

    @Data
    public static class Http {

        private Basic basic = new Basic();
        private String rootUrl;

        @Data
        public static class Basic {
            private String username;
            private String password;
        }

    }

}
