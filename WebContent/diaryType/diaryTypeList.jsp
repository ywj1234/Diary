<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<<script type="text/javascript">
	function diaryTypeDelete(diaryTypeId) {
		if(confirm("你确定要删除吗？")){
			window.location="diaryType?action=delete&diaryTypeId="+diaryTypeId;
		}
	}
</script>


<div class="data_list">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/list_icon.png"/>
		日记类別列表
		<span class="diaryType_add"><button class="btn btn-primary" type="button" onclick="javascript:window.location='diaryType?action=preSave'">添加日记类别</button></span>
		</div>
		<div>
			<table class="table table-striped table-hover">
				<tr>
					<th>编号</th>
					<th>类别名称</th>
					<th>操作</th>
				</tr>
				<c:forEach var="diaryType" items="${diaryTypeList }">
					<tr>
						<td>${diaryType.diaryId }</td>	
						<td valign="middle">${diaryType.typeName }</td>
						<td><button class="btn btn-primary" type="button" onclick="javascript:window.location='diaryType?action=preSave&diaryId=${diaryType.diaryId }'">修改</button>&nbsp;&nbsp;
						<button class="btn btn btn-danger" type="button" onclick="diaryTypeDelete(${diaryType.diaryId})">删除</button></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div align="center" ><font color="red" size="5px">${error }</font></div>
</div>