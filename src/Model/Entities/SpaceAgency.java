public class SpaceAgency{
  private String name; 
  private Integer id;

  public SpaceAgency(String name, Integer id){
    this.name = name;
    this.id = id;
  }

  public String getSAName(){
    return name;
  }

  public void setSAName(String name){
    this.name = name;
  }

  public Integer getSAId(){
    return id;
  }

  public void setSAId(Integer id){
    this.id = id;
  }
}
