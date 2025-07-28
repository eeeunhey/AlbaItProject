package service;

public class BoardServiceFactory {
	private static BoardService boardService;
	
	public BoardService getInstance() {
		if(boardService == null)
			boardService = new BoardService();
		return boardService;
	}

}
