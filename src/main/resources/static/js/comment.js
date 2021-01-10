var commentId = 0;

var randomNum = Math.floor((Math.random() * 26));
$('.vpanel').prepend('<div class="vwrap"> <p class="cancel-reply text-right" style="display:none;" title="取消回复"></p> <div class="vheader item3"> <input id="nick" name="nick" placeholder="昵称" class="vnick vinput" type="text"> <input id="mail" name="mail" placeholder="邮箱(必填)" class="vmail vinput" type="email"> <input id="link" name="link" placeholder="网址(https://)" class="vlink vinput" type="text"> </div> <div class="vedit"> <textarea id="veditor" class="veditor vinput" placeholder="建议使用QQ邮箱，评论头像采用QQ头像"></textarea> <div class="vrow"> <div class="vcol vcol-40 vctrl text-right emoji"> <span title="表情" class="iconfont icon-xiaolian"></span> <span title="预览" class="iconfont icon-yulan"></span> </div> </div> </div> <div class="vrow"> <div class="vcol vcol-30"> <a alt="Markdown is supported" href="https://guides.github.com/features/mastering-markdown/" class="vicon" target="_blank"> </a> </div> <div class="vcol vcol-70 text-right"> <button type="button" title="Cmd|Ctrl+Enter" class="vsubmit vbtn">提交</button> </div> </div> <div class="vcol vcol-60 status-bar"></div><div class="vemojis" style="display:none;"></div> <div class="vinput vpreview" style="display:none;"></div> <div style="display:none;" class="vmark"></div> </div>');
$(".veditor.vinput").css('background','url(https://cdn.jsdelivr.net/gh/lete114/CDN/emoji/Sitich/Sitich'+(++randomNum)+'.gif) 100% 100% no-repeat');
$('.vmeta .vat').click(function () {
    $('.vwrap').remove();
    commentId = $(this).attr("value");
    $($(this).parent(".vmeta").siblings(".vreply-wrapper")).prepend('<div class="vwrap"> <p class="cancel-reply text-right" style="display:block;" title="取消回复"><i class="iconfont icon-error"></i></p> <div class="vheader item3"> <input id="nick" name="nick" placeholder="昵称" class="vnick vinput" type="text"> <input id="mail" name="mail" placeholder="邮箱(必填)" class="vmail vinput" type="email"> <input id="link" name="link" placeholder="网址(https://)" class="vlink vinput" type="text"> </div> <div class="vedit"> <textarea id="veditor" class="veditor vinput" placeholder="建议使用QQ邮箱，评论头像采用QQ头像"></textarea> <div class="vrow"><div class="vcol vcol-40 vctrl text-right emoji"> <span title="表情" class="iconfont icon-xiaolian"></span> <span title="预览" class="iconfont icon-yulan"></span> </div> </div> </div> <div class="vrow"> <div class="vcol vcol-30"> <a alt="Markdown is supported" href="https://guides.github.com/features/mastering-markdown/" class="vicon" target="_blank"> </a> </div> <div class="vcol vcol-70 text-right"> <button type="button" title="Cmd|Ctrl+Enter" class="vsubmit vbtn">提交</button> </div> </div> <div class="vcol vcol-60 status-bar"></div><div class="vemojis" style="display:none;"></div> <div class="vinput vpreview" style="display:none;"></div> <div style="display:none;" class="vmark"></div> </div>');
    $(".veditor.vinput").css('background','url(https://cdn.jsdelivr.net/gh/lete114/CDN/emoji/Sitich/Sitich'+randomNum+'.gif) 100% 100% no-repeat')
});
$(document).on('click', '.cancel-reply.text-right i', function () {
    commentId = 0;
    $('.vreply-wrapper .vwrap').remove();
    $('.vpanel').prepend('<div class="vwrap"> <p class="cancel-reply text-right" style="display:block;" title="取消回复"></p> <div class="vheader item3"> <input id="nick" name="nick" placeholder="昵称" class="vnick vinput" type="text"> <input id="mail" name="mail" placeholder="邮箱(必填)" class="vmail vinput" type="email"> <input id="link" name="link" placeholder="网址(https://)" class="vlink vinput" type="text"> </div> <div class="vedit"> <textarea id="veditor" class="veditor vinput" placeholder="建议使用QQ邮箱，评论头像采用QQ头像"></textarea> <div class="vrow"> <div class="vcol vcol-40 vctrl text-right emoji"> <span title="表情" class="iconfont icon-xiaolian"></span> <span title="预览" class="iconfont icon-yulan"></span> </div> </div> </div> <div class="vrow"> <div class="vcol vcol-30"> <a alt="Markdown is supported" href="https://guides.github.com/features/mastering-markdown/" class="vicon" target="_blank"> </a> </div> <div class="vcol vcol-70 text-right"> <button type="button" title="Cmd|Ctrl+Enter" class="vsubmit vbtn">提交</button> </div> </div> <div class="vcol vcol-60 status-bar"></div><div class="vemojis" style="display:none;"></div> <div class="vinput vpreview" style="display:none;"></div> <div style="display:none;" class="vmark"></div> </div>');
    $(".veditor.vinput").css('background','url(https://cdn.jsdelivr.net/gh/lete114/CDN/emoji/Sitich/Sitich'+randomNum+'.gif) 100% 100% no-repeat')
});

