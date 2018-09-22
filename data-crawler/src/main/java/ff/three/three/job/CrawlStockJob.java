package ff.three.three.job;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.service.crawler.StockCrawlerService;
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
 * @class CrawlStockJob
 * @email forest@magicwindow.cn
 * @date 2018/9/22 20:51
 * @description
 */
@Data
public class CrawlStockJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlStockJob.class);

    @Autowired
    private StockCrawlerService stockCrawlerService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("crawl stock job run");
        try {
            this.stockCrawlerService.crawl();
        } catch (MwException e) {
            LOGGER.error("crawl stock job error ", e);
        }
    }

}