package ff.three.three.controller;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.service.crawler.TxCrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Forest Wang
 * @package ff.three.three.controller
 * @class TxController
 * @email forest@magicwindow.cn
 * @date 2018/10/2 16:49
 * @description
 */
@RestController
@RequestMapping(path = "/tx")
public class TxController {


    private static final Logger LOGGER = LoggerFactory.getLogger(TxController.class);


    @Autowired
    private TxCrawlerService txCrawlerService;

    @RequestMapping("/crawlDaily")
    public String crawlDaily(@RequestParam(name = "init", required = false, defaultValue = "false") boolean init) throws MwException {
        txCrawlerService.crawlDaily(init);
        return "crawlDaily";
    }
}