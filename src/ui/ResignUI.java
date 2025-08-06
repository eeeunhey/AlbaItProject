package ui;

import dao.UserDAO;

public class ResignUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("\n[회원 탈퇴]");

		String userId = BaseUI.loginUserId;
		String confirm = scanStr("정말 탈퇴하시겠습니까? (yes/no): ");

		if (!"yes".equalsIgnoreCase(confirm)) {
			System.out.println("❌ 탈퇴가 취소되었습니다.");
			return;
		}

		UserDAO dao = new UserDAO();
		boolean result = dao.deleteUser(userId);

		if (result) {
			System.out.println("✅ 탈퇴가 완료되었습니다.");
			BaseUI.loginUserId = null;
			BaseUI.loginUserType = null;
		} else {
			System.out.println("❌ 탈퇴에 실패했습니다.");
		}
	}

}
