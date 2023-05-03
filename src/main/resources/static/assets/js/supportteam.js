
//서포터팀상세보기 페이지에서 팀 정보탭의 정보를 ajax로 표현
    $(document).ready(function () {
    const team_title = document.querySelector("#team_title").textContent;
    const team_fixed = 1;
    console.log(team_title);
    $.ajax({
    url: "/teams/" + team_title + "/" + team_fixed,
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
    loop: true,
    navigation: {
    prevEL: "#prev-feature",
    nextEL: "#next-feature"
},
})
    console.log("성공했수당");
},
    error: function () {
    console.log("실패했슴둥");
}
});
});


//서포터팀상세보기 페이지에서 관리탬의 신청목록

$(document).ready(function () {
    const team_title = document.querySelector("#team_title").textContent;
    const team_fixed = 0;
    $.ajax({
        url: "/teams/{team_title}/{team_fixed}",
        type: "GET",
        dataType: "json",
        success: function (userList) {
            $.each(userList, function (user) {
                const div = $("<div class='swiper-slide w-auto'>" +
                    "<div class='card-hover text-center' style='max-width:306px'>" +
                    "<img  class='d-block rounded-5 mb-4' src='../assets/img/landing/creative-agency/team/05.jpg'  alt='Image'/>" +
                    "<h3 class='h5 mb-1'>" + member.team_memberId + "</h3>" +
                    "<a type='button' class='btn btn-secondary mt-5' href='#'>거절</a>" +
                    "<a type='button' class=\"btn btn-success mt-5\" href=\"#\">수락</a>" +
                    "</div></div>");
                $("#userItem").append(div);
            });
            let swiper = new Swiper('.swiper', {
                slidesPerView: 'auto',
                spaceBetween: 24,
                loop: true,
                navigation: {
                    prevEL: "#prev-feature",
                    nextEL: "#next-feature"
                }
            })
            console.log("성공했수당");
        },
        error: function () {
            console.log("실패했슴둥123");
        }
    });
});
/*<script>
    $(document).ready(function () {
    $.ajax({
        url: "/teams/" + team_title,
        type: "GET",
        dataType: "json",
        success: function (data) {
            $('#team_title').text(data.team_title);
            $('#team_writer').text(data.team_writer);
            $('#sido').text(data.sido);
            $('#deadline').text("마감일 " + data.deadline);
            $('#member_num').text(data.member_num);
            $('#simple_content').text(data.simple_content);
            if (data.finished == 0) {
                $('#finished').text("모집중");
            } else {
                $('#finsihed').text("모집완료");
            }
        },
        error: function () {
            console.log("실패했슴둥");
        }
    });
});
</script>

 */
