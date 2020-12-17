//package com.example.demo_mvc.Service.ProductImpl;
//
//import com.example.demo_mvc.Domain.Product;
//import com.example.demo_mvc.Domain.ProductQuery;
//import com.example.demo_mvc.Repo.ProductRepo;
//import com.example.demo_mvc.Service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.criteria.*;
//import java.util.ArrayList;
//import java.util.List;
//@Service
//@Transactional
//public class ProductServiceImpl implements ProductService {
//
//    @Autowired
//    ProductRepo productRepo;
//
//
//    @Override
//    public List<Product> findAll() {
//        List<Product> all = productRepo.findAll();
//        return all;
//    }
//
//    @Override
//    public Page<Product> findBookNoCriteria(Integer page, Integer size) {
//        Sort sort = Sort.by(Sort.Direction.ASC,"id");
//        Pageable pageable=PageRequest.of(page,size, sort);
//        return productRepo.findAll(pageable);
//    }
//
//    @Override
//    public Page<Product> findBookCriteria(Integer page, Integer size, ProductQuery productQuery) {
//        Sort sort = Sort.by(Sort.Direction.ASC,"id");
//        Pageable pageable=PageRequest.of(page,size, sort);
//        Specification<Product> spec=new Specification<Product>() {
//            @Override
//            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Path<Object> description = root.get("Description");
//                Path<Object> Type = root.get("Type");
//                Path<Object> price = root.get("price");
//
//                System.out.println("-------------------");
//                System.out.println(description);
//                System.out.println("-------------------");
//
//
//                List<Predicate> list = new ArrayList<Predicate>();
//                if (null!=productQuery.getDescription()&&!"".equals(productQuery.getDescription())){
//                    list.add((Predicate)cb.like(description.as(String.class),"%"+productQuery.getDescription()+"%"));
//                }
//                if (null!=productQuery.getPrice()&&!"".equals(productQuery.getPrice())){
//                    list.add((Predicate)cb.like(price.as(String.class),"%"+productQuery.getPrice()+"%"));
//                }
//                if (null!=productQuery.getType()&&!"".equals(productQuery.getType())){
//                    list.add((Predicate)cb.like(Type.as(String.class),"%"+productQuery.getType()+"%"));
//                }
//
//
//
//                Predicate[] p=new Predicate[list.size()];
//                return cb.and(list.toArray(p));
//            }
//        };
//        return productRepo.findAll(spec, pageable);
//
//    }
//
//}
