package boardVO;

public class UserVO {

	private int userNo;
	private String userId;
	private String password;
	private String name; 
	private String nickname;
	private String userType; // 'A', 'U', 'C'
	private String status;
	private String createDate;

	// 생성자
	public UserVO(int userNo, String userId, String password, String name ,String nickname, String userType, String status,
			String createDate) {
		this.userNo = userNo;
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.nickname = nickname;
		this.userType = userType;
		this.status = status;
		this.createDate = createDate;
	}

	// getter
	public int getUserNo() {
		return userNo;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserType() {
		return userType;
	}

	public String getStatus() {
		return status;
	}

	public String getCreateDate() {
		return createDate;
	}

	@Override
	public String toString() {
		return "UserVO [userNo=" + userNo + ", userId=" + userId + ", password=" + password + ", name=" + name
				+ ", nickname=" + nickname + ", userType=" + userType + ", status=" + status + ", createDate="
				+ createDate + "]";
	}

	
}
