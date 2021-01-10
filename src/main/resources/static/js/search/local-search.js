$(function () {
  let loadFlag = false
  const openSearch = function () {
    $('body').css({
      width: '100%',
      overflow: 'hidden'
    })
    $('#local-search').css('display', 'block')
    $('#local-search-input input').focus()
    $('#search-mask').fadeIn()
    /*if (!loadFlag) {
      search(GLOBAL_CONFIG.localSearch.path)
      loadFlag = true
    }*/

    // shortcut: ESC
    document.addEventListener('keydown', function f (event) {
      if (event.code === 'Escape') {
        closeSearch()
        document.removeEventListener('keydown', f)
      }
    })
  }

  const closeSearch = function () {
    $('body').css({
      width: '',
      overflow: ''
    })
    $('#local-search').css({
      animation: 'search_close .5s'
    })

    setTimeout(function () {
      $('#local-search').css({
        animation: '',
        display: 'none'
      })
    }, 500)

    $('#search-mask').fadeOut()
  }

  const searchClickFn = () => {
    $('a.social-icon.search').on('click', openSearch)
    $('#search-mask, .search-close-button').on('click', closeSearch)
  }

  searchClickFn()
  window.addEventListener('pjax:success', function () {
    $('#local-search').is(':visible') && closeSearch()
    searchClickFn()
  })

  $(document).ready(function(){
    $('#search').keyup(function(){
      $.ajax({
        type: "POST",
        url: "/search",
        data: {
          Content: ""+$("#search").val()
        },
        success: function (result) {
          $(".search-result-list").remove();
          $("#local-hits").prepend("<div class=\"search-result-list\"></div>");
          var arr=result.blog;
          for(var i=0;i<result.blog.length;i++){
            $('.search-result-list')
                .prepend("<div id='empty' style='margin-bottom: 10px;'><div style='width: 70%; float: right;'> <a class=\"aside-post-title\" id='search-title' href="+arr[i].blogSubUrl+" title="+arr[i].blogTitle+">"+arr[i].blogTitle+"</a> <span id='description'>"+arr[i].description+"</span> <time id='search-time' class=\"aside-post_meta post-meta-date\" title=\"发表于 2020-11-18\">2020-11-18</time> </div> <a href=\"douyin\" title="+arr[i].blogTitle+"> <img id='search-cover' alt="+arr[i].blogTitle+" src="+arr[i].blogCoverImage+"> </a></div> ");
          }
        }
      })
    })
  });
})
