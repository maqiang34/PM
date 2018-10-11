var isyzok = false,remote_ip_info = "";
if (location.href != top.location.href) { top.location.href = "../login.htm";}
//登录
function ckfsstyle(type) {
    if (type == 1) {
        clsyle("#ui", 2, '请输入用户名！');
    } else if (type == 2) {
        clsyle("#up", 2, '请输入密码！');
    }
}
function removelallsyle(em, text) {
    $(em).text(text);
    $(em).removeClass("onok");
    $(em).removeClass("onFocus");
    $(em).removeClass("onError");
    $(em).removeClass("onError1");
}
function clsyle(em, type, text) {
    removelallsyle(em, text);
    if (type == 1) {
        $(em).addClass("onok");
    } else if (type == 2) {
        $(em).addClass("onFocus");
    } else if (type == 3) {
        $(em).addClass("onError");
    } else if (type == 4) {
        $(em).addClass("onError1");
    }
}
function ckuid(type) {
    if (type == 1) {
        var name = $("#userId");
        if (name.val() == "") {
            clsyle("#ui", 3, '请输入用户名！');
            return false;
        } else {
            clsyle("#ui", 1, '');
        }
    } else if (type == 2) {
        var password = $("#upassword");
        if (password.val() == "") {
            clsyle("#up", 3, '请输入密码！');
            return false;
        } else {
            clsyle("#up", 1, '');
        }
    }
}
function login() {
    var adds = "127.0.0.1  本地登录", name = $("#userId"), password = $("#upassword");
    if (remote_ip_info != null && remote_ip_info != "") { adds = $("#keleyivisitorip").html() + " " + remote_ip_info["province"] + "省" + remote_ip_info["city"] + "市";} $("#uip").val(adds);
    if(!window.localStorage.lip){window.localStorage.lip=adds;};$("#lip").val(adds);
    if (name.val() == "") {
        name.focus();
        return false;
    }
    if (password.val() == "") {
        password.focus();
        return false;
    }
    $("#input_sik").val(new Date().getHours());
    $.ajax({
        url: '/i/admin/userlogin',
        type: 'POST',
        data:$('#sellloginform').serialize() ,
        error: function() {  clsyle("#ui", 2, '服务异常！'); },
        success: function(data) {
        	if (data.success) {
				window.sessionStorage.asikey=data.entity.token;
				window.sessionStorage.sysadmin=JSON.stringify(data.entity);
                window.location.href = "admin/main.html";
                return;
			} else {
				clsyle("#ui", 4, data.message);
			}
        }
    });

}
function getOrder(){
    var data={"orderid":"2018090521028"};
    $.ajax({
        url: '/i/order/selectOrder',
        type: 'POST',
        data: data,
        success: function(data) {
            console.log(data);
        }
    });
}
function collectData(){
    var data='{"autoPeriod":0,"collector":"","gps":{"lat":31.215206,"lng":121.392705,"time":1533714314000},"trayno":"0080B0403C000000120FA48C","isAutoReport":1,"goodList":[{"name":"草莓牛奶","barcode":"","unit":"盒","weight":"","goodIds":100032,"number":20},{"name":"香蕉牛奶","barcode":"","unit":"盒","weight":"","goodIds":100033,"number":20},{"name":"蓝莓牛奶","barcode":"","unit":"盒","weight":"","goodIds":100034,"number":20},{"name":"芒果牛奶","barcode":"","unit":"盒","weight":"","goodIds":100035,"number":20},{"name":"巧克力牛奶","barcode":"","unit":"盒","weight":"","goodIds":100036,"number":20},{"name":"纯牛奶","barcode":"","unit":"盒","weight":"","goodIds":100037,"number":20}],"shelf":"0080B0","tempture":"+64.00","time":"","voltage":3072,"orderid":"LP00104513127462WER1"}'
    $.ajax({
        url: '/i/collect/HandsetBindGoods',
        type: 'POST',
        dataType:'json',
        data: {"collectData":data},
        success: function(data) {
            console.log(data);
        }
    });
}

function testLogin(){
    var data={"name":"usertest","pwd":"usertest"}
    $.ajax({
        url: '/i/user/userlogin',
        type: 'POST',
        dataType:'json',
        data: data,
        success: function(data) {
            console.log(data);
        }
    });
}
function testShelf(){
    var data={"trayno":"0080B0403C000000120FA48A","shelf":"0080B0"}
    $.ajax({
        url: '/i/collect/HandsetBindShelf',
        type: 'POST',
        dataType:'json',
        data: data,
        success: function(data) {
            console.log(data);
        }
    });
}

$(document).on({
    keyup: function (e) {
        if (e.keyCode == '13') {
            login();
        }
    }
});
// testShelf();
// getOrder();
// collectData();
// testLogin();


