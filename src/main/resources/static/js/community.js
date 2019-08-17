function post() {
    var questionId = $("#questionId").val();
    // console.log(questionId);
    var comment = $("#comment-text").val();
    // console.log(comment);
    // console.log(111);
    $.ajax({
        type: "POST",
        contentType:'application/json',
        url: "/comment",
        data: JSON.stringify({
            "parentId":questionId,
            "content":comment,
            "type":1
        }),
        success:function (response) {
            // console.log(response);
            if (response.code == 200){
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