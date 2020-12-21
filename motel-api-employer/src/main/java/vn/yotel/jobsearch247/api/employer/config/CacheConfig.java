package vn.yotel.jobsearch247.api.employer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableCaching
@EnableScheduling
@Configuration
@Slf4j
public class CacheConfig {
    public static final String TOTAL_CRAWL_CACHE_NAME = "totalCrawl";
    public static final String CANDIDATE_FREE_CACHE_NAME = "candidateFree";
    public static final String LIST_JOB = "listJob";
    public static final String LIST_BASIC_LOCATION = "listBasicLocation";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    public static final String initMessageInfo = "Cache info : caching {} at {}";
    public static final String clearMessageInfo = "Cache info : clear {} at {}";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Scheduled(fixedRate = Timer.ONE_HOUR)
    @CacheEvict(value = {TOTAL_CRAWL_CACHE_NAME}, allEntries = true)
    public void clearTotalCraw() {
        log.info(clearMessageInfo, TOTAL_CRAWL_CACHE_NAME, DATE_FORMAT.format(new Date()));

    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = {CANDIDATE_FREE_CACHE_NAME}, allEntries = true)
    public void clearCandidateFree() {
        log.info(clearMessageInfo, CANDIDATE_FREE_CACHE_NAME, DATE_FORMAT.format(new Date()));

    }
}
