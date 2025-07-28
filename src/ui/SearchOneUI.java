package ui;

import boardVO.PostVO;

public class SearchOneUI extends BaseUI {
	// 하나 선택해서 조회하는 UI

	@Override
	public void execute() throws Exception {

		int no = scanInt("조회할 글번호를 입력하세요 : ");

		PostVO postNo = boardService.searchBoardByNo(no);

		if (postNo == null) {
			System.out.println("입력하신 게시글 [" + no + "]번에 해당하는 게시글이 없습니다");
			return;
		}

		System.out.println("-------------------------------");
		System.out.println("\t글번호 : " + postNo.getPostId());
		System.out.println("\t작성자 : " + postNo.getUserId());
		System.out.println("\t제목 : " + postNo.getTitle());
		System.out.println("\t내용 : " + postNo.getContent());
		System.out.println("\t근무지 : " + postNo.getLocation());
		System.out.println("\t급여 : " + postNo.getPay());
		System.out.println("\t근무시간 : " + postNo.getWorkTime());
		System.out.println("\t마감일 : " + postNo.getDeadline());
		System.out.println("\t등록일 : " + postNo.getCreatedAt());
		System.out.println("-------------------------------");

	}
	// 글번호가 있는 경우 없는 경우가 발생할 경우 처리하기

}
