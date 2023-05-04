
//아직 처리하지 않은 것들 : applicationTeam()에서 파라미터로 userId를 받아와서 작업해야함, 지금은 직접 더미값 입력하여 테스트한 상태
//팀삭제, 모집마감, 팀 수정 : 팀장만이 삭제할 수 있게 바꾸어야 함.


//caught (in promise) Error: A listener indicated an asynchronous response by returning true, but the message channel closed before a response was received
//위의 에러때문에 처리함
self.addEventListener('message',function(event){
    //비동기작업 실행
    doAsyncWork().then(function(result){
        //비동기 작업이 완료되면 결과를 반환함.
        self.postMemssage(result);
    });
    //비동기작업이 완료되지 않았으므로 true를 반환하지 않음
});

function doAsyncWork(){
    //Promise를 사용하여 비동기 작업 수행
    return new Promise(function(resolve,reject){
        //비동기 작업이 완료되면 resolve 호출
        setTimeout(function () {
            resolve('result');
        }, 1000);
    });
}

//서포터팀상세보기 페이지에서 팀 정보탭의 정보를 ajax로 표현
    $(document).ready(function () {
    const team_title = document.querySelector("#team_title").textContent;
    const team_fixed = 1;
    console.log(team_title);
    $.ajax({
    url: "/teams/" + team_title + "/1",
    type: "GET",
    dataType: "json",
    success: function (memberList) {
    //데이터를 HTML태그에 붙이는 로직
    $.each(memberList, function (index, member) {
    console.log(member);
    const div = $("<div class='swiper-slide w-auto'>" +
    "<div class='card-hover text-center' style='max-width:306px'>" +
    "<img  class='d-block rounded-5 mb-4' src='../assets/img/landing/creative-agency/team/05.jpg'  alt='Image'/>" +
    "<h3 class='h5 mb-1'>" + member.team_memberId + "</h3>" +
    "</div></div>");
    $("#memberItem").append(div);
});
    //Swiper가 작동하지 않은것은 swiper가 제대로 초기화가 되지 않았기 때문이다. 따라서 콜백함수내에서 swiper초기화 수행
    let swiper = new Swiper('.swiper',{
    slidesPerView: 'auto',
    spaceBetween: 24,
    loop: false,
    navigation: {
    prevEL: "#prev-feature",
    nextEL: "#next-feature"
},
})
    console.log("첫 번째 Ajax 요청 성공");
},
    error: function () {
    console.log("첫 번째 Ajax 요청 실패");
}
});
});



//서포터팀상세보기 페이지에서 관리탬의 신청목록

$(document).ready(function () {
    const team_title = document.querySelector("#team_title").textContent;
    const team_fixed = 0;
    $.ajax({
        url: "/teams/" + team_title + "/" + team_fixed,
        type: "GET",
        dataType: "json",
        success: function (userList) {
            $.each(userList, function (index, user) {
                console.log(user);
                //finished에 따른 모집완료 여부

                //데이터를 html에 붙이는 로직
                const div2 = $("<div id='" +user.team_memberId +"' class='swiper-slide w-auto'>" +
                    "<div class='card-hover text-center' style='max-width:306px'>" +
                    "<img  class='d-block rounded-5 mb-4' src='../assets/img/landing/creative-agency/team/05.jpg'  alt='Image'/>" +
                    "<h3 id='team_userId' class='h5 mb-1'>" + user.team_memberId + "</h3>" +
                    "<a type='button' class='btn btn-secondary mt-5' onclick=\"rejectMember('" + user.team_memberId + "')\">거절</a>" +
                    "<a type='button' class=\"btn btn-success mt-5\" onclick=\"acceptMember('" + user.team_memberId + "')\">수락</a>" +
                    "</div></div>");


                $("#userItem").append(div2);
            });
            let swiper = new Swiper('.swiper', {

                slidesPerView: 'auto',
                spaceBetween: 24,
                loop: false,
                navigation: {
                    prevEL: "#prev-feature",
                    nextEL: "#next-feature"
                }
            })
            console.log("성공했수당123");
        },
        error: function () {
            console.log("실패했슴둥123");
        }
    });
});

