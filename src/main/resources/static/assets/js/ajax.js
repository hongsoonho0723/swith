
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
    const currentUrl = window.location.href;
    console.log(currentUrl);
// URL에서 "stories/posts/" 다음에 오는 문자열을 추출하여 story_no 값을 가져옴
    const story_no = currentUrl.split("/stories/")[1];
    console.log(story_no);
    $.ajax({
        type: "GET",
        url: "/replies/" + story_no,
        success: function (replyDTOList) {
            let html = "";
            const replyCount = replyDTOList.length; // 댓글 수
            replyDTOList.forEach(function (replyDTO) {
                const replyNo= replyDTO.reply_no;
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
                const reply_no = $(this).data("reply-no");
                deleteReplyBtn(reply_no);
            });
            $(".edit-reply-btn").on("click", function () {
                const replyNo = $(this).data("reply-no");
                const replyContent = $(this).closest(".border-bottom").find("p").text(); // 수정 버튼을 누르면 버튼에서 가장 가까운 border-bottomd 클래스 찾아 p태그의 내용을 가져온다
                const html = '<div class="input-group justify-content-center" style="width: 100%; margin: 0 auto">' +
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
    const replyNo = $(this).data("reply-no");
    const replyContent = $("#edit-comment-content").val();
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


