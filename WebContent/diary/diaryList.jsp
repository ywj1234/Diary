<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/list_icon.png" />
		日记列表
	</div>
	<div class="diary_datas">
		<ul>
			<c:forEach var="diary" items="${diaryList }">
				<li>『<fmt:formatDate value="${diary.releaseDate }" type="date"
						pattern="yyyy-MM-dd" />』<span>&nbsp;<a
						href="diary?action=show&diaryId=${diary.diaryId }">${diary.title }</a></span></li>
			</c:forEach>
		</ul>
	</div>
	<div class="pagination pagination-centered">
		<ul>${pageCode }
		</ul>
	</div>
	<font size="3" color="#0088cc">共有${total }条记录，
	 <c:if test="${total%pageBean.getPageSize() == 0 }">
      				总页数<fmt:formatNumber type="number"
				value="${total/pageBean.getPageSize() }" maxFractionDigits="0" />页
      			</c:if> <c:if test="${total%pageBean.getPageSize() != 0 }">
				总页数
				<fmt:formatNumber type="number"
					value="${total/pageBean.getPageSize()+0.5 }" maxFractionDigits="0" />
				页
		</c:if></font>
</div>