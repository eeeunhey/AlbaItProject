package boardVO;

import java.util.Date;

public class ApplyVO {

    private int applyId;
    private int userNo;
    private int postID;
    private String status;
    private Date applyDate;

    private String postTitle; // JOIN 시 공고 제목 포함

    public ApplyVO() {}

    public ApplyVO(int applyId, int userNo, int postID, String status, Date applyDate, String postTitle) {
        this.applyId = applyId;
        this.userNo = userNo;
        this.postID = postID;
        this.status = status;
        this.applyDate = applyDate;
        this.postTitle = postTitle;
    }

    // Getter/Setter
    public int getApplyId() { return applyId; }
    public void setApplyId(int applyId) { this.applyId = applyId; }

    public int getUserNo() { return userNo; }
    public void setUserNo(int userNo) { this.userNo = userNo; }

    public int getPostID() { return postID; }
    public void setPostID(int postID) { this.postID = postID; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getApplyDate() { return applyDate; }
    public void setApplyDate(Date applyDate) { this.applyDate = applyDate; }

    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }

    @Override
    public String toString() {
        return "ApplyVO [applyId=" + applyId + ", userNo=" + userNo + ", postID=" + postID +
               ", status=" + status + ", applyDate=" + applyDate + ", postTitle=" + postTitle + "]";
    }
}
