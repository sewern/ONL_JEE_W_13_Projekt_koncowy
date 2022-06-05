package pl.coderslab.finalproj.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="order_items")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name="asort_id")
  private Assortment assortment;

  @Column(nullable = false)
  private Integer quantity;

  @Column(scale=2, precision=3, nullable = false)
  private Double weight;

  @Column(name="completion_date")
  private Date completionDate;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setOrder(Document document) {
    this.order = order;
  }
}
