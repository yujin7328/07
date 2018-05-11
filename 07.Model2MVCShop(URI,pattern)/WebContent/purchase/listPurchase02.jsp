<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	// �˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�  
	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
	   	document.detailForm.submit();		
	}
	</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase.do?userId=${purchase.buyer.userId }" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">���� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="12" >��ü ${resultPage.totalCount}�Ǽ�, ����${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="50">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="50">��ǰ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ�̸�</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">�������̸�</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��������ȭ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��������</td>
		<td class="ct_line02"></td>
	</tr>
	<tr>
		<td colspan="20" bgcolor="808285" height="1"></td>
	</tr>

	<c:set var="i" value="0" />
	<c:forEach var="purchase" items="${list}">
		<c:set var="i" value="${ i+1 }" />
	<tr class="ct_list_pop">
		<td align="center">
			<a href="/getPurchase.do?tranNo=${purchase.tranNo}">${ i }</a></td>
		<td></td>
		<td align="center">${purchase.purchaseProd.prodNo}</td>
		<td></td>
		<td align="center">${purchase.purchaseProd.prodName}</td>
		<td></td>
		<td align="center">${purchase.receiverName}</td>
		<td></td>
		<td align="center">${purchase.receiverPhone}</td>
		<td></td>
		<td align="center">����
			<c:if test="${purchase.tranCode=='1'}">
			<a>���ſϷ�</a>
			</c:if>
			<c:if test="${purchase.tranCode=='2'}">
			<a>�����</a>
			</c:if>
			<c:if test="${purchase.tranCode!='1' && purchase.tranCode!='2'}">
			<a>��ۿϷ�</a>
			</c:if>
			�����Դϴ�.
			</td>
		<td></td>
		<td align="center">
		<c:if test="${purchase.tranCode=='2'}">
			<a href="/updateTranCode.do?tranNo=${purchase.tranNo}&tranCode=3">���ǵ���</a>
			</c:if>	
		</td>
		<td></td>
	</tr>
		</c:forEach>
	<tr>
		<td colspan="20" bgcolor="D6D7D6" height="1"></td>
	</tr>
</table>

<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}"/>
			
			<jsp:include page="../common/pageNavigator.jsp"/>	
			
    	</td>
	</tr>
</table>
<!-- PageNavigation End... -->
<!--  ������ Navigator �� -->
</form>

</div>

</body>
</html>