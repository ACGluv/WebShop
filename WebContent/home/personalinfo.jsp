<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.util.Good"  %>
<%@ page import="com.util.ShopCart"  %>
<%	String longinmesg="你好，请登录";
	String mail=(String)session.getAttribute("onlineuser");
	int total=0;
	ShopCart cart=null;
	if(mail==null){
		//request.setAttribute("loginbeforecart", "login");
		session.setAttribute("loginstate", "myStore");//从myStore前的登录
		response.sendRedirect("passport.jsp");
	}else{
		longinmesg=mail;
		cart = (ShopCart)session.getAttribute("cart");
		if(cart!=null){
			total = cart.getTotal();
		}
	}

%>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置个人信息</title>
<link rel="shortcut icon" href="imgs/favicon.ico"/>
<link href="<%= request.getContextPath()%>/home/css.css" rel="stylesheet" type="text/css">
<link href="<%= request.getContextPath()%>/home/myStorecss.css" rel="stylesheet" type="text/css">
<link href="<%= request.getContextPath()%>/home/cartcss.css" rel="stylesheet" type="text/css">
<link href="<%= request.getContextPath()%>/home/personalinfocss.css" rel="stylesheet" type="text/css">
<script src="http://api.map.baidu.com/api?v=1.5&ak=CqSmd95LZGbKrsshOnjTNUB3" type="text/javascript"></script>
<script type="text/javascript" src="js.js"></script>
</head>
<body>
	<div class="bigContainer">
		<div id="shortcut">
			<div class="logrow">
				<ul class="rowleft">
					<!-- 显示城市 -->
					<li id="mycity" calss="city">
						<div class="citydiv">
							<i class="citylogo"></i>
							<script type="text/javascript">
							   // 百度地图API功能
							   var map = new BMap.Map("bdMapBox");
							   var nowCity = new BMap.LocalCity();
							   nowCity.get(bdGetPosition);
							   function bdGetPosition(result){
							   var cityName = result.name; //当前的城市名
							  /*自定义代码*/
							   atCity.innerHTML = cityName;
						   		}
							</script>
							<span id="atCity">北京</span>
							<!-- 下拉城市菜单********未完******** -->
							<div class="citylist">
								<div class="item">北京</div>
								<div class="item">上海</div>
							</div><!-- citylist -->
						</div>
					</li>
					
				</ul><!--rowleft  -->
				<!-- 右边登录注册处 -->
				<ul class="rowright">
					<li class="loginbutton">
						
						<a class="linklogin" href="passport.jsp"><%=longinmesg %></a>
						<a class="linkregist" href="regist.jsp">免费注册</a>
					</li><!-- loginbutton -->
					<li class="spacer"></li>
					<li class="mygoods">
						<a class="linkmygoods">我的购物车</a>
					</li>
					<li class="spacer"></li>
					<li class="mystore">
						<a class="linkmystore">我的商城</a>
					</li>
				</ul><!--rowright  -->
			</div><!--logrow  -->
		</div><!-- shortcut -->
		
		<div id="nav">
			<div class="w">
				<div class="logo">
					<a href="//localhost:8080/WebShop/" target="_blank" class="fore1" >
						<img alt="商城首页" src="imgs/logo.jpg" style="width:158px;height:80px;">
					</a>
					<a href="//localhost:8080/WebShop/home/myStore.jsp" target="_self" class="fore2" >我的商城</a>
					<a href="//localhost:8080/WebShop/" target="_blank" class="fore3" >返回商城首页</a>
				</div>
				<div class="navitems">
					<ul>
						<li class="fore-1">
							<a target="_self" href="//localhost:8080/WebShop/home/myStore.jsp" clstag="homepage|keycount|home2013|Homeindex">首页</a>
						</li>
						
								</ul>
							</div>
							<div class="nav-r">
								<div id="search-2014" style="margin: 20px 5px;">
										<ul id="shelper" class="hide"></ul>
										<div class="form">
											<input onkeydown="javascript:if(event.keyCode==13) search('key');" autocomplete="off" id="key" accesskey="s" class="text blurcolor" type="text">
											<button onclick="search('key');return false;" class="button cw-icon" type="button"><i></i>搜索</button>
										</div>
								    </div>
								 
								<div id="settleup-2014" class="dorpdown" style="margin-top: 20px;">
										<div class="cw-icon">
											<i class="ci-left"></i>
											<i class="ci-right">&gt;</i><i class="ci-count" id="shopping-amount">0</i>
											<a target="_blank" href="//cart.jd.com/cart.action">我的购物车</a>
										</div>
										<div class="dorpdown-layer"><div class="spacer"></div><div id="settleup-content"><span class="loading"></span></div></div>
									</div>
								    <div id="hotwords-2014"></div>
							</div>
							<div class="clr"></div>
				</div>
		</div>
		
		<div id="container">
			<div class="w">
				<div id="content">
					<div id="sub">
					<!--  /widget/menu/menu.tpl -->
					<div id="menu">
	<dl class="fore1">
		<dt id="_MYJD_order">订单中心</dt>
		<dd class="fore1_1" id="_MYJD_ordercenter">
			<a href="buyer_order.jsp" target="_self">我的订单(买)</a>
		</dd>
		<dd class="fore1_4" id="_MYJD_yushou">
			<a href="payed_order.jsp" target="_self">待收货</a>
		</dd>
		<dd class="fore1_5" id="_MYJD_comments">
			<a  href="history_order.jsp" target="_self">历史订单</a>
		</dd>
                <dd class="fore1_7" id="_MYJD_alwaysbuy">
			<a href="evaluate_add.jsp" target="_self">评价晒单</a>
		</dd>
	</dl>
	<dl class="fore2">
		<dt id="_MYJD_gz">关注中心</dt>
		<dd class="fore2_1" id="_MYJD_product">
			<a href="caregood.jsp" target="_self">关注的商品</a>
		</dd>
		<dd class="fore2_2" id="_MYJD_vender">
			<a href="caresaler.jsp" target="_self">关注的店铺</a>
		</dd>
		<dd class="fore2_7" id="_MYJD_history">
			<a href="history.jsp" target="_blank">浏览历史</a>
		</dd>
	</dl>
	<dl class="fore3">
		<dt id="_MYJD_zc">卖家中心</dt>
		<dd class="fore3_1" id="_MYJD_cashbox">
			<a href="send_pre_order.jsp" target="_self">待发货订单</a>
		</dd>
		<dd class="fore3_1" id="_MYJD_cashbox">
			<a href="send_after_order.jsp" target="_self">已发货订单</a>
		</dd>
		<dd class="fore3_1" id="_MYJD_cashbox">
			<a href="send_received_order.jsp" target="_self">已收货订单</a>
		</dd>
		<dd class="fore3_1" id="_MYJD_cashbox">
			<a href="mygoods.jsp" target="_self">我上传的商品</a>
		</dd>
		<dd class="fore3_2" id="_MYJD_credit">
			<a target="_blank" class=""  href="upload.jsp">上传商品</a>
		</dd>
	</dl>
	
	<dl class="fore6">
		<dt id="_MYJD_sz">设置</dt>
		<dd class="fore6_1 curr" id="_MYJD_info">
			<a href="personalinfo.jsp" target="_self">个人信息</a>
		</dd>
	</dl>
</div>

					<div id="menu-ads">
					    <!--广告全部放这里-->
					
                </div>
               
				</div><!-- content -->
				 <div id="main">
				 <div class="personform">
				 		<div class="itemp">
				 			<span class="label">用户邮箱：</span>
				 			<input type="text" id="mailid" name="mailno" disabled value="<%=mail%>">
				 			
				 		</div>
				 		<div class="itemp" id="aliasArea">
				 			<span class="label">登录名：</span>
				 			<input type="text" name="nickname" id="nicknameid">
				 		</div>
				 		<div class="itemp">
				 			<span class="label">地址：</span>
				 			<input type="text" name="address" id="addressid">
				 		</div>
				 		<div class="itemp" >
				 			<input type="button" value="确定" class="sub" onclick="per_modify()" style="width:298px;">
				 			<input type="hidden" name="mailno" value="<%=mail%>">
				 			<input type="hidden" name="info" value="1">
				 			
				 		</div>
				 	</div>
				 </div>
			</div><!-- w -->
		
		</div><!-- container -->
		
		
	</div><!-- bigContainer -->

		
</body>
</html>