function rejectMember(team_memberId){
    const team_title = document.querySelector("#team_title").textContent;
    /*const team_memberId = document.querySelector("#team_userId").textContent;*/
    $.ajax({
        url: "/teams/admin/"+ team_title +"/"+ team_memberId,
        type: "DELETE",
        dataType: "json",
        success: function (res){
            console.log("멤버요청 거절 성공");
            $("#"+ team_memberId).remove();
        },
        error: function () {
            console.log("멤버요청 거절 실패");
        },
    })
}

function acceptMember(team_memberId){
    const team_title = document.querySelector("#team_title").textContent;
    /*const team_memberId = document.querySelector("#team_userId").textContent;*/
    $.ajax({
        url: "/teams/admin/"+ team_title +"/"+ team_memberId,
        type: "PATCH",
        dataType: "json",
        success: function (res){
            console.log("멤버요청 수락 성공");
            $("#"+ team_memberId).remove();
            //팀 멤버에 추가
            const div = $("<div class='swiper-slide w-auto'>" +
                "<div class='card-hover text-center' style='max-width:306px'>" +
                "<img  class='d-block rounded-5 mb-4' src='../assets/img/landing/creative-agency/team/05.jpg'  alt='Image'/>" +
                "<h3 class='h5 mb-1'>" + team_memberId + "</h3>" +
                "</div></div>");
            $("#memberItem").append(div);
            let swiper = new Swiper('.swiper', {

                slidesPerView: 'auto',
                spaceBetween: 24,
                loop: false,
                navigation: {
                    prevEL: "#prev-feature",
                    nextEL: "#next-feature"
                }
            })
        },
        error: function () {
            console.log("멤버요청 수락 실패");
        },
    })
}

//신청하기 클릭시 실행됨
function applicationTeam(){
    //아이디 받기
    const team_title= document.querySelector("#team_title").textContent;
    const teamMemberDTO = {
        "team_title" : team_title,
        "team_memberId": "testUSer8"    //userId
    }
    $.ajax({
        url: "/teams/info",
        type: "POST",
        data: JSON.stringify(teamMemberDTO),
        contentType:"application/json",
        success: function (user){
            alert('신청완료');
            document.querySelector("#applicationButton").innerText = "신청완료";
            console.log(user);
            console.log("서포터팀멤버로 신청하기 성공");
            //관리탭의 신청목록에 추가하기
            const div2 = $("<div id='" +user.team_memberId +"' class='swiper-slide w-auto'>" + //user.team_memberId
                "<div class='card-hover text-center' style='max-width:306px'>" +
                "<img  class='d-block rounded-5 mb-4' src='../assets/img/landing/creative-agency/team/05.jpg'  alt='Image'/>" +
                "<h3 id='team_userId' class='h5 mb-1'>" + user.team_memberId + "</h3>" +
                "<a type='button' class='btn btn-secondary mt-5' onclick=\"rejectMember('" + user.team_memberId + "')\">거절</a>" +  ////user.team_memberId
                "<a type='button' class=\"btn btn-success mt-5\" onclick=\"acceptMember('" + user.team_memberId + "')\">수락</a>" +  //user.team_memberId
                "</div></div>");
            $("#userItem").append(div2);
            //비동기로 작업하므로 swiper초기화시켜주기
            let swiper = new Swiper('.swiper', {

                slidesPerView: 'auto',
                spaceBetween: 24,
                loop: false,
                navigation: {
                    prevEL: "#prev-feature",
                    nextEL: "#next-feature"
                }
            })
        },
        error: function () {
            console.log("서포터팀멤버로 신청하기 실패");
        },

    })
}

