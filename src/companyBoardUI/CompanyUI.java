package companyBoardUI;


import exception.ChoiceOutOfBoundException;
import ui.AddUI;
import ui.BaseUI;
import ui.DeleteUI;
import ui.ExitUI;
import ui.IBoardUI;
import ui.SeachAllUI;
import ui.SearchOneUI;
import ui.UpdateUI;

public class CompanyUI extends BaseUI {
	
	private String menu () {
		printHeader();
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("               기업 회원 메뉴 선택                ");
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		System.out.println("\t1. 전체게시글 조회");
		System.out.println("\t2. 게시글 보기");
		System.out.println("\t3. 채용공고 등록");
		System.out.println("\t4. 지원자 조회");
		System.out.println("\t5. 게시글 등록");
		System.out.println("\t6. 게시글 수정");
		System.out.println("\t7. 게시글 삭제");
		System.out.println("\t0. 종료");
		System.out.println("-----------------------------------");
		String type = scanStr("\t항목을 선택하세요 : ");

		return type;
	}

	@Override
	public void execute() throws Exception{

		
		while (true) {

			try {
			String choice = menu();
			IBoardUI ui = null;

			switch (choice) {

			case "1":
				ui = new SeachAllUI();
				
				break;
			case "2":
				ui = new SearchOneUI();
				
				break;
			case "3":
				ui = new PostAddUI();
				break;
			case "4":
				ui = new AddUI();
				
				break;
			case "5":
				ui = new UpdateUI();
				
				break;
			case "6":
				ui = new DeleteUI();
				//System.out.println("게시글 삭제 선택");
				break;
			case "0":
				ui = new ExitUI();
				//ExitUI exit = new ExitUI();
				//ExitUI 클래스 호출해 객체를 생성한다 (종료하기 기능)
				//exit.execute();
				// ExitUI에서 재정의한 execute를 호출한다
				
				/**
				 여기 해당하는 값에 명령어를 처리해야 하는데 너무 길다
				 메뉴가 늘면 늘수록 보드 UI가 어마무시하게 늘어나겠다
				 그럼 클래스로 분리해야 되겠네 그럼 종료 클래스 만들자
				 */
				
				/** 종료 행동만들기
				System.out.println("-----------------------------");
				System.out.println("\t프로그램을 종료합니다.");
				System.out.println("-----------------------------");
				System.exit(0);
				// 해당하는 종료값이 들어오면 종료되는 메서드이다
				*/
				break;
			}
			if(ui != null) {
				// 번호 선택 시 ui에 객체값이 들어오기 때문에 null 상태가 아니다
				ui.execute();
				// 선택 시 각각 오버라이드한 객체의 execute 값을 가져온다
			} else {
				
				throw new ChoiceOutOfBoundException("메뉴에서 선택할 수 없는 숫자입니다.");
			     //System.out.println("\t 잘못선택하셨습니다.");
			     // 없는 번호를 선택할 경우 null 값이 들어오고 잘못 선택했습니다 출력
			}
			} catch (ChoiceOutOfBoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}

