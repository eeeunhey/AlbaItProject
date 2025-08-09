package companyBoardUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import ui.BaseUI;
import boardVO.PostVO;
import dao.PostDAO;

public class PostAddUI extends BaseUI {

    private static final DateTimeFormatter DEADLINE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void execute() throws Exception {
        printHeader();
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("                 📝 채용 공고 작성하기               ");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        // 1) 입력
        String userID    = scanRequired("📌 ID             : ");
        String title     = scanRequired("📌 제목            : ");
        String content   = scanRequired("🖊️  내용            : ");
        String location  = scanRequired("📍 근무 지역       : ");
        String pay       = scanRequired("💰 시급/급여       : ");
        String workTime  = scanRequired("⏰ 근무 시간       : ");
        String deadlineStr;

        LocalDate deadlineDate;
        while (true) {
            deadlineStr = scanRequired("📅 마감일 (yyyy-MM-dd): ");
            try {
                deadlineDate = LocalDate.parse(deadlineStr, DEADLINE_FMT);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("❌ 날짜 형식이 잘못되었습니다! 예: 2025-05-10");
            }
        }

        // 2) 요약 보여주고 확인받기
        System.out.println("\n━━━━━━━━ 입력 내용 확인 ━━━━━━━━");
        System.out.println("ID         : " + userID);
        System.out.println("제목       : " + title);
        System.out.println("내용       : " + content);
        System.out.println("근무 지역  : " + location);
        System.out.println("시급/급여  : " + pay);
        System.out.println("근무 시간  : " + workTime);
        System.out.println("마감일     : " + deadlineStr);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        String confirm = scanStr("이대로 등록할까요? (Y/N): ").trim().toUpperCase();
        if (!confirm.equals("Y")) {
            System.out.println("⏪ 등록을 취소했습니다.");
            return;
        }

        // 3) VO 구성
        PostVO post = new PostVO();
        post.setUserId(userID.trim());
        post.setTitle(title.trim());
        post.setContent(content.trim());
        post.setLocation(location.trim());
        post.setPay(pay.trim());
        post.setWorkTime(workTime.trim());
        // ▶ 현재 프로젝트 구조상 deadline을 String으로 저장한다고 했으니 유지
        post.setDeadline(deadlineStr);

        // 4) DAO 호출
        List<PostVO> postList = new ArrayList<>();
        postList.add(post);

        PostDAO postDao = new PostDAO();
        boolean success = postDao.insertJobPostList(postList);

        System.out.println();
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        if (success) {
            System.out.println("✅ 게시글이 성공적으로 등록되었습니다!");
        } else {
            System.out.println("❌ 게시글 등록에 실패했습니다.");
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    /**
     * 공백/엔터만 넣는 입력 방지용
     */
    private String scanRequired(String label) {
        while (true) {
            String v = scanStr(label);
            if (v != null && !v.trim().isEmpty()) return v;
            System.out.println("⚠️  필수 항목입니다. 다시 입력해 주세요.");
        }
    }
}
