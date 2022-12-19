//회원목로조회에서 회원이름 클릭 시 진행 
function showDetailmemberInfo(memberId) {
	//학생이름 클릭하면 이벤트 발생 하여 함수 실행 
	
	//1) ajax start
	$.ajax({
		url: '/admin/showDetailmemberInfo', //요청경로
		type: 'post',
		data: { 'memberId': memberId }, //필요한 데이터
		success: function(result) {

		  let str = ''; // 문자열 상자 만들기
	      str += `<div>memberId: ${result.memberId}</div>
			        <div>memberName: ${result.memberName}</div>
			        <div>memberAddr: ${result.memberAddr}</div>
			        <div>memberEmail: ${result.memberEmail}</div>
			        <div>memberRole: ${result.memberRole}</div>
			        <div>memberStatus: ${result.memberStatus}</div>`;

			const detailDiv = document.querySelector('#detailDiv');
			detailDiv.innerHTML = ''; // 다른 학생이름 클릭시 다시 지워주고 넣어주기위해서 빈값만들기
			detailDiv.insertAdjacentHTML('beforeend',str);
			

		},
		error: function() {
			alert('실패');
		}
	});
	//1) ajax end

}

