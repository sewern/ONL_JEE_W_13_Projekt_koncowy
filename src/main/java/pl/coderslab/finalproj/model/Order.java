package pl.coderslab.finalproj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @Column(length = 10)
  private String number;

  @Column(name="issue_date", nullable = false)
  private Date issueDate;

  @Column(length = 50)
  private String customer;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;


  public String getNumber() {
    return number;
  }

  public void setNumber(Long id) {
    this.number = number;
  }
}
