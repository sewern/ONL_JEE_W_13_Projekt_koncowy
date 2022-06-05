package pl.coderslab.finalproj.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "assortments")
public class Assortment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private AssortType assortType;

  @Column
  private Short thickness;

  @Column
  private Integer width;

  @Column
  private Integer length;

  @Column(scale=2, precision=3)
  private Double weight;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
