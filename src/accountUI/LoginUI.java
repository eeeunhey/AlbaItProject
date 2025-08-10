package accountUI;

import companyBoardUI.CompanyUI;
import dao.UserDAO;
import personalBoardUI.PersonalUI;
import ui.BaseUI;
import adminBoardUI.AdminUI;

public class LoginUI extends BaseUI {

	private String userTypeLabel; // "개인", "기업", "관리자"
	private String expectedTypeCode; // "U", "C", "A"

	public LoginUI(String userTypeLabel) {
		this.userTypeLabel = userTypeLabel;
		this.expectedTypeCode = toTypeCode(userTypeLabel);
	}

	@Override
	public void execute() throws Exception {

		for (int i = 0; i < 15; i++)
			System.out.println();
		printHeader();
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.printf("               %s 회원 로그인 페이지               \n", userTypeLabel);
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

		UserDAO dao = new UserDAO();

		while (true) {
			String id = scanStr("🆔 아이디      :  ");
			String password = scanStr("🔑 비밀번호     : ");

			int isValid = dao.validateUserDetail(id, password);
			if (isValid == 1) { // 로그인 성공
				// 회원번호 & 회원유형 불러오기
				int userNo = dao.getUserNoById(id);
				String actualUserType = dao.getUserTypeById(id);

				// 전역 세션 저장
				BaseUI.loginUserId = id;
				BaseUI.loginUserNo = userNo;
				BaseUI.loginUserType = actualUserType;

				// 관리자 로그인
				if ("A".equalsIgnoreCase(actualUserType)) {
					System.out.println("\n✅ 관리자 로그인 성공! 관리자 페이지로 이동합니다.\n");
					new AdminUI().execute();
					return;
				}

				// 사용자 유형이 맞는지 확인
				if (actualUserType != null && actualUserType.equals(expectedTypeCode)) {
					if ("U".equals(actualUserType)) {
						System.out.println("\n✅ 로그인 성공! 개인 회원 페이지로 이동합니다.\n");
						new PersonalUI().execute();
					} else if ("C".equals(actualUserType)) {
						System.out.println("\n✅ 로그인 성공! 기업 회원 페이지로 이동합니다.\n");
						new CompanyUI().execute();
					}
					return;
				} else {
					System.out.println("\n❌ 로그인 실패! 회원 유형이 다릅니다.");
					System.out.println("처음으로 돌아갑니다.\n");
					return;
				}

			} else if (isValid == 0) { // 아이디 없음
				System.out.println("\n❌ 로그인 실패! 존재하지 않는 아이디입니다.");
				new SignUpUI().execute();
				return;

			} else if (isValid == 2) { // 비밀번호 틀림
				System.out.println("\n❌ 로그인 실패! 비밀번호가 틀렸습니다. 다시 입력해주세요.\n");
				continue;

			} else { // 기타
				System.out.println("\n⚠️ 등록되지 않은 회원입니다.");
				System.out.println("1. 회원가입");
				System.out.println("2. 로그인 다시 시도");
				int choice = scanInt("선택 ▶ ");
				if (choice == 1) {
					new SignUpUI().execute();
					return;
				}
			}
		}
	}

	private String toTypeCode(String label) {
		if (label == null)
			return "";
		switch (label.trim()) {
		case "개인":
			return "U";
		case "기업":
			return "C";
		case "관리자":
			return "A";
		default:
			return "";
		}
	}
}
