package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{
	
	@Autowired
	@Qualifier("purchaseDAOImpl")
	private PurchaseDAO dao; 


	public void setDao(PurchaseDAOImpl dao) {
		this.dao = dao;
	}


	public void addPurchase(Purchase purchase) throws Exception{
		dao.insertPurchase(purchase);
		
	}

	public Purchase getPurchase(int tranNo) throws Exception{
	
		return dao.findPurchase(tranNo);
		
	}
	
	public Purchase getPurchase2(int ProdNo) throws Exception{

		return dao.findPurchase2(ProdNo);
		
	
	}
	
	
	public Map<String,Object> getPurchaseList(Search search,String buyerId) throws Exception{
		
		System.out.println("0-----들어옴" + search + buyerId);
		
		return dao.getPurchaseList(search, buyerId);
		
		
	}
	
	public HashMap<String,Object> getSaleList(Search search) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		return map;	
	}
	
	
	public void updatePurchase(Purchase purchase) throws Exception{		
		System.out.println("들어왔나???"+purchase);
		dao.updatePurchase(purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception{
		
		dao.updateTranCode(purchase);
	}
	
	
	
}
