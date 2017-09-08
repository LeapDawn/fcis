// var rootUrl = "http://120.76.122.187:8888";
var rootUrl = publicDom.config.rootUrl;
var loginUrl = rootUrl + "/user/login";
var logoutUrl = rootUrl + "/user/logout";

$(function () {
	login();
})
function login() {
	$('#loginClick').on('click', function(){
		var user = $('#inputName').val();
		var paswd = $('#passWord').val();
		var message = {
			"id":user,
			"password":paswd
		}
		var data = JSON.stringify(message);
		console.log(data);
		if (user != "" && paswd != "") {
			publicDom.getData("post",loginUrl,data,function (data) {
				if (!data.code) {
					alert("登录成功!");
					location.href="index.html";
					event.preventDefault();
					$('form').fadeOut(500);
					$('.wrapper').addClass('form-success');
				}
				else{
					alert(data.value);
				}
			})
		}
		else if (user=="") {
			alert("请输入用户名！");
		}
		else if (paswd=="") {
			alert("请输入密码！");
		}
		else{
			alert("Error:页面发生错误!");
		}
	});
}
