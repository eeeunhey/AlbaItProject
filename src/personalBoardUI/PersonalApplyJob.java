package personalBoardUI;
import java.util.List;

import dao.ApplyDAO;
import ui.BaseUI;

public class PersonalApplyJob extends BaseUI {

	@Override
	public void execute() throws Exception {
		  ApplyDAO dao = new ApplyDAO();
		    List<String[]> list = dao.getMyApplies(BaseUI.loginUserId);

		    System.out.println("지원현황");
		    System.out.println("번호 | 공고번호 | 제목 | 상태 | 지원일");
		    for (String[] row : list) {
		        System.out.printf("%s | %s | %s | %s | %s\n",
		            row[0], row[1], row[2], row[3], row[4]);
		
	}
}
