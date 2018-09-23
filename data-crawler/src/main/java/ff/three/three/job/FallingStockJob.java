package ff.three.three.job;

import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.util.DateUtils;
import ff.three.three.service.analysis.FallingStockAnalysisService;
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
 * @class FallingStockJob
 * @email forest@magicwindow.cn
 * @date 2018/9/23 22:01
 * @description
 */
@Data
public class FallingStockJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FallingStockJob.class);

    @Autowired
    private FallingStockAnalysisService fallingStockAnalysisService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("falling stock job run");
        try {
            String date = DateUtils.format(DateUtils.now(), DateUtils.FORMAT_D_3);
            this.fallingStockAnalysisService.logFallingStock(date);
        } catch (MwException e) {
            LOGGER.error("falling stock job error ", e);
        }
    }
}
