package ui;

import accountUI.AccountUI;
import exception.ChoiceOutOfBoundException;

public abstract class BaseBoardUI extends BaseUI {

	// 공용 메뉴 항목
	private static final String[] COMMON_ITEMS = { "전체게시글 조회", // 1번
			"게시글 조회" // 2번
	};

	// 공통 + 개별 메뉴를 합쳐서 출력
	private String[] mergeMenuItems(String[] specificItems) {
		String[] merged = new String[COMMON_ITEMS.length + specificItems.length];
		System.arraycopy(COMMON_ITEMS, 0, merged, 0, COMMON_ITEMS.length);
		System.arraycopy(specificItems, 0, merged, COMMON_ITEMS.length, specificItems.length);
		return merged;
	}

	protected abstract String getMenuTitle();

	protected abstract String[] getSpecificMenuItems(); // 공용 외의 개별 메뉴

	protected abstract IBoardUI getSpecificUI(String choice) throws ChoiceOutOfBoundException;

	// 선택된 번호에 따라 UI 객체 리턴
	protected IBoardUI getUIForChoice(String choice) throws ChoiceOutOfBoundException {
		switch (choice) {
		case "1":
			return new SeachAllUI();
		case "2":
			return new SearchOneUI();
		default:
			return getSpecificUI(choice);
		}
	}

	private String menu() {
		printHeader();
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("            " + getMenuTitle());
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

		String[] items = mergeMenuItems(getSpecificMenuItems());
		for (int i = 0; i < items.length; i++) {
			System.out.println("\t" + (i + 1) + ". " + items[i]);
		}
		System.out.println("\t0. 종료");
		System.out.println("-----------------------------------");
		return scanStr("\t항목을 선택하세요 : ");
	}

	@Override
	public void execute() throws Exception {
		while (true) {
			try {
				String choice = menu();

				if (choice.equals("0")) {
					// 종료 시 하위 선택
					while (true) {
						System.out.println("\n📍 무엇을 하시겠습니까?");
						System.out.println("1. 🔁 로그인 페이지로 돌아가기");
						System.out.println("2. ❌ 프로그램 종료");
						String subChoice = scanStr("선택 > ");

						if (subChoice.equals("1")) {
							new AccountUI().execute();
							return;
						} else if (subChoice.equals("2")) {
							new ExitUI().execute();
							return;
						} else {
							System.out.println("⚠️ 1 또는 2 중에서 선택해주세요.");
						}
					}
				}

				IBoardUI ui = getUIForChoice(choice);
				ui.execute();

			} catch (ChoiceOutOfBoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
