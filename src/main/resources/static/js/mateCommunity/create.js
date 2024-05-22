//본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
function DaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            document.getElementById("startingP").value = data.roadAddress;
        }
    }).open();
}

function empty_check(){
    let title = document.getElementById('title');
    let startingP = document.getElementById('startingP');
    let startingDetail = document.getElementById('startingDetail');
    let walkingM = document.getElementById('walkingM');
    let content = document.getElementById('content');
    let searchTag = document.getElementById('searchTag');

    if (title.value.trim() == ""){
        alert('제목을 입력해주세요!')
        title.focus();
        return false;
    } else if (startingP.value.trim() == ""){
        alert('위치를 입력해주세요!')
        return false;
    } else if (startingDetail.value.trim() == ""){
        alert('상세위치를 입력해주세요!')
        startingDetail.focus();
        return false;
    } else if (walkingM.value.trim() == "" || walkingM.value <= 0){
        alert('집합인원 설정해주세요!')
        walkingM.focus();
        return false;
    } else if (content.value.trim() == "") {
        alert('내용을 입력해주세요!')
        content.focus();
        return false;
    } else if (searchTag.value.trim() == ""){
        alert('검색태그를 최소 한개이상 입력해주세요!')
        searchTag.focus();
        return false;
    }
}

function send(){
    if (empty_check() == false){
        return false;
    }
    // // 날짜
    let date = document.querySelector('#assembleDate').value;
    console.log(date);

    // 오전 또는 오후
    let amOrPm = document.querySelector('input[name="amOrPm"]:checked').value;
    // 시
    let hour = document.querySelector('#hour');
    let hour_result = hour.options[hour.selectedIndex].value;
    // 분
    let minute = document.querySelector('#minute');
    let minute_result = minute.options[minute.selectedIndex].value;

    // document.getElementById('assembleTime').textContent= String(date) + " " + String(amOrPm) + " " + String(hour) + " : " + String(minute);

    document.getElementById('assembleTime').value = String(date) + " " + String(amOrPm) + String(hour_result) + ":" + String(minute_result);

    // let searchTags = document.getElementById('searchTag');
    // let searchTag_list = searchTags.value.split(' ');
    // searchTags.value = null;
    //
    // for (let i=0 ; i < searchTag_list.length ; i++){
    //     searchTags.value += '#' + searchTag_list[i] + " ";
    // }

    document.getElementById('frm').submit();
}

window.onload = () => {
    let assembleDate = document.querySelector('#assembleDate');
    assembleDate.value = new Date().toISOString().substring(0, 10);

    let offset = 1000 * 60 * 60 * 9 // 9시간 밀리세컨트 값
    let today = new Date(Date.now() + offset)

    assembleDate.setAttribute('min', today.toISOString().substring(0,10));

    today.setMonth(today.getMonth()+1)
    today.setDate(today.getDate()-1)
    assembleDate.setAttribute('max', today.toISOString().substring(0,10));

    let time = document.querySelector("#hour");
    for (let i = 1; i < 13; i++){
        if(i < 10) {
            time.options[i] = new Option('0' + i, String(i));
        }else{
            time.options[i] = new Option(i+'', String(i));
        }
    }
}