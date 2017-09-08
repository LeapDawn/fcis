var rooturl = publicDom.config.rootUrl;
// var localRooturl = "http://127.0.0.1:8888";

//数据字典管理var
var getDataDirUrl = rooturl + "/DataDir/getDataDir";
var getDetailUrl = rooturl + "/DataDir/getDetail";
var modifyDetailUrl = rooturl + "/DataDir/modify";
var deleteDetailUrl = rooturl + "/DataDir/delete";
var addUrl = rooturl + "/DataDir/insert";

//用户信息管理var
var userListUrl = rooturl + "/user/list";
var addUserUrl = rooturl + "/user/save";
var deleteUserUrl = rooturl + "/user/delete";
var editUrl = rooturl +"/user/update"

//数据备份管理var,现在先本地测试
var backupListUrl = rooturl + "/backups/list";
var backupDelUrl = rooturl + "/backups/deletion";
var backupUrl = rooturl + "/backups/backup";
var backupRestoreUrl = rooturl + "/backups/restore";


// ------------------------
var page = 1;
var message = {
		"page":page,
		"rows":4,
}
var data = JSON.stringify(message);


// -------------------------
$(function(){
	//用户管理
	divInit(data);
	goTo(0);
	addUser()
	deleteUser();
	userEdit();

	//数据字典管理
	init();
	checkDetail();
	detailEdit();
	deleteDetail();
	addDetail();

	//数据备份
	initBackup();
	deleteBackup();
	backup();
	restoreBackup();
})





