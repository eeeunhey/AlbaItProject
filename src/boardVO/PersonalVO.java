package boardVO;

public class PersonalVO {
	private int userNo;
	private String userAge;
	private String location;
	private String career;
	private String time; 
	private String intro;
	
	
	
	public PersonalVO(int userNo, String userAge, String location, String career, String time, String intro) {
		super();
		this.userNo = userNo;
		this.userAge = userAge;
		this.location = location;
		this.career = career;
		this.time = time;
		this.intro = intro;
	}



	public int getUserName() {
		return userNo;
	}



	public void setUserName(int userName) {
		this.userNo = userName;
	}



	public String getUserAge() {
		return userAge;
	}



	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getCareer() {
		return career;
	}



	public void setCareer(String career) {
		this.career = career;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getIntro() {
		return intro;
	}



	public void setIntro(String intro) {
		this.intro = intro;
	}



	@Override
	public String toString() {
		return "PersonalVO [userNo=" + userNo + ", userAge=" + userAge + ", location=" + location + ", career=" + career
				+ ", time=" + time + ", intro=" + intro + "]";
	}

	

	
}
