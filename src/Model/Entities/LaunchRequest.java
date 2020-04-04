public class LaunchRequest {
  private Integer id;
  private Integer sat_id;
  private Integer agency_id;
  private boolean is_approved;
  private String scheduled_date;
  private String launch_system;

  public LaunchRequest(Integer id, boolean is_approved, String launch_system, Integer sat_id, Integer agency_id, String scheduled_date){
    this.sat_id = sat_id;
    this.agency_id = agency_id;
    this.is_approved = is_approved;
    this.scheduled_date = scheduled_date;
    this.launch_system = launch_system;
  }
  public Integer getLRId(){
    return id;
  }

  public void setLRID(Integer id){
    this.id = id;
  }

  public Integer getLRSatId(){
    return sat_id;
  }

  public void setLRSatId(Integer sat_id){
    this.sat_id = sat_id;
  }

  public Integer getLRAgencyId(){
    return agency_id;
  }

  public void setLRSAgencyId(Integer agency_id){
    this.agency_id = agency_id;
  }
  public boolean getLRis_approved(){
    return is_approved;
  }

  public void setLRIs_approved(boolean is_approved){
    this.is_approved = is_approved;
  }

  public String getLRScheduledDate(){
    return scheduled_date;
  }

  public void setLRScheduledDate(){
    this.scheduled_date = scheduled_date;
  }

  public String getLaunchSystem(){
    return launch_system;
  }

  public void setLaunchSystem(String launch_system){
    this.launch_system = launch_system;
  }
}
