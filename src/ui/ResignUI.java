package ui;

import dao.UserDAO;

public class ResignUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("\n[회원 탈퇴]");

		String userId = BaseUI.loginUserId;
		if (userId == null) {
			System.out.println("⚠️ 로그인 상태가 아닙니다.");
			return;
		}

		String password = scanStr("비밀번호를 입력해주세요: ");
		UserDAO dao = new UserDAO();

		// 비밀번호 검증
		int validateResult = dao.validateUserDetail(userId, password);
		if (validateResult != 1) {
			System.out.println("❌ 비밀번호가 올바르지 않습니다. 탈퇴를 취소합니다.");
			return;
		}

		String confirm = scanStr("정말 탈퇴하시겠습니까? (yes/no): ");
		if (!"yes".equalsIgnoreCase(confirm)) {
			System.out.println("❌ 탈퇴가 취소되었습니다.");
			return;
		}

		boolean result = dao.deleteUser(userId);

		if (result) {
			System.out.println("✅ 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.");
			// 로그아웃 처리
			BaseUI.loginUserId = null;
			BaseUI.loginUserType = null;
		} else {
			System.out.println("❌ 탈퇴에 실패했습니다. 관리자에게 문의해주세요.");
		}
	}
}
