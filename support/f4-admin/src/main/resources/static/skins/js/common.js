//日期格式化
Date.prototype.format = function (format) {
    var args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var i in args) {
        var n = args[i];
        if (new RegExp("(" + i + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
    }
    return format;
};

var enter_event={
    //回车事件,不做任何处理
    event_null:function () {
        $(document).keyup(function (e) {
            if (e.keyCode == 13) {
               return false
            }
        });
    },
    //回车事件,触发指定按钮的点击
    event_button_click:function (button_id) {
        document.onkeydown = function(e){
            var ev = document.all ? window.event : e;
            if(ev.keyCode==13) {
                $("#"+button_id).click();
                return false;
            }
        }
    },
    //回车事件,触发id为btn_save的按钮的点击事件
    event_save_click:function () {
        enter_event.event_button_click("btn_save")
    },
    //回车事件,触发id为btn_search的按钮的点击事件
    event_search_click:function () {
        enter_event.event_button_click("btn_search")
    }
}

var select2_utils={
    //在没有查询到数据时,使用的select2参数
    null_data:{
        language: 'zh-CN',
        placeholder:"没有查询到数据",
        data:null,
    },
    /*
    * 以select2加载一个下拉框
    * label_id              下拉框的id
    * placeholder           要显示的提示文本
    * url                   请求的url
    * para                  请求附带的参数
    * key1                  展示返回数据的那个字段
    * key2                  额外展示返回数据的那个字段
    * key_id                id使用返回数据的那个字段
    * */
    load_select2_key_id:function (label_id,placeholder,url,para,key1,key2,key_id) {
        var sele="#"+label_id;
        $.getJSON(url,para!=null?para:{},function(o){
            var data=o.data;
            var count=o.data.length;
            if(o.code ==0 &&data!=null&&count!=0){
                var select2_data=select2_utils.arrayToSelect2_key2_id(data,key_id,key1,key2,placeholder)
                $(sele).select2(select2_data);
            }else{
                $(sele).select2(select2_utils.null_data);
            }
        });
    },

    load_select2_key:function (label_id,placeholder,url,para,key1,key2) {
        return select2_utils.load_select2_key_id(label_id,placeholder,url,para,key1,key2,"id");
    },
    load_select2_key2:function (label_id,placeholder,url,para,key2) {
        return select2_utils.load_select2_key_id(label_id,placeholder,url,para,"name",key2,"id");
    },
    load_select2:function (label_id,placeholder,url,para) {
        return select2_utils.load_select2_key_id(label_id,placeholder,url,para,"name",null,"id");
    },



    /*
       快捷的将查询的数据转换为select2可用的数组,将转换为形如:张三(123456)  李四(计算机专业) 的数据形式
       data:要转换的源数据
       key_id: 要转换为id的 属性
       key1:要转换为name前缀的属性值
       key2:要转换为name括号里信息 的属性值
       placeholder:要展示的占位符信息
    * */
    arrayToSelect2_key2_id:function (data,key_id, key1, key2, placeholder) {
        var temp = [];
        $.each(data, function (index, elem) {
            var value=elem[key1];
            if(key2!=null){
                value=value + "(" + elem[key2] + ")";
            }
            temp.push({id: elem[key_id], text: value});
        })
        return {language: 'zh-CN',data: temp, placeholder: placeholder};
    },
    //快捷的将查询的数据转换为select2可用的数组,仅适用于简单的转换
    arrayToSelect2:function(data, key, placeholder) {
        return select2_utils.arrayToSelect2_key2_id(data,"id",key,null,placeholder)
    },
    //快捷的将查询的数据转换为select2可用的数组,仅适用于简单的转换
    arrayToSelect2_key:function(data, key1,key2, placeholder) {
        return select2_utils.arrayToSelect2_key2_id(data,"id",key1,key2,placeholder)
    }

}

var common_utils={
    status_map:{
        "-1":["禁用","brown"],
        "0":["启用","forestgreen"]
    },
    transform_map:function (key,map) {
        var res=map[key];
        return res!=null?res:"";
    },
    transform_map_color:function (key,map) {
        var value=map[key];
        if(value==null){
            return "";
        }
        var html="<span style='color:"+value[1]+"'>"+value[0]+"</span>"
        return html;
    },
    status_transform:function (key) {
        return common_utils.transform_map_color(key,common_utils.status_map)
    }

}

//将ajax()、post()请求时的数组参数视为一个数组,而不是多个数组参数
jQuery.ajaxSettings.traditional = true;

//form的封装参数数组转为参数字面量
function paraArrayToLiteral(para) {
    var literal = {};
    for (var i = 0; i < para.length; i++) {
        var key=para[i].name;
        //如果当前值已经存在,说明有一个参数是数组
        if(literal[key]!=null){
            //把value改成数组类型,储存所有值
            var curr=literal[key];
            if((typeof curr)=="object"){
                curr.push(para[i].value)
                literal[key]=curr;
            }else{
                var temp=new Array()
                temp.push(curr);
                temp.push(para[i].value)
                literal[key]=temp;
            }
        }else{
            literal[key] = para[i].value
        }

    }
    return literal;
}

//将字面量封装成参数字符串
function LiteralToString(literal) {
    var index = 0;
    var res = "";
    for (var key in literal) {
        if (index == 0) {
            res = res + "?";
        } else {
            res = res + "&";
        }
        res = res + key + "=" + literal[key];
        index++;
    }
    return res;
}

//将字面量数组封装成参数字符串
function ArrayLiteralToString(array) {
    var index = 0;
    var res = "";
    for (var i = 0; i < array.length; i++) {
        var literal = array[i];
        for (var key in literal) {
            if (index == 0) {
                res = res + "?";
            } else {
                res = res + "&";
            }
            res = res + key + "=" + literal[key];
            index++;
        }
    }
    return res;
}

//将数组封装成参数字符串
function ArrayToString(paraName, array) {
    var res = "";
    for (var i = 0; i < array.length; i++) {
        if (i == 0) {
            res = res + "?";
        } else {
            res = res + "&";
        }
        res = res + paraName + "=" + array[i];
    }
    return res;
}


//获取字面量数组的第一个id项
function paraGetOneId(para) {
    if (para.length == 0) {
        return null;
    } else {
        var temp = para[0];
        return temp.id;
    }
}

//获取字面量数组的id项
function paraGetId(para) {
    var ids = [];
    if (para.length == 0) {
        return null;
    }
    for (var i = 0; i < para.length; i++) {
        ids.push(para[i].id)
    }
    return ids;
}

//将数组转换为","分隔的字符串
function asString(para) {
    var ids = "";
    if (para.length == 0) {
        return null;
    }
    for (var i = 0; i < para.length; i++) {
        if(i!=0){
            ids=ids+",";
        }
        ids=ids+(para[i])
    }
    return ids;
}

function isEmpty(str) {
    if (str == null) {
        return true;
    }
    str = str + "";
    return str.replace(/\s/g, "") == ""
};


function rodio_chick(lab) {
    $(lab).addClass("on");
}


/**通过JSON字符串向界面对象放值的方法
 * 例子
 * $("#form1").setFormValues({id:111,name:'ccc',dyh:'aaaa' ,khfs:'-1'});
 * auth sntey
 * @param {Object} $
 */
(function ($) {
    $.fn.setFormValues = function (json) {
        var form1 = $(this);
        $.each(json, function (id, value) {
            /**该功能是为jquery版的 combox提供**/
            var this_obj = form1.find("[name='" + id + "']");
            var this_obj_type = this_obj.attr("type");
            var  tagName=null;
            if(this_obj.length!=0){
                tagName =this_obj[0].tagName;
            }
            if (undefined == this_obj_type || this_obj_type == "" || "text" == this_obj_type) {
                this_obj.val(value);
            } else if (this_obj_type == "radio" || "checkbox" == this_obj_type) {
                if (value instanceof Array) {
                    this_obj.each(function () {
                        for (i = 0; i < value.length; i++) {
                            if ($(this).val() == value[i]) {
                                $(this).prop({'checked': true});
                            }
                        }
                    });
                } else {
                    this_obj.each(function () {
                        if ($(this).val() == value) {
                            $(this).prop({'checked': true});
                        } else {
                            $(this).prop({'checked': false});
                        }
                    });
                }
            } else {
                this_obj.val(value);
            }
            //进行select的赋值
            if( tagName == "SELECT"){
                this_obj.val(value).trigger('change');;
            }
        });
    };
    $.fn.clearFormValues = function () {
        var form1 = $(this);
        form1.find("input").each(function () {
            var this_obj = $(this);
            var this_obj_type = $(this).attr("type");
            if (undefined == this_obj_type || this_obj_type == "" || "text" == this_obj_type || "hidden" == this_obj_type) {
                this_obj.val("");
            } else if (this_obj_type == "radio" || "checkbox" == this_obj_type) {
                this_obj.each(function () {
                    this_obj.prop({'checked': false});
                });
            } else {
                this_obj.val("");
            }
        });
        form1.find("select").each(function () {
            $(this).val("");
        });
        form1.find("textarea").each(function () {
            $(this).val("");
        });
    };
    $.fn.validate = function () {
        var form1 = $(this);
        b = true;
        $.each(form1.find(".must"), function (i, v) {
            var cv = $(this).val();
            if (cv.replace(/\s/g, "") == "") {
                $(this).focus();
                b = false;
                return b;
            }
        });

        return b;
    };
    /****
     * 判断对象的值是否为空.
     * @memberOf {TypeName}
     * @return {TypeName}
     */
    $.fn.isEmpty = function () {
        var cv = $(this).val();
        return cv.replace(/\s/g, "") == ""
    };
    //单选框的点击与取消点击
    //绑定点击事件

})(jQuery);



//以json格式发送post请求.用于在后台有@requestBody注解时
function postJSON(url,para,success){
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(para),
        dataType: "json",
        success:success
    });
};


