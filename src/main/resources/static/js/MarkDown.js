// 获取MarkDown内容
$.post({
    url: "/ObtainMarkDown",
    data: {blogId: "" + $("#blogId").val()},
    success: function (result) {
        $("#markdown").text(result)
    }
})
// MarkDown转Html
function markdownToHTML() {
    editormd.markdownToHTML("article-container", {
        htmlDecode: "style,script,iframe", //可以过滤标签解码
        emoji: true,
        taskList: true,
        tex: true,               // 默认不解析
        // flowChart:true,         // 默认不解析
        // sequenceDiagram:true,  // 默认不解析
    });
}

// 引入
$.getScript('/admin/plugins/editormd/lib/marked.min.js')
$.getScript('/admin/plugins/editormd/lib/prettify.min.js')
$.getScript('/admin/plugins/editormd/editormd.js', markdownToHTML)