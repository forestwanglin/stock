package ff.three.three.controller;

import cn.magicwindow.common.controller.IController;
import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.util.DateUtils;
import ff.three.three.service.analysis.HammerLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Forest Wang
 * @package ff.three.three.controller
 * @class HammerLineController
 * @email forest@magicwindow.cn
 * @date 2018/9/24 14:58
 * @description
 */
@RestController
@RequestMapping(path = "/hammerLine")
public class HammerLineController implements IController {

    @Autowired
    private HammerLineService hammerLineService;

    @RequestMapping("/markHammerStock/{sd}/{ed}")
    public String markHammerStock(@PathVariable(name = "sd") String sd,
                                  @PathVariable(name = "ed") String ed) throws MwException {
        Date startDate = DateUtils.parse(sd, DateUtils.FORMAT_D_3);
        Date endDate = DateUtils.parse(ed, DateUtils.FORMAT_D_3);
        for (Date date = startDate; date.compareTo(endDate) < 0; date = DateUtils.addDays(date, 1)) {
            this.hammerLineService.findAll(DateUtils.format(date, DateUtils.FORMAT_D_3));
        }
        return "success";
    }

}
