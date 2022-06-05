package pl.coderslab.finalproj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {
  @Id
  @Column(length = 10)
  private String code;

  @Column(length = 10)
  private String place;

  @Column(length = 10)
  private String sector;

  public String getCode() {
    return code;
  }

  public void setCode(Long id) {
    this.code = code;
  }
}
