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

function modifyStory(story_no) {

    let formData = new FormData();
    /*var content = tinymce.activeEditor.getContent();*/
    let html = editor.getData();
    formData.append('s_category', $('input[name="s_category"]:checked').val());
    formData.append('title', $('input[name="title"]').val());
    formData.append('user_type', $('input[name="user_type"]').val());
    formData.append('content', html);
    formData.append('image', $('input[name="image"]')[0].files[0]);

    var userType = $('input[name="user_type"]:checked').val();
    if (userType !== '개인') {
        var selectedTeamNo = $('select[name="team_title"]').val();
        formData.append('team_no', selectedTeamNo);
    }

    $.ajax({
        type: 'PUT',
        url: '/stories/posts/' + story_no,
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
        alert("Story posted successfully.");
            window.location.href = '/stories/' + story_no;
        },
        error: function(error) {
            alert("Error occurred while posting the story.");
            console.log(error);
        }
    });
}

//댓글


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



function refreshReplies() {
    const currentUrl = window.location.href;
    const story_no = currentUrl.split("/stories/")[1];

    // 서버에서 현재 사용자 정보를 가져옴
    $.ajax({
        type: "GET",
        url: "/api/getLoggedInUserId",
        success: function (loggedInUserId) {
            // AJAX 요청
            $.ajax({
                type: "GET",
                url: "/replies/" + story_no,
                success: function (replyDTOList) {
                    let html = "";
                    const replyCount = replyDTOList.length;
                    console.log(replyDTO.regdate);

                    replyDTOList.forEach(function (replyDTO) {
                        const replyNo = replyDTO.reply_no;
                        const replyWriter = replyDTO.reply_writer;
                        const isEditable = loggedInUserId !== "" && loggedInUserId === replyWriter;

                        html += '<div class="border-bottom pt-4 mt-3 mb-0">' +
                            '<div class="d-flex align-items-center pb-1 mb-3">' +
                            '<img class="rounded-circle" src="../assets/img/avatar/07.jpg" width="48" alt="Comment author">' +
                            '<div class="ps-3">' +
                            '<h6 class="mb-0">' + replyWriter + '</h6>' +
                            '<span class="fs-sm text-muted">' + replyDTO.regdate + '</span>' +
                            '</div>';

                        if (isEditable) {
                            html += '<div class="pe-4 ms-auto">' +
                                '<button class="edit-reply-btn pe-2 text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">수정</button>' +
                                '<button class="delete-reply-btn text-body fs-sm" data-reply-no="' + replyNo + '" style="border: none; background-color:transparent;">삭제</button>' +
                                '</div>';
                        }

                        html += '</div>' +
                            '<p class="pb-2 mb-0">' + replyDTO.content + '</p>' +
                            '</div>';
                    });

                    $("#replyDTOList").html(html);
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
        },
        error: function () {
            alert("로그인 정보 불러오기 실패");
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

function popularstories()
{
    $.ajax({
        url: '/stories/popular-stories',
        type: 'GET',
        success: function (stories) {
            let html = '';
            stories.forEach(function (stories) {
                /*let content = stories.content;
                if (content.length > 50) {
                    content = content.slice(0, 50) + '...';
                }*/
                let link = `/stories/${stories.story_no}`;
                let img = `/img/${stories.image_main}`;
                html += '<div class="card mx-3 p-0">' +
                    '<img src="' + img + '" class="card-img-top popul-story-img">' +
                    '<div class="card-body px-lg-5 py-lg-3">' +
                    '<a class="text-decoration-none text-dark" href="' + link + '">' +
                    '<h6 class="card-title">' + stories.title + '</h6>' +
                    '<p class="card-text fs-sm">' + stories.story_writer + '</p>' +
                    '</a>' +
                    '</div>' +
                    '</div>';
            });
            $("#popular-stories").html(html);

        }
    });
}

