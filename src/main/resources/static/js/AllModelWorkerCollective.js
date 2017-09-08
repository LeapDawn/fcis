var rooturl = publicDom.config.rootUrl;
var saveurl=rooturl+"/AllModelWorkerController/saveAdvancedCollective";
var updateurl=rooturl+"/AllModelWorkerController/updateAdvancedCollective";
var deleteurl=rooturl+"/AllModelWorkerController/deleteAdvancedCollective";
var listurl=rooturl+"/AllModelWorkerController/listAdvancedCollective";
var downloadurl=rooturl+"/AllModelWorkerController/downloadData";
var totalpage=1;
var conditionmessage={
	"page":1,
	"rows":4,
	"key":{}
}
$(function(){
	init();
	add();
	update();
	del();
	search();
	detail();
	download();
})
function renderDate(timeStamp){
	var date = new Date(timeStamp);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	if (month<10) {
		month = "0" + month;
	}
	if (day<10) {
		day = "0" + day;
	}
	var dateStr = year + "-" + month + "-" +day;
	return dateStr;
}
function load(){
publicDom.getData("post",listurl,JSON.stringify(conditionmessage),function(res){
if(!res.code){
	for (var i = 0; i < res.value.data.length; i++) {
		$('#2').find('tbody').append('<tr><td><input type="checkbox" name="order"></td>'
								+'<td>'+res.value.data[i].id+'</td>'
								+'<td>'+res.value.data[i].name+'</td>'
								+'<td>'+res.value.data[i].honoraryTitle+'</td>'
								+'<td>'+res.value.data[i].ownedCityIndustry+'</td>'
								+'<td>'+res.value.data[i].principalName+'</td>'
								+'<td>'+res.value.data[i].contactDetail+'</td>'
								+'<td>'+renderDate(res.value.data[i].additionTime)+'</td>'
								+'<td><button class="btn btn-default checkInfo" data-toggle="modal" data-target="#infoModal">详情</button></td>'
								+'<td><button class="btn btn-default groupEditBtn" data-toggle="modal" data-target="#editModal">编辑</button></td>'
							+'</tr>');}
}
	else{
		
	}
	});
}
function init(){
	$('#2').find('tbody').empty();
    $('#groupAddComName').val("");
	$('#groupAddTitle').val("");
	$('#groupAddProduct').val("");
	$('#groupAddRespdName').val("");
	$('#groupAddTelefon').val("");
	$('#addMainAchievement').val("");
	conditionmessage.page=1;
	publicDom.getData("post",listurl,JSON.stringify(conditionmessage),function(res){
if(!res.code){
	totalpage=res.value.pages;
		for (var i = 0; i < res.value.data.length; i++) {
	$('#2').find('tbody').append('<tr><td><input type="checkbox" name="order"></td>'
								+'<td>'+res.value.data[i].id+'</td>'
								+'<td>'+res.value.data[i].name+'</td>'
								+'<td>'+res.value.data[i].honoraryTitle+'</td>'
								+'<td>'+res.value.data[i].ownedCityIndustry+'</td>'
								+'<td>'+res.value.data[i].principalName+'</td>'
								+'<td>'+res.value.data[i].contactDetail+'</td>'
								+'<td>'+renderDate(res.value.data[i].additionTime)+'</td>'
								+'<td><button class="btn btn-default checkInfo" data-toggle="modal" data-target="#infoModal">详情</button></td>'
								+'<td><button class="btn btn-default groupEditBtn" data-toggle="modal" data-target="#editModal">编辑</button></td>'
							+'</tr>');}
}
	else{
		
	}
	if (totalpage != 1) {
			var navigationHtml = '<nav aria-label="Page navigation">' +'<ul class="pagination">'+'<li><a href="javascript:previous();" aria-label="Previous" class="previousLink">'
					       +'<span aria-hidden="true">'+'&laquo;'+'</span></li>'
						       + '</a>';
			for(var i = 1 ; i <= totalpage ; i++){
				navigationHtml += '<li><a class="pageLink" href="javascript:goTo('+i+')" longdesc = "'+i+'"> '+i+'</a></li>';
			}
			navigationHtml +=  '<li><a href="javascript:nextPage();" aria-label="Next" class="nextLink">'
							       +'<span aria-hidden="true">'+'&raquo;'+'</span></li>'
							       + '</a></nav>';
			$('#pageNavbar').html(navigationHtml);//将分页条写入页面
			$('.pageLink:first').addClass('activePage');//为第一页添加 .activePage
		}
		else{
			$('#pageNavbar').empty();
		}
	});
}

