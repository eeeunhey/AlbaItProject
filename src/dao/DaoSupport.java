package dao;

public abstract class DaoSupport {
	protected RuntimeException wrap(Exception e, String msg) {
		return new RuntimeException(msg, e); // 오류 발생 시 해당 메소드를 표시해준다
		
	}
}