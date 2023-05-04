
/*$("#s_register").click(function(event){

    event.preventDefault();  // 기본 이벤트 막기

    // json 형식으로 데이터 set
    var params = {
        story_writer : $("#story_writer").val(),
        s_catecory: $('input[name="s_category"]:checked').val(),
        title: $('#text-input').val(),
        content: $('#content').val(),
        image: $('#input-image').val()
    }

    // ajax 통신
    $.ajax({
        type : "POST",            // HTTP method type(GET, POST) 형식이다.
        url : "/stories/posts",      // 컨트롤러에서 대기중인 URL 주소이다.
        data : params,            // Json 형식의 데이터이다.
        success : function(res){
            // 응답코드 > 0000
            alert(res.code);
        },

    });
});*/

    $(document).ready(function() {
        //storyForm은 form태그에 id값으로 추가해줘야 함
    $('#storyForm').submit(function(event) { //ajax에서는 submit을 사용하지 않음. ajax는 비동기형식이고 submit과 form은 동기형식
        event.preventDefault(); //form태그를 이용하여 submit을 사용하려면 submit의 이벤트를 막아줘야 함
        let formData = new FormData(this);

        $.ajax({
            type: "POST",
            url: "/stories/posts",
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                alert("Story posted successfully.");
                window.location.href = '/stories';
            },
            /*error: function(error) {
                alert("Error occurred while posting the story.");
                console.log(error);
            }*/
        });
    });
    });

 /*   const storyRead = () => {
        $.ajax({
            type: "get",
            url: "/stories/" + storyno,
            success: function (data){

            }

        });
    };*/

/*
$('#deleteBtn').on('click', function() {
    const story_no = $(this).data('story-no');
    $.ajax({
        type: 'DELETE',
        url: '/stories/posts/' + story_no,
        contentType: 'application/json',
        success: function(res) {
            console.log("Story deleted successfully");
        },
        error: function(jqXHR, textStatus) {
            console.log("Error deleting story: " + textStatus);
        }
    });
});*/
function deleteBtn(story_no) {
    $.ajax({
        type: 'DELETE',
        url: '/stories/posts/' + story_no,
        contentType: 'application/json',
        success: function(res) {
            console.log("Story deleted successfully");
            window.location.href = '/stories'; // 페이지 이동
        },
        error: function(jqXHR, textStatus) {
            console.log("Error deleting story: " + textStatus);
        }
    });
}

//댓글
function refreshReplies() {
    // 현재 페이지의 URL을 가져옴
    var currentUrl = window.location.href;
    console.log(currentUrl);
// URL에서 "stories/posts/" 다음에 오는 문자열을 추출하여 story_no 값을 가져옴
    var story_no = currentUrl.split("/stories/")[1];
    console.log(story_no);
    $.ajax({
        type: "GET",
        url: "/replies/" + story_no,
        success: function (replyDTOList) {
            var html = "";
            var replyCount = replyDTOList.length; // 댓글 수
            replyDTOList.forEach(function (replyDTO) {
                var replyNo= replyDTO.reply_no;
                html += '<div class="border-bottom pt-4 mt-3 mb-0">' +
                    '<div class="d-flex align-items-center pb-1 mb-3">' +
                    '<img class="rounded-circle" src="../assets/img/avatar/07.jpg" width="48" alt="Comment author">' +
                    '<div class="ps-3">' +
                    '<h6 class="mb-0">' + replyDTO.reply_writer + '</h6>' +
                    '<span class="fs-sm text-muted">' + replyDTO.regdate + '</span>' +
                    '</div>' +
                    '<div class="pe-4 ms-auto">' +
                    '<button class="pe-2 text-body fs-sm" style="border: none; background-color:transparent;">수정</button>' +
                    '<button class="text-body fs-sm delete-reply-btn" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">삭제</button>' +
                    '</div>' +
                    '</div>' +
                    '<p class="pb-2 mb-0">' + replyDTO.content + '</p>' +
                    '</div>';
            });
            $("#replyDTOList").html(html);
            $("#replyCount").text("(" + replyCount + ")");

            // 삭제 버튼에 대한 클릭 이벤트 핸들러 등록
            $(".delete-reply-btn").on("click", function () {
                var reply_no = $(this).data("reply-no");
                deleteReplyBtn(reply_no);
            });
        },
        error: function () {
            alert("댓글 조회에 실패했습니다.");
        }
    });
}

function deleteReplyBtn(reply_no) {
    $.ajax({
        type: 'DELETE',
        url: '/replies/' + reply_no,
        contentType: 'application/json',
        success: function(res) {
            console.log("Story deleted successfully");
            refreshReplies();
        },
        error: function(jqXHR, textStatus) {
            console.log("Error deleting story: " + textStatus);
        }
    });
}




