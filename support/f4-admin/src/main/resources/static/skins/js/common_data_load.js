var load={
    //以select2加载一个下拉框
    //label_id 下拉框的id
    /*
    * 以select2加载一个下拉框
    * label_id              下拉框的id
    * placeholder           要显示的提示文本
    * url                   请求的url
    * para                  请求附带的参数
    * */
    load_select2:function (label_id,placeholder,url,para) {
        var sele="#"+label_id;
        //清空下拉框
        // $(sele).empty();
        $.getJSON(url,para!=null?para:{},function(o){
            var data=o.data;
            var count=o.data.length;
            if(o.code ==0 &&data!=null&&count!=0){
                var tmp = [];//用来存储select2内容
                $(data).each(function(index, elem){
                    tmp.push({
                        text: elem.name,//下拉框内容
                        id: elem.id //下拉框id
                    });
                });
                $(sele).select2({
                    language: 'zh-CN',
                    placeholder:placeholder,
                    data:tmp,
                });
            }else{
                $(sele).select2({
                    language: 'zh-CN',
                    placeholder:"没有查询到数据",
                    data:null,
                });
            }
        });
    }
}

