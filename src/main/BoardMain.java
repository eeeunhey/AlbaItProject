package main;


import accountUI.AccountUI;
import ui.BoardUI;

public class BoardMain {

	public static void main(String[] args) {
		
		//1. 키보드를 입력받고 화면에 출력
		//2. UI이란 이름을 붙이자
		//3. 관리를 하기 위해서
		// 메인문에서 예외 처리 하기
		
		AccountUI accountUI = new AccountUI();
 // 먼저 회원가입 UI 실행
		
		//BoardUI ui = new BoardUI();
		try {
			accountUI.execute();
			//ui.execute();
			//ui의 execute() 메서드 실행
		} catch(Exception e) {
			//예외 정보를 받아오는 변수
			e.printStackTrace();
			// 에러의 원인과 위치를 콘솔에 출력
		}
		
		
	}
	

}