//댓글
/*
function refreshReplies() {
    // 현재 페이지의 URL을 가져옴
    var currentUrl = window.location.href;
    console.log(currentUrl);
// URL에서 "stories/posts/" 다음에 오는 문자열을 추출하여 story_no 값을 가져옴
    var story_no = currentUrl.split("/stories/")[1];
    console.log(story_no);
    $.ajax({
        type: "GET",
        url: "/replies/" + story_no,
        success: function (replyDTOList) {
            var html = "";
            var replyCount = replyDTOList.length; // 댓글 수
            replyDTOList.forEach(function (replyDTO) {
                var replyNo= replyDTO.reply_no;
                html += '<div class="border-bottom pt-4 mt-3 mb-0">' +
                    '<div class="d-flex align-items-center pb-1 mb-3">' +
                    '<img class="rounded-circle" src="../assets/img/avatar/07.jpg" width="48" alt="Comment author">' +
                    '<div class="ps-3">' +
                    '<h6 class="mb-0">' + replyDTO.reply_writer + '</h6>' +
                    '<span class="fs-sm text-muted">' + replyDTO.regdate + '</span>' +
                    '</div>' +
                    '<div class="pe-4 ms-auto">' +
                    '<button class="edit-reply-btn pe-2 text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">수정</button>' +
                    '<button class="delete-reply-btn text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">삭제</button>' +
                    '</div>' +
                    '</div>' +
                    '<p class="pb-2 mb-0">' + replyDTO.content + '</p>' +
                    '</div>';
            });
            $("#replyDTOList").html(html);
            //$("#replyDTOList").append(html); // 기존의 html 변수 대신 append 함수를 사용하여 HTML을 추가
            $("#replyCount").text("(" + replyCount + ")");

            // 삭제 버튼에 대한 클릭 이벤트 핸들러 등록
            $(".delete-reply-btn").on("click", function () {
                var reply_no = $(this).data("reply-no");
                deleteReplyBtn(reply_no);
            });

            //수정버튼
            $(".edit-reply-btn").on("click", function () {
                var replyNo = $(this).data("reply-no");
                var replyContent = $(this).closest(".border-bottom").find("p").text(); // 수정 버튼을 누르면 버튼에서 가장 가까운 border-bottomd 클래스 찾아 p태그의 내용을 가져온다
                var html = '<div class="input-group justify-content-center" style="width: 100%; margin: 0 auto">' +
                    '<input type="text" class="form-control" placeholder="댓글 작성" id="edit-comment-content" value="' + replyContent + '">' +
                    '<button type="button" class="btn btn-outline-info btn-icon" id="submitEditReply" data-reply-no="' + replyNo + '">' +
                    '<i class="ai-edit"></i>' +
                    '</button>' +
                    '<button type="btn btn-outline-info btn-ico" class="btn btn-outline-secondary" id="cancelEditReply">' +
                    'X' +
                    '</button>' +
                    '</div>';
                $(this).closest(".border-bottom").html(html);

                $("#cancelEditReply").on("click", function () {
                    var html = '<div class="d-flex align-items-center pb-1 mb-3">' +
                        '<img class="rounded-circle" src="../assets/img/avatar/07.jpg" width="48" alt="Comment author">' +
                        '<div class="ps-3">' +
                        '<h6 class="mb-0">' + replyDTO.reply_writer + '</h6>' +
                        '<span class="fs-sm text-muted">' + replyDTO.regdate + '</span>' +
                        '</div>' +
                        '<div class="pe-4 ms-auto">' +
                        '<button class="edit-reply-btn pe-2 text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">수정</button>' +
                        '<button class="delete-reply-btn text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">삭제</button>' +
                        '</div>' +
                        '</div>' +
                        '<p class="pb-2 mb-0">' + replyDTO.content + '</p>';
                    $(this).closest(".border-bottom").html(html);
                });
            });

        },
        error: function () {
            alert("댓글 조회에 실패했습니다.");
        }
    });
}

function deleteReplyBtn(reply_no) {
    $.ajax({
        type: 'DELETE',
        url: '/replies/' + reply_no,
        contentType: 'application/json',
        success: function(res) {
            console.log("reply deleted successfully");
            refreshReplies();
        },
        error: function(jqXHR, textStatus) {
            console.log("Error deleting story: " + textStatus);
        }
    });
}

$(document).on("click", "#submitEditReply", function () {
    var replyNo = $(this).data("reply-no");
    var replyContent = $("#edit-comment-content").val();
    $.ajax({
        type: "PUT",
        url: "/replies/" + replyNo,
        data: {"content" : replyContent},
        success: function (originalReplyDTO) {
            var html = '<div class="d-flex align-items-center pb-1 mb-3">' +
                '<img class="rounded-circle" src="../assets/img/avatar/07.jpg" width="48" alt="Comment author">' +
                '<div class="ps-3">' +
                '<h6 class="mb-0">' + originalReplyDTO.reply_writer + '</h6>' +
                '<span class="fs-sm text-muted">' + originalReplyDTO.regdate + '</span>' +
                '</div>' +
                '<div class="pe-4 ms-auto">' +
                '<button class="edit-reply-btn pe-2 text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">수정</button>' +
                '<button class="delete-reply-btn text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">삭제</button>' +
                '</div>' +
                '</div>' +
                '<p class="pb-2 mb-0">' + originalReplyDTO.content + '</p>';
            $("#replyDTOList").find("[data-reply-no='" + replyNo + "']").closest(".border-bottom").html(html);
        },
        error: function () {
            alert("댓글 수정에 실패했습니다.");
        }
    });
});*/

