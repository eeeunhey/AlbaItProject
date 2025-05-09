package dao;

import java.util.ArrayList;
import java.util.List;

import boardVO.BoardVO;
import boardVO.PostVO;

public class BoardDAO {
	

	private List<BoardVO> boardList;
	// BoardVO : 클래스는 게시글 하나의 데이터를 저장해 <한개만 관리>
	// List<BoardVO> List에 넣었으니깐 여러개의 BoardVO 데이터를 저장한다
	// BoardDAO 는 리스트에 저장한<BoardVO> 모음을 게시글 목록을 만들어주고 불러온다
	// 게시판이 있어야지 하는 말만 해논 상태이다 -> null 상태라서 아무것도 동작을 못한다

	public BoardDAO() {
		boardList = new ArrayList<>();
		// 왜 BoardVO 를 저장할 공간에 new ArrayList를 만들었지?
		// 위에는 말만 해논 상태고 거기에 입력받은 값을 넣어줘야지! -> 그래서 new ArrayList를 쓴다
	}

	public void insertBoard(BoardVO board) { // 게시글을 리스트에 추가하는 기능이라 반환이 필요없다
		// 하나의 게시글(BoardVO)을 받아서 boardList(게시글 전체 목록을 담는 리스트) 추가 한다
		// 게시글 하나를 매개변수로 받아오는 함수다 객체를 파라미터로 받았어 그걸 저장해 하는 기능이다
		boardList.add(board);
		// 리스트니깐 add로 게시글 하나 추가한다
	}

}
