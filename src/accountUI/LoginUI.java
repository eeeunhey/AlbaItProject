package accountUI;

import companyBoardUI.CompanyUI;
import dao.UserDAO;
import personalBoardUI.PersonalUI;
import ui.BaseUI;
import ui.IBoardUI;
import adminBoardUI.AdminUI;

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

		UserDAO dao = new UserDAO();

		while (true) {
			int isValid = dao.validateUserDetail(id, password);
			String actualUserType = dao.getUserTypeById(id); // DB에 저장된 USER_TYPE: U, C, A

			switch (isValid) {
			case 1:
				BaseUI.loginUserId = id;
				BaseUI.loginUserType = actualUserType;

				IBoardUI nextUI = null;

				if ("A".equals(actualUserType)) {
					System.out.println("\n✅ 관리자 로그인 성공! 관리자 페이지로 이동합니다.\n");
					nextUI = new AdminUI();
				} else if ("U".equals(actualUserType) && "개인".equals(userType)) {
					System.out.println("\n✅ 로그인 성공! 개인 회원 페이지로 이동합니다.\n");
					nextUI = new PersonalUI();
				} else if ("C".equals(actualUserType) && "기업".equals(userType)) {
					System.out.println("\n✅ 로그인 성공! 기업 회원 페이지로 이동합니다.\n");
					nextUI = new CompanyUI();
				} else {
					System.out.println("\n❌ 로그인 실패! 회원 유형이 다릅니다.");
					System.out.println("처음으로 돌아갑니다.\n");
					return;
				}

				if (nextUI != null) {
					nextUI.execute();
				}
				return;

			case 0:
				System.out.println("\n❌ 로그인 실패! 존재하지 않는 아이디입니다.");
				System.out.println("회원가입 페이지로 이동합니다.");
				new SignUpUI().execute();
				return;

			case 2:
				System.out.println("\n❌ 로그인 실패! 비밀번호가 틀렸습니다.");
				System.out.println("다시 입력해주세요.\n");
				break;

			default:
				System.out.println("\n⚠️ 등록되지 않은 회원입니다.");
				System.out.println("1. 회원가입");
				System.out.println("2. 로그인 다시 시도");
				int choice = scanInt("선택 ▶ ");

				if (choice == 1) {
					new SignUpUI().execute();
				} else {
					new LoginUI(userType).execute(); // 다시 로그인 시도
				}
				return;
			}
		}
	}
}
