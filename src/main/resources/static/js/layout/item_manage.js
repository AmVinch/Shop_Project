//-------------------상품(판매)여부 라디오 버튼 클릭시 진행되는 함수---------------------------------//
function changeItemStatus(itemCode, status){
	
      $.ajax({
         url: '/admin/changeItemStatus', //요청경로
         type: 'post',
         data: {'itemStatus':status,'itemCode':itemCode}, //필요한 데이터
         success: function(result) {
			const modal = new bootstrap.Modal('#updateStatusModal');
			modal.show();  
         },
         error: function() {
            alert('실패');
         }
      });
}



//--------------------------재고 수량변경 버튼 클릭시 진행되는 함수---------------------------------//
function changeStock(itemCode, selectedTag){
	//const result =  confirm('제품재고 수량를 변경할까요?');
	/////////////////////////[ 태그선택 요소 ]////////////////////////////////////
	// this라는 액션을 취할 때 = 변경버튼클릭할 때 태그가 어딨는지 찾아가야한다.
	// 형제의 자식태그 밸류값을 빼온다
	// 부모태그 찾아갈때 : parentElemnet
	// 이전형제태그 찾아갈때 : previousElemnetSibling
	// 다음형제 노드 : nextElemnetSibling
	// 감싸고 있는 태그 중 가장 가까운 상위태그 : closest()
	// 자식태그(모든) : children
	// 자식이 여러명있을 때 배열로 접근하여 자식태그를 선택해준다
	///////////////////////////////////////////////////////////////////////////////
    
    //-----------------------[   재고 수량 value값 선택하여 갖고오기   ]---------------------------------------//
    // : 태그를 찾아가서 변경될 수량 input태그 밸류값을 가져온다.
	//-----------------------[주의할 점]-----------------------------------------------------------------------//
    // -----  데이터 하나만 들고올 때
	// const itemStock = selectedTag.parentElemnet.previousElemnetSibling.children[0].vlaue;
	// ----- 데이터 여러개 들고올때
	// : 단, 아래처럼 클래스로 불러오게되면 for문을 도는 반복문이기때문에 중복이되어 데이터를 들고오지않는다.
	// 왜냐면 내가 실제로 변경할 인풋태그 값 하나만 불러와야하기때문이다.
	// -- 잘못된 방법
	// const itemStock = document.querySelectorAll('.stockInput');
	// -- 올바른 방법
	// : 선택한 태그를 둘러싸고 있는! 감싸고있는!(감싸지않으면 x) 가장 가까운 td태그 선택
	//---------------------------------------------------------------------------------------------------------//
	// <방법 1>   
	// const itemStock1 = selectedTag.parentElemnet.children[0].vlaue;
	// <방법 2>
	// const itemStock = selectedTag.closest('td').querySelector('.stockInput').vlaue;
	//---------------------------------------------------------------------------------------------------------//
	
	 const itemStock = selectedTag.closest('td').querySelector('#itemStocks').value;
    
      //1) ajax start
      $.ajax({
         url: '/admin/changeStock', //요청경로
         type: 'post',
         data: {'itemCode':itemCode ,'itemStock':itemStock}, //필요한 데이터
         success: function(result) {
		// -- alert창 띄우는 ver.
        // alert('수량을 변경했습니다');
        // -- modal창 띄우는 ver.
        // 버튼을 클릭했을 때가 아니라 원했을 때 띄우기
	        const modal = new bootstrap.Modal('#updateStockModal');
			modal.show();        
        
         },
         error: function() {
            alert('실패');
         }
      });
      //1) ajax end
}







