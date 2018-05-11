package com.model2.mvc.service.purchase.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("purchaseDAOImpl")
public class PurchaseDAOImpl implements PurchaseDAO{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;
	
	public PurchaseDAOImpl() {
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}	
	
	public Purchase findPurchase2(int prodNo) throws Exception{
		
		return sqlSession.selectOne("PurchaseMapper.getPurchase2", prodNo);
		}
	
		public Purchase findPurchase(int tranNo) throws Exception{
			
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
		}
						
		public HashMap<String,Object> getSaleList(Search search) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		return map;
		}
		
		public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
			
			int total;
			List<Purchase> list = new ArrayList<Purchase>();
			
			map.put("search", search);
			map.put("buyerId", buyerId);
		
			total = sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId);
			list = sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
			
			System.out.println("listttttttt"+list);
			map.put("total", total);
			map.put("list", list);
			
			return map;
			}
	
		public void insertPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
		}
				
		public void updatePurchase(Purchase purchase) throws Exception {
	sqlSession.update("PurchaseMapper.updatePurchase", purchase);
		}	
				
		public void updateTranCode(Purchase purchase) throws Exception {		
			sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}	
		
		private int getTotalCount(String sql) throws Exception {
			int totalCount = 0;
			return totalCount;
		}
		
		// 게시판 currentPage Row 만  return 
		private String makeCurrentPageSql(String sql , Search search){	
			return sql;
		}

	}

	
