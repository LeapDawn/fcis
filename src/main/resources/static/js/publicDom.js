var publicDom = {

	//配置项
	config:{
		rootUrl: "http://127.0.0.1:8888",
		url: "http://127.0.0.1:8888", // 请求接口的url
	},

    
    /*
      type 请求类型，
      param JSON字符串，
      f 拿到数据后执行的回调函数
    */
	getData: function(type, url, param, f, isAsync, err) {
		
		var ajaxParam  = {
			type: type,
			url: url,
			data: param,		
			async: isAsync !== false,
			contentType: "application/json",  
		    processData: false,  
			success: function(data) {				
				var jsonData;
				try{
					jsonData = $.parseJSON(data);
				}catch(e) { // 返回值不是json格式
					if(typeof f !== 'function') { // 如果没有回调函数,抛出异常。
						throw new Error('请求数据之后，没有回调函数!');
					}
					f(data);
					if (data.code == -1) {
						window.location.href = rootUrl + "/login.html";
					}
					return;
				}
				if(jsonData.code === 302){
					window.location.href = jsonData.msg||"/html/login_store.html";
					
				} else if(jsonData.code===-1){
					//超时或者存在
					window.location.href = rootUrl + "/login.html";
				}
				else {

					if(typeof f !== 'function') { // 如果没有回调函数,抛出异常。

						throw new Error('请求数据之后，没有回调函数!');
					}
					f(jsonData);
				}
			},
			error: function(xhr,textStatus) {
				
			}
		};
		$.ajax(ajaxParam);
	},
	

	//退出登录
};