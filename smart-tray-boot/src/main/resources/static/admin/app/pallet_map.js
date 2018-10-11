var map = new BMap.Map("allmap");
map.centerAndZoom(new BMap.Point(105.000, 38.000), 5);
map.enableScrollWheelZoom();
var mapdata;
var j=0;
function initMap(data){
    // 百度地图API功能
    var myIcon2 = new BMap.Icon("tb1_0.png", new BMap.Size(30, 40));
            var point = new BMap.Point(data.gps.lng, data.gps.lat,{icon:myIcon2});
            var marker = new BMap.Marker(point);
            var contentStr="<p>载具编号:</p>"+
                "<p>"+data.pno +"</p>"+
                "<p>环境温度:"+data.tempture +"</p>"+
                "<p>电池电压:"+data.voltage +"</p>"+
                "<p>载具位置:"+data.address +"</p>"+
                "<p>拥有商品数:"+data.goodNumber +"<a ref='#' onclick='selectPallet("+data.palletId+")' style='cursor: pointer;margin-left:80px' >商品详情</a></p>";
            var content = contentStr;
            var choseData={};
            choseData.content=content;
            addClickHandler(choseData, marker); //添加点击事件
            map.addOverlay(marker);
        }
// //添加聚合效果。
// var markerClusterer = new BMapLib.MarkerClusterer(map, {marker-s:markers});
function changeStatus(type){
    if(type==0){
        return "托盘";
    }else{
        return "保温箱";
    }
}
var opts = {
    width : 280, // 信息窗口宽度
    height: 300, // 信息窗口高度
    title : "详细信息" , // 信息窗口标题
    enableMessage:true//设置允许信息窗发送短息
};



function addClickHandler(content,marker){
    marker.addEventListener("click",function(e){
        openInfo(content.content,e)}
    );
}

function selectPallet(palletId){
    var param={"palletId":palletId};
    var col = [[
        {field: 'ck', checkbox: true},
        {field: 'id', title: '序号',width:30, sortable: true},
        {field: 'goodsId', title: '货品编号',width:30, sortable: true,formatter:changeStatus},
        {field: 'name', title:'货品名称',width:30, align: 'center',sortable: true},
        {field: 'spec', title: '货品单位',width:30,align: 'center', sortable: true},
        {field: 'number', title: '货品数量',width:30,align: 'center', sortable: true},
        {field: 'addTime', title: '添加时间',width:30,align: 'center', sortable: true}
    ]];
    initalertTable("商品管理","icon-cold", "POST", "../../i/pallet/getGoodsByPalletId", param,"#div_filteri",null, col,true, null);
    $('#pallet_manage').dialog('open');
}
function openInfo(content,e){
    var p = e.target;
    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
    var infoWindow = new BMap.InfoWindow(content,opts); // 创建信息窗口对象
    map.openInfoWindow(infoWindow,point); //开启信息窗口
}
function initMapData() {
    $.ajax({
        url: "/i/pallet/getPalletCount",
        type: "post",
        success: function (data) {
            $("#allPallet").html(data.allPallet);
            $("#usePallet").html(data.usePallet);
            $("#allCover").html(data.allCover);
            $("#useCover").html(data.useCover);
        }
    })
    $.ajax({
        url: "/i/pallet/getMapData",
        type: "post",
        success: function (data) {
            mapdata = data;
            for (var i = 0; i < mapdata.length; i++) {
                $.ajax({
                    url: "http://api.map.baidu.com/geocoder/v2/?ak=PljdkOFpwmjpU00lFQhakTbjilGaH0or&location=" + mapdata[i].gps.lat + "," + mapdata[i].gps.lng + "&output=json&pois=0",
                    type: "get",
                    dataType: 'jsonp',
                    crossDomain: true,
                    success: function (localData) {
                        mapdata[j].address = localData.result.formatted_address;
                        console.log(localData);
                        initMap(mapdata[j]);
                        j++;
                    }
                })
            }
        }
    })
}
initMapData();