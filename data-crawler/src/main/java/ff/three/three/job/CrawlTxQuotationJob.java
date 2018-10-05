package ff.three.three.job;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.service.crawler.TxCrawlerService;
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
 * @class CrawlTxQuotationJob
 * @email forest@magicwindow.cn
 * @date 2018/10/3 15:25
 * @description
 */
@Data
public class CrawlTxQuotationJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlTxQuotationJob.class);

    @Autowired
    private TxCrawlerService txCrawlerService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("crawl tx quotation job run");
        try {
            this.txCrawlerService.crawlLatestQuotation();
        } catch (MwException e) {
            LOGGER.error("crawl tx quotation job error ", e);
        }
    }

}