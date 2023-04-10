const logout = () => {
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

const validateString = (str) => {

    if (str === null) return false;
    if (typeof str === 'string' && str === '') return false;
    if (typeof str === 'undefined') return false;

    return true;
}