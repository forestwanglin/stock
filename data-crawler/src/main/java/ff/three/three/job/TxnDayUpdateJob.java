package ff.three.three.job;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.service.entity.TxnDayService;
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
 * @class TxnDayUpdateJob
 * @email forest@magicwindow.cn
 * @date 2018/9/25 00:20
 * @description
 */
@Data
public class TxnDayUpdateJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxnDayUpdateJob.class);

    @Autowired
    private TxnDayService txnDayService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("txn day update job run");
        try {
            this.txnDayService.txnDatesUpdate();
        } catch (MwException e) {
            LOGGER.error("error", e);
        }
    }
}