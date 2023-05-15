let idCheck = true;
let emailCheck = true;
let nicknameCheck = true;

////////폼두개 하나로 전송
function submitForms() {
    // 첫 번째 폼과 두 번째 폼의 데이터를 가져와서 하나로 합침
    var formData = new FormData(document.getElementById('form1'));
    for (var pair of new FormData(document.getElementById('form2')).entries()) {
        formData.append(pair[0], pair[1]);
    }
    const checkbox1 = document.getElementById('keep-signedin1');
    const checkbox2 = document.getElementById('keep-signedin2');
    const checkbox3 = document.getElementById('keep-signedin3');



    const passwordInput = document.querySelector('input[name="pwd"]');
    const passwordConfirmInput = document.querySelector('input[name="pwd-confirm"]');

    var check1 = /^[a-zA-Z0-9]{8,16}$/;
    var reg = /^\d{11}$/;
    var phone = document.querySelector('input[name="phone"]');
    if(idCheck){
        event.preventDefault();
        alert("아이디를 확인해주세요");
    }
    else if(!check1.test(passwordInput.value)){
        event.preventDefault();
        alert('비밀번호는 8자리이상16자리이하 영문 숫자로 구성해주세요');
    }
    else if (!checkbox1.checked || !checkbox2.checked || !checkbox3.checked) {
        // 하나라도 체크되어 있지 않으면 폼 전송 막고 알림창 출력
        event.preventDefault();
        alert('이용약관 동의에 체크를 해주세요.');
    }
    else if(emailCheck){
        event.preventDefault();
        alert("이메일을 확인해주세요");
    }
    else if(nicknameCheck){
        event.preventDefault();
        alert("닉네임을 확인해주세요");
    }
    else if(!reg.test(phone.value)){
        event.preventDefault();
        alert("휴대폰번호는 숫자만 입력해주세요");
    }
    else if(passwordInput.value !== passwordConfirmInput.value){
        event.preventDefault();
        alert("비밀번호가 일치하지 않습니다.");
    }
    else{
        // 합쳐진 데이터를 전송
        var xhr2 = new XMLHttpRequest();
        xhr2.open('POST', '/members/signup'); // 로그인 페이지로 이동
        xhr2.onload = function() {
            if (xhr2.status === 200) {
                window.location.href = '/members/signin';
            } else {
                // 요청이 실패한 경우 처리할 작업
            }
        };
        xhr2.send(formData);
    }
    // 합쳐진 데이터를 전송
}


//중복체크
$(document).ready(function() {

    // 아이디 유효성 검사
    $('#userId').blur(function() {
        var userId = $(this).val();
        var check1 = /^[a-zA-Z0-9]{8,16}$/;
        $.ajax({
            url: '/checkDuplicateId',
            data: {userId: userId},
            type: 'GET',
            success: function(response) {
                if (response.duplicate) {
                    idCheck = true;
                    alert('중복된 아이디입니다.');
                } else {
                    if(userId === ""){
                        alert('아이디를 입력하세요');
                    }else{
                        if(!check1.test(userId)){
                            alert('아이디는 8자리이상16자리이하 영문 숫자로 구성해주세요');
                        }else{
                            idCheck = false;
                            alert('사용가능한 아이디 입니다.');
                        }
                    }
                }
            },
            error: function() {
                alert('서버와의 통신에 실패했습니다.');
            }
        });
    });



    // 닉네임
    $('#nickname').blur(function() {
        var nickname = $(this).val();
        $.ajax({
            url: '/check-nickname',
            data: {nickname: nickname},
            type: 'GET',
            success: function(response) {
                if (response.duplicate) {
                    nicknameCheck = true;
                    alert('중복된 닉네임입니다.');
                } else {
                    if (nickname === ""){
                        alert('닉네임을 입력해주세요');
                    }else{
                        nicknameCheck = false;
                        alert('사용가능한 닉네임 입니다.');
                    }
                }
            },
            error: function() {
                alert('서버와의 통신에 실패했습니다.');
            }
        });
    });

    //비밀번호 찾기 / 변경
    $('#find-pwd').click(function(e) {
        e.preventDefault(); // 폼 제출 이벤트 방지
        var name1 = $('#name2').val(); // name 입력란 값 가져오기
        var email1 = $('#email2').val(); // email 입력란 값 가져오기
        var userId1 = $('#userId2').val(); // id 입력란 값 가져오기
        $.ajax({
            type: 'POST', // 전송 방식
            url: '/find_pwd', // 서버 URL
            data: {name: name1, email: email1,userId: userId1}, // 전송할 데이터
            success: function(response) {
                if (response.username == null){
                    alert("일치하는 회원정보가 없습니다");
                }else{
                    // 유저정보 인증에 성공한 경우
                    // 알러트 창을 생성합니다.
                    var code = prompt("입력한 이메일로 발송된 인증번호를 입력하세요.").toString();
                    // 입력된 인증번호를 서버로 전송합니다.
                    $.ajax({
                        type: 'POST', // 전송 방식
                        url: '/verify',
                        data: { code:code,email:email1 },
                        success: function(response) {
                            if (response.duplicate) {
                                // 인증 성공시 실행될 코드
                                $('#find_pw').modal('show');
                            } else {
                                alert('인증번호가 올바르지 않습니다.');
                            }
                        },
                        error: function(error) {
                            console.error('Error:', error);
                        }
                    });
                }
            }
        });
    });

    // 이메일 유효성 검사
    $('#email').blur(function() {
        var email = $(this).val();
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        $.ajax({
            url: '/check-email',
            data: {email: email},
            type: 'GET',
            success: function(response) {
                if (response.duplicate) {
                    emailCheck = true;
                    alert('중복된 이메일입니다.');
                } else {
                    if(email === ""){
                        alert('이메일을 입력해 주세요');
                    }else{
                        if(!emailRegex.test(email)){
                            alert('이메일 형식으로 입력해주세요');
                        }else{
                            emailCheck = false;
                            alert('사용가능한 이메일 입니다.');
                        }
                    }
                }
            },
            error: function() {
                alert('서버와의 통신에 실패했습니다.');
            }
        });
    });

    // 아이디 찾기
    $('#submit-btn').click(function(e) {
        e.preventDefault(); // 폼 제출 이벤트 방지
        var name = $('#name').val(); // name 입력란 값 가져오기
        var email = $('#email1').val(); // email 입력란 값 가져오기
        $.ajax({
            type: 'POST', // 전송 방식
            url: '/check_user', // 서버 URL
            data: {name: name, email: email}, // 전송할 데이터
            success: function(response) {
                if (response.userId == null){
                    alert("일치하는 회원정보가 없습니다");
                }else{
                    alert("등록된 아이디는 " + response.userId + "입니다.");
                }
            }
        });
    });

    $('input[type=radio][name="joinType"]').change(function() {
        const disabledTypeInput = document.querySelector('input[name="disabledType"]');
        const gradeInput = document.querySelector('input[name="grade"]');
        if($(this).val() === "1"){
            disabledTypeInput.disabled = false;
            gradeInput.disabled = false;
        }else{
            disabledTypeInput.disabled = true;
            gradeInput.disabled = true;
        }

    });

});