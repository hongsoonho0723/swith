//////// 타입 전송
const radioButtons = document.querySelectorAll('input[name="joinType"]');
let joinType = '';
//// 서포터 장애인 선택시 활성 비활성
const disableTypeInput = document.querySelector('input[name="disableType"]');
const gradeInput = document.querySelector('input[name="grade"]');
const regionInputs = document.querySelectorAll('input[name="region"]');
const preferenceInput = document.querySelector('input[name="preference"]');

radioButtons.forEach((radioButton) => {
    radioButton.addEventListener('change', (event) => {
        joinType = event.target.value;

        if (event.target.value === '1') {
            // 1번 옵션 선택 시, 모든 인풋 요소를 활성화
            disableTypeInput.removeAttribute('disabled');
            gradeInput.removeAttribute('disabled');
            regionInputs.forEach((input) => {
                input.removeAttribute('disabled');
            });
            preferenceInput.removeAttribute('disabled');
        } else if (event.target.value === '2') {
            // 2번 옵션 선택 시, 모든 인풋 요소를 비활성화
            disableTypeInput.setAttribute('disabled', true);
            gradeInput.setAttribute('disabled', true);
            preferenceInput.setAttribute('disabled', true);
        }
        console.log(`선택된 옵션: ${joinType}`);
    });
});

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
    if(idCheck){
        event.preventDefault();
        alert("아이디를 확인해주세요");
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
    // 아이디 중복체크
    $('#userId').blur(function() {
        var userId = $(this).val();
        $.ajax({
            url: '/checkDuplicateId',
            data: {userId: userId},
            type: 'GET',
            success: function(response) {
                if (response.duplicate) {
                    idCheck = true;
                    alert('중복된 아이디입니다.');
                } else {
                    idCheck = false;
                    alert('사용가능한 아이디 입니다.');
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
                    nicknameCheck = false;
                    alert('사용가능한 닉네임 입니다.');
                }
            },
            error: function() {
                alert('서버와의 통신에 실패했습니다.');
            }
        });
    });

    // 이메일
    $('#email').blur(function() {
        var email = $(this).val();
        $.ajax({
            url: '/check-email',
            data: {email: email},
            type: 'GET',
            success: function(response) {
                if (response.duplicate) {
                    emailCheck = true;
                    alert('중복된 이메일입니다.');
                } else {
                    emailCheck = false;
                    alert('사용가능한 이메일 입니다.');
                }
            },
            error: function() {
                alert('서버와의 통신에 실패했습니다.');
            }
        });
    });
});