// 添加评论表情包
function emojiImg(){
    var url = 'https://cdn.jsdelivr.net/gh/lete114/CDN@latest/emoji';
    var img = [];
    var alt = [];
    for (var i = 1; i <= 54; i++) {
        img.push(url+"/tieba/tieba-"+i+".png")
        alt.push("tieba-"+i)
    }
    for (var i = 1; i <= 101; i++) {
        img.push(url+"/qq/qq-"+i+".gif")
        alt.push("qq-"+i)
    }
    for (var i = 1; i <= 116; i++) {
        img.push(url+"/aru/aru-"+i+".gif")
        alt.push("aru-"+i)
    }
    for (let i = 0; i < img.length ; i++) {
        $(".vemojis").append('<i title="'+alt[i]+'"><img alt="'+alt[i]+'" class="vemoji" src="'+img[i]+'"></i>');
    }
}

// 评论表情包显示与隐藏
$(document).on('click', '.iconfont.icon-xiaolian', function () {
    if ($(".vemojis").css("display")=="none"){
        $(".vemojis").css("display","block")
        $(".iconfont.icon-xiaolian").addClass("active")
        $(".vinput.vpreview").css("display","none")
        $(".iconfont.icon-yulan").removeClass("active")
        $(".vcol.vcol-60.status-bar").css("bottom","202px");
        emojiImg();
    }else{
        $(".vemojis").css("display","none")
        $(".iconfont.icon-xiaolian").removeClass("active")
        $(".vcol.vcol-60.status-bar").css("bottom","58px");
        $('.vemojis i').remove();
    }
});
// 评论预览显示与隐藏
$(document).on('click', '.iconfont.icon-yulan', function () {
    if ($(".vinput.vpreview").css("display")=="none"){
        $(".vinput.vpreview p").remove();
        $(".vinput.vpreview").css("display","block")
        $(".iconfont.icon-yulan").addClass("active")
        $(".vemojis").css("display","none")
        $(".iconfont.icon-xiaolian").removeClass("active")
        $(".vcol.vcol-60.status-bar").css("bottom","127px");
        var converter = new showdown.Converter();
        $('.vinput.vpreview').append(converter.makeHtml($("#veditor").val()));
        // 给转html后的img添加class
        $(".vinput.vpreview img").addClass("vemoji")
    }else{
        $(".vinput.vpreview").css("display","none")
        $(".iconfont.icon-yulan").removeClass("active")
    }
});
// 键盘弹起自动响应
$('#veditor').keyup(function(){
    if ($(".vinput.vpreview").css("display")=="block"){
        $(".vinput.vpreview p").remove();
        var converter = new showdown.Converter();
        $('.vinput.vpreview').append(converter.makeHtml($("#veditor").val()));
        // 给转html后的img添加class
        $(".vinput.vpreview img").addClass("vemoji")
    }
})


// 获取评论图片
$(document).on('click', '.vemojis .vemoji', function () {
    var src = $(this).attr("src");
    var alt = $(this).attr("alt");
    $("#veditor").val($("#veditor").val()+'!['+alt+']('+src+')');
});


