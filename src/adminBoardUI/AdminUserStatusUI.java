package adminBoardUI;
import dao.AdminUserDAO;
import ui.BaseUI;

public class AdminUserStatusUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("━━━━━━━━━━━━ 회원 상태 변경 ━━━━━━━━━━━━");

		int userId = scanInt("상태를 변경할 회원 ID를 입력하세요: ");
		String newStatus = scanStr("새로운 상태 입력 (정상 / 정지 / 탈퇴): ");

		if (!newStatus.equals("정상") && !newStatus.equals("정지") && !newStatus.equals("탈퇴")) {
			System.out.println("⚠️ 잘못된 상태입니다. [정상 / 정지 / 탈퇴] 중 하나를 입력하세요.");
			return;
		}

		AdminUserDAO dao = new AdminUserDAO();
		boolean success = dao.updateUserStatus(userId, newStatus);

		if (success) {
			System.out.println("✅ 회원 상태가 성공적으로 변경되었습니다.");
		} else {
			System.out.println("❌ 회원 상태 변경 실패. 해당 ID가 존재하지 않거나 오류가 발생했습니다.");
		}
	}
}