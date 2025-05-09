package boardVO;

public class PostVO {
	private int postId;
	private String userId;
	private String title;
	private String content;
	private String location;
	private String pay;
	private String workTime;
	private String deadline;
	private String createdAt;

	public PostVO() {
		super();
	}
	
	public PostVO(String userId, String title, String content, String location, String pay, String workTime, String deadline) {
		super();
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.location = location;
		this.pay = pay;
		this.workTime = workTime;
		this.deadline = deadline;
	}

	public PostVO(int postId, String userId, String title, String content, String location, String pay, String workTime,
			String deadline, String createdAt) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.location = location;
		this.pay = pay;
		this.workTime = workTime;
		this.deadline = deadline;
		this.createdAt = createdAt;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "PostVO [postId=" + postId + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", location=" + location + ", pay=" + pay + ", workTime=" + workTime + ", deadline=" + deadline
				+ ", createdAt=" + createdAt + "]";
	}

	
}
