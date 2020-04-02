package Model.Entities;
public class Constellation{
  private String name;
  private String purpose;

  public Constellation(String name, String purpose){
    this.name = name;
    this.purpose = purpose;
  }

  public String getCName(){
    return name;
  }

  public void setCName(){
    this.name = name;
  }

  public String getCpurpose(){
    return purpose;
  }

  public void setCpurpose(){
    this.purpose = purpose;
  }
}
