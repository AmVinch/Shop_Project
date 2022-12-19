package Kh.study.shop.date;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//----------------------------------------------------------------------------------//
// @RestController
// 위 컨트롤러를 사용시, 페이지이동은 안가고 데이터를 그대로 보내줘서 화면에 띄워진다.
// 날짜데이터가 한 페이지가 아닌 여러페이지에서도 사용될 가능성이 높다.
// 그래서 날짜와 관련된 클래스 하나를 만들어서 
// 날짜관련 함수를 하나를 한번만 만들어서  쭉 사용하기 편리하게 이용한다.
//----------------------------------------------------------------------------------//
@RestController
@RequestMapping("/date")
public class DateController {
	
	// 현재 날짜
	@GetMapping("/test1")
	public String test1() {
		//----------[날짜 세팅 방법]-------------------//
		//1) Calendar 사용
		// 객체생성방법은 조금 다르다.
		// 왜? 객체가 하나만 있어도 돌려사용가능하기때문
		// 디자인패턴 : 싱글톤 패턴
		Calendar cal = Calendar.getInstance();
		
		// 오늘의 년/월/일 를 가져온다.
		// 단, 월은 항상  +1을 해야한다.
		//왜? 
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;// 월은 기본세팅값이 0부터시작하기때문에
		int date = cal.get(Calendar.DATE);
		//삼항연산자) 0000-00-00 날짜형식에맞게 만들기
		String monthStr =  month/10 == 0 ? "0" + month: month + "" ;
		String dateStr =  date/10 == 0 ? "0" + date: date + "" ;
		
		String nowDate = year + "-" + monthStr + "-" + dateStr;
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@ "+nowDate);//2022-10-05
		
		// @RestController 어노테이션 사용으로 
		// nowDate 리턴값이 페이지이동은 안가고 데이터를 그대로 보내줘서 화면에 띄워진다.
		
		return nowDate;
	}
}