//팀 삭제
function teamRemove(){
    const team_title = document.querySelector("#team_title").textContent;
    const supportTeamDTO ={
        "team_title": team_title,
        "team_writer": "testUser11",
    }
    $.ajax({
        url: "/teams/admin/" + team_title,
        type: "DELETE",
        data: JSON.stringify(supportTeamDTO),
        contentType: "application/json",
        success: function () {
            console.log("팀삭제 성공");
            window.location.href="/teams";
            console.log("이동 성공")
        },
        error: function(){
            console.log("팀삭제 실패");

        }
    })
}

//모집마감 버튼 클릭시
function recruitFinished(){
    const team_title = document.querySelector("#team_title").textContent;
    let recruitButton = document.getElementById("recruitButton");
    let finished;
    if(recruitButton.textContent ==="모집완료"){
        finished = true;
    }else if (recruitButton.textContent ==="모집완료취소") {
        finished = false;
    }
    const supportTeamDTO = {
        "team_title" : team_title,
        "finished" : finished,
    };
    $.ajax({
        url: "/teams/total/" + team_title,
        type: "PATCH",
        data: JSON.stringify(supportTeamDTO),
        contentType: "application/json",
        success:function (data){
            console.log(data);
            if(data.finished){
                recruitButton.innerText = "모집완료취소";
            alert('모집마감 처리되었습니다.');
            console.log("서포터팀 모집마감 확정");
            }else {
                recruitButton.innerText = "모집완료";
            alert('모집마감이 취소되었습니다.');
            console.log("서포터팀 모집마감이 취소되었습니다.");
            }
        },
        error: function () {
            console.log("서포터팀 모집마감 실패");
        },
    })
}

//모집마감 여부
$(document).ready(function(){
    const team_title = document.querySelector("#team_title").textContent;
    const recruitButton = document.querySelector("#recruitButton");
    const finished = recruitButton.dataset.finished; // 모집완료 여부 값 가져오기
    const supportTeamDTO = {
        "team_title" : team_title,
        "finished" : finished,
        "inquiry" : "dummy"
    };
   $.ajax({
      url: "/teams/total/" + team_title,
       type: "PATCH",
       data: JSON.stringify(supportTeamDTO),
       contentType: "application/json",
       success: function (data){
           if (data.finished) {
               recruitButton.innerText = "모집완료취소";
           }else{
               recruitButton.innerText = "모집완료";

           }
           console.log("모집완료여부버튼 업데이트 성공");
       },
       error: function () {
           console.log("모집완료여부버튼 업데이트 실패");
       },
   });
});

//팀 수정 페이지 이동
function viewModifyTeam(){
    const team_title = document.querySelector("#team_title").textContent;
    $.ajax({
        url: "/teams/admin/" + team_title,
        type: "GET",
        data: "json",
        success: function (res){
            console.log("팀수정페이지 이동 성공");

        },
        error: function () {
            console.log("팀수정페이지 이동 실패");
        },

    })
}

//---------------------------modify----------------------------------------------------------

function modifyForm(){
    const team_title = document.querySelector("#team_title").textContent;
    const supportTeamDTO={
        "team_title": team_title,
        "team_writer": "testUser1",
        "content": $("$content").val(),
        "sido": $("#city").val(),
        "sigungu": $("#district").val(),
        "member_num": $("#member_num").val(),
        "image_team": "C://user/motorora",  //$("#input-image").val(),
        "simple_content": $("#simple_content").val(),
        "inquiry": $("#inquiry").val(),
        "finished": false,
        "deadline": $("#deadline").val()
    };
    $.ajax({
        url: "/teams/admin/"+team_title,
        type: "PUT",
        data: JSON.stringify(supportTeamDTO),
        contentType: "application/json",
        success: function (res){
            console.log("서포터팀 수정 완료")
        },
        error: function(){
            console.log("서포터팀 수정 실패")
        }
    })
}