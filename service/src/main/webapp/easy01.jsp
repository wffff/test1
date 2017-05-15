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
        $(function () {
//            $.messager.alert("标题","内容","error");
            /**
             $.messager.confirm("标题内容","是否确认",function (s) {
                if(s){
                    $.messager.alert("","确认");
                }else{
                    $.messager.alert("","取消");
                }
            })
             })
             */

            /**
             $.messager.prompt("提示内容","请输入内容",function (val) {
                $.messager.alert("",val)
            });
             */

            /**
             $.messager.progress({
           title:"我是进度条",
            msg:"文本内容",
            text:"正在加载...",
            interval:2000
        });
             */

            $.messager.show({
                title: "我是进度条",
                msg: "文本内容"
            });

        });
    </script>
</head>
<body>
<p>JSP内容</p>

</body>
</html>