var isEmai = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
var isUrl = /^([hH][tT]{2}[pP]:\/\/*|[hH][tT]{2}[pP][sS]:\/\/*|[fF][tT][pP]:\/\/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\/])+(\?{0,1}(([A-Za-z0-9-~]+\={0,1})([A-Za-z0-9-~]*)\&{0,1})*)$/;
// 评论
$(document).on('click', '.vsubmit.vbtn', function () {
    var blogId = $('#blogId').val();
    var nick = $('#nick').val();
    var mail = $('#mail').val();
    var link = $('#link').val();
    var content = $('#veditor').val();
    // 防止Xss
    content = filterXSS(content);

    var error = ".vcol.vcol-60.status-bar";

    if (nick == "") {
        $(error).text("请输入昵称");
        $('#nick').focus();
        return false;
    } else if (nick.length < 2) {
        $(error).text("昵称过短");
        $('#nick').focus();
        return false;
    } else if (nick.length >= 8) {
        $(error).text("昵称过长");
        $('#nick').focus();
        return false;
    } else if (mail == "") {
        $(error).text("请输入邮箱");
        $('#mail').focus();
        return false;
    } else if (link == "") {
        $(error).text("请输入你的网站链接");
        $('#link').focus();
        return false;
    } else if (content == "") {
        $(error).text("请输入内容");
        $('#veditor').focus();
        return false;
    } else if (content.length>1000) {
        $(error).text("输入的内容超出允许范围");
        $('#veditor').focus();
        return false;
    } else if (!isEmai.test(mail)) {
        $(error).text("邮箱不规范，请重新输入");
        $('#mail').focus();
        return false;
    } else if (!isUrl.test(link)) {
        $(error).text("网址不规范，请重新输入");
        $('#link').focus();
        return false;
    } else {
        $(error).text("");
        swal({
            text: "请输入验证码",
        }).then((result)=>{
            var data = {
                "verifyCode": $('.verifyCode').val(),
                "blogId": blogId,
                "commentId": commentId,
                "nick": nick,
                "email": mail,
                "link": link,
                "commentUrl": window.location.href,
                "content": content
            }
            NProgress.start();
            if (result){
                $.ajax({
                    type: "POST",
                    url: "/reply",
                    data: data,
                    success: function (result) {
                        if (result == "邮箱不规范，请重新输入") {
                            $(error).text(result);
                            $('#mail').focus();
                            return false;
                        }
                        if (result == "网址不规范，请重新输入") {
                            $(error).text(result);
                            $('#link').focus();
                            return false;
                        }
                        if (result == "true") {
                            swal("评论提交成功请等待博主审核", {
                                icon: "success",
                            });
                            $("#veditor").val("")
                            NProgress.done();
                        }else if(result == "验证码错误"){
                            swal(result, {
                                icon: "error",
                            });
                        }else{
                            swal("评论失败", {
                                icon: "error",
                            });
                        }
                    }
                })
            }
        });
        // 清空弹窗文章 追加验证码和输入框 验证码刷新 光标锁定输入框
        $(".swal-text").text("");
        $(".swal-text").append('<img alt="单击图片刷新！" class="pointer" src="/getCaptchaImg" onclick="this.src=\'/getCaptchaImg?d=\'+new Date()*1"><br><input class="verifyCode" placeholder="请输入验证码" type="text">');
        $(".pointer").attr('src','/getCaptchaImg?d='+new Date()*1);
        $('.verifyCode').focus();

     }
})

// 获取qq头像
$(document).ready(function(){
    $('#mail').keyup(function(){
        $.post({
            url: "/obtainQQInfo",
            data: {mail:""+$("#mail").val()},
            success:function (result) {
                var data = JSON.parse(JSON.stringify(result))
                $("#nick").val(data.nick)
            }
        })
    })
})
// ctrl+enter评论提交 enter验证码提交
$(document).keypress(function(e){
    if(e.ctrlKey && e.which == 13 || e.which == 10) {
        $(".vsubmit.vbtn").click();
    } else if (e.which==13) {
        $(".swal-button").click();
    }
})

// 给评论去的所有img添加class
$(".vcards .vcontent img").addClass("vemoji")


