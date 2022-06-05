package pl.coderslab.finalproj.model;

import javax.persistence.*;

@Entity
@Table(name = "steel_grades")
public class SteelGrade {
  @Id
  @Column(length = 10)
  private String code;

  @Column(nullable = false)
  private AssortType assortType;


  public String getCode() {
    return code;
  }

  public void setCode(Long id) {
    this.code = code;
  }
}
