package team5.service;

import java.util.ArrayList;
import team5.model.Supplier;

public interface SupplierService {
	
	public boolean saveSupplier(Supplier supplier);
	public void updateSupplier(Supplier supplier);
	public ArrayList<Supplier> findAllSuppliers();
	public Supplier findSupplierById(Long id);
	public void deleteSupplier(Supplier supplier);
	public ArrayList<String> findAllSupplierNames();
	public Supplier findSupplierByName(String name);
	
}
