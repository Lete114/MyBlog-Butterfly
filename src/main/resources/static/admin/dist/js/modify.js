$(function () {
    //修改个人信息
    $('#updateUserNameButton').click(function () {
        $("#updateUserNameButton").attr("disabled",true);
        var name = $('#name').val();
        var author = $('#author').val();
        if (validUserNameForUpdate(name, author)) {
            //ajax提交数据
            var params = $("#userNameForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/modifybasic",
                data: params,
                success: function (result) {
                    if (result=="success") {
                        swal("保存成功", {
                            icon: "success",
                        });
                        $("#updateUserNameButton").prop("disabled",false);
                    }
                    else {
                        swal(result.message, {
                            icon: "error",
                        });
                        $("#updateUserNameButton").prop("disabled",false);
                    };
                }
            });
        } else {
            $("#updateUserNameButton").prop("disabled",false);
        }
    });
    //修改密码
    $('#updatePasswordButton').click(function () {
        $("#updatePasswordButton").attr("disabled",true);
        var originalPassword = $('#originalPassword').val();
        var newPassword = $('#newPassword').val();
        if (validPasswordForUpdate(originalPassword, newPassword)) {
            var params = $("#userPasswordForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/modifypassword",
                data: params,
                success: function (result) {
                    if (result=="success") {
                        swal("保存成功", {
                            icon: "success",
                        });
                        window.location.href="/admin/login";
                    }
                    else {
                        swal("原密码错误", {
                            icon: "error",
                        });
                        $("#updatePasswordButton").attr("disabled",false);
                    };
                }
            });
        } else {
            $("#updatePasswordButton").attr("disabled",false);
        }
    });
})

/**
 * 名称验证
 */
function validUserNameForUpdate(name, author) {
    if (isNull(name) || name.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入登陆名称！");
        return false;
    }
    if (isNull(author) || author.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("昵称不能为空！");
        return false;
    }
    if (!validUserName(name)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的登录名！");
        return false;
    }
    if (!validCN_ENString2_18(author)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的昵称！");
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入原密码！");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("新密码不能为空！");
        return false;
    }
    if (!validPassword(newPassword)) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入符合规范的密码！");
        return false;
    }
    return true;
}
