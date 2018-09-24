package ff.three.three.controller;

import cn.magicwindow.common.controller.IController;
import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.util.DateUtils;
import ff.three.three.domain.HammerStockCandidate;
import ff.three.three.service.analysis.HammerLineService;
import ff.three.three.service.entity.HammerStockCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.controller
 * @class HammerStockController
 * @email forest@magicwindow.cn
 * @date 2018/9/23 23:31
 * @description
 */
@Controller
@RequestMapping(path = "/hammerStock")
public class HammerStockController implements IController {

    @Autowired
    private HammerStockCandidateService hammerStockCandidateService;

    @Autowired
    private HammerLineService hammerLineService;

    @RequestMapping(value = "/realTime", method = RequestMethod.GET)
    public String realTime(Model model, HttpServletRequest request) {
        List<HammerStockCandidate> list = this.hammerStockCandidateService.queryByDate(DateUtils.format(DateUtils.now(), DateUtils.FORMAT_D_3));
        model.addAttribute("list", list);

        return "hammerStock";
    }

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
