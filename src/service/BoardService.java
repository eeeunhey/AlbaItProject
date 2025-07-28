package service;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import boardVO.PostVO;
import dao.BoardNoSequence;
import dao.PostDAO;

public class BoardService {

	private PostDAO postDao;

	public BoardService() {
		postDao = new PostDAO();
	}
	
	public void addBoard(PostVO board) {
		// 게시글일련번호 생성
		int boardNo = BoardNoSequence.getBoardNo();
		board.setPostId(boardNo);
		
		// 등록시간 세팅 (년도4자리-월2자리-일2자리)
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String createdAt = sdf.format(new Date());
		board.setCreatedAt(createdAt);
		
		List<PostVO> boardList = List.of(board); 
		postDao.insertJobPostList(boardList);
	}

	

    public List<PostVO> searchBoardAll() {
        return postDao.selectAllBoard();  // ✅ 객체로 호출
    }

    public PostVO searchBoardByNo(int boardNo) {
    	PostVO board = postDao.selectByNo(boardNo);  
    	return board;// ✅ 변수명 통일
        
    }

    public boolean removeBoardByNo(int boardNo) {
        return postDao.deleteByNo(boardNo);  // ✅ 변수명 통일
    }
    

}













