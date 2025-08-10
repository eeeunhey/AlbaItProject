package ui;

import boardVO.UserVO;
import dao.UserDAO;
import java.text.SimpleDateFormat;

public class ViewInfoUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("\n📄 [회원 정보 조회]");

		// 로그인 여부 체크
		if (BaseUI.loginUserId == null) {
			System.out.println("⚠️ 로그인 상태가 아닙니다.");
			return;
		}

		UserDAO dao = new UserDAO();
		UserVO user = dao.getUserDetail(BaseUI.loginUserId);

		if (user == null) {
			System.out.println("❌ 회원 정보를 불러오지 못했습니다.");
			return;
		}

		// 날짜 포맷
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		// 정보 출력
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		System.out.printf("%-8s: %d\n", "회원번호", user.getUserNo());
		System.out.printf("%-8s: %s\n", "아이디", nullSafe(user.getUserId()));
		System.out.printf("%-8s: %s\n", "이름", nullSafe(user.getName()));
		System.out.printf("%-8s: %s\n", "닉네임", nullSafe(user.getNickname()));
		System.out.printf("%-8s: %s\n", "회원유형", convertUserTypeName(user.getUserType()));
		System.out.printf("%-8s: %s\n", "가입일", user.getRegDate() != null ? df.format(user.getRegDate()) : "-");
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	}

	// null일 경우 대체 문자 반환
	private String nullSafe(String value) {
		return value != null ? value : "-";
	}

	// 코드 → 한글 변환
	private String convertUserTypeName(String code) {
		switch (code) {
		case "U":
			return "개인회원";
		case "C":
			return "기업회원";
		case "A":
			return "관리자";
		default:
			return "알수없음";
		}
	}
}
