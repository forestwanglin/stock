package ff.three.three.config;

import ff.three.three.job.CrawlHistoryByDayJob;
import ff.three.three.job.CrawlStockJob;
import ff.three.three.job.FallingStockJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Forest Wang
 * @package io.merculet.config
 * @class JobConfig
 * @email forest@magicwindow.cn
 * @date 2018/7/4 21:56
 * @description
 */
@Configuration
public class JobConfig {

    private static final String CRAWL_STOCK_JOB_ID = "CRAWL_STOCK";
    private static final String CRAWL_STOCK_HISTORY_BY_DAY_JOB_ID = "CRAWL_STOCK_HISTORY_BY_DAY";
    private static final String FALLING_STOCK_JOB_ID = "FALLING_STOCK";

    @Bean
    public JobDetail crawlJobDetail() {
        return JobBuilder.newJob(CrawlStockJob.class).withIdentity(CRAWL_STOCK_JOB_ID, CrawlStockJob.class.getName())
                .storeDurably().build();
    }

    @Bean
    public Trigger crawlJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 30 0 * * ?");
        return TriggerBuilder.newTrigger().forJob(crawlJobDetail()).withIdentity(CRAWL_STOCK_JOB_ID, CrawlStockJob.class.getName())
                .withSchedule(cronScheduleBuilder).build();
    }

    @Bean
    public JobDetail crawlStockHistoryByDayJobDetail() {
        return JobBuilder.newJob(CrawlHistoryByDayJob.class)
                .withIdentity(CRAWL_STOCK_HISTORY_BY_DAY_JOB_ID, CrawlHistoryByDayJob.class.getName())
                .storeDurably().build();
    }

    @Bean
    public Trigger crawlStockHistoryByDayJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 30 15 * * ?");
        return TriggerBuilder.newTrigger().forJob(crawlStockHistoryByDayJobDetail())
                .withIdentity(CRAWL_STOCK_HISTORY_BY_DAY_JOB_ID, CrawlHistoryByDayJob.class.getName())
                .withSchedule(cronScheduleBuilder).build();
    }


    @Bean
    public JobDetail fallingStockJobDetail() {
        return JobBuilder.newJob(FallingStockJob.class)
                .withIdentity(FALLING_STOCK_JOB_ID, FallingStockJob.class.getName())
                .storeDurably().build();
    }

    @Bean
    public Trigger fallingStockJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 18 * * ?");
        return TriggerBuilder.newTrigger().forJob(fallingStockJobDetail())
                .withIdentity(FALLING_STOCK_JOB_ID, FallingStockJob.class.getName())
                .withSchedule(cronScheduleBuilder).build();
    }

}
