package Model.Entities;
public class GroundStation{
  private Float latitude;
  private Float longitude;
  private String name;

public GroundStation(Float latitude, Float longitude, String name){
  this.latitude = latitude;
  this.longitude = longitude;
  this.name = name;
}

public String getGSName(){
  return name;
}

public void setGSName(String name){
  this.name = name;
}

public Float getGSLatitude(){
  return latitude;
}

public void setGSLatitude(Float latitude){
  this.latitude = latitude;
}

public Float getGSLongtitude(){
  return longitude;
}

public void setGSLongtitude(Float longitude){
  this.longitude = longitude;
}
}
