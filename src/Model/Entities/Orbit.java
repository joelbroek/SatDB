public class Orbit{
  private Integer id;
  private String type;
  private Float longitude;
  private Integer eccentricity;
  private Integer axis;

  public Orbit (Integer id, String type, Float longitude, Integer eccentricity, Integer axis){
    this.id = id;
    this.type = type;
    this.longitude = longitude;
    this.eccentricity = eccentricity;
    this.axis = axis;
  }

  public Integer getOId(){
    return id;
  }

  public void setOId(Integer id){
    this.id = id;
  }

  public String getOType(){
    return type;
  }

  public void setOType(String type){
    this.type = type;
  }

  public Float getOLongtitude(){
    return longitude;
  }

  public void setOLongtitude(Float longitude){
    this.longitude = longitude;
  }

  public Integer getOEccentricity(){
    return eccentricity;
  }

  public void setOEccentricity(Integer eccentricity){
    this.eccentricity = eccentricity;
  }

  public Integer getOAxis(){
    return axis;
  }

  public void setOAxis(Integer axis){
    this.axis = axis;
  }
}
