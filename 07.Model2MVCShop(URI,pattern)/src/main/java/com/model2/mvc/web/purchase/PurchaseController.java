package com.model2.mvc.web.purchase;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

//==> 회원관리 Controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	//setter Method 구현 않음
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseController(){
		System.out.println(this.getClass());
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}

	/*	==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	==> 아래의 두개를 주석을 풀어 의미를 확인 할것*/
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public String addPurchase(@ModelAttribute("purchase") Purchase purchase ,
																	@RequestParam("prod_no") String prodNo, HttpSession session) throws Exception {

		purchase.setPurchaseProd(productService.getProduct(Integer.parseInt(prodNo)));
		purchase.setBuyer((User)session.getAttribute("user"));

		purchaseService.addPurchase(purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}	
	
	@RequestMapping(value="addPurchaseView", method=RequestMethod.GET)
	public String addPurchaseView( @RequestParam("prod_no") String prodNo, Model model) throws Exception {
		System.out.println("prodNo222"+prodNo);
		Product product = productService.getProduct(Integer.parseInt(prodNo));
		
		model.addAttribute("product", product);
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping(value="listPurchase")
	public String listPurchase( @ModelAttribute("search") Search search , Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		User user = (User)session.getAttribute("user");
		String userId = user.getUserId();
		Map<String , Object> map=purchaseService.getPurchaseList(search, userId);
	
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("total")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		System.out.println( map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchaseView.jsp";
	}

	

		
		
	@RequestMapping(value="getPurchase", method=RequestMethod.GET)
	public String getPurchase(HttpServletRequest request, Model model) throws Exception {

		
		Purchase purchase= purchaseService.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchaseDetail.jsp";
		}
	
	
	@RequestMapping(value="updatePurchaseView", method=RequestMethod.GET)
	public String updatePurchaseView(@RequestParam("tranNo") String tranNo, Model model) throws Exception {
		
		Purchase purchase = purchaseService.getPurchase(Integer.parseInt(tranNo));
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/UpdatePurchase.jsp";
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.POST)
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase,  HttpServletRequest request) throws Exception {
	System.out.println("업데이트"+request.getParameter("tranNo"));
		purchaseService.updatePurchase(purchase);
		System.out.println("업데이트2");
		return "forward:/purchase/getPurchase?tranNo="+request.getParameter("tranNo");
		
}
		
	@RequestMapping(value="updateTranCode")
	public String updateTranCode(HttpServletRequest request) throws Exception {
		
		Purchase purchase = purchaseService.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));	
		purchase.setTranCode(request.getParameter("tranCode"));
		purchaseService.updateTranCode(purchase);	
	
		return "forward:/purchase/listPurchase";
	}
	
	
	
/*@RequestMapping("/updateTranCode.do")
public String updateTranCode(@RequestParam("prodNo") String prodNo, @RequestParam("tranCode") String tranCode) throws Exception {
	
	Purchase purchase = purchaseService.getPurchase2(Integer.parseInt(prodNo));	
	purchase.setTranCode(tranCode);
	purchaseService.updateTranCode(purchase);	

	return "forward:/listPurchase.do";
}

*/

	
	@RequestMapping(value="updateTranCodeByProd", method=RequestMethod.GET)
	public String updateTranCodeByProd(HttpServletRequest request) throws Exception {
	
		Purchase purchase = purchaseService.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));	
		purchase.setTranCode(request.getParameter("proTranCode"));
		purchaseService.updateTranCode(purchase);	
		
		return "forward:/product/listProduct?menu=manage";
	}
	
	/*@RequestMapping("/updateTranCodeByProd.do")
	public String updateTranCodeByProd(@RequestParam("prodNo") String prodNo, @RequestParam("prodTranCode") String prodTranCode) throws Exception {
		
		Purchase purchase = purchaseService.getPurchase2(Integer.parseInt(prodNo));	
		purchase.setTranCode(prodTranCode);
		purchaseService.updateTranCode(purchase);	
		
		return "forward:/listProduct.do?menu=manage";
	}
	*/
	
		
}