package com.example.procurehub.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    @Bean
    public DefaultConfiguration jooqConfiguration(DataSource dataSource) {
        DefaultConfiguration jooqConfig = new DefaultConfiguration();
        jooqConfig.set(dataSource);
        jooqConfig.set(SQLDialect.POSTGRES);
        jooqConfig.set(new Settings().withExecuteLogging(true));
        return jooqConfig;
    }

    @Bean
    public DSLContext dslContext(DefaultConfiguration configuration) {
        return DSL.using(configuration);
    }
}
