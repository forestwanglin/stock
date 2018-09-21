package ff.three.three.controller;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.service.crawler.StockCrawlerService;
import ff.three.three.service.crawler.StockQuotationCrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Forest Wang
 * @package io.merculet.controller
 * @class TestController
 * @email forest@magicwindow.cn
 * @date 2018/7/5 11:01
 * @description
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {


    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private StockCrawlerService stockCrawlerService;

    @Autowired
    private StockQuotationCrawlerService stockQuotationCrawlerService;

    @RequestMapping("/crawlStock")
    public String crawlStock() throws MwException {
        stockCrawlerService.crawl();
        return "xxx";
    }

    @RequestMapping("/crawlStockQuo")
    public String crawlStockQuo() throws MwException {
        stockQuotationCrawlerService.crawlStockQuotation();
        return "xxx";
    }


    @RequestMapping("/txnByDate/{date}")
    public String txnByDate(@PathVariable(name = "date") String date) throws IOException, InterruptedException, ExecutionException, MwException {
        return "dd";
    }

}
