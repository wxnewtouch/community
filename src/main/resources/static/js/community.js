/**
 * 二级评论
 */
function comment(e){
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}
/**
 * 这个是进行评论的
 */
function post() {
    var targetId = $("#questionId").val();
    // console.log(questionId);
    var content = $("#comment-text").val();
    comment2target(targetId,1,content);
}

/**
 * 方法的封装
 */
function comment2target(targetId,type,content){
    if (!content){
        alert("评论不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        contentType:'application/json',
        url: "/comment",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function (response) {
            // console.log(response);
            if (response.code == 200){
                window.location.reload();
                $("#comment_section").hide();
            }else{
                if (response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=410760a04a8e8e2caf6b&redirect_uri=http://localhost:8080/callback&scope=user&state=aaa");
                        window.localStorage.setItem("closeFlag","YES");
                    }
                }else{
                    alert(response.message);
                }
            }
        }
    });
}

/**
 * 控制显隐
 * @param e
 */
function commentISHide(e) {
    var id = e.getAttribute("data-id");
    if (e.getAttribute("ShowComment")){
        $("#comment-"+id).removeClass("in");
        e.removeAttribute("ShowComment");
        e.classList.remove("active");
        console.log(e);
    }else{
        e.setAttribute("ShowComment","open");
        $("#comment-"+id).addClass("in");
        e.classList.add("active");
        console.log(e);
    }
}