package com.model2.mvc.web.product;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> ȸ������ Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
/*	==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�*/
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product, @RequestParam("file")MultipartFile file) throws Exception {
		//			product.setProTranCode("0");
		productService.addProduct(product);
		
		File f= new File("\"C:\\\\Users\\\\Bit\\\\git\\\\06MVCShop(afterRe)\\\\06.Model2MVCShop(Presentation+BusinessLogic)\\\\WebContent\\\\images\\\\uploadFiles\\\\"+file.getOriginalFilename());
		file.transferTo(f);
		product.setFileName(file.getOriginalFilename());
		
		return "forward:/product/getProduct.jsp";
		}
			
	/*@RequestMapping(value="addProductAction")
	public String addProductAction(@ModelAttribute("product") Product product, HttpServletRequest request, HttpServletResponse response) throws Exception{
	if(FileUpload.isMultipartContent(request)) {
		String temDir = "C:\\workspace\\06.Model2MVCShop(Presertation+BusinessLogic)\\WebContent\\images\\uploadFiles\\";
		
		DiskFileUpload fileUpload = new DiskFileUpload();
		
		fileUpload.setRepositoryPath(temDir);
		//setSizeThreshold�� ũ�⸦ ����� �Ǹ� ������ ��ġ�� �ӽ÷� �����Ѵ�
		fileUpload.setSizeMax(1024*1024*10); // �ִ� 1�ް����� ���ε� ���� (1024* 1024* 100) << 100MB
		
		fileUpload.setSizeThreshold(1024*100); //�ѹ��� 100k ������ �޸𸮿� ����
		
		if(request.getContentLength() < fileUpload.getSizeMax()) {
			product = new Product();
			
			StringTokenizer token = null;
			
			//parseRequest()�� FileItem�� �����ϰ� �ִ� ListŸ���� �����Ѵ�.
			List fileItemList = fileUpload.parseRequest(request);
			
			int Size = fileItemList.size();
			
			for(int i=0; i<Size; i++) {
				FileItem fileItem = (FileItem)fileItemList.get(i);
				//isFormField()�� ���ؼ� ������������ �Ķ�������� �����Ѵ�. �Ķ���Ͷ�� true
				if(fileItem.isFormField()) {
					if(fileItem.getFieldName().equals("manuDate")) {
						token = new StringTokenizer(fileItem.getString("utf-8"),"-");
						String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
						token.nextToken();
						product.setManuDate(manuDate);
					}
					else if(fileItem.getFieldName().equals("prodName")) {
						product.setProdName(fileItem.getString("utf-8"));
					}else if(fileItem.getFieldName().equals("prodDetail")) {
						product.setProdDetail(fileItem.getString("utf-8"));
					}else if(fileItem.getFieldName().equals("price")) {
						product.setPrice(Integer.parseInt(fileItem.getString("utf-8")));
					}
				}else {//���������̸�
					if(fileItem.getSize() > 0 ) { //������ �����ϴ� if
						int idx = fileItem.getName().lastIndexOf("\\");
						//getName�� ��θ� �� �������� ������ lastIndexOf�� �߶󳽴�.
						if(idx==-1){
							idx=fileItem.getName().lastIndexOf("/");
						}
						
						String fileName = fileItem.getName().substring(idx +1);
						product.setFileName(fileName);
						try {
							File uploadedFile = new File(temDir, fileName);
							fileItem.write(uploadedFile);
						}catch(IOException e) {
							System.out.println(e);
						}
					}else {
						product.setFileName("../../images/empty.GIF");
					}
				}
			}
			
			productService.addProduct(product);
			
			request.setAttribute("prodvo", product);
		}else { //���ε��ϴ� ������ setSizeMax���� ū���
			int overSize = (request.getContentLength() / 1000000);
			System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮��"+overSize+"MB�Դϴ�.')");
			System.out.println("history back() </script>");
		}
	}else {
		System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�.");
	}

	return "forward:/product/getProduct.jsp";
	
	
}

*/
	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct( @RequestParam("menu") String menu, @RequestParam("prodNo") String prodNo, Model model ) throws Exception {
	Product product = productService.getProduct(Integer.parseInt(prodNo));
	
	System.out.println("�ٰٰ�");
	
		model.addAttribute("product", product);
		if(menu.equals("manage")) {
			return "forward:/product/updateProduct.jsp";
			}
			else { 
				
			return "forward:/product/getProductDetail.jsp";
			}

	}
		
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product, Model model) throws Exception{
		productService.updateProduct(product);
		return "forward:/product/getProductDetail.jsp?menu=ok";
	}
	
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
			if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}System.out.println("orderby::::"+search.getOrderby());
		search.setPageSize(pageSize);
		
		search.setOrderby( request.getParameter("orderby"));
		
		System.out.println("orderby"+request.getParameter("orderby"));
		Map<String , Object> map=productService.getProductList(search);
	
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("total")).intValue(), pageUnit, pageSize);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", request.getParameter("menu"));
		
		return "forward:/product/listProduct.jsp";
	}
	

}