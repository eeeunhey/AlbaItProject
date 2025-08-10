package accountUI;

import boardVO.UserVO;
import dao.UserDAO;
import ui.BaseUI;

public class SignUpUI extends BaseUI {
	private UserDAO dao = new UserDAO();

	@Override
	public void execute() throws Exception {
		printHeader();
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.printf("                 회원 가입 페이지                   \n");
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

		System.out.println("회원 유형을 선택하세요:");
		String typeNum = scanStr("1. 개인 회원    2. 기업 회원 : ");
		String id = scanStr("🆔 아이디 : ");
		if (dao.isDuplicate(id)) {
			System.out.println("❌ 이미 존재하는 아이디입니다. 다시 시도해주세요.");
			return;
		}
		String pw = scanStr("🔑 비밀번호 : ");
		String nickname = scanStr("👤 닉네임 : ");
		String name = scanStr("👤 이름(실명) : ");
		if (dao.isDuplicateNickname(nickname)) {
			System.out.println("❌ 이미 존재하는 닉네임입니다. 다시 시도해주세요.");
			return;
		}

		String userType = null;
		if (typeNum.equals("1")) {
			userType = "U";
		} else if (typeNum.equals("2")) {
			userType = "C";
		} else {
			System.out.println("잘못된 입력입니다.");
			return;
		}

		// activeYn = "Y" 기본 활성 상태
		UserVO newUser = new UserVO(0, id, pw, name, nickname, userType, "Y", null);
		boolean success = dao.insertUser(newUser);

		if (success) {
			System.out.println("✅ 회원가입 성공!");
		} else {
			System.out.println("❌ 회원가입 실패 (이미 회원으로 등록되어 있습니다)");
		}
		System.out.println("\n⏪ 로그인 페이지로 돌아갑니다...\n");
		Thread.sleep(1000);
		new AccountUI().execute();
	}
}
