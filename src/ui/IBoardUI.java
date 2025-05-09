package ui;

public interface IBoardUI {
	/** 인터페이스는 이름정의하는 곳이구나 
	 *  
	 * 위 인터페이스는 각 행위별 메소드명 통합 호출하기 위해 만든 인터페이스다
	 * 인터페이스라는 표시하기 위해 이름 앞에 I를 붙인다
	 * 이제 implements로 상속받은 아이들은 execute란 이름을 사용한다
	 * */
	void execute() throws Exception;
	// execute 로 지정하고 실행할 때 잘못될 상황에 대비하여 Exception 예외 처리한다
	// 이제 모든 UI에 적용해보자! implements 설정하자!

}
