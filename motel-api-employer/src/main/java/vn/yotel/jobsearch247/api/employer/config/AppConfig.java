package vn.yotel.jobsearch247.api.employer.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.yotel.commons.context.AppContext;
import vn.yotel.commons.util.PasswordUtil;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"vn.yotel"})
@ImportResource(value = {"classpath:applicationContext-webembedded.xml"})
@Profile("JobSearch")
@Slf4j
public class AppConfig {

    /**
     * JDBC properties
     */
    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jndi.datasource}")
    private String jndiDatasource;

    @Value("${jdbc.max-total-connection}")
    private int jdbcMaxTotalConnection;

    @Value("${jdbc.max-idle-connection}")
    private int jdbcMaxIdleConnection;

    @Value("${jdbc.max-init-connection}")
    private int jdbcInitConnection;


    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(this.jdbcDriverClassName);
        basicDataSource.setUrl(this.jdbcUrl);
        basicDataSource.setUsername(PasswordUtil.decryptPropertyValue(this.jdbcUsername));
        basicDataSource.setPassword(PasswordUtil.decryptPropertyValue(this.jdbcPassword));
        basicDataSource.setInitialSize(this.jdbcInitConnection);
        basicDataSource.setMaxIdle(this.jdbcMaxIdleConnection);
        basicDataSource.setMaxTotal(this.jdbcMaxTotalConnection);
        return basicDataSource;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Primary
    public AppContext appContext() {
        return new AppContext();
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
