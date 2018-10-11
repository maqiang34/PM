/*过滤参数*/
var queryParams = {audit: null, keyword: null};
var position;

/*加载操作按钮*/
function cellStyler(value, row) {
    return '<button class="btns" onclick="deleletPallet('+row.palletId+')">删除</button>';
}
/*初始化表格*/
function init_table() {
    var col = [[
        {field: 'ck', checkbox: true},
        {field: 'palletId', title: '序号',width:30,align: 'center', sortable: true},
        {field: 'id', title: '载具编号',width:30, sortable: true},
        {field: 'type', title: '载具类型',width:30, sortable: true,formatter:changeStatus},
        {field: 'tempture', title: '环境温度',width:30, align: 'center',sortable: true},
        {field: 'voltage', title: '电池电压(V)',width:30,align: 'center', sortable: true},
        {field: 'isAutoReport', title: '自动上报',width:30,align: 'center', sortable: true,formatter:isAutoReport},
        {field: 'autoPeriod', title: '上报周期(分钟)',width:30,align: 'center', sortable: true},
        {field: 'time', title: '上传时间', width: 30, align: 'center', sortable: true},
        {field: 'change', title: '操作', width: 30, align: 'center', sortable: true,formatter: cellStyler}
    ]];
    initTable("托盘管理","icon-cold", "POST", "/i/pallet/getpalletList", queryParams,"#div_filteri",null, col,true, onDblClickRow);


}
function isAutoReport(isAutoReport){
    if(isAutoReport==1){
        return "是";
    }else{
        return "否";
    }
}
function changeStatus(type){
    if(type==0){
        return "托盘";
    }else{
        return "保温箱";
    }
}
function deleletPallet(id){
    var data={"palletId":id};
    $.ajax({
        url: '/i/collect/deletePalletById',
        type: 'post',
        dataType:'json',
        data:data,
        success: function (data){
            $('#objTable').datagrid("reload");
        }
    })
}
//双击编辑用户信息
function onDblClickRow(index, field) {
    var chosePallet=objTable.datagrid("getSelected");
    var param={"palletId":chosePallet.palletId};
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
    $("#goodManage").window("open");
}
function onClickRow(rowIndex){
    $(this).datagrid('clearSelections');
    $(this).datagrid("checkRow",rowIndex);
}
$().ready(function () {
    init_table();
});//初始化数据