$(function () {
    // //页面加载完了之后移除loadding动画
    // //$(".J_mainContent", window.parent.document).removeClass("page-loading");
    // //form表单的一些样式优化
    // $("body").on("click", ".J_checkBox input", function (event) {
    //     event.stopPropagation();
    //     var _this = $(this).parents(".J_checkBox");
    //     if (_this.hasClass("disabled") || _this.hasClass("check-disabled")) {
    //         return;
    //     }
    //     if (_this.hasClass("on")) {
    //         _this.removeClass("on");
    //         //$(this).removeAttr("checked");
    //     } else {
    //         _this.addClass("on");
    //         //$(this).attr("checked","checked");
    //     }
    //     /*var bccArray=[];
    //      $("input[name='bcc']:checked").each(function(){
    //           bccArray.push($(this).val());
    //     });
    //     console.log(bccArray);*/
    // });
    // $("body").on("click", ".J_radioBox label input", function () {
    //     var _this = $(this).parents("label");
    //     if (_this.hasClass("disabled") || _this.hasClass("check-disabled")) {
    //         return;
    //     }
    //     if (_this.hasClass("on")) return;
    //     _this.parents(".J_radioBox").find("label").removeClass("on");
    //     _this.addClass("on");
    //     //console.log(_this.parents(".J_radioBox").find("input"));
    // });
    //
    // //产品下载里面修改radioBox
    // $("body").on("click", ".J_radioBox2 label.radio-inline input", function () {
    //     var _this = $(this).parents("label.radio-inline");
    //     if (_this.hasClass("disabled") || _this.hasClass("check-disabled")) {
    //         return;
    //     }
    //     if (_this.hasClass("on")) {
    //         return;
    //     } else {
    //         _this.parents(".J_radioBox2").find("label").removeClass("on");
    //         _this.parents(".J_radioBox2").find("label input").prop("checked", false);
    //         _this.parents(".J_radioBox1").find("label").addClass("on");
    //         _this.parents(".J_radioBox1").find("label input").prop("checked", true);
    //         // console.log(_this.parents(".J_radioBox2").find("input"));
    //     }
    //
    // });
    // //产品下载里面修改checkBox
    // $("body").on("click", ".J_checkBox1 input", function (event) {
    //     event.stopPropagation();
    //     var _this = $(this).parents(".J_checkBox1");
    //     if (_this.hasClass("disabled") || _this.hasClass("check-disabled")) {
    //         return;
    //     }
    //     if (_this.hasClass("on")) {
    //         _this.removeClass("on");
    //         var flag = false;
    //         _this.siblings(".J_checkBox1").each(function (i, item) {
    //             if ($(item).find("input").is(":checked")) {
    //                 flag = true;
    //                 return;
    //             }
    //         });
    //         if (flag == false) {
    //             _this.parents(".J_radioBox1").find("label.radio-inline").removeClass("on");
    //             _this.parents(".J_radioBox1").find("label.radio-inline input").prop("checked", false);
    //         }
    //     } else {
    //         _this.addClass("on");
    //         _this.parents(".J_radioBox1").siblings(".J_radioBox1").find("label").removeClass("on");
    //         _this.parents(".J_radioBox1").siblings(".J_radioBox1").find("label input").prop("checked", false);
    //         _this.parents(".J_radioBox1").find("label.radio-inline").addClass("on");
    //         _this.parents(".J_radioBox1").find("label.radio-inline input").prop("checked", true);
    //
    //     }
    //     // console.log(_this.parents(".J_radioBox2").find("input"));
    // });
    // //上传文件选择
    // $("body").on("change", ".J_fileButton", function () {
    //     var _this = $(this);
    //     _this.parents(".J_FileBox").find(".J_fileInput").val(_this.val());
    //
    // });
    // //提示信息的展开与关闭
    // $("body").on("click", ".J_arrowAction", function () {
    //     if ($(this).find("i").hasClass("gi-up")) {
    //         if ($(this).parents(".g-warning-box")) {
    //             //$(this).parents(".g-warning-box").find(".g-title").css("border-color","#faebcc");
    //             $(this).parents(".g-warning-box").find(".J_tipBox").slideDown("slow");
    //             $(this).find("i").removeClass("gi-up").addClass("gi-down");
    //         }
    //         if ($(this).parents(".g-warning-dialog")) {
    //             if ($(this).parents(".g-warning-dialog").find(".J_tipBox")) {
    //                 $(this).parents(".g-warning-dialog").find(".J_tipBox").slideDown("slow");
    //                 //$(this).parents(".g-warning-dialog").find(".aui_title").css("border-color","#faebcc");
    //                 $(this).find("i").removeClass("gi-up").addClass("gi-down");
    //             }
    //         }
    //         return;
    //     }
    //     if ($(this).find("i").hasClass("gi-down")) {
    //         if ($(this).parents(".g-warning-box")) {
    //             // $(this).parents(".g-warning-box").find(".g-title").css("border-color","#ececec");
    //             $(this).parents(".g-warning-box").find(".J_tipBox").slideUp("slow");
    //             $(this).find("i").removeClass("gi-down").addClass("gi-up");
    //         }
    //         if ($(this).parents(".g-warning-dialog")) {
    //             if ($(this).parents(".g-warning-dialog").find(".J_tipBox")) {
    //                 $(this).parents(".g-warning-dialog").find(".J_tipBox").slideUp("slow");
    //                 //  $(this).parents(".g-warning-dialog").find(".aui_title").css("border-color","#ececec");
    //                 $(this).find("i").removeClass("gi-down").addClass("gi-up");
    //             }
    //         }
    //         return;
    //     }
    // });
    // //更多筛选条件的展开与关闭
    // $("body").on("click", ".J_downBtn", function () {
    //     $(this).hide();
    //     $(this).parents("form").find(".J_upBtn").show();
    //     $(this).parents("form").find(".J_upBtn").removeClass("hide");
    //     $(this).parents("form").find(".J_moreFilter").slideDown("slow");
    // });
    // $("body").on("click", ".J_upBtn", function () {
    //     $(this).hide();
    //     $(this).parents("form").find(".J_downBtn").show();
    //     $(this).parents("form").find(".J_moreFilter").slideUp("slow");
    // });
    // //点击iframe页面内容关闭左侧菜单
    // $("body").on("click", function () {
    //     $(".g-nav", window.parent.document).find(".nav-second-level").hide();
    //     $(".g-nav", window.parent.document).find("li").removeClass("active");
    //     //点击iframe页面内容关闭tab选项卡的右侧菜单
    //     $(".J_tabMenu", window.parent.document).hide();
    //     //点击iframe页面关闭无结果提示
    //     $(".g-search-nav", window.parent.document).hide();
    // });
    // //竖直列表点击选中事件
    // $("body").on("click", ".J_navStacked li", function () {
    //     if ($(this).hasClass("active")) return;
    //     $(this).addClass("active");
    //     $(this).siblings().removeClass('active');
    // });
    // //右侧页面高度
    // if ($(".J_pageContainer").length > 0) {
    //     $(".J_pageContainer").height($(window).height() - 20);
    //     $(".J_pageContainer").niceScroll({});
    //     $(window).resize(function () {
    //         $(".J_pageContainer").height($(window).height() - 20);
    //         $(".J_pageContainer").niceScroll({}).resize();
    //     });
    // }
});

