package com.model2.mvc.service.product.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDAO;

@Repository("productDaoImpl")
public class ProductDAOImpl implements ProductDAO{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public ProductDAOImpl() {
	}

	public Product findProduct(int prodNo) throws Exception {
	System.out.println("prodNo"+prodNo);
		return sqlSession.selectOne("ProductMapper.getProduct",prodNo);
	
	}

	public Map<String, Object> getProductList(Search search) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		
		List<Product> list =sqlSession.selectList("ProductMapper.getProductList", search);;
		int total = sqlSession.selectOne("ProductMapper.getTotalCount", search);
		System.out.println("list::::::"+list);
		
		map.put("total", total);
		map.put("list", list);
		
		return map;
	}

	public void updateProduct(Product product) throws Exception {
		sqlSession.update("ProductMapper.updateProduct", product);
	}

	@Override
	public void insertProduct(Product product) throws Exception {
		sqlSession.insert("ProductMapper.addProduct", product);
	}
}
