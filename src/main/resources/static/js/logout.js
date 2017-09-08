var rootUrl = publicDom.config.rootUrl;
var logoutUrl = rootUrl + "/user/logout";

$(function () {
	logOut();
})
function logOut() {
	$('#logout').on('click', function() {
		publicDom.getData("get",logoutUrl,null,function (data) {
			if (!data.code) {
				alert("退出成功！");
				location.href="login.html";
				
			}
			else{
				alert(data.value);
			}
		})
	});
}