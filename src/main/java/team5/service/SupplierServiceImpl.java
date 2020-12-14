package team5.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team5.model.Supplier;
import team5.repo.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	SupplierRepository srepo;
	
	@Transactional
	public boolean saveSupplier(Supplier supplier) {
		if(srepo.save(supplier) != null)
			return true;
		else
			return false;
	}
	
	@Transactional
	public void updateSupplier(Supplier supplier) {
		srepo.save(supplier);
	}
	
	@Transactional
	public ArrayList<Supplier> findAllSuppliers(){
		return (ArrayList<Supplier>) srepo.findAll(); 
	}
	
	@Transactional
	public Supplier findSupplierById(Long id) {
		return srepo.findById(id).get();
	}
	
	@Transactional
	public void deleteSupplier(Supplier supplier) {
		srepo.delete(supplier);
	}
	
	@Transactional
	public ArrayList<String> findAllSupplierNames(){
		List<Supplier> suppliers = srepo.findAll();
		ArrayList<String> names = new ArrayList<String>();
		for(Iterator<Supplier> iterator = suppliers.iterator(); iterator.hasNext();) {
			Supplier supplier = (Supplier)iterator.next();
			names.add(supplier.getClass().getName());
		}
		return names;
	}
	
	@Transactional
	public Supplier findSupplierByName(String name) {
		return srepo.findSupplierByName(name).get(0);
	}
	
	
}