//初始化分页条 分页条goto函数
function divInit(data) {
	$('#tableTab_1').find('tbody').empty();
	var totalPage,perPage;
	publicDom.getData("post",userListUrl,data,function (data) {
		totalPage = parseInt(data.value.pages);
		perPage = data.value.rows;
		$('#showPerPage').val(perPage);
		$('#CurrentPage').val(0);
		page = 1;
		var currentLink = 0;
		if (totalPage != 1) {
			var navigationHtml = '<nav aria-label="Page navigation">' +'<ul class="pagination">'+'<li><a href="javascript:previous();" aria-label="Previous" class="previousLink">'
					       +'<span aria-hidden="true">'+'&laquo;'+'</li></span>'
						       + '</a>';
			for(var i = 0 ; i < totalPage ; i++){
				navigationHtml += '<li><a class="pageLink" href="javascript:goTo('+currentLink+')" longdesc = "'+currentLink+'"> '+(currentLink+1)+'</a></li>';
				currentLink++;
			}
			navigationHtml +=  '<li><a href="javascript:nextPage();" aria-label="Next" class="nextLink">'
							       +'<span aria-hidden="true">'+'&raquo;'+'</span></li>'
							       + '</a></nav>';
			$('#pageNavbar').html(navigationHtml);//将分页条写入页面
			$('#totalPage').val(totalPage);//保存totalPage
			$('.pageLink:first').addClass('activePage');//为第一页添加 .activePage
		}
		else{
			$('#pageNavbar').empty();
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
	$('#tableTab_1').find('tbody').empty();
	var data = JSON.stringify(message);
	initUser(data);
	$('.activePage').removeClass('activePage');
	$('.pageLink[longdesc='+newPage+']').addClass('activePage');//为新页面添加 .activePage
	$('#CurrentPage').val(newPage);//修改CurrentPage值
}




// 获取数据字典目录
function init() {
	// 初始化数据字典目录
	publicDom.getData("post",getDataDirUrl,JSON.stringify({}),function(data) {
		console.log(data);//测试
			if (!data.code){
				for (var i = 0; i < data.value.length ; i++) {
					$('#2').find('tbody').append('<tr><td>'+data.value[i].id+'</td><td>'+data.value[i].name+'</td><td>'
						+'<button class="btn btn-default checkBtnDir" data-toggle="modal" data-target="#dataDirModal" >'
						+"查看"+'</button>'+'</td></tr>')
				}
			}
	})
}

// 加载数据字典明细
function reloadDetail(id) {
	$('#dataDirModal').find('tbody').empty();
						var message = {
							"dataDir":id
						}
						var data = JSON.stringify(message);
						publicDom.getData("post",getDetailUrl,data,function(data) {
						console.log(data);
							if (!data.code){
								for (var i = 0; i < data.value.length ; i++) {
									$('#dataDirModal').find('tbody').append('<tr><td>'+'<input type="checkbox" name="dirOrder">'+'</td><td>'+data.value[i].id+'</td>'
											+'<td>'+data.value[i].content+'</td><td>'+data.value[i].dataDir+'</td>'
											+'<td><button class="btn btn-default dirEdit" data-toggle="modal" data-target="#editDetailModal" data-backdrop="static">'+"编辑"+'</button>'
											+'</td></tr>');
								}
							}
							else{
							alert(data.value);
							}
						})
}
// 查看数据字典明细信息
function checkDetail() {
	//获取数据字典目录下明细信息
	$('tbody').on('click','.checkBtnDir',function() {
		$('#dataDirModal').find('tbody').empty();
		var getId = $(this).parent().siblings('td:eq(0)').text();
		reloadDetail(getId);
	});
}

// 数据字典明细编辑
function detailEdit() {
	var getID;
	publicDom.getData("post",getDataDirUrl,JSON.stringify({}),function(data) {
		for (var i = 0; i <data.value.length ; i++) {
			$('#dataDirId').siblings('ul').append('<li><a class="selectDataDirID">'+data.value[i].id+'</a></li>');
		}
	}) 
	$('body').on('click','.dirEdit', function() {// 初始化编辑窗口
		getID = $(this).parent().siblings('td:eq(1)').text();
		var getContent = $(this).parent().siblings('td:eq(2)').text();
		var	getDirID = $(this).parent().siblings('td:eq(3)').text();
		$('#detailId').val(getID);
		$('#dataDirId').text(getDirID);
		$('#detailContent').text(getContent);
	});
	$('#selectDirItem').on('click', '.selectDataDirID',function() {
		$('#dataDirId').text($(this).text());
	});
	//点击保存
	$('#editDetailSave').on('click', function() {
		var newContent = $('#detailContent').val();
		var newDirId =parseInt($('#dataDirId').text());	
		var intId = parseInt(getID);
		var message = {
			"id":intId,
		 	"content":newContent,
			"dataDir":newDirId,
		}
		var data = JSON.stringify(message);
		publicDom.getData("post",modifyDetailUrl,data,function(data) {
			if (!data.code) {
				alert("修改成功！");
				$('#editDetailModal').modal('hide');
				reloadDetail(newDirId);// 重新加载明细
			}
			else{
				alert(data.value);
			}
		})
	});
}

// 数据字典明细删除
function deleteDetail() {
	$('#dirDetailDel').on('click', function() {
		var ids="";
		var dirId ;
		// 遍历被选中的checkbox
		$('#dataDirModal').find('tbody').find("input[type='checkbox']:checked").each(function(){
			 ids += $(this).parent().siblings('td:eq(0)').text() + ",";//拼接获得的id
			 dirId = $(this).parent().siblings('td:eq(2)').text();
		});
		ids=ids.substring(0,ids.length-1);
		if (ids!="") {
			if (confirm("确定要删除选中的项吗?")) {
				var message = {
					"ids" : ids,
				}
				var data = JSON.stringify(message);
				console.log(dirId);
				publicDom.getData("post",deleteDetailUrl,data,function (data) {
					if (!data.code) {
						alert(data.value);
						reloadDetail(dirId);// 重新加载明细
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
// 数据字典明细信息
function addDetail(){
	$('#dirDetailAdd').on('click', function() {
		$('#addDirId').val($('#dataDirModal').find('tbody').find('td:eq(3)').text());
	});
	$('#addDetailSave').on('click', function() {
		var dirId = parseInt($('#dataDirModal').find('tbody').find('td:eq(3)').text());
		var addContent  = $('#addDetailContent').val();
		var message = {
			"content":addContent,
	  		"dataDir":dirId
		}
		var data = JSON.stringify(message);
		console.log(data);
			publicDom.getData("post",addUrl,data,function(data) {
				alert(data.value);
				$('#addDetailModal').modal('hide');
				reloadDetail(dirId);
			})
		});
}




//用户管理

// 初始化用户列表
function initUser(data) {
	$('#1').find('tbody').empty();
	publicDom.getData("post",userListUrl,data,function(data) {
		console.log(data);//测试
			if (!data.code){
				for (var i = 0; i < data.value.data.length ; i++) {
					var genderStr = '男';
					if(data.value.data[i].gender == 1){
						genderStr = '女';
					}

					var functionStr ;
					if(data.value.data[i].function  == 1){
						functionStr = '录入';
					}
					else if (data.value.data[i].function  == 2){
						functionStr = '特殊信息查询';
					}
					else{
                        functionStr = '管理员';
					}
	
					$('#1').find('tbody').append('<tr><td><input type="radio" name="deleteRadio"></td>'+'<td>'+data.value.data[i].id+'</td><td>'
						+ data.value.data[i].name +'</td><td>'  + genderStr +'</td><td>'
						+ data.value.data[i].identityCard +'</td><td>' + data.value.data[i].tel +'</td><td>' 
						+ data.value.data[i].email +'</td><td>' + data.value.data[i].ownedAssociationName +'</td><td>' 
						+ functionStr
						+'</td><td><button class="btn btn-default editBtnUser" data-toggle="modal" data-target="#userModal">编辑</button></td></td><tr>'
						// +'<button class="btn btn-default checkBtnDir" data-toggle="modal" data-target="#dataDirModal" >'
						// +"查看"+'</button>'+'</td></tr>'
						)
				}
			}
			else{
				// alert(data.value);
			}
	})
}

// 添加新用户
function addUser(){
	$('.addBtnUser').on('click', function(){
		$('#addMail').val("");
		$('#addUserOwnedAssociation').val("") ;
		$('#addUser').val("");
		$('#addId').val("");
		$('#addName').val("");
		$('#newPaswd').val("");
		$('#addTelefon').val("");
		var message = {
			"dataDir":1
		}
		var data = JSON.stringify(message);
		console.log(data);
			publicDom.getData("post",getDetailUrl,data,function(data) {
				$('#addUserOwnedAssociation').empty();
				for (var i = 0; i < data.value.length ; i++){
					console.log(data.value[i].id);
                   $('#addUserOwnedAssociation').append('<option value ="' + data.value[i].id
                    +'">' + data.value[i].content + '</option>');
				}
			})
	});
	
	$('#addSave').on('click', function() {
		var email = $('#addMail').val();
		var ownedAssociation =$('#addUserOwnedAssociation').val() ;
		var id = $('#addUser').val();
		var identityCard = $('#addId').val();
		var name = $('#addName').val();
		var password = $('#newPaswd').val();
		var tel = $('#addTelefon').val();
		var f = $('#addUserFunction').val();
        if(f == '录入'){
        	f = 1;
        }
        else if (f == '特殊信息查询') {
        	f = 2;
        }
        else{
        	f = 7;
        }

		var gender  = $('#addUserGender').val();
		if(gender == '男'){
        	gender = 0;
        }
        else{
        	gender = 1;
        }

		var message = {
			"email":email,
			"function":f,
			"gender":gender,
			"ownedAssociation" : ownedAssociation,
			"id":id,
			"identityCard":identityCard,
			"name":name,
			"password":password,
	  		"tel":tel
		}
		var data = JSON.stringify(message);
		console.log(data);
			publicDom.getData("post",addUserUrl,data,function(data) {
				var curPage = parseInt($('#CurrentPage').val());
				alert(data.value);
				$('#addModal').modal('hide');
				$('#1').find('tbody').empty();
				var message = {
						"page":page,
						"rows":4,
				}
				var data = JSON.stringify(message);
				// divInit(data);
	            goTo(curPage);
			})
		});
}

//删除用户
function deleteUser() {
	$('.deleteBtnUser').on('click', function() {
		var id="";
		var curPage = parseInt($('#CurrentPage').val());

		$('#1').find('tbody').find("input[type='radio']:checked").each(function(){
			 id = $(this).parent().siblings('td:eq(0)').text() ;
			 console.log(id);
		});
		
		if (id !="") {
			if (confirm("确定要删除选中的项吗?")) {
				var message = {
					"id" : id
				}
				var data = JSON.stringify(message);
				publicDom.getData("post",deleteUserUrl,data,function (data) {
						alert(data.value);
				        $('#1').find('tbody').empty();
				        

				        var message = {
						"page":page,
						"rows":4,
						}
						var data = JSON.stringify(message);
			            goTo(curPage);
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

//修改用户(未完)
function userEdit() {
	var curPage;
	var message = {
			"dataDir":1
		}
		var data = JSON.stringify(message);
		publicDom.getData("post",getDetailUrl,data,function(data) {
			if (!data.code) {
				// $('#factory').empty();
				for (var i = 0; i < data.value.length ; i++){
	               $('#factory').append('<option value ="' + data.value[i].id
	                +'">' + data.value[i].content + '</option>');
				}
			}
			else{
				
			}
		})
    $('tbody').on('click', '.editBtnUser',function(){
    	curPage = $('#CurrentPage').val();
    	var id = $(this).parent().siblings('td:eq(1)').text();
    	var name = $(this).parent().siblings('td:eq(2)').text();
    	var sex = $(this).parent().siblings('td:eq(3)').text();
    	var identify = $(this).parent().siblings('td:eq(4)').text();
    	var telefon = $(this).parent().siblings('td:eq(5)').text();
    	var email = $(this).parent().siblings('td:eq(6)').text();
    	var fac = $(this).parent().siblings('td:eq(7)').text();
    	var right = $(this).parent().siblings('td:eq(8)').text();
    	$('#userName').val(id);
    	$('#yourName').val(name);
    	$('#sex').val(sex);
    	$('#yourId').val(identify);
    	$('#telefon').val(telefon);
    	$('#yourMail').val(email);
    	$('#chooseRight').val(right);
    	$('#factory').val(fac); 
    });
    	$('#editSave').on('click', function() {
    		var id = $('#userName').val();
    		var name = $('#yourName').val();
	    	var sex = $('#sex').val();
	    	var identify = $('#yourId').val();
	    	var tel = $('#telefon').val();
	    	var mail = $('#yourMail').val();
	    	var right = $('#chooseRight').val();
	    	var fac = $('#factory').val();
	    	if (fac == null) {
	    		alert("请选择所属总工会!");
	    	}
	    	else{}
	    	if (sex == "男") {
	    		var gender = 0;
	    	}
    		else if (sex == "女") {
    			var gender = 1;
    		}
    		else{}
    		if(right == '录入'){
	        	right = 1;
	        }
	        else if (right == '特殊信息查询') {
	        	right = 2;
	        }
	        else{
	        	right = 7;
	        }
	        console.log(right,gender);
    		var message = {
    			"email":mail,
    			"function":right,
    			"gender":gender,
    			"id":id,
    			"identityCard":identify,
    			"name":name,
    			"ownedAssociation":fac,
    			"password":"",
    			"tel":tel
    		}
    		var data = JSON.stringify(message);
    		publicDom.getData("post",editUrl,data,function (data) {
    			console.log(data);
    			if (!data.code) {
    				alert("修改成功！");
    				$('#userModal').modal('hide');
    				goTo(curPage);
    			}
    		})
    	});
}





//数据库备份还原

//初始化备份文件列表
function initBackup() {
	publicDom.getData("get",backupListUrl,null,function(data) {
		console.log(data);
			if (!data.code){
				for (var i = 0; i < data.value.length ; i++) {
					$('#3').find('tbody').append('<tr><td><input type="radio" name="deleteRadio"></td><td>'
						+data.value[i].fileName+'</td><td>'
						+ data.value[i].lastTime 
						+'</td><td><button class="btn btn-default backupDel">删除</button></td></tr>'
						// +'<button class="btn btn-default checkBtnDir" data-toggle="modal" data-target="#dataDirModal" >'
						// +"查看"+'</button>'+'</td></tr>'
						)
					$('#4').find('tbody').append('<tr><td><input type="radio"></td><td>'
						+data.value[i].fileName+'</td><td>'
						+ data.value[i].lastTime 
						+'</td><td><button class="btn btn-default reBackup">还原</button></td></tr>'
						)
				}
			}
	})
}

//删除备份文件
function deleteBackup() {
	$('tbody').on('click','.backupDel',function(){
		var fileName="";

		$('#3').find('tbody').find("input[type='radio']:checked").each(function(){
			 fileName = $(this).parent().siblings('td:eq(0)').text() ;
			 console.log(fileName);
		});
		
		if (fileName !="") {
			if (confirm("确定要删除选中的项吗?")) {
				var message = {
					"fileName" : fileName
				}
				var data = JSON.stringify(message);
				publicDom.getData("post",backupDelUrl,data,function (data) {
						alert(data.value);
				        $('#3').find('tbody').empty();
				        $('#4').find('tbody').empty();			        
						initBackup();
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

//备份数据库
function backup() {
	$('#addBackup').on('click',function(){
		var fileName = $('#filename').val();
		var message = {
				"fileName":fileName
			}
		var data = JSON.stringify(message);
        publicDom.getData("post",backupUrl,data,function (data) {
						alert(data.value);
				        $('#3').find('tbody').empty();		
				        $('#4').find('tbody').empty();		        
						initBackup();
				})
	});
}


//还原数据库
function restoreBackup() {
	$('tbody').on('click','.reBackup',function(){
		var fileName="";

		$('#4').find('tbody').find("input[type='radio']:checked").each(function(){
			 fileName = $(this).parent().siblings('td:eq(0)').text() ;
			 console.log(fileName);
		});
		
		if (fileName !="") {
			if (confirm("确定要还原选中的项吗?")) {
				var message = {
					"fileName" : fileName
				}
				var data = JSON.stringify(message);
				publicDom.getData("post",backupRestoreUrl,data,function (data) {
						alert(data.value);
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