<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic Panel - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/demo.css">
    <script type="text/javascript" src="jquery-easyui-1.5.2/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
    <script>
        /**
        $(function () {
            $("#mypanel").panel({
                title: "我的面板",
                width: 600,
                height: 300,
                iconCls: 'icon-edit',
                collapsible: true,
                closable: true,
                content: "我是面板的内容"
            });
        });
        */

    </script>
</head>
<body>
<%--<div id="mypanel" class="easyui-panel" title="我的panel" minimizable=true maximizable=true--%>
<%--collapsible=true closable="true"--%>
<%--iconCls="icon-add" style="width:300px;height:300px">--%>
<%--我是内容--%>
<%--</div>--%>

<%--<div id="mypanel"></div>--%>

<div id="mywin" class="easyui-window" draggable="false" resizable="false" title="我的窗口" style="width:300px;height:300px"></div>

</body>
</html>