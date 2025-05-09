package exception;

public class ChoiceOutOfBoundException extends Exception {
	// 예외 처리를 위해 만든 클래스이다
	
	public ChoiceOutOfBoundException() {
		super();
		//super();는 Exception 클래스의 기본 생성자를 호출
	}
	
	public ChoiceOutOfBoundException (String message) {
		super(message);
		//예외 메시지를 직접 전달할 수 있게 해주는 생성자
		//throw new ChoiceOutOfBoundException("에러메세지 출력");
		//예외가 발생했을 때, 이 메시지가 에러 로그에 출력
	}
}
