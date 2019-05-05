<%--
  Created by IntelliJ IDEA.
  User: chenlin
  Date: 2019.3.10
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a  id="addIndex" class="easyui-linkbutton">一键导入索引库</a>
<script type="text/javascript">
    $(function(){
        $("#addIndex").click(function () {
            $.post("/index/add", null,function(data){
                if(data.status == 200){
                    $.messager.alert('提示','新增索引成功!');
                }else {
                    $.messager.alert('提示','新增索引失败!');
                }

            });
        })
    });
</script>
</body>
</html>
