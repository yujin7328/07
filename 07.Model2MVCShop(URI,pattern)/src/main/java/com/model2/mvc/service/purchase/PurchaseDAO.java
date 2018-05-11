package com.model2.mvc.service.purchase;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDAO {
	
	public Purchase findPurchase2(int prodNo) throws Exception;
	
	public Purchase findPurchase(int tranNo) throws Exception;
						
	public Map<String,Object> getSaleList(Search search) throws Exception;
			
	public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception;
			
	public void insertPurchase(Purchase purchase) throws Exception;

	public void updatePurchase(Purchase purchase) throws Exception ;
		
	public void updateTranCode(Purchase purchase) throws Exception ;
	
}
		
	