package ff.three.three.config;

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

//    private static final String VALIDATE_TXN_SIGN_MAP_JOB_ID = "VALIDATE_TXN_SIGN_MAP";
//    private static final String CHECK_TXN_STATUS_JOB_ID = "CHECK_TXN_STATUS";
//    private static final String SYNC_ADDRESS_BALANCE_JOB_ID = "SYNC_ADDRESS_BALANCE";
//    private static final String SCAN_ADDRESS_TXN_JOB_ID = "SCAN_ADDRESS_TXN";
//    private static final String LOAD_ETH_TXN_JOB_ID = "LOAD_ETH_TXN";
//
//    private static final String RECONCILIATION_JOB_ID = "RECONCILIATION";
//
//    @Bean
//    public JobDetail testJobDetail() {
//        return JobBuilder.newJob(TestJob.class).withIdentity("Test", TestJob.class.getName())
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger testJobTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
//        return TriggerBuilder.newTrigger().forJob(testJobDetail()).withIdentity("Test", TestJob.class.getName())
//                .withSchedule(cronScheduleBuilder).build();
//    }
//
//    @Bean
//    public JobDetail validateTxnSignMapJobDetail() {
//        return JobBuilder.newJob(ValidateTxnSignMapJob.class)
//                .withIdentity(VALIDATE_TXN_SIGN_MAP_JOB_ID, ValidateTxnSignMapJob.class.getName())
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger validateTxnSignMapJobTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
//        return TriggerBuilder.newTrigger().forJob(validateTxnSignMapJobDetail())
//                .withIdentity(VALIDATE_TXN_SIGN_MAP_JOB_ID, ValidateTxnSignMapJob.class.getName())
//                .withSchedule(cronScheduleBuilder).build();
//    }
//
//    @Bean
//    public JobDetail checkTxnStatusJobDetail() {
//        return JobBuilder.newJob(CheckTxnStatusJob.class)
//                .withIdentity(CHECK_TXN_STATUS_JOB_ID, CheckTxnStatusJob.class.getName())
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger checkTxnStatusTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 * * * * ?");
//        return TriggerBuilder.newTrigger().forJob(checkTxnStatusJobDetail())
//                .withIdentity(CHECK_TXN_STATUS_JOB_ID, CheckTxnStatusJob.class.getName())
//                .withSchedule(cronScheduleBuilder).build();
//    }
//
//    @Bean
//    public JobDetail syncAddressBalanceJobDetail() {
//        return JobBuilder.newJob(SyncAddressBalanceJob.class)
//                .withIdentity(SYNC_ADDRESS_BALANCE_JOB_ID, SyncAddressBalanceJob.class.getName())
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger syncAddressBalanceTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 * * * ?");
//        return TriggerBuilder.newTrigger().forJob(syncAddressBalanceJobDetail())
//                .withIdentity(SYNC_ADDRESS_BALANCE_JOB_ID, SyncAddressBalanceJob.class.getName())
//                .withSchedule(cronScheduleBuilder).build();
//    }
//
//    @Bean
//    public JobDetail scanAddressTxnJobDetail() {
//        return JobBuilder.newJob(ScanAddressTxnJob.class)
//                .withIdentity(SCAN_ADDRESS_TXN_JOB_ID, ScanAddressTxnJob.class.getName())
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger scanAddressTxnTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 5/10 * * * ?");
//        return TriggerBuilder.newTrigger().forJob(scanAddressTxnJobDetail())
//                .withIdentity(SCAN_ADDRESS_TXN_JOB_ID, ScanAddressTxnJob.class.getName())
//                .withSchedule(cronScheduleBuilder).build();
//    }
//
//    @Bean
//    public JobDetail loadEthTxnJobDetail() {
//        return JobBuilder.newJob(LoadEthTxnJob.class)
//                .withIdentity(LOAD_ETH_TXN_JOB_ID, LoadEthTxnJob.class.getName())
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger loadEthTxnTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/10 * * * ?");
//        return TriggerBuilder.newTrigger().forJob(loadEthTxnJobDetail())
//                .withIdentity(LOAD_ETH_TXN_JOB_ID, LoadEthTxnJob.class.getName())
//                .withSchedule(cronScheduleBuilder).build();
//    }
//
//    @Bean
//    public JobDetail reconciliationJobDetail() {
//        return JobBuilder.newJob(LoadEthTxnJob.class)
//                .withIdentity(RECONCILIATION_JOB_ID, ReconciliationJob.class.getName())
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger reconciliationTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 5 0 * * ?");
//        return TriggerBuilder.newTrigger().forJob(reconciliationJobDetail())
//                .withIdentity(RECONCILIATION_JOB_ID, ReconciliationJob.class.getName())
//                .withSchedule(cronScheduleBuilder).build();
//    }


}
