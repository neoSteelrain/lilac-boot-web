function logout(){
    $.ajax({
        type:"post",
        url:"/member/logout",
        success:function (responseObj, textStatus, jqXHR){
            location.href = "/";
        },
        error:function (jqXHR, textStatus, errorThrown){
            alert("로그아웃 도중 에러 발생");
        }
    });
}

function validateString(str) {

    if (str === null) return false;
    if (typeof str === 'string' && str === '') return false;
    if (typeof str === 'undefined') return false;

    return true;
}

function rgb2hex(rgb) {
    if (  rgb.search("rgb") == -1 ) {
        return rgb;
    } else {
        rgb = rgb.match(/^rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*(\d+))?\)$/);
        function hex(x) {
            return ("0" + parseInt(x).toString(16)).slice(-2);
        }
        return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
    }
}