function add() {
	$('#groupEdSave').on('click',function(){
		var name=$('#groupAddComName').val();
		var honor=$('#groupAddTitle').val();
		var industry=$('#groupAddProduct').val();
		var principal=$('#groupAddRespdName').val();
		var tele=$('#groupAddTelefon').val();
		var achieve=$('#addMainAchievement').val();
		var message={
			"contactDetail":tele,
			"honoraryTitle":honor,
			"name":name,
			"outstandingDeed":achieve,
			"ownedCityIndustry":industry,
			"principalName":principal
		}
		var data=JSON.stringify(message);
		publicDom.getData("post",saveurl,data,function(data){
			if(!data.code){
				alert("新增成功");
				$('#groupAddModal').modal('hide');
				init();
			}
			else{
				alert(data.value);
			}
		});
	});
}
function update(){
	$('body').on('click','.groupEditBtn',function(){
		var id;
	    var name;
		var honor;
		var industry;
		var principal;
		var tele;
		id= $(this).parent().siblings('td:eq(1)').text();
		name=$(this).parent().siblings('td:eq(2)').text();
		honor=$(this).parent().siblings('td:eq(3)').text();
		industry=$(this).parent().siblings('td:eq(4)').text();
		principal=$(this).parent().siblings('td:eq(5)').text();
		tele=$(this).parent().siblings('td:eq(6)').text();
		publicDom.getData("post",listurl,JSON.stringify({"page":1,"rows":1,"key":{"id":id}}),function(res){
			if(!res.code)
			$('#mainAchievement').val(res.value.data[0].outstandingDeed);
		else
			{alert(res.value);}
		});
		$('#id').val(id);
		$('#groupComName').val(name);
		$('#groupTitle').val(honor);
		$('#groupProduct').val(industry);
		$('#groupRespdName').val(principal);
		$('#groupTelefon').val(tele);
	});
	$('#groupEditSave').on('click',function(){
		var id;
	    var name;
		var honor;
		var industry;
		var principal;
		var tele;
		var achieve;
		id=$('#id').val();
		name=$('#groupComName').val();
		honor=$('#groupTitle').val();
		industry=$('#groupProduct').val();
		principal=$('#groupRespdName').val();
		tele=$('#groupTelefon').val();
		achieve=$('#mainAchievement').val();
		var message={
			"contactDetail":tele,
			"honoraryTitle":honor,
			"name":name,
			"outstandingDeed":achieve,
			"ownedCityIndustry":industry,
			"principalName":principal,
			"id":id
		}
		var data=JSON.stringify(message);
		publicDom.getData("post",updateurl,data,function(data){
			if(!data.code){
				alert("修改成功");
				$('#editModal').modal('hide');
				init();
			}
			else{
				alert(data.value);
			}
		});
	});
}
function del(){
$('.groupDeleteBtn').on('click', function() {
		var ids="";
		// 遍历被选中的checkbox
		$('#2').find('tbody').find("input[type='checkbox']:checked").each(function(){
			 ids += $(this).parent().siblings('td:eq(0)').text() + ",";//拼接获得的id
		});
		ids=ids.substring(0,ids.length-1);
		if (ids!="") {
			if (confirm("确定要删除选中的项吗?")) {
				var message = {
					"ids":ids
				}
				var data = JSON.stringify(message);
				publicDom.getData("post",deleteurl,data,function (data) {
					if (!data.code) {
						alert("删除成功");
						init();
					}
					else{
						alert(data.value);
					}					
				});
			}
		}
		else{
			alert("未有选中项！")
		}
	});
}
function search(){
$('#selectCondition').change(function(){
	var condition=$('#selectCondition').val();
	if(condition=="新增时间"){
		$('#inputSearchText').attr({type:'date'});
	}
	else{
$('#inputSearchText').attr({type:'text'});
	}
});
$('#groupSearchBtn').on('click',function(){
	var condition=$('#selectCondition').val();
	var input=$('#inputSearchText').val();
	var searchkey={};
switch(condition){
case "新增时间":searchkey={"additionTime":input};break;
case "荣誉称号":searchkey={"honoraryTitle":input};break;
case "联系方式":searchkey={"contactDetail":input};break;
case "公司名称":searchkey={"name":input};break;
case "负责人名称":searchkey={"principalName":input};break;
case "所属市州产业":searchkey={"ownedCityIndustry":input};break;
default:break;
}
conditionmessage.key=searchkey;
init();
});
}
// 上一页
function previous() {
	conditionmessage.page--;
	if (conditionmessage.page>0) {
		goTo(conditionmessage.page);
	}
	else{
		alert("已是最前一页!");
		conditionmessage.page++;
	}
}
// 下一页
function nextPage() {
	conditionmessage.page++;
	if (conditionmessage.page<=totalpage) {
		goTo(conditionmessage.page);
	}
	else{
		alert("已是最后一页!");
		conditionmessage.page--;
	}
}
// 跳转到页
function goTo(newPage){
	$('#2').find('tbody').empty();
	conditionmessage.page=newPage;
	load();
	$('.activePage').removeClass('activePage');
	$('.pageLink[longdesc='+conditionmessage.page+']').addClass('activePage');//为新页面添加 .activePage
}
function detail(){
$('body').on('click','.checkInfo',function(){
	var id= $(this).parent().siblings('td:eq(1)').text();
publicDom.getData("post",listurl,JSON.stringify({"page":1,"rows":1,"key":{"id":id}}),function(res){
			if(!res.code){
			$('#detail').val(res.value.data[0].outstandingDeed);
		}
		else
			{alert(res.value);}
		});
});
}
function download() {
    $('#downLoad').on('click',function () {
        var ids = "";
        $('#2').find('tbody').find("input[type='checkbox']:checked").each(function(){
            ids = ids+$(this).parent().siblings('td:eq(0)').text() + ",";
        });
        window.open(downloadurl + "?ids=" +ids+ "&type=1","_self");
           return false;
    });
}