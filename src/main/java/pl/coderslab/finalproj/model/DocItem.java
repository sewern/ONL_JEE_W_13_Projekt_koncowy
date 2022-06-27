package pl.coderslab.finalproj.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name="doc_items")
public class DocItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //@Column(columnDefinition = "BIGINT")
  //private BigInteger id;
  private Long id;

  @Column//(nullable = false)
  private String name;

  //@ManyToOne(fetch = FetchType.EAGER)
  //@JoinColumn(insertable = false, updatable = false)
  //private Doc doc;

  //public DocItem() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "DocItem{" +
      "id=" + id + '\'' +
      ", name='" + name +
      '}';
  }
}

/*
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
  private BigDecimal weight;


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
*/