$(function () {
    //修改站点信息
    $('#updateWebsiteButton').click(function () {
        $("#updateWebsiteButton").attr("disabled", true);
        //ajax提交数据
        var params = $("#websiteForm").serialize();
        $.ajax({
            type: "POST",
            url: "/admin/modifywebsite",
            data: params,
            success: function (result) {
                if (result=="success") {
                    swal("保存成功", {
                        icon: "success",
                    });
                    $("#updateWebsiteButton").prop("disabled",false);
                }
                else {
                    swal(result.message, {
                        icon: "error",
                    });
                    $("#updateWebsiteButton").prop("disabled",false);
                }
                ;
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
                $("#updateWebsiteButton").prop("disabled",false);
            }
        });
    });
    //个人信息
    $('#updateUserInfoButton').click(function () {
        $("#updateUserInfoButton").attr("disabled", true);
        var params = $("#userInfoForm").serialize();
        $.ajax({
            type: "POST",
            url: "/admin/modifyperson",
            data: params,
            success: function (result) {
                if (result=="success") {
                    swal("保存成功", {
                        icon: "success",
                    });
                    $("#updateUserInfoButton").prop("disabled",false);
                }
                else {
                    swal(result.message, {
                        icon: "error",
                    });
                    $("#updateUserInfoButton").prop("disabled",false);
                };

            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
                $("#updateUserInfoButton").prop("disabled",false);
            }
        });
    });
})
