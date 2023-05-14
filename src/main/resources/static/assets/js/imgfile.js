/*function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('preview').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        document.getElementById('preview').src = "";
    }
}*/

/*function readImage(input) {

    // 인풋 태그에 파일이 있는 경우
    if(input.files && input.files[0]) {

        // 이미지 파일인지 검사 (생략)

        // FileReader 인스턴스 생성
        const reader = new FileReader()

        // 이미지가 로드가 된 경우
        reader.onload = e => {
            const previewImage = document.getElementById("preview-image")
            previewImage.src = e.target.result
        }

        // reader가 이미지 읽도록 하기
        reader.readAsDataURL(input.files[0])
    }
}*/

function readImage(input) {
    if (input.files && input.files[0]) {
        const file = input.files[0];

        // 이미지 파일인지 확인
        if (!file.type.startsWith("image/")) {
            // 이미지 파일이 아닌 경우 메시지 창 표시
            alert("이미지 파일만 업로드할 수 있습니다.");
            return;
        }

        const reader = new FileReader();

        reader.onload = e => {
            const previewImage = document.getElementById("preview-image");
            previewImage.src = e.target.result;
        }

        reader.readAsDataURL(file);
    }
}



