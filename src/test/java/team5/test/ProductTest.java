package team5.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team5.model.Product;
import team5.model.UsageRecord;
import team5.model.UsageRecordDetail;
import team5.model.User;
import team5.repo.ProductRepository;
import team5.repo.UsageRecordDetailRepo;
import team5.repo.UsageRecordRepository;
import team5.repo.UserRepository;

@SpringBootTest
public class ProductTest {
	
	@Autowired
	public ProductRepository productRepo;
	@Autowired
	public UserRepository urepo;
	@Autowired
	public UsageRecordRepository usageRepo;
	@Autowired
	public UsageRecordDetailRepo usageDetailRepo;
	
//	@Test
//	public void saveProduct() {
//		productRepo.save(new Product(2345,"Collision Cart","The original high quality, low friction dynamics carts made from durable machined aluminum.",
//									"Type A","Blue",200,"Category A", 2000 ,  3000, 1200, 2, 2, "12345" , 1, 1));
//		productRepo.save(new Product(3432,"Large Table Clamp","Can attach to tables, shelves, or other boards up to 10 cm thick.",
//				"Type B","Black",200,"Category B", 2500 ,  3000, 1260, 3, 2, "12345" , 1, 1));
//		productRepo.save(new Product(5432,"Tension Protractor","ME-6855","Type C","White",500,"Category C", 8000 ,  3500, 1240, 6, 4, "2314" , 1, 4));
//		
//		productRepo.save(new Product(2345,"Collision Cart","The original high quality, low friction dynamics carts made from durable machined aluminum.",
//									"Type A","Blue",200,"Category A", 2000 ,  3000, 1200, 2, 2, "12345" , 8, 3));
//		productRepo.save(new Product(3432,"Large Table Clamp","Can attach to tables, shelves, or other boards up to 10 cm thick.",
//				"Type B","Black",200,"Category B", 2500 ,  3000, 1260, 3, 2, "12345" , 8, 5));
//		productRepo.save(new Product(5432,"Tension Protractor","ME-6855","Type C","White",500,"Category C", 8000 ,  3500, 1240, 6, 4, "2314" , 7, 4));
//		productRepo.save(new Product(3452,"Jeep wheel","The best wheel for jeep.",
//				"Type A","Black",200,"Category A", 2000 ,  3000, 1200, 2, 2, "12345" , 5, 2));
//		productRepo.save(new Product(3433,"Large Table Clamp","Can attach to tables, shelves, or other boards up to 10 cm thick.",
//				"Type B","Black",200,"Category B", 2500 ,  3000, 1260, 3, 2, "12345" , 6, 3));
//		productRepo.save(new Product(5433,"Tension Protractor","ME-6855","Type C","White",500,"Category C", 8000 ,  3500, 1240, 6, 4, "2314" , 10, 4));
//		
//		ArrayList<Product> plist = new ArrayList<Product>();		
//		plist = (ArrayList<Product>) productRepo.findAll();
//		
//		System.out.println("Print all products");
//		for (Iterator<Product> iterator = plist.iterator(); iterator.hasNext();) {
//			Product product =  iterator.next();
//			System.out.println(product.toString());
//		}
//	}
	
	@Test
	public void saveUsageRecord() throws ParseException {
		Product first = productRepo.findById((long)1);
		Product second = productRepo.findById((long)2);
		Product third = productRepo.findById((long)3);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fDate = formatter.parse("01-12-2020");
		Date sDate = formatter.parse("03-12-2020");
		Date tDate = formatter.parse("05-12-2020");
		User admin1 = urepo.findById((long)1).get();
		UsageRecord oneR = usageRepo.save(new UsageRecord("john","SAA1235", fDate, admin1));
		UsageRecord secondR = usageRepo.save(new UsageRecord("Mary","SAB2345", sDate, admin1));
		UsageRecord thirdR = usageRepo.save(new UsageRecord("Peter","SAC3456", tDate, admin1));
		UsageRecordDetail oneRd = usageDetailRepo.save(new UsageRecordDetail(first,oneR,3));
		UsageRecordDetail secRd = usageDetailRepo.save(new UsageRecordDetail(second,oneR,3));
		UsageRecordDetail thiRd = usageDetailRepo.save(new UsageRecordDetail(third,secondR,3));
		UsageRecordDetail thuRd = usageDetailRepo.save(new UsageRecordDetail(first,secondR,3));
		UsageRecordDetail friRd = usageDetailRepo.save(new UsageRecordDetail(third,oneR,3));
	}
	

}
