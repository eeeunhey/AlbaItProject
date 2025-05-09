package ui;

public class DeleteUI extends BaseUI {
	// 게시글을 삭제하는 UI
	// 게시글 삭제를 위한 execute 부분 재정의
	@Override
	public void execute() throws Exception {
		
		int no = scanInt("삭제할 게시판 번호를 입력하세요 : ");
		
		Thread.sleep(1000);
		System.out.println("["+ no +"] 번 게시글을 삭제하였습니다.");
	
	
	}
}
