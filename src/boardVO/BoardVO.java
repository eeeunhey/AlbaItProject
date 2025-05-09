package boardVO;

public class BoardVO {
	// Value Object 라고 하고 파라미터 입력값에 따라 기능을 적용하는 곳이다
	// 예를 들어 
	// 기능: 회원가입   필요한 정보(파라미터): 사용자명, 이메일, 비밀번호, 전화번호 ㅂ
	// 회원가입(사용자,이메일, 비밀번호, 전화번호) -> 이건 회원가입 기능이다!!!!! 회원가입기능 적용해
	// 기능 : 게시글 작성 (제목, 내용, 작성자) -> 어!!! 이건 게시글 작성 기능이다!!!!
	// 어떤 파라미터가 들어오냐에 따라 해당 기능을 적용한다.
	
	
	/* 왜 private를 쓸까
	 * 1. 외부에서 잘못된 값을 넣는 것을 방지하기 위해 사용 / 
	 * 	Member m = new Member();
		m.age = -100; // 말도 안 되는 값!
		이런 일이 허용되면 나이 같은 개념이 무의미해지고, 프로그램이 엉뚱한 동작을 할 수 있다
		멤버 변수를 외부에서 막 다 쓰고 있으면 → 그 변수를 바꾸려면 모든 코드 수정해야 한다
		반대로, getter/setter만 공개하면 → 내부 구조는 바꿔도 외부는 건드릴 필요 없어요.
	
	 * 2. 한 곳에서 제어하기 위해 -> 그럼 여기서만 수정해주면 끝나겠다!
	 * 3. 객체의 기능을 일관적으로 유지하기 위해 사용한다
	 * **/
	private int no;
	// 게시판 번호 저장하기 // 저장하고 나서 적용되는 값이네 그럼 저장하고나서 적용하자
	private String title;
	// 게시판 제목 저장하기
	private String writer;
	// 게시판 글쓴이 저장하기
	private String content;
	// 게시판 내용 저장하기 
	private String regDate;
	// 게시판 저장 날짜 저장하기 // 저장하고 나서 적용돠는 값이다 그럼 저장하고 나서 적용하자
	
	public BoardVO() {
		super();
		//기본생성자다
	}
	public BoardVO(String title, String writer, String content) {
		// 게시글 작성할 때 입력받을 데이터다 생성자 단축어 Alt + Shift + S → O
		super();
		this.title = title;
		this.writer = writer;
		this.content = content;
	}
	public BoardVO(int no, String title, String writer, String content, String regDate) {
		// 게시글 작성하고 나서 저장되는 데이터다 // 번호랑 날짜는 게시글 입력완료 하면 저장되는 데이터다
		super();
		this.no = no;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.regDate = regDate;
	}
	
	
	// getter / setter 자동생성 단축어 Alt + Shift + S → R
	// getter : 깂을 가져올때 사용한다 -> 왜냐 내가 멤버변수를 프라이빗으로 해서 접근이 안되니깐 쓴다  
	// setter : 가져온 값을 바꿀 때 사용한다 -> get에서 받아온 값으로 바꿔준다!
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	// toString 자동생성 단축어 Alt + Shift + S → S

	/* toStirng 왜쓸까?
	 * 객체를 문자열로 표현할 때 사용된다 -> 왜 문자열로 표현하는데 재정의 해서 사용하지?
	 * 모든 클래스는 object라는 클래스를 상속받는데 상속받은 기능중에 toString이라는 기능이 있어
	 * 오버라이딩 안한 toString 을 그냥 가져다 쓰면 toString이 가진 메소드를 가져오긴 해 
	 * 내가 원하는 정보를 가져오지 못한다 getClass().getName() + "@" + Integer.toHexString(hashCode())
	 * 이런식으로 사용하면 값은 Member@6d06d69c(메모리 주소와 비슷한 고유값) 컴퓨터가 이해하고 있는 언어로 가져온다 -> 사람이 해석불가...
	 * 그래 그럼 내가 보고싶은 값으로 재정의하자!!! 
	 */
	
	
	@Override
	public String toString() {
		return "BoardVO [no=" + no + ", title=" + title + ", writer=" + writer + ", content=" + content + ", regDate="
				+ regDate + "]";
	}
	
}
