package team5.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import team5.model.Product;
import team5.model.UsageRecordDetail;
import team5.service.IService;

@Controller
@RequestMapping("/usage")
public class UsageController {
	
	@Autowired
	private IService<Product> productInterface;
	
	@PostMapping("/report")
	public ModelAndView usageReport(@RequestParam("startDate") String startD, @RequestParam("endDate") String endD, @RequestParam("productSelected") long id) throws ParseException {
		ModelAndView mv = new ModelAndView();
		if (endD == "" || startD == "") {
			mv.setViewName("redirect:/usage/report");
			return mv;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(startD);
		Date endDate = formatter.parse(endD);
		Product product = productInterface.findById(id);
		List<UsageRecordDetail> fullUsageList = product.getUsageDetailList();
		List<UsageRecordDetail> usageList = new ArrayList<UsageRecordDetail>();
		for(UsageRecordDetail x : fullUsageList) {
			if(!x.getUsageRecord().getDate().after(endDate) && !x.getUsageRecord().getDate().before(startDate)) {
				usageList.add(x);
			}
		}
		mv.setViewName("usageReportDetails");
		mv.addObject("product",product);
		mv.addObject("usageList", usageList);
		mv.addObject("fromDate", startD);
		mv.addObject("ToDate", endD);
		return mv;
	}
	
	@GetMapping("/report")
	public ModelAndView usageReport() {
		ModelAndView mv = new ModelAndView();
		List<Product> products = productInterface.findAll();
		mv.setViewName("usageReport");
		mv.addObject("products", products);
		return mv;
	}
	
	
}
