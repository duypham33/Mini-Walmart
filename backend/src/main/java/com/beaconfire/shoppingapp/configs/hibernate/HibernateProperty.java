package com.beaconfire.shoppingapp.configs.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@ConfigurationProperties(prefix = "database.hibernate")
public class HibernateProperty {
    private String url;
    private String driver;
    private String username;
    private String password;
    private String dialect;
    private Boolean showSql;
    private String ddlAuto;
}
