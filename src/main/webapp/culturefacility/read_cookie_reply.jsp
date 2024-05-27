<c:set var="culturefno" value="${culturefacilityVO.culturefno }" />
<c:set var="cname" value="${culturefacilityVO.cname }" />
<c:set var="raddress" value="${culturefacilityVO.raddress }" />        
<c:set var="latitude" value="${culturefacilityVO.latitude }" />
<c:set var="longitude" value="${culturefacilityVO.longitude }" />
<c:set var="addr_code" value="${culturefacilityVO.addr_code }" />
<c:set var="phone" value="${culturefacilityVO.phone }" />
<c:set var="closeddays" value="${culturefacilityVO.closeddays }" />
<c:set var="operatingtime" value="${culturefacilityVO.operatingtime }" />
<c:set var="pa" value="${culturefacilityVO.pa }" />


<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script>
    var frm_facilityreview = $('#frm_facilityreview');
    $('#reviewcomment', frm_facilityreview).on('click', check_login);  // 댓글 작성시 로그인 여부 확인
    $('#btn_create', frm_facilityreview).on('click', facilityreview_create);  // 댓글 작성시 로그인 여부 확인

    function check_login() {
        var frm_facilityreview = $('#frm_facilityreview');
        if ($('#memberno', frm_facilityreview).val().length == 0 ) {
            $('#modal_cname').html('댓글 등록'); // 제목 
            $('#modal_reviewcomment').html("로그인해야 등록 할 수 있습니다."); // 내용
            $('#modal_panel').modal();            // 다이얼로그 출력
            return false;  // 실행 종료
        }
    }

    function facilityreview_create() {
        var frm_facilityreview = $('#frm_facilityreview');
        
        if (check_login() != false) { // 로그인 한 경우만 처리
            var params = frm_facilityreview.serialize();
            if ($('reviewcomment', frm_facilityreview).val().length > 300) {
                $('#modal_cname').html('댓글 등록'); // 제목 
                $('#modal_reviewcomment').html("댓글 내용은 300자이상 입력 할 수 없습니다."); // 내용
                $('#modal_panel').modal();           // 다이얼로그 출력
                return;  // 실행 종료
            }

            $.ajax({
                url: "../facilityreview/create", // action 대상 주소
                type: "post",          // get, post
                cache: false,          // 브라우저의 캐시영역 사용안함.
                async: true,           // true: 비동기
                dataType: "json",      // 응답 형식: json, xml, html...
                data: params,          // 서버로 전달하는 데이터
                success: function(rdata) { // 서버로부터 성공적으로 응답이 온 경우
                    var msg = ""; // 메시지 출력
                    if (rdata.cnt > 0) {
                        $('#modal_reviewcomment').attr('class', 'alert alert-success'); // CSS 변경
                        msg = "댓글을 등록했습니다.";
                        $('#reviewcomment', frm_facilityreview).val('');
                        $('#passwd', frm_facilityreview).val('');
                        $('#facilityreview_list').html(''); // 댓글 목록 패널 초기화
                        $("#facilityreview_list").attr("data-replypage", 1);  // 댓글이 새로 등록됨으로 1로 초기화
                    } else {
                        $('#modal_reviewcomment').attr('class', 'alert alert-danger'); // CSS 변경
                        msg = "댓글 등록에 실패했습니다.";
                    }
                    $('#modal_cname').html('댓글 등록'); // 제목 
                    $('#modal_reviewcomment').html(msg);     // 내용
                    $('#modal_panel').modal();           // 다이얼로그 출력
                },
                // Ajax 통신 에러, 응답 코드가 200이 아닌 경우, dataType이 다른 경우 
                error: function(request, status, error) { // callback 함수
                    console.log(error);
                }
            });
        }
    }
</script>

<jsp:include page="/menu/top.jsp" flush='false' />
<!-- Modal 알림창 시작 -->
<div class="modal fade" id="modal_panel" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-reviewcomment">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id='modal_cname'></h4><!-- 제목 -->
            </div>
            <div class="modal-body">
                <p id='modal_reviewcomment'></p>  <!-- 내용 -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div> <!-- Modal 알림창 종료 -->

<!-- 댓글 영역 시작 -->
<div style='width: 80%; margin: 0px auto;'>
    <hr>
    <form name='frm_facilityreview' id='frm_facilityreview'> 
        <input type='hidden' name='culturefno' id='culturefno' value='${culturefno}'>
        <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
        
        <textarea name='reviewcomment' id='reviewcomment' style='width: 100%; height: 60px;' placeholder="댓글 작성, 로그인해야 등록 할 수 있습니다."></textarea>
        <input type='password' name='passwd' id='passwd' placeholder="비밀번호">
        <button type='button' id='btn_create'>등록</button>
    </form>
    <hr>
    <div id='facilityreview_list' data-replypage='1'></div>
    <div id='facilityreview_list_btn' style='border: solid 1px #EEEEEE; margin: 0px auto; width: 100%; background-color: #EEFFFF;'>
        <button id='btn_add' style='width: 100%;'>더보기 ▽</button>
    </div>  
</div>
<!-- 댓글 영역 종료 -->

<jsp:include page="/menu/bottom.jsp" flush='false' />
</body>
</html>