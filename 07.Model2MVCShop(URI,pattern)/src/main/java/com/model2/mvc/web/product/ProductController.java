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

//==> 회원관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
/*	==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	==> 아래의 두개를 주석을 풀어 의미를 확인 할것*/
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
		//setSizeThreshold의 크기를 벗어나게 되면 지정한 위치에 임시로 저장한다
		fileUpload.setSizeMax(1024*1024*10); // 최대 1메가까지 업로드 가능 (1024* 1024* 100) << 100MB
		
		fileUpload.setSizeThreshold(1024*100); //한번에 100k 까지는 메모리에 저장
		
		if(request.getContentLength() < fileUpload.getSizeMax()) {
			product = new Product();
			
			StringTokenizer token = null;
			
			//parseRequest()는 FileItem을 포함하고 있는 List타입을 리턴한다.
			List fileItemList = fileUpload.parseRequest(request);
			
			int Size = fileItemList.size();
			
			for(int i=0; i<Size; i++) {
				FileItem fileItem = (FileItem)fileItemList.get(i);
				//isFormField()를 통해서 파일형식인지 파라미터인지 구분한다. 파라미터라면 true
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
				}else {//파일형식이면
					if(fileItem.getSize() > 0 ) { //파일을 저장하는 if
						int idx = fileItem.getName().lastIndexOf("\\");
						//getName은 경로를 다 가져오기 때문에 lastIndexOf로 잘라낸다.
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
		}else { //업로드하는 파일이 setSizeMax보다 큰경우
			int overSize = (request.getContentLength() / 1000000);
			System.out.println("<script>alert('파일의 크기는 1MB까지 입니다. 올리신 파일 용량은"+overSize+"MB입니다.')");
			System.out.println("history back() </script>");
		}
	}else {
		System.out.println("인코딩 타입이 multipart/form-data가 아닙니다.");
	}

	return "forward:/product/getProduct.jsp";
	
	
}

*/
	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct( @RequestParam("menu") String menu, @RequestParam("prodNo") String prodNo, Model model ) throws Exception {
	Product product = productService.getProduct(Integer.parseInt(prodNo));
	
	System.out.println("겟겟겟");
	
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