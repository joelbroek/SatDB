public class Location{
  private Float latitude;
  private Float longitude;
  private String location;

  public Location(Float latitude, Float longitude, String location){
    this.latitude = latitude;
    this.longitude = longitude;
    this.location = location;
  }

  public Float getLatitude(){
    return latitude;
  }

  public void setLatitude(Float latitude){
    this.latitude = latitude;
  }

  public Float getLongtitude(){
    return longitude;
  }

  public void setLongtitude(Float longitude){
    this.longitude = longitude;
  }

  public String getLocation(){
    return location;
  }

  public void setLocation(String location){
    this.location = location;
  }
}
