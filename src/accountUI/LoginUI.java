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
				break;

			case 2:
				System.out.println("❌ 로그인 실패! 비밀번호가 틀렸습니다.");
				break;

			case 3:
				System.out.println("❌ 로그인 실패! 회원유형이 다릅니다.");
				
				break;

			default:
				System.out.println("⚠️ 등록되지 않은 회원입니다. 회원가입창으로 이동합니다.");
				new SignUpUI().execute();
				break;
				
			}
			System.out.println("───────────────────────────────\n\n");
			
		}
		
	}
}
