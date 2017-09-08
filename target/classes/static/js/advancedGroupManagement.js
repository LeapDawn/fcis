var rootUrl = publicDom.config.rootUrl;
var editUrl = rootUrl + "/ac/update";
var countUrl = rootUrl + "/ac/statics";
var lastCountUrl = rootUrl + "/ac/lastStaticsResult"
var addUrl = rootUrl + "/ac/addition";
var deleteUrl = rootUrl + "/ac/delete";
var searchUrl = rootUrl + "/ac/list";
var inLoadUrl = rootUrl + "/ac/upload";
var outLoadUrl = rootUrl + "/ac/export";
var downLoadUrl = rootUrl + "/ac/download";
// 初始化获取数据
var page = 1;
var message = {
		"key":{},
		"page":page,
		"rows":4,
}
var data = JSON.stringify(message);
//-----------------------
$(function(){
	preventEnter();
	init(data);
	goTo(0);
	detail();
	search();
	edit();
	add();
	delt();
	lastCount();
	count();
	downLoad();
	outLoad();
	inLoad();
})
//禁用回车事件
function preventEnter() {
	$(this).keydown(function(e) {
		var key = window.event?e.keyCode:e.which;
		if (key.toString() == "13") {
			return false;
		}
	});
}
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
//初始化分页条 分页条goto函数
function init(data) {
	$('#infoTable').find('tbody').empty();
	var totalPage;
	// 分页初始化 -显示分页条
	publicDom.getData("post",searchUrl,data,function (data) {
		totalPage = parseInt(data.value.pages);
		perPage = data.value.rows;
		totalItem = data.value.total;
		$('#showPerPage').val(perPage);
		$('#CurrentPage').val(0);
		$('#totalItem').val(totalItem)
		page = 1;
		var currentLink = 0;
		if (totalPage != 1) {
			var navigationHtml = '<nav aria-label="Page navigation">' +'<ul class="pagination">'+'<li><a href="javascript:previous();" aria-label="Previous" class="previousLink">'
					       +'<span aria-hidden="true">'+'&laquo;'+'</li></span>'
						       + '</a>';
			for(var i = 0 ; i < totalPage ; i++){
				navigationHtml += '<li><a class="pageLink" href="javascript:goTo('+currentLink+')" longdesc = "'+currentLink+'"> '+(currentLink+1)+'</a></li>';
				// <a class="pageLink" href="javascript:goTo(0)" longdesc="0">1</a> 第一页
				currentLink++;
			}
			navigationHtml +=  '<li><a href="javascript:nextPage();" aria-label="Next" class="nextLink">'
							       +'<span aria-hidden="true">'+'&raquo;'+'</span></li>'
							       + '</a></nav>';
			$('#pageNavbar').html(navigationHtml);//将分页条写入页面
			$('#totalPage').val(totalPage);//保存totalPage
			$('.pageLink').first().addClass('activePage');//为第一页添加 .activePage
		}
		else{
			$('#pageNavbar').empty();
		}
		
	})
}
// 加载对应页数据
function loadInfo(data) {
	publicDom.getData("post",searchUrl,data,function (data) {
		for (var i = 0; i < data.value.data.length ; i++) {// 加载集体荣誉信息
			var date = renderDate(data.value.data[i].additionTime);
			$('#infoTable').find('tbody').append('<tr>'
								+'<td><input type="checkbox" name="order"></td>'
								+'<td>'+data.value.data[i].id+'</td>'
								+'<td>'+data.value.data[i].name+'</td>'
								+'<td>'+data.value.data[i].honoraryTitle+'</td>'
								+'<td>'+data.value.data[i].ownedCityIndustry+'</td>'
								+'<td>'+data.value.data[i].principalName+'</td>'
								+'<td>'+data.value.data[i].contactDetail+'</td>'
								+'<td>'+date+'</td>'
								+'<td><button class="btn btn-default checkInfo" data-toggle="modal" data-target="#infoModal">'+'详情'+'</button></td>'
								+'<td><button class="btn btn-default editBtn" data-toggle="modal" data-target="#editModal">'+'编辑'+'</button></td>'
							+'</tr>')
		}
	})
}
// 上一页
function previous() {
	var newPage = parseInt($('#CurrentPage').val())-1;
	console.log(newPage);
	if (newPage>=0) {
		goTo(newPage);
	}
	else{
		alert("已是最前一页!");
	}
}
// 下一页
function nextPage() {
	var newPage = parseInt($('#CurrentPage').val())+1;
	var totalPage = parseInt($('#totalPage').val());
	if (newPage<totalPage) {
		goTo(newPage);
	}
	else{
		alert("已是最后一页!");
	}
}
// 跳转到页
function goTo(newPage){
	message.page = newPage+1; //修改要查询的page值
	$('#infoTable').find('tbody').empty();
	var data = JSON.stringify(message);
	loadInfo(data);
	$('.activePage').removeClass('activePage');
	$('.pageLink[longdesc='+newPage+']').addClass('activePage');//为新页面添加 .activePage
	$('#CurrentPage').val(newPage);//修改CurrentPage值
}
// 获取突出事迹
function getDeed(id) {
	var message = {
		"key":{
			"id":id
		},
		"page":page,
		"rows":4
		}
		var data= JSON.stringify(message);
		publicDom.getData("post",searchUrl,data,function (data) {
			console.log(data);
			var detailContent = data.value.data[0].outstandingDeed;
			$('#checkMainDeed').val(detailContent);
			$('#mainAchievement').val(detailContent);
	})
}
// 详情
function detail() {
	$('body').on('click', '.checkInfo',function() {
		var getId = $(this).parent().siblings('td:eq(1)').text();
		getDeed(getId);
	});
}
// 编辑
function edit() {
	// 初始化编辑窗口
	var curPage;
	$('tbody').on('click','.editBtn', function() {
		curPage = parseInt($('#CurrentPage').val());
		var id = $(this).parent().siblings('td:eq(1)').text();
		getDeed(id);
		var company = $(this).parent().siblings('td:eq(2)').text();
		var title = $(this).parent().siblings('td:eq(3)').text();
		var owner = $(this).parent().siblings('td:eq(4)').text();
		var people = $(this).parent().siblings('td:eq(5)').text();
		var moblie = $(this).parent().siblings('td:eq(6)').text();
		var date = $(this).parent().siblings('td:eq(7)').text();
		var deed = $('#outstandingDeed').val();
		$('#groupId').val(id);
		$('#groupComName').val(company);
		$('#groupTitle').val(title);
		$('#groupProduct').val(owner);
		$('#groupRespdName').val(people);
		$('#groupTelefon').val(moblie);
		$('#addDate').val(date);
		$('#mainAchievement').val(deed);
	});
	$('#groupEditSave').on('click', function() {
		var newId = $('#groupId').val();
		var newCompany = $('#groupComName').val();
		var newTitle = $('#groupTitle').val();
		var newOwner = $('#groupProduct').val();
		var newPeople =$('#groupRespdName').val();
		var newMoblie = $('#groupTelefon').val();
		var newDeed = $('#mainAchievement').val();		
		var message = {
			"id":newId,
			"ownedCityIndustry":newOwner,
			"contactDetail":newMoblie,
			"honoraryTitle":newTitle,
			"name":newCompany,
			"outstandingDeed":newDeed,
			"principalName":newPeople,
		}
		var data = JSON.stringify(message);
		console.log(data);
		publicDom.getData("post",editUrl,data,function (data) {
			alert(data.value);
			$('#editModal').modal('hide');
			goTo(curPage);
		})
		
	});
}
// 显示最近一次统计结果
function lastCount() {
	$('.countButton').on('click', function() {
		$('#countChart').css({display: 'none',});
		$('#lastCount').css({display: 'block',});
			publicDom.getData("get",lastCountUrl,null,function(data) {
				if (!data.code) {
					$('#countTable').find('tbody').empty();
					$('#countTable').find('thead').empty();
					$('#lastCount').css({display: 'block',});
					var date = renderDate(data.value.createDate)
					var name = new Array();
					var chartData =  new Array();
					var object =  eval(data.value.resultContext);//将JSON字符串转化为对象
					$('#startDate').val(date);
					$('#countTable').find('thead').append('<tr>'
											+'<th>'+'荣誉称号'+'</th>'
											+'<th>'+'拥有该荣誉称号的先进集体数目'+'</th></tr>');
					for(var i = 0 ; i < object.length ; i++){
						chartData[i] = new Object();
						chartData[i].value = object[i].num;
						chartData[i].name = object[i].title;
						name[i] = object[i].title;

						$('#countTable').find('tbody').append('<tr><td>'+chartData[i].name+'</td><td>'
															+chartData[i].value+'</td></tr>')
					}
					console.log(chartData);
					var chart = echarts.init(document.getElementById('lastCount'));//初始化echarts实例
				 	count = {
							    title : {
							        text: '荣耀称号统计',
							        subtext: '最近一次的统计结果',
							        x:'center'
							    },
							    tooltip : {
							        trigger: 'item',
							        formatter: "{a} <br/>{b} : {c} ({d}%)"
							    },
							    legend: {
							        orient: 'vertical',
							        left: 'left',
							        data:name
							    },
							    series : [
							        {
							            name: '- 先进集体',
							            type: 'pie',
							            radius : '55%',
							            center: ['50%', '60%'],
							            data:chartData,
							            itemStyle: {
							                emphasis: {
							                    shadowBlur: 10,
							                    shadowOffsetX: 0,
							                    shadowColor: 'rgba(0, 0, 0, 0.5)'
							                }
							            }
							        }
							    ]
							};
						chart.setOption(count);
					}
				else{
					alert(data.value);
				}
			})
		});
}
// 统计
function count() {
	$('#countBtn').on('click', function() {
		var start = $('#startDate').val();
		var end = $('#endDate').val();
		var chartData = new Array();//存储用于echarts的数据
		var name = new Array();
		if (start != "" && end !="" && start<end) {
			console.log(start,end);
			var message = {
				"beginDate":start,
				"endDate":end
			}
			var data = JSON.stringify(message);
			publicDom.getData("post",countUrl,data,function (data) {
				if (!data.code) {
					$('#countChart').css({display: 'block',});//显示新统计结果
					$('#lastCount').css({display: 'none',});//取消最近一次统计结果显示
					console.log(data);
					$('#countTable').find('tbody').empty();
					$('#countTable').find('thead').empty();
					$('#countTable').find('thead').append('<tr>'
											+'<th>'+'荣誉称号'+'</th>'
											+'<th>'+'拥有该荣誉称号的先进集体数目'+'</th></tr>')
					if (data.value.length == 0) {
						alert("该时段未有统计数据！");
					}
					else{
						for (var i = 0; i < data.value.length ; i++) {
							chartData[i]=new Object();
							chartData[i].value = data.value[i].num;
							chartData[i].name = data.value[i].title;
							name[i]=data.value[i].title;
							$('#countTable').find('tbody').append('<tr><td>'+data.value[i].title+'</td><td>'
																+data.value[i].num+'</td></tr>')
						}
						 var newChart = echarts.init(document.getElementById('countChart'));//初始化echarts实例
						  newCount = {
						    title : {
						        text: '荣耀称号统计',
						        subtext: '仅供参考',
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        left: 'left',
						        data:name
						    },
						    series : [
						        {
						            name: '- 先进集体',
						            type: 'pie',
						            radius : '55%',
						            center: ['50%', '60%'],
						            data:chartData,
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: 'rgba(0, 0, 0, 0.5)'
						                }
						            }
						        }
						    ]
						};
						newChart.setOption(newCount);
					}
				}	
				else{
					alert(data.value);
				}
			})
		}
		else if (start == "")
		{
			alert("请选择起始日期！");
		}
		else if( end == "")
		{
			alert("请选择结束日期！");
		}
		else if(start>end){
			alert("起始日期大于结束日期，请重新选择!")
		}
		else{

		}
		
	});	
}
// 申报/新增
function add() {
	$('#groupAddSave').on('click', function() {
		var company = $('#groupAddComName').val();
		var title = $('#groupAddTitle').val();
		var owner = $('#groupAddProduct').val();
		var people = $('#groupAddRespdName').val();
		var moblie = $('#groupAddTelefon').val();
		var deed = $('#addMainAchievement').val();
		var message = {
			"contactDetail":moblie,
			"honoraryTitle":title,
			"name":company,
			"outstandingDeed":deed,
			"ownedCityIndustry":owner,
			"principalName":people
		}
		var data = JSON.stringify(message);
		console.log(data);
		publicDom.getData("post",addUrl,data,function (data) {
			alert(data.value);
			$('#groupAddModal').modal('hide');
			var reload = {
				"key":{},
				"page":1,
				"rows":4
			}
			var send = JSON.stringify(reload);
			init(send);
			goTo(0);
		})
	});
}
// 删除
function delt() {
	$('.deleteBtn').on('click', function() {
		var ids="";
		curPage = parseInt($('#CurrentPage').val());
		$('#infoTable').find('tbody').find("input[type='checkbox']:checked").each(function(){
			 ids += $(this).parent().siblings('td:eq(0)').text() + ",";
		});
		ids=ids.substring(0,ids.length-1);
		console.log(curPage);
		if (ids!="") {
			if (confirm("确定要删除选中的项吗?")) {
				var message = {
					"ids" : ids,
				}
				var data = JSON.stringify(message);
				publicDom.getData("post",deleteUrl,data,function (data) {
					if (!data.code) {
						alert(data.value);
						var message = {
							"key":{},
							"page":1,
							"rows":4,
						}
						var data = JSON.stringify(message);
						init(data);
						goTo(0);
					}
					else{
						alert(data.value);
					}					
				})
			}
			else{
			}
		}
		else{
			alert("未有选中项！")
		}		
	});
}
// 查询
function search() {
	// 控制输入查询框
	$('#searchTerm').change(function() {
		var term = $('#searchTerm').val();
		if (term == "新增日期") {
			$('#inputSearchText').attr({type: 'date'});
		}
		else
		{
			$('#inputSearchText').attr({type: 'text'});
		}
	});
	$('#searchBtn').on('click', function() {
		var page = 1;
		var term = $('#searchTerm').val();
		var inputTerm = $('#inputSearchText').val();
		if (inputTerm != "") {
			switch(term){
				case("单位名称"):
					 message = {
						"key":{
							"name":inputTerm,
						},
						"page":page,
						"rows":4
					}
					break;
				case("荣誉称号"):
					 message = {
						"key":{
							"honoraryTitle":inputTerm,
						},
						"page":page,
						"rows":4
					}
					break;
				case("负责人名称"):
					 message = {
						"key":{
							"principalName":inputTerm,
						},
						"page":page,
						"rows":4
					}
					break;
				case("所属市业产业"):
					 message = {
						"key":{
							"ownedCityIndustry":inputTerm,
						},
						"page":page,
						"rows":4
					}
					break;
				case("新增日期"):
					 message = {
						"key":{
							"additionTime":inputTerm,
						},
						"page":page,
						"rows":4
					}
					break;
			}
			var data = JSON.stringify(message);
			console.log(data);
			init(data);
			publicDom.getData("post",searchUrl,data,function (data) {
				if (data.value.total !=0) {
					alert("查询成功!")
					for (var i = 0; i < data.value.data.length ; i++) {
						var date = renderDate(data.value.data[i].additionTime);
						// 加载集体荣誉信息
						$('#infoTable').find('tbody').append('<tr>'
										+'<td><input type="checkbox" name="order"></td>'
										+'<td>'+data.value.data[i].id+'</td>'
										+'<td>'+data.value.data[i].name+'</td>'
										+'<td>'+data.value.data[i].honoraryTitle+'</td>'
										+'<td>'+data.value.data[i].ownedCityIndustry+'</td>'
										+'<td>'+data.value.data[i].principalName+'</td>'
										+'<td>'+data.value.data[i].contactDetail+'</td>'
										+'<td>'+date+'</td>'
										+'<td><button class="btn btn-default checkInfo" data-toggle="modal" data-target="#infoModal">'+'详情'+'</button></td>'
										+'<td><button class="btn btn-default editBtn" data-toggle="modal" data-target="#editModal">'+'编辑'+'</button></td>'
										+'</tr>')
					}
				}
				else{
					alert("未找到相应的数据!");
				}
			})
		}
		else{
			alert("输入查询条件为空!将为您加载所有数据...");
			message = {
				"key":{},
				"page":1,
				"rows":4
			}
			var data = JSON.stringify(message);
			init(data);
			goTo(0);
		}
	})
}
// 数据导入
function inLoad() {
	$('#loadIn').on('click', function() {
		$('#fileName').val('');
		$('#fileName').click();
	})
		$('#fileName').unbind().change(function(){
			// var url = "http://182.149.196.33:8888/ac/upload";
			var file = this.files[0];
			var formData = new FormData();
			formData.append("file",file);
			console.log(file)
			if (!(/.\.xls|.\.xlsx/).exec(file.name)) {
				alert("请确保上传.xls或.xlsx文件格式！");
			}
			else{
				$.ajax({  
			      url:inLoadUrl,  
			      type: 'POST',  
			      data: formData,
			      async: false,  
			      cache: false,  
			      enctype:'multipart/form-data',
			      contentType:false,  
			      processData: false,  
			      success: function (data) {
			      	console.log(data);
			      	if(!data.code){
			        	alert("导入文件"+file.name+"成功!"+"并"+data.value);
			        	var message = {
							"key":{},
							"page":1,
							"rows":4,
						}
						var data = JSON.stringify(message);
						init(data);
						goTo(0);
			        }
			        else{
		        		alert("导入失败！")
		  	        	}
		          },  
			      error: function (data) { 
			   			alert("导入时发生错误，导入失败！")
			      }  
			});  
		}
	})
}
// 数据导出
function outLoad() {
	$('#loadBtn').on('click', function() {
		var start = $('#startLoad').val();
		var end = $('#endLoad').val();
		if (start != "" && end != "" && start<end) {
			window.open(outLoadUrl + "?beginDate=" +start+ "&endDate=" +end+ "","_self");
		}
		else if (start == "") {
			alert("请选择起始日期!");
		}
		else if (end == "") {
			alert("请选择结束日期!")
		}
		else if (start>end){
			alert("起始日期大于结束日期，请重新选择!");
		}
		else{

		}
	});
	

}
// 数据下载
function downLoad() {
	$('#downLoad').on('click', function(){
		var ids="";
		curPage = parseInt($('#CurrentPage').val());
		$('#infoTable').find('tbody').find("input[type='checkbox']:checked").each(function(){
			 ids += $(this).parent().siblings('td:eq(0)').text() + ",";
		});
		ids=ids.substring(0,ids.length-1);
			if (ids != "") {
				console.log(ids);
				window.open(downLoadUrl + "?ids=" +ids+ "&type=1","_self");
			}
			else{
				alert("请选择需要下载的信息!");
			}
		return false;
	})
}
function modeDownload() {
	$('#modeDownload').on('click', function() {

	});
}
