package ui;

public class ExitUI extends BaseUI {
	// 내가 0번을 눌렀을 때 종료되는 기능을 구현하는 클래스이다
	// 입력받는 기능을 하는 BaseUI를 상속받는다
	// execute 라는 이름을 가진 메소드를 사용하기 위해 IBoardUI를 상속받는다

	@Override
	public void execute() throws Exception {
		System.out.println("\n\n========================================");
		System.out.println("\t프로그램을 종료합니다.");
		System.out.println("========================================");
		System.exit(0);
	}

}
