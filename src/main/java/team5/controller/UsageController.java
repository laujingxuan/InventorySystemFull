package team5.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team5.model.Product;
import team5.model.UsageRecordDetail;
import team5.service.ProductService;

@Controller
@RequestMapping("/usage")
public class UsageController {
	
	@Autowired
	private ProductService product_svc;
	
	@PostMapping("/report")
	public String usageReport(Model model, @RequestParam("startDate") String startD, @RequestParam("endDate") String endD, @RequestParam("productSelected") long id) throws ParseException {
		
		if (endD == "" || startD == "") {
			return "redirect:/usage/report";
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(startD);
		Date endDate = formatter.parse(endD);
		Product product = product_svc.findById(id);
		List<UsageRecordDetail> fullUsageList = product.getUsageDetailList();
		List<UsageRecordDetail> usageList = new ArrayList<UsageRecordDetail>();
		for(UsageRecordDetail x : fullUsageList) {
			if(!x.getUsageRecord().getDate().after(endDate) && !x.getUsageRecord().getDate().before(startDate)) {
				usageList.add(x);
			}
		}

		model.addAttribute("product",product);
		model.addAttribute("usageList", usageList);
		model.addAttribute("fromDate", startD);
		model.addAttribute("ToDate", endD);
		return "usageReportDetails";
	}
	
	@GetMapping("/report")
	public String usageReport(Model model) {

		model.addAttribute("products", product_svc.findAll());
		return "usageReport";
	}
	
	
}