function refreshReplies() {
    // 현재 페이지의 URL을 가져옴
    var currentUrl = window.location.href;
    console.log(currentUrl);
// URL에서 "stories/posts/" 다음에 오는 문자열을 추출하여 story_no 값을 가져옴
    var story_no = currentUrl.split("/stories/")[1];
    console.log(story_no);
    $.ajax({
        type: "GET",
        url: "/replies/" + story_no,
        success: function (replyDTOList) {
            var html = "";
            var replyCount = replyDTOList.length; // 댓글 수
            replyDTOList.forEach(function (replyDTO) {
                var replyNo= replyDTO.reply_no;
                html += '<div class="border-bottom pt-4 mt-3 mb-0">' +
                    '<div class="d-flex align-items-center pb-1 mb-3">' +
                    '<img class="rounded-circle" src="../assets/img/avatar/07.jpg" width="48" alt="Comment author">' +
                    '<div class="ps-3">' +
                    '<h6 class="mb-0">' + replyDTO.reply_writer + '</h6>' +
                    '<span class="fs-sm text-muted">' + replyDTO.regdate + '</span>' +
                    '</div>' +
                    '<div class="pe-4 ms-auto">' +
                    '<button class="edit-reply-btn pe-2 text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">수정</button>' +
                    '<button class="delete-reply-btn text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">삭제</button>' +
                    '</div>' +
                    '</div>' +
                    '<p class="pb-2 mb-0">' + replyDTO.content + '</p>' +
                    '</div>';
            });
            $("#replyDTOList").html(html); // 기존의 html 변수 대신 append 함수를 사용하여 HTML을 추가
            $("#replyCount").text("(" + replyCount + ")");

            // 삭제 버튼에 대한 클릭 이벤트 핸들러 등록
            $(".delete-reply-btn").on("click", function () {
                var reply_no = $(this).data("reply-no");
                deleteReplyBtn(reply_no);
            });
            $(".edit-reply-btn").on("click", function () {
                var replyNo = $(this).data("reply-no");
                var replyContent = $(this).closest(".border-bottom").find("p").text(); // 수정 버튼을 누르면 버튼에서 가장 가까운 border-bottomd 클래스 찾아 p태그의 내용을 가져온다
                var html = '<div class="input-group justify-content-center" style="width: 100%; margin: 0 auto">' +
                    '<input type="text" class="form-control" placeholder="댓글 작성" id="edit-comment-content" value="' + replyContent + '">' +
                    '<button type="button" class="btn btn-outline-info btn-icon" id="submitEditReply" data-reply-no="' + replyNo + '">' +
                    '<i class="ai-edit"></i>' +
                    '</button>' +
                    '</div>';
                $(this).closest(".border-bottom").html(html);
            });

        },
        error: function () {
            alert("댓글 조회에 실패했습니다.");
        }
    });
}

function deleteReplyBtn(reply_no) {
    $.ajax({
        type: 'DELETE',
        url: '/replies/' + reply_no,
        contentType: 'application/json',
        success: function(res) {
            console.log("reply deleted successfully");
            refreshReplies();
        },
        error: function(jqXHR, textStatus) {
            console.log("Error deleting story: " + textStatus);
        }
    });
}

$(document).on("click", "#submitEditReply", function () {
    var replyNo = $(this).data("reply-no");
    var replyContent = $("#edit-comment-content").val();
    $.ajax({
        type: "PUT",
        url: "/replies/" + replyNo,
        data: {"content" : replyContent},
        success: function (replyDTO) {
            refreshReplies();
        },
        error: function () {
            alert("댓글 수정에 실패했습니다.");
        }
    });
});
