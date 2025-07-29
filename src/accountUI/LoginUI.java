package accountUI;

import companyBoardUI.CompanyUI;
import dao.UserDAO;
import personalBoardUI.PersonalUI;
import ui.BaseUI;
import ui.IBoardUI;

public class LoginUI extends BaseUI {

	private String userType; // "개인", "기업", "관리자"

	public LoginUI(String userType) {
		this.userType = userType;
	}

	@Override
	public void execute() throws Exception {

		for (int i = 0; i < 15; i++) {
			System.out.println();
		}
		printHeader();
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.printf("               %s 회원 로그인 페이지               \n", userType);
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

		String id = scanStr("🆔 아이디      :  ");
		String password = scanStr("🔑 비밀번호     : ");

		System.out.println("\n───────────────────────────────");

		// 로그인 검증에 대한 구현
		UserDAO dao = new UserDAO();

		while (true) {
			int isValid = dao.validateUserDetail(id, password, userType);
			switch (isValid) {
			case 1:
			    BaseUI.loginUserId = id;
			    BaseUI.loginUserType = userType;
				
				IBoardUI nextUI = null;
				switch (userType) {
				case "개인":
					nextUI = new PersonalUI();
					break;
				case "기업":
					nextUI = new CompanyUI();
					break;
				case "관리자":
					nextUI = new AdminUI();
					break;
				}
				if (nextUI != null)
					nextUI.execute();
				return;

			case 0:
				System.out.println("❌ 로그인 실패! 존재하지 않는 아이디입니다.");
				System.out.println("회원가입 페이지로 이동합니다.");
				new SignUpUI().execute();
				return;

			case 2:
				System.out.println("❌ 로그인 실패! 비밀번호가 틀렸습니다.");
				System.out.println("다시 입력해주세요.\n");
				break;

			case 3:
				System.out.println("❌ 로그인 실패! 회원 유형이 다릅니다.");
				System.out.println("처음으로 돌아갑니다.\n");
				return; // 여기서도 보통 메인으로 돌려보냄

			default: // DB 오류 또는 알 수 없는 경우
				System.out.println("⚠️ 등록되지 않은 회원입니다. 회원가입창으로 이동합니다.");
				new SignUpUI().execute();
				return;

			}
			System.out.println("───────────────────────────────\n\n");

		}

	}
}
