public class Satellite{
  private Integer id;
  private String name;
  private Integer orbit_id;
  private String orbit_type;
  private String constellation;

  public Satellite(Integer id, String name, Integer orbit_id, String orbit_type, String constellation){
    this.id = id;
    this.name = name;
    this.orbit_id = orbit_id;
    this.orbit_type =  orbit_type;
    this.constellation = constellation;
  }

  public Integer getSId(){
    return id;
  }

  public void setSId(Integer id){
    this.id = id;
  }

  public String getSName(){
    return name;
  }

  public void setSName(String name){
    this.name = name;
  }

  public Integer getSOrbitID(){
    return orbit_id;
  }

  public void setOrbitID(Integer orbit_id){
    this.orbit_id = orbit_id;
  }

  public String getSOrbitType(){
    return orbit_type;
  }

  public void setSOrbitType(String orbit_type){
    this.orbit_type = orbit_type;
  }

  public String getSConstellation(){
    return constellation;
  }

  public void setSConstellation(String constellation){
    this.constellation =  constellation;
  }
}
