$(document).ready(function () {
    $(".starbutton").click((event) => {
        const $target = $(event.currentTarget)
        const boardNo = $target.data("board-no")

        updateWish()

        function updateWish() {
            const boardJjimDTO = {
                "board_no": boardNo,
            }

            $.ajax({
                url: "/match/wish",
                type: "POST",
                data: boardJjimDTO,
                success: function (isWish) {
                    $target.find("i")
                        .removeClass()
                        .addClass(isWish ? "ai-star" : "ai-star-filled")// $(this) 대신 변수 $this 사용
                    console.log("성공")
                },
                error: function () {
                    console.log("실패")
                }
            });
        }
    })

})
