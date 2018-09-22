package ff.three.three.job;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.service.crawler.StockQuotationCrawlerService;
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
 * @class CrawlHistoryByDayJob
 * @email forest@magicwindow.cn
 * @date 2018/9/22 20:52
 * @description
 */
@Data
public class CrawlHistoryByDayJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlHistoryByDayJob.class);

    @Autowired
    private StockQuotationCrawlerService stockQuotationCrawlerService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("crawl stock history job run");
        try {
            this.stockQuotationCrawlerService.crawlStockQuotation();
        } catch (MwException e) {
            LOGGER.error("crawl stock job history error ", e);
        }
    }

}