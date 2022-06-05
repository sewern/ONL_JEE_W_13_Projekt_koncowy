package pl.coderslab.finalproj.model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="doc_items")
public class DocItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="doc_id")
  private Document document;

  @ManyToOne
  @JoinColumn(name="asort_id")
  private Assortment assortment;

  @Column(nullable = false)
  private Integer quantity;

  @Column(scale=2, precision=3, nullable = false)
  private Double weight;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setDocument(Document document) {
    this.document = document;
  }
}
