package adminBoardUI;

import boardVO.AdminUserVO;
import dao.AdminUserDAO;
import ui.BaseUI;

import java.util.List;

public class AdminUserListUI extends BaseUI {
    @Override
    public void execute() throws Exception {
        AdminUserDAO dao = new AdminUserDAO();
        List<AdminUserVO> users = dao.getAllUsers();

        System.out.println("━━━━━━━━━━━━━━━━━ 전체 회원 목록 ━━━━━━━━━━━━━━━━━");
        for (AdminUserVO user : users) {
            System.out.printf("ID: %d | 이름: %s | 유형: %s | 상태: %s | 신고수: %d\n",
                    user.getUserId(), user.getNickname(), user.getUserType(),
                    user.getStatus(), user.getReportCount());
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
