package ui;

public class UpdateUI extends BaseUI {
	// 입력받을 꺼니깐 BaseUI 상속받자
	//게시글을 수정하는 UI 부분
	// UpdateUI에 맞게 오버라이드 하자
	//관리자에 넣으면 좋겠다
	
	
	@Override
	public void execute() throws Exception {
		System.out.println("게시판 수정 선택!");
		
		int no = scanInt ("수정할 글번호를 입력하세요 : "  );
		// 게시글 수정 선택시 수정할 글번호 입력하기
		String title = scanStr("수정할 제목을 입력하세요");
		// 수정할 제목 저장하기
		String writer = scanStr("수정할 글쓴이를 입력하세요");
		// 수정한 글쓴이 입력하기
		String content = scanStr("수정할 내용을 입력하세요 :");
		// 수정할 내용 입력하기
		
		Thread.sleep(1000);
		System.out.printf("[%d]번 게시글을 수정하였습니다.", no);
		//수정 확인하기
		
	}

	
	
	

}
