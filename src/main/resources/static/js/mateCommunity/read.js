window.onload = () =>{
    if([[${ (int) session.getAttribute("memberno")) == ${mateCommunityVO.memberNo}]]) {
        document.getElementById('delete_btn').addEventListener("click", () => {
            let url = './delete';

            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(
                    {mCommunityNo: [[${mateCommunityVO.mCommunityNo}]]}
                )
            })
                .then(response => response.json())
                .then(rdata => {
                    console.log(rdata.cnt);
                    if (rdata.cnt == 0) { // 패스워드 불일치
                        alert("삭제 실패 했습니다.");
                        return false;
                    } else {
                        alert("성공적으로 삭제 되었습니다.");
                        location.href = 'mateCommunity/list_all';
                    }
                })
                .catch(error => { // 서버 다운등 통신 에러
                    console.error('Error:', error);
                });
        });
    }

    document.getElementById('recruit').addEventListener('click', () => {
        let url = '/mateApply/recruit?mCommunityNo='+[[${mateCommunityVO.mCommunityNo}]];

        fetch(url, {
            method: 'GET'
        })
            .then(response => response.json())
            .then(rdata => {
                console.log(rdata.cnt);
                if (rdata.cnt == 0) { // 패스워드 불일치
                    alert("신청 실패 했습니다.");
                    return false;
                }else{
                    alert("신청 되었습니다.");
                    location.reload();
                }
            })
            .catch(error => { // 서버 다운등 통신 에러
                console.error('Error:', error);
            });
    });

    document.getElementById('login_need').addEventListener('click', ()=>{
        location.href="/member/login"
    })
}