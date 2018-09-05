var commonUtil = {
	emailRegExp : /^[\.\-_a-z0-9A-Z]+@([\-_a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,4}$/,
	mobileReg   : /^1(\d{10})$/,
	telReg   : /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/,
	floatReg   : /^\d+(\.\d+)?$/,
	//去掉字符串前后空格
	strTrim : function(str) {
		return str.replace(/^\s*|\s*$/g,"");
	},
	
	//判断是否为数字
	checkvalue : function (val){
	   if(!/^[0-9]+\d*$/.test(val) || val =='' || val == 0){
	     return false;
	   }
	   return true;
	},
	//验证手机号码
	checkMobile : function(val) {
		if(!commonUtil.mobileReg.test(val) ){
			return false;
		}
		return true;
	},
	//验证固定电话
	checkTel : function(val) {
		if(!commonUtil.telReg.test(val) ){
			return false;
		}
		return true;
	},
	//验证邮箱
	checkEmail : function(val) {
		if(!commonUtil.emailRegExp.test(val) ){
			return false;
		}
		return true;
	},
	//验证浮点数
	checkFloat : function(val) {
		if(!commonUtil.floatReg.test(val) ){
			return false;
		}
		return true;
	},
	//返回字符串长度
	getStrLength : function (str) {
    	if(str == null) return 0;
	  	return str.replace(/^\s*|\s*$/g,"").length;
   	},
   	//检查字符串是否为空
   	checkStrEmpty : function(str) {
   		var a = commonUtil.strTrim(str);
   		if(a == null || a == "") {
			return false;
		}
   		return true;
   	},
   	
   	//返回字符串字节，一个汉字占两个字节
   	getStrByteLength : function (str){
	    var totalCount = 0; 
	    if(str == null) return 0;
	    var tempLength = str.replace(/^\s*|\s*$/g,"").length;
	    for (var i = 0; i < tempLength; i++) {
	        var c = str.charCodeAt(i);
	        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
	             totalCount++;
	         }else {    
	             totalCount+=2;
	         }
	     }
	     
	    return totalCount;
	},
   	
   	/**
   	 * 半角<=>全角 之间的转换
   	 * @param str   要转换的字符串
   	 * @param flag  true表示全角转半角，false表示半角转全角
   	 * @return
   	 */
	DBC2SBC : function (str, flag){
   	    var result = '';
   	    str = str.replace(/。/g,"．");
   	    for(var i = 0; i < str.length; i++){
   	        code = str.charCodeAt(i);
   	        if(flag){
   	            if(code >= 65281 && code <= 65373) result += String.fromCharCode(str.charCodeAt(i) - 65248);
   	            else if(code == 12288) result += String.fromCharCode(str.charCodeAt(i) - 12288 + 32);
   	                else result += str.charAt(i);
   	        }
   	        else{
   	            if(code >= 33 && code <= 126) result += String.fromCharCode(str.charCodeAt(i) + 65248);
   	            else if(code == 32) result += String.fromCharCode(str.charCodeAt(i) - 32 + 12288);
   	                else result += str.charAt(i);
   	        }
   	    }
   	    return result;
   	},
	//
   	getCookieVal : function(key){
		if (document.cookie){
			var cookieArr = (document.cookie).split('; ');
			for (var i = 0; i < cookieArr.length; i++){
				var keyArr = cookieArr[i].split('=');
				if (keyArr[0] == key){
					return keyArr[1];
				}
				if (i == cookieArr.length - 1){
					return 'empty';
				}
			}
		}
		else{
			return 'empty';
		}
	},

	addcookie : function(name, value, options) {
	    if (typeof value != 'undefined') { // name and value given, set cookie
	        options = options || {};
	        if (value === null) {
	            value = '';
	            options.expires = -1;
	        }
	        var expires = '';
	        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
	            var date;
	            if (typeof options.expires == 'number') {
	                date = new Date();
	                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
	            } else {
	                date = options.expires;
	            }
	            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
	        }
	        // CAUTION: Needed to parenthesize options.path and options.domain
	        // in the following expressions, otherwise they evaluate to undefined
	        // in the packed version for some reason...
	        var path = options.path ? '; path=' + (options.path) : '';
	        var domain = options.domain ? '; domain=' + (options.domain) : '';
	        var secure = options.secure ? '; secure' : '';
	        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	    } else { // only name given, get cookie
	        var cookieValue = null;
	        if (document.cookie && document.cookie != '') {
	            var cookies = document.cookie.split(';');
	            for (var i = 0; i < cookies.length; i++) {
	                var cookie = jQuery.trim(cookies[i]);
	                // Does this cookie string begin with the name we want?
	                if (cookie.substring(0, name.length + 1) == (name + '=')) {
	                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
	                    break;
	                }
	            }
	        }
	        return cookieValue;
	    }
	},
	//事件绑定
	addEvent : function ( obj, type, fn ) {
        if ( obj.attachEvent ) {
            obj['e'+type+fn] = fn;
            obj[type+fn] = function(){obj['e'+type+fn]( window.event );}
            obj.attachEvent( 'on'+type, obj[type+fn] );
        } else
            obj.addEventListener( type, fn, false );
    },
    //事件取消
	removeEvent : function ( obj, type, fn ) {
	    if ( obj.detachEvent ) {
	        obj.detachEvent( 'on'+type, obj[type+fn] );
	        obj[type+fn] = null;
	    } else
	        obj.removeEventListener( type, fn, false );
	},
	
	clearWaitInfo : function() {
		var waitInfo = document.getElementById("waitInfo");
		if(waitInfo != null){
			waitInfo.parentNode.removeChild(waitInfo);
		 }
	},
	
	boxBg: null,
	box: null,
	loginFrame: null,

	getPageSize: function(){
		var xScroll, yScroll;  
		if (document.documentElement.scrollWidth){//FF
			xScroll = document.documentElement.scrollWidth;
			yScroll = document.documentElement.scrollHeight;
		}
		else if (window.innerHeight && window.scrollMaxY) {
			xScroll = document.body.scrollWidth;
			yScroll = window.innerHeight + window.scrollMaxY;
		} else if (document.body.scrollHeight >= document.body.offsetHeight){ // all but Explorer Mac
			xScroll = document.body.scrollWidth;
			yScroll = document.body.scrollHeight;
		} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
			xScroll = document.body.offsetWidth;
			yScroll = document.body.offsetHeight;
		}
	
		var windowWidth, windowHeight;
		if (self.innerHeight) {	// all except Explorer
			windowWidth = self.innerWidth;
			windowHeight = self.innerHeight;
		} else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
			windowWidth = document.documentElement.clientWidth;
			windowHeight = document.documentElement.clientHeight;
		} else if (document.body) { // other Explorers
			windowWidth = document.body.clientWidth;
			windowHeight = document.body.clientHeight;
		}
	
		var pageWidth,
			pageHeight;
		pageWidth = xScroll > windowWidth ? xScroll : windowWidth;
		pageHeight = yScroll > windowHeight ? yScroll : windowHeight;
		//网页被卷去的上高度  
		var scrollTop = window.pageYOffset   
	    || document.documentElement.scrollTop   
	    || document.body.scrollTop || 0;
		//网页被卷去的左宽度  
		var scrollLeft = window.pageXOffset   
		    || document.documentElement.scrollLeft   
		    || document.body.scrollLeft || 0;  
		
		return {windowWidth: windowWidth, windowHeight: windowHeight, pageWidth: pageWidth, pageHeight: pageHeight, scrollTop:scrollTop, scrollLeft:scrollLeft };
	},

	showBox : function(boxWidth, boxHeight,dstPage){
		var pageAttr = this.getPageSize(),
			boxPosLeft = pageAttr.scrollLeft + (pageAttr.windowWidth - boxWidth) / 2,
			boxPosTop = pageAttr.scrollTop + (pageAttr.windowHeight - boxHeight) / 2;
			var boxBg = document.getElementById('popup_boxBg'),
				box = document.getElementById('popup_box'),
				loginFrame = document.getElementById('login_frame');
			this.boxBg = boxBg;
			this.box = box;
			this.loginFrame = loginFrame;
			
				
		this.boxBg.style.height = pageAttr.pageHeight + 'px';
		this.boxBg.style.width = pageAttr.pageWidth + 'px';
		this.boxBg.style.display = 'block';
		this.box.style.left = (boxPosLeft) > 0 ? boxPosLeft + 'px' : '0px';
		this.box.style.top = (boxPosTop) > 0 ? boxPosTop + 'px' : '0px';
		this.loginFrame.src = dstPage;
		this.loginFrame.width = boxWidth + "px";
		this.loginFrame.height = boxHeight + "px";
		this.box.style.display = 'block';
	},

	

	resizeBox : function(boxWidth, boxHeight){
		if (this.box.style.display != 'block'){
			return;
		}
		var pageAttr = this.getPageSize(),
			boxPosLeft = (pageAttr.windowWidth - boxWidth) / 2,
			boxPosTop = (pageAttr.windowHeight - boxHeight) / 2;
				
		this.boxBg.style.height = pageAttr.pageHeight + 'px';
		this.boxBg.style.width = pageAttr.pageWidth + 'px';
		this.box.style.left = (boxPosLeft) > 0 ? boxPosLeft + 'px' : '0px';
		this.box.style.top = (boxPosTop) > 0 ? boxPosTop + 'px' : '0px';
	},
	createBox : function(){
		$("<div id='popup_box' class='popup_box'></div>")
		.append('<iframe id="login_frame" name="login_frame" src="" width="100%" frameborder="0" height="100%" scrolling="no"></iframe>')
		.appendTo($(document.body));

		$("<div id='popup_boxBg' class='popup_boxBg'></div>")
		.append('<iframe width="100%" height="100%"></iframe>')
		.appendTo($(document.body));
	},
	hideBox : function(){
		parent.$("#popup_box").hide();
		parent.$("#popup_boxBg").hide();
	},
	removeBox : function(){
		parent.$("#popup_box").remove(); 
		parent.$("#popup_boxBg").remove(); 
	},
	closeBtn : function() {
		commonUtil.hideBox();
		commonUtil.removeBox();
	},
	aa : function(url) {
		parent.locaction.reload();
	},
	/**
	 * 在需要的地方调用此方法 
	 *  @param boxWidth:目标页面宽度
	 *  @param boxHeight:目标页面高度
	 *  @param dstPage:目标页面，即要弹出的页面
	 *  @param backUrl:目标页面执行完后，回调的页面，目前没有用到
	 * 
	 * **/
	popup_page : function(boxWidth, boxHeight, dstPage, backUrl){
		commonUtil.createBox();
		commonUtil.showBox(boxWidth, boxHeight, dstPage);
		commonUtil.addEvent(window, 'resize', function(){commonUtil.resizeBox(boxWidth, boxHeight);});
	},
	popup_page_floor : function(boxWidth, boxHeight, dstPage, backUrl){
		commonUtil.createBox();
		commonUtil.showBox(boxWidth, boxHeight, dstPage);
		commonUtil.addEvent(window, 'resize', function(){commonUtil.resizeBox(boxWidth, boxHeight);});
	},
	//点击输入框后，删除错误提示
	field_focus : function(validata) {
		//为  字段加入相应事件，点击时候去掉错误提示
		$.each(validata, function(key, val) {
			var id = "#"+key;
			var error_id = "#"+key+"_error";
			
			if(val.type == 'text' || val.type == 'textarea') {
				$(id).focus( function(){
					$(error_id).remove();
				} );
			} else if ( val.type == 'select' ) {
				$(id).change( function(){
					$(error_id).remove();
				} );
			}
			
		});
	},
	//验证各个字段值是否合法
	add_shop_validata : function(validata) {
		var flag = false;
		$.each(validata, function(key, val) {
			var id = "#"+key;
			var error_id = key+"_error";
			
			//存在就不再写入信息
			if( $("#"+error_id).length > 0 ) {
				return true;
			}
			var temp_value = $(id).val();
			if(temp_value == null || temp_value == "" || temp_value == -1) {
				$(id).after("<span id=\""+error_id+"\" class=\"has-error\">"+val.error_value+"</span>");
				flag = true;
			}
		});
		return flag;
	},		
};