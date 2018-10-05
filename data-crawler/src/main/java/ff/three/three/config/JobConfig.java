package ff.three.three.config;

import ff.three.three.job.*;
import ff.three.three.service.analysis.TxAnalysisService;
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
    private static final String RT_HAMMER_STOCK_JOB_ID = "RT_HAMMER_STOCK";
    private static final String TXN_DAY_UPDATE_JOB_ID = "TXN_DAY_UPDATE";
    private static final String TX_QUOTATION_JOB_ID = "TX_QUOTATION";
    private static final String TX_QUOTATION_ANALYSIS_JOB_ID = "TX_QUOTATION_ANALYSIS";

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
    public JobDetail txnDayUpdateJobDetail() {
        return JobBuilder.newJob(TxnDayUpdateJob.class)
                .withIdentity(TXN_DAY_UPDATE_JOB_ID, TxnDayUpdateJob.class.getName())
                .storeDurably().build();
    }

    @Bean
    public Trigger txnDayUpdateJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 10 * * ?");
        return TriggerBuilder.newTrigger().forJob(txnDayUpdateJobDetail())
                .withIdentity(TXN_DAY_UPDATE_JOB_ID, TxnDayUpdateJob.class.getName())
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

    @Bean
    public JobDetail rtHammerStockJobDetail() {
        return JobBuilder.newJob(RtHammerStockJob.class)
                .withIdentity(RT_HAMMER_STOCK_JOB_ID, RtHammerStockJob.class.getName())
                .storeDurably().build();
    }

    @Bean
    public Trigger rtHammerStockJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 45/1 14,15 * * ?");
        return TriggerBuilder.newTrigger().forJob(rtHammerStockJobDetail())
                .withIdentity(RT_HAMMER_STOCK_JOB_ID, RtHammerStockJob.class.getName())
                .withSchedule(cronScheduleBuilder).build();
    }

    @Bean
    public JobDetail txQuotationJobDetail() {
        return JobBuilder.newJob(RtHammerStockJob.class)
                .withIdentity(TX_QUOTATION_JOB_ID, CrawlTxQuotationJob.class.getName())
                .storeDurably().build();
    }

    @Bean
    public Trigger txQuotationJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 16 * * ?");
        return TriggerBuilder.newTrigger().forJob(txQuotationJobDetail())
                .withIdentity(TX_QUOTATION_JOB_ID, CrawlTxQuotationJob.class.getName())
                .withSchedule(cronScheduleBuilder).build();
    }

    @Bean
    public JobDetail txQuotationAnalysisJobDetail() {
        return JobBuilder.newJob(RtHammerStockJob.class)
                .withIdentity(TX_QUOTATION_ANALYSIS_JOB_ID, TxAnalysisService.class.getName())
                .storeDurably().build();
    }

    @Bean
    public Trigger txQuotationAnalysisJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 17 * * ?");
        return TriggerBuilder.newTrigger().forJob(txQuotationAnalysisJobDetail())
                .withIdentity(TX_QUOTATION_ANALYSIS_JOB_ID, TxAnalysisService.class.getName())
                .withSchedule(cronScheduleBuilder).build();
    }

}
