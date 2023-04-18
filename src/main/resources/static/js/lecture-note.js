function showPlaylistAddModal(paramPlayListId) {
    const memberIdVal = $("#memberIdHidden").val();
    console.log("paramPlayListId : " + paramPlayListId);
    console.log("memberIdVal : " + memberIdVal);
    $.ajax({
        type: "get",
        url: "/lecture/modal-playlist-template",
        dataType: "text",
        data: {
            memberId: memberIdVal,
            playListId: paramPlayListId
        },
        success: function (data) {
            let html = jQuery('<div>').html(data);
            let contents = html.find("div#playlist-add-modal").html();
            $('#div-modal-lecture-note').html(contents);
            $("#addNoteModal").modal("show");

        }, error: function (error) {
            alert("강의노트 입력창 호출에 실패하였습니다.");
        }
    })
}

function likeVideo(paramVideoId) {
    let param = {
        "videoId": paramVideoId
    }
    $.ajax({
        type: "post",
        url: "/video/api/like-video",
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(param),
        success: function (result) {
            let color = rgb2hex($("#img-like-count").css("color"));
            if (color == "#ff0000") {
                $("#img-like-count").css("color", "#5830e0");
            } else {
                $("#img-like-count").css("color", "#ff0000");
                $("#img-dislike-count").css("color", "#5830e0");
            }
            $("#img-like-count").text(' ' + result.likeCount);
            $("#img-dislike-count").text(' ' + result.dislikeCount);
        },
        error: function (err) {
            alert(err);
        }
    })
}

function dislikeVideo(paramVideoId) {
    let param = {
        "videoId": paramVideoId
    }
    $.ajax({
        type: "post",
        url: "/video/api/dislike-video",
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(param),
        success: function (result) {
            let color = rgb2hex($("#img-dislike-count").css("color"));
            if (color == "#ff0000") {
                $("#img-dislike-count").css("color", "#5830e0");
            } else {
                $("#img-like-count").css("color", "#5830e0");
                $("#img-dislike-count").css("color", "#ff0000");
            }
            $("#img-like-count").text(' ' + result.likeCount);
            $("#img-dislike-count").text(' ' + result.dislikeCount);
        },
        error: function (err) {
            alert(err);
        }
    })
}