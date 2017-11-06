<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function checkForm(){
		var nickName=document.getElementById("nickName").value;
		if(nickName==null||nickName==""){
			document.getElementById("error").innerHTML="昵称不能为空！";
			return false;
		}
		return true;
	}
</script>

<div class="data_list">
	<div class="data_list_title">
		<img
			src="${pageContext.request.contextPath}/images/user_edit_icon.png" />
		个人信息设置
	</div>
		<div class="row-fluid">
			<div class="span4">
				<img width="80%" src="${resultUser.imageName }" style="margin-top: 20px" class="img-rounded" />
			</div>
			<div class="span8" style="padding-top: 20px;">
			
			
				<form action="user?action=save" method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
					<table>
						<tr>
							<td width="20%"><font color="#5555FF">头像路径：</font></td>
							<td><input type="file" id="imagePath" name="imagePath"/></td>
						</tr>
						<tr>
							<td><font color="#FF3EFF">我的名字：</font></td>
							<td><input type="text" id="nickName" name="nickName" value="${resultUser.nickName }" style="margin-top: 5px;height: 30px;" /></td>
						</tr>
						<tr>
							<td valign="top"><font color="#00DD00">我的心情：</font></td>
							<td>
								<textarea rows="10" id="mood" name="mood" style="width:100%;">${resultUser.mood }</textarea>
							</td>
						</tr>
						<tr>
							<td><button class="btn btn-primary" type="submit">保存</button></td>
							<td><button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>&nbsp;&nbsp;<font id="error" color="#DC143C">${error }</font></td>
						</tr>
					</table>
				</form>
			</div>
	</div>
</div>