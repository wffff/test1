<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic Panel - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/demo/demo.css">
    <script type="text/javascript" src="jquery-easyui-1.5.2/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
    <script>
        $(function () {
            $('#t_student').datagrid({
                title: '学生列表',
                width: 1000,
                height: 400,
                url: '/rest/student/form',
                method: 'get',
                striped: true,
                fitColumns: true,
                rownumbers:true,
                loadMsg:"数据加载中,请稍后",
                singleSelect:true,
                rowStyler:function (index,record) {
                    if(record.time>1494385869000){
                    return "background:white";
                    }
                },
                frozenColumns: [[
                    {
                        field: 'id',
                        title: "编号",
                        width: 80
                    }, {
                        field: 'name',
                        title: "姓名",
                        width: 120
                    },
                ]],
                columns: [[
                    {
                        field: 'del',
                        title: "是否删除",
                        width: 40
                    }, {
                        field: 'time',
                        title: "创建时间",
                        width: 80
                    }, {
                        field: 'last',
                        title: "最后修改时间",
                        width: 120
                    }
                ]],
                pagination:true,
                pageSize:5,
                pageList:[5,10,15,20,25,30]
            });
        });

    </script>
</head>
<body>
<table id="t_student"></table>

<%--<table class="easyui-datagrid" title="Basic DataGrid" style="width:700px;height:auto"--%>
<%--data-options="singleSelect:true,collapsible:true,url:'/rest/student/form',method:'get'">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th data-options="field:'id',width:100">编号</th>--%>
<%--<th data-options="field:'name',width:100">姓名</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--</table>--%>

</body>
</html>