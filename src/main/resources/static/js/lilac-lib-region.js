function changeLibRegionCode(){
    const m_selectedRegion = $('#location-main').val();
    $.ajax({
        type:"get",
        url:"/search/dtl-region-code",
        dataType:"json",
        data:{ regionCode: m_selectedRegion },
        success:(result) => {
            initDetailRegionCode(result);
        },
        error:(err) => {
            alert("세부지역코드를 불러오지 못했습니다.");
        }
    });
}

function initDetailRegionCode(detailRegionCodes){
    /*
        템플릿 문자열 = 백틱 기호호 감싼다. ``
        `<option value='세부지역코드'>세부지역명</option>`
        선택하지 않은 값은 -1 로 설정
     */
    $("#location-sub").empty();
    $("#location-sub").append('<option value="-1">--- 세부지역 ---</option>');
    for(const region of detailRegionCodes){
        $("#location-sub").append(`<option value=${region.code}>${region.detailName}</option>`);
    }
}