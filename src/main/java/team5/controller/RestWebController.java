package team5.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import team5.model.Product;
import team5.model.RoleType;
import team5.model.User;
import team5.nonEntityModel.ProductNotFoundException;
import team5.nonEntityModel.UserListAJAX;
import team5.service.ProductInterface;
import team5.service.UserInterface;


@RestController
public class RestWebController {
	
	@Autowired
	private UserInterface userInterface;
	
	@Autowired
	private ProductInterface pInterface;
	
	//for the javascript AJAX call, listing of users
	@PostMapping("/user/users")
	public ResponseEntity<?> viewUser(@RequestBody UserListAJAX userListForm) {
		List<User> users;
		if (userListForm.getRoleType().equals("ALL")) {
			users = userInterface.findAll();
		}else {
			users = userInterface.findByJobRole(RoleType.valueOf(userListForm.getRoleType()));
		}
		return ResponseEntity.ok(users);
	}
	
	//get all the products name with its id
	@GetMapping("/api/products")
	public ResponseEntity<?> allName(){
		List<Product> products = pInterface.findAll();
		Map<Long, String> nameID = new HashMap<Long, String>();
		for(Product product: products) {
			nameID.put(product.getId(), product.getName());
		}
		return ResponseEntity.ok(nameID);
	}
	
	//get the product detail based on the id and attribute specified
	@GetMapping("/api/products/{id}/{attribute}")
	public ResponseEntity<?> findOne(@PathVariable("id") Long id, @PathVariable("attribute") String attribute) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = Product.class.getMethod("get"+ StringUtils.capitalize(attribute));
		Product product = pInterface.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		return ResponseEntity.ok(m.invoke(product).toString());
	}
	
	//create the product and return the product id
	@PostMapping("/api/products")
	public ResponseEntity<?> newProduct(@RequestBody Product newProduct) {
		return ResponseEntity.ok(pInterface.createProduct(newProduct).getId());
	}
	
	//update the quantity based on the product id and return the new quantity
	@PutMapping("api/products/{id}/{quantity}")
	public ResponseEntity<?> updateProductQuantity(@PathVariable(value = "id") Long id, @PathVariable(value="quantity") Integer quantity) {
		Product product = pInterface.findById(id).get();
		if (product.getUnit()<quantity) {
			return ResponseEntity.badRequest()
		            .body("Not enough stock");
		}else {
			product.setUnit(product.getUnit()- quantity);
			return ResponseEntity.ok(pInterface.updateProduct(product));
		}
	}
	
	//delete the product
	@DeleteMapping("/api/products/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
	    pInterface.deleteById(id);
	    return ResponseEntity.ok("Deletion success");
	}
}

