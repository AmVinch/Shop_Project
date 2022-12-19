//사용여부(USE,UNUSE)라디오 버튼 클릭 시 진행 
function changeStatus(cateCode, status){
   const result =  confirm('상품의 상태를 변경할까요?');
   if(result){
      //1) ajax start
      $.ajax({
         url: '/admin/changeStatus', //요청경로
         type: 'post',
         data: {'cateStatus':status,'cateCode':cateCode}, //필요한 데이터
         success: function(result) {
            alert('상태를 변경했습니다');
            
            //[셀렉트박스 목록 다시 조회]
            // 사용여부를 변경하면, 
            // 우측화면 셀렉트박스에서도 디비저장된 부분이 연동되어 조회되어야한다.(변경 후,사용중인 카테목록들만)
            // ajax안에 ajax를 사용해야한다.

			// 하지만, 위의 처럼 적으면 복잡하다
            // 또다시 적으면 코딩이 복잡하니 간략히 사용위해 함수 하나 만들어서 해당 함수만 사용한다.
            selectCategoryListInUseAjax();
      
         },
         error: function() {
            alert('실패');
         }
      });
      //1) ajax end
   }
}

/////////////////////////////////////////////////////////////////////////////////////////////
//카테고리 사용여부 변경 후 실행되는 ajax 함수
// (해당 함수가 실행되는 사용중인 카테고리 목록을 다시 조회한다.)
function selectCategoryListInUseAjax(){
	
	//ajax start
    $.ajax({
	     url: '/admin/selectCategoryListInUseAjax', //요청경로
	     type: 'post',
	     data: {}, //필요한 데이터
	     success: function(result) {
			alert(result);
			// 삭제해주고 다시 만들어야하는 셀렉트문 
			//<select class="form-select" th:field="*{cateCode}">
            //<option th:each="cateList: ${cateListUse}" th:text="${cateList.cateName}" th:value="${cateList.cateCode}"></option>
            //</select>			
			
			// 셀렉트박스안에 있는 조회문을 다시 삭제 후,
			// 셀렉트박스를 빈값을 준 뒤, 다시 만들어줘야한다.
			
			/*const selectBox = document.querySelector('#cateCode');
		    selectBox.innerHTML = '';
		    
		    let str = '';
		    for(const cateList of result){
				// 백팁으로 감싸면 모두 문자열은 문자열 변수는 변수대로 인식한다(js문법)
			    str += `<option value="${cateList.cateCode}">${cateList.cateName}</option>`; 
			}
		    selectBox.insertAdjacentHTML('beforeend',str);
		    // afterbegin : 내가 선택한 셀렉트박스가 시작한 직후에
		    // beforeend : 내가 선택한 셀렉트박스가 끝나기 직전에 (둘이같다)*/
	       
	     },
	     error: function() {
	        alert('실패');
	     }      
     });
      //ajax end
}
















