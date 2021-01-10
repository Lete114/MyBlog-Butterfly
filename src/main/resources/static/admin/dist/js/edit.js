var blogEditor;
// Tags Input
$('#blogTags').tagsInput({
    width: '100%',
    height: '38px',
    htmlDecode : "style,script,iframe|on*",
    emoji : true,
    defaultText: '文章标签'
});

//Initialize Select2 Elements
$('.select2').select2()

$(function () {
    blogEditor = editormd("blog-editormd", {
        width: "100%",
        height: 640,
        htmlDecode: "style,script,iframe", //可以过滤标签解码
        toolbarAutoFixed: false,
        syncScrolling: "single",
        path: "/admin/plugins/editormd/lib/",
        toolbarModes: 'full',
    });
});

$('#confirmButton').click(function () {
    $("#blogCoverImage").attr("src", $("#blogCover").val());
    var blogTitle = $('#blogTitle').val();
    var blogSubUrl = $('#blogSubUrl').val();
    var blogCategoryId = $('#blogCategoryId').val();
    var blogTags = $('#blogTags').val();
    var blogContent = blogEditor.getMarkdown();
    if (isNull(blogTitle)) {
        swal("请输入文章标题", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTitle, 150)) {
        swal("标题过长", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogSubUrl, 150)) {
        swal("路径过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogCategoryId)) {
        swal("请选择文章分类", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogTags)) {
        swal("请输入文章标签", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTags, 150)) {
        swal("标签过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogContent)) {
        swal("请输入文章内容", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTags, 100000)) {
        swal("文章内容过长", {
            icon: "error",
        });
        return;
    }
    $('#articleModal').modal('show');
});

$('#saveButton').click(function () {
    var blogId = $('#blogId').val();
    var blogTitle = $('#blogTitle').val();
    var blogSubUrl = $('#blogSubUrl').val();
    var blogCategoryId = $('#blogCategoryId').val();
    var blogCategoryName = $('#select2-blogCategoryId-container').text();
    var description = $('#description').val();
    var blogTags = $('#blogTags').val();
    var blogContent = blogEditor.getMarkdown();
    var blogCoverImage = $('#blogCoverImage')[0].src;
    var blogStatus = $("input[name='blogStatus']:checked").val();
    var enableComment = $("input[name='enableComment']:checked").val();
    if (isNull(blogCoverImage) || blogCoverImage.indexOf('img-upload') != -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }
    var url = '/admin/blogs/save';
    var swlMessage = '保存成功';
    var data = {
        "blogTitle": blogTitle,
        "blogSubUrl": blogSubUrl,
        "blogCategoryId": blogCategoryId,
        "blogTags": blogTags,
        "blogContent": blogContent,
        "blogCoverImage": blogCoverImage,
        "blogStatus": blogStatus,
        "blogCategoryName": blogCategoryName,
        "description": description,
        "enableComment": enableComment
    };
    if (blogId > 0) {
        url = '/admin/blogs/modify';
        swlMessage = '修改成功';
        data = {
            "blogId": blogId,
            "blogTitle": blogTitle,
            "blogSubUrl": blogSubUrl,
            "blogCategoryId": blogCategoryId,
            "blogTags": blogTags,
            "blogContent": blogContent,
            "blogCoverImage": blogCoverImage,
            "blogStatus": blogStatus,
            "blogCategoryName": blogCategoryName,
            "description": description,
            "enableComment": enableComment
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: data,
        success: function (result) {
            if (result=="success") {
                $('#articleModal').modal('hide');
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回博客列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/blogs";
                })
            }else if(result=="6"){
                $('#articleModal').modal('hide');
                swal('文章标签最大数量不得超过6', {
                    icon: "error",
                });
            }else if(result=="description"){
                $('#articleModal').modal('hide');
                swal('文章描述不能为空', {
                    icon: "error",
                });
            }else {
                $('#articleModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/blogs";
});
