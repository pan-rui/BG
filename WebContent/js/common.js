
var CN_address=[
    {name:"湖北",child:[{name:"天门",child:[{name:"渔薪"},{name:"拖市"},{name:"杨场"}]},
                        {name:"仙桃",child:[{name:"第1个"},{name:"第二个"},{name:"第三个"}]}]},
    {name:"广东",child:[{name:"深圳",child:[{name:"南山",child:[{name:"科技园"},
                        {name:"南头"}]},{name:"福田",child:[{name:"新洲"},{name:"上梅林"},{name:"石厦"}]}]},
                        {name:"东莞",child:[{name:"石碣"},{name:"东坑"},{name:"厚街"}]}]
    }];
var US_address=[
    {name:"hubei",child:[{name:"tianmen",child:[{name:"yuxin"},{name:"tuoshi"},{name:"yangchang"}]},
        {name:"xiantao",child:[{name:"One"},{name:"two"},{name:"three"}]}]},
    {name:"guangdong",child:[{name:"shenzhen",child:[{name:"nanshan",child:[{name:"kejiyuan"},
        {name:"nantou"}]},{name:"futian",child:[{name:"xinzhou"},{name:"shangmeilin"},{name:"shixia"}]}]},
        {name:"dongguan",child:[{name:"shijie"},{name:"dongkeng"},{name:"houjie"}]}]
    }];
var states=[{name:"CN",child:CN_address,currency:"HKD"},{name:"US",child:US_address,currency:"USD"}];
var address=CN_address;
function loadA(el) {
    $(el).empty();
    $.each(address,function(k,v){
    $(el).append("<option value='"+v["name"]+"'>"+v["name"]+"</option>")
    });
    loadB($("select[name='city']"));
}
function loadB(el){
    $(el).empty();
    var citys;
    $.each(address,function(k,v){
        if(v.name==$("select[name='province']").val()) {
            citys = v.child;
            return false;
        }
    });
    $.each(citys,function(k,v){
    $(el).append("<option value='"+v["name"]+"'>"+v["name"]+"</option>");
    });
    loadC($("select[name='area']"))
}
function loadC(el){
    $(el).empty();
    var areas;
    $.each(address,function(k,v){
        if(v.name==$("select[name='province']").val()) {
            $.each(v.child,function(k,v){
                if(v.name==$("select[name='city']").val()){
                areas= v.child;
                    return false;
                }
            })
            return false;
        }
    });
    $.each(areas,function(k,v){
        $(el).append("<option value='"+v["name"]+"'>"+v["name"]+"</option>");
    })
}
function loadStates(){
    $.each(states,function(k,v){
        if(v.name=="CN")
        $("#state").append("<option value='"+ v.name+"' selected>"+ v.name+"</option>")
        else
        $("#state").append("<option value='"+ v.name+"'>"+ v.name+"</option>")
    });
    changeCurrency();
    loadA($("select[name='province']"))
}
function changeStates(){
    $.each(states,function(k,v){
        if(v.name==$("#state").val()){
            address= v.child;
            return false;
        }
    });
    changeCurrency();
    loadA($("select[name='province']"));
}

function changeCurrency(){
    $.each(states,function(k,v){
        if(v.name==$("#state").val()){
            $("#currencyCode").html("");
            $("#currencyCode").append("<option value='"+ v.currency+"' selected>"+ v.currency+"</option>")
            return false;
        }
    });
}

/*cookie*/
function setCookie(name, value)
{
    var Days = 30;//默认30天有效期
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ encodeURIComponent(value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return decodeURIComponent(arr[2]);
    else
        return null;
}

function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

//验证码更换
function changeImg(el){
    el.src="/RandImg.jpg?time="+new Date().getTime();
}

