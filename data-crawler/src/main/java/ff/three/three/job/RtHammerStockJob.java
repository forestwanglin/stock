package ff.three.three.job;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.service.crawler.RtHammerStockCrawlerService;
import lombok.Data;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Forest Wang
 * @package ff.three.three.job
 * @class RtHammerStockJob
 * @email forest@magicwindow.cn
 * @date 2018/9/23 23:03
 * @description
 */
@Data
public class RtHammerStockJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RtHammerStockJob.class);

    @Autowired
    private RtHammerStockCrawlerService rtHammerStockCrawlerService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("rt hammer stock job run");
        try {
            this.rtHammerStockCrawlerService.crawl();
        } catch (MwException e) {
            LOGGER.error("rt hammer stock job error ", e);
        }
    }
}