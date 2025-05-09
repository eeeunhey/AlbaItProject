package dao;

public class BoardNoSequence {

	private static int boardNo = 1;
	
	public static synchronized int getBoardNo() {
		return BoardNoSequence.boardNo++;
	}
}
