package Model.Entities;
public class UserType{
  private String user_type;
  private Integer clearance;

  public UserType(String user_type, Integer clearance){
    this.user_type = user_type;
    this.clearance = clearance;
  }

  public String getUTUsertype(){
    return user_type;
  }

  public void setUTUsertype(String user_type){
    this.user_type = user_type;
  }

  public Integer getUTClearance(){
    return clearance;
  }

  public void setUTClearance(Integer clearance){
    this.clearance = clearance;
  }
}
