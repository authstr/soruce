/*
* 封装了一些用于异步加载数据的方法
*
* @authstr
* @2020年1月12日12:31:16
* */
var load={
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
                var select2_data=select2_utils.arrayToSelect2(data,"name",placeholder)
                $(sele).select2(select2_data);
            }else{
                $(sele).select2({
                    language: 'zh-CN',
                    placeholder:"没有查询到数据",
                    data:null,
                });
            }
        });
    },


}

