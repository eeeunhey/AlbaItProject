package personalBoardUI;

import java.util.List;

import boardVO.ApplyVO;
import dao.ApplyDAO;
import ui.BaseUI;

public class PersonalApplyJob extends BaseUI {

	@Override
	public void execute() throws Exception {
	    ApplyDAO dao = new ApplyDAO();
	    List<ApplyVO> list = dao.getMyApplies(BaseUI.loginUserId);

	    System.out.println();
	    System.out.println("📄 나의 지원 현황");
	    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	    System.out.printf("%-5s %-8s %-20s %-8s %-12s\n", "번호", "공고번호", "제목", "상태", "지원일");
	    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

	    if (list.isEmpty()) {
	        System.out.println("📭 지원한 내역이 없습니다.");
	    } else {
	        for (ApplyVO row : list) {
	        	
	        	
	            System.out.printf("%-5d %-8d %-20s %-8s %-12tF\n",
	                    row.getApplyId(),
	                    row.getPostID(),
	                    row.getPostTitle(),
	                    row.getStatus(),
	                    row.getApplyDate());
	            
	        }
	    }

	    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	}

}
