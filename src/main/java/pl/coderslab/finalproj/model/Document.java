package pl.coderslab.finalproj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "documents")
public class Document {
  @Id
  @Column(length = 10)
  private String number;

  @Column(nullable = false)
  private DocType docType;

  @Column(name="issue_date", nullable = false)
  private Date issueDate;

  @OneToMany(mappedBy = "document")
  private List<DocItem> docItems;


  public String getNumber() {
    return number;
  }

  public void setNumber(Long id) {
    this.number = number;
  }

  public DocType getName() {
    return docType;
  }

  public void setDocType(String name) {
    this.docType = docType;
  }
}
