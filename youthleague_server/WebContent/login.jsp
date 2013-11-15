<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>网站管理员登陆</title>
		<script src="<%=path%>/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="<%=path%>/admin/js/login.js" type="text/javascript"></script>
		<script language="JavaScript">
		
	function correctPNG()
	{
	    var arVersion = navigator.appVersion.split("MSIE")
	    var version = parseFloat(arVersion[1])
	    if ((version >= 5.5) && (document.body.filters)) 
	    {
	       for(var j=0; j<document.images.length; j++)
	       {
	          var img = document.images[j]
	          var imgName = img.src.toUpperCase()
	          if (imgName.substring(imgName.length-3, imgName.length) == "PNG")
	          {
	             var imgID = (img.id) ? "id='" + img.id + "' " : ""
	             var imgClass = (img.className) ? "class='" + img.className + "' " : ""
	             var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "
	             var imgStyle = "display:inline-block;" + img.style.cssText 
	             if (img.align == "left") imgStyle = "float:left;" + imgStyle
	             if (img.align == "right") imgStyle = "float:right;" + imgStyle
	             if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle
	             var strNewHTML = "<span " + imgID + imgClass + imgTitle
	             + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
	             + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
	             + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>" 
	             img.outerHTML = strNewHTML
	             j = j-1
	          }
	       }
	    }    
	}
	//window.attachEvent("onload", correctPNG);
	</script>
		<link href="<%=path%>/admin/images/skin.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<table width="100%" height="166" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="42" valign="top">
					<table width="100%" height="42" border="0" cellpadding="0"
						cellspacing="0" class="login_top_bg">
						<tr>
							<td width="1%" height="21">
								&nbsp;
							</td>
							<td height="42">
								&nbsp;
							</td>
							<td width="17%">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<table width="100%" height="532" border="0" cellpadding="0" cellspacing="0" class="login_bg">
						<tr>
							<td width="49%" align="right">
								<table width="91%" height="532" border="0" cellpadding="0"
									cellspacing="0" class="login_bg2">
									<tr>
										<td height="138" valign="top">
											<table width="89%" height="427" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td height="149">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td height="80" align="right" valign="top">
														<img src="<%=path%>/admin/images/tuanwei/logo.png" width="279"
															height="68">
													</td>
												</tr>
												<tr>
													<td height="198" align="right" valign="top">
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
															<br /><br />
															<tr>
																<td>
																   <a href="<%=path%>/admin/upload/download.jsp?name=yl_android.apk">
																      <img src="<%=path%>/admin/images/tuanwei/down_android.png" border="0"//>
																   </a>
																</td>
																<td>
																   <a href="<%=path%>/admin/upload/download.jsp?name=yl_iphone.ipa">
																       <img src="<%=path%>/admin/images/tuanwei/down_iphone.png" border="0"//>
																   </a>
																</td>
															</tr>
															<tr>
																<td><br /><br/></td>
															</tr>
															<tr>
																<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																    <a href="<%=path%>/admin/upload/download.jsp?name=yl_android.apk">
																        <img src="<%=path%>/admin/images/tuanwei/down_android_2.png" height="150" width="150" border="0"//>
																    </a>
																</td>
																<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																    <a href="<%=path%>/admin/upload/download.jsp?name=yl_iphone.ipa">
																        <img src="<%=path%>/admin/images/tuanwei/down_iphone_2.png" height="150" width="150" border="0"//>
																    </a>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="1%">
								&nbsp;
							</td>
							<td width="50%" valign="bottom">
								<table width="100%" height="59" border="0" align="center"
									cellpadding="0" cellspacing="0">
									<tr>
										<td width="4%">
											&nbsp;
										</td>
										
										<td width="96%" height="38">
											<span class="login_txt_bt">登陆后台管理</span>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td height="21">
											<table cellSpacing="0" cellPadding="0" width="100%" border="0" id="table211" height="328">
												<tr>
													<td height="164" colspan="2" align="middle">
													
														<form action="<%=path%>/StaffServlet?type=login" method="post">
														
															<table cellSpacing="0" cellPadding="0" width="100%" border="0" height="143" id="table212">
																<tr>
																	<td width="80" height="38" class="top_hui_text">
																		<span class="login_txt">手机号码：</span>
																	</td>
																	<td style="width:160px" height="38" colspan="2" class="top_hui_text">
																		<input name="mobile" id="username" class="editbox4" value="" onblur="c_username()">
																		<span id="s_username" class="login_txt"></span>
																	</td>
																</tr>
																
																<tr>
																	<td width="80" height="38" class="top_hui_text">
																		<span class="login_txt">&nbsp;&nbsp;&nbsp;密  码： </span>
																	</td>
																	<td  style="width:170px"  height="38" colspan="2" class="top_hui_text">
																		<input class="editbox4" id="password" type="password" name="password">
																		<!--<img src="admin/images/luck.gif" width="19" height="18"> -->
																		<span id="s_password" class="login_txt"></span>
																	</td>
																</tr>

																<tr>
																	<td height="38">
																		&nbsp;
																	</td>
																	<td width="15%">
																		<input name="login" type="submit" class="button" id="login" value="登 陆"">
																	</td>
<!--																	<td width="15%" class="top_hui_text">-->
<!--																		<input name="cs" type="button" class="button" id="cs" value="取 消" onClick="move()">-->
<!--																	</td>-->
																	<td>
																		<a href="<%=path%>/admin/staff/LogonStaff.jsp" ><font style="color:red" >注 册 </font></a>
																	</td>
																</tr>
															</table>
															<br>
														</form>
													</td>
												</tr>
												<tr>
													<td width="533" height="124" align="right" valign="bottom">
														<img src="<%=path%>/admin/images/login-wel.gif"
															width="242" height="128">
													</td>
													<td width="57" align="right" valign="bottom">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="20">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="login-buttom-bg">
						<tr>
							<td align="center">
								<span class="login-buttom-txt"> </span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
