//package com.example.demo_mvc.Controller;
//
//
//import com.example.demo_mvc.Domain.Product;
//import com.example.demo_mvc.Domain.ProductQuery;
//import com.example.demo_mvc.Service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/product")
//public class ProductController {
//    @Autowired
//    public void setProductService(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @Autowired
//    ProductService productService;
//
//
////    @RequestMapping("/list")
////    public String showList(Model model){
////        List<Product> list = productService.findAll();
////        model.addAttribute("products",list);
////        return "list";
////    }
//
//    @RequestMapping("/findBookNoQuery")
//    public String findBookNoQuery(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
//                                  @RequestParam(value ="size", defaultValue = "5") Integer size){
//        Page<Product> list = productService.findBookNoCriteria(page, size);
//        model.addAttribute("products", list);
//        model.addAttribute("flag", 2);
//        model.addAttribute("pageCount",list.getTotalPages()-1);
//
//        return "list";
//    }
//
//
//    @RequestMapping("/findBookQuery")
//    public String findBookQuery(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
//                                @RequestParam(value ="size", defaultValue = "5") Integer size, ProductQuery productQuery){
//
//        Page<Product> list = productService.findBookCriteria(page, size,productQuery);
//        model.addAttribute("products", list);
//        model.addAttribute("condition", productQuery);
//        model.addAttribute("flag", 1);
//        model.addAttribute("pageCount",list.getTotalPages()-1);
//        return "list";
//    }
//
//}
