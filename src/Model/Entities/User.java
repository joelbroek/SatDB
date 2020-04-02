public class User{

  private String username;
  private String user_type;
  private String password;

  public Customer(String username, String user_type, String password) {
    this.username = username;
    this.user_type = user_type;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username){
    this.username = username;
  }

  public String getUser_type()m {
    return user_type;
  }

  public void setUser_type(String user_type){
    this.user_type = user_type;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(){
    this.password = password;
  }
}
