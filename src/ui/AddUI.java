package ui;

import boardVO.BoardVO;
import dao.BoardDAO;

public class AddUI extends BaseUI {
	// 새글을 추가하는 UI
	
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		String title = scanStr("제목을 입력하세요 : ");
		// BaseUI의 입력기능을 상속받았으니 scanStr 사용해서 title변수에 넣기
		String writer = scanStr("글쓴이를 입력하세요 : ");
		// BaseUI의 입력기능을 상속받았으니 scanStr 사용해서 writer변수에 넣기
		String content = scanStr("내용을 입력하세요 : ");
		// BaseUI의 입력기능을 상속받았으니 scanStr 사용해서 contents변수에 넣기
		
		BoardVO board = new BoardVO(); // 새로운 게시글 저장 객체를 만든다
		// 한개의 게시글 데이터를 저장하는 클래스 객체다
		board.setTitle(title);
		// VO 객체 안에 만들어논 setTitle 메소드에 위에 입력 받은 값을 파라미터에 넣어준다
		board.setWriter(writer);
		//VO 객체 안에 만들어논 setWriter 메소드에 위에 입력 받은 값을 파라미터에 넣어준다
		board.setContent(content);
		//VO 객체 안에 만들어논 setContent 메소드에 위에 입력 받은 값을 파라미터에 넣어준다
		
		BoardDAO dao = new BoardDAO();
		dao.insertBoard(board);
		
		
		
		// DAO(게시글을 목록에다 넣어주는 녀석) 적용하기 게시글을 목록에다 넣자!!!
		// BoardDAO dao = new BoardDAO();
		// dao.insertBoard(title, writer, content); 원래는 이런식을 값을 전달해줘야 하지만
		// BoardDAO dao = new BoardDAO();
		// dao.insertBoard(board)-> 이제는 BoardVO 클래스 객체를 만들어서 통으로 넘겨준다 
		
		
		
		Thread.sleep(1000);
		// 새글 등록을 마치고 메뉴가 너무 빨리 뜬다 -> 조금 텀을 주자
		
		System.out.println("\n\t게시글 등록이 완료되었습니다.");
		//게시글 등록이 완료되면 출력
		// BoardUI에 가서 호출하기
	}
}
