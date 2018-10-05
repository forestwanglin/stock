package ff.three.three.job;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.service.analysis.TxAnalysisService;
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
 * @class AnalysisTxQuotationJob
 * @email forest@magicwindow.cn
 * @date 2018/10/3 15:57
 * @description
 */
@Data
public class AnalysisTxQuotationJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisTxQuotationJob.class);

    @Autowired
    private TxAnalysisService txAnalysisService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("analysis tx quotation job run");
        try {
            this.txAnalysisService.analyze();
        } catch (MwException e) {
            LOGGER.error("analysis tx quotation job error ", e);
        }
    }

}