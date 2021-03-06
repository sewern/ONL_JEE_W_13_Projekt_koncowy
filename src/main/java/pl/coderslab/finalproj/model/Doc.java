package pl.coderslab.finalproj.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "docs")
public class Doc {
  enum EmptyItemManage{ NONE, PREVENT, REMOVE_IFLAST, REMOVE_ALWAYS}

  //private final static boolean PREVENT_EMPTY_LIST= true;

  @Transient
  private final static EmptyItemManage EMPTY_ITEM_MANAGE= EmptyItemManage.REMOVE_IFLAST;

  //private EmptyItemManage EMPTY_ITEM_MANAGE= EmptyItemManage.REMOVE_IFLAST;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10)
  private String number;

  //@Column(name="issue_date", nullable = false)
  @Column(name="issue_date")
  private String issueDate;

  //@Column(name="issue_date", nullable = false)
  private String issuer;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  //@OneToMany(mappedBy="doc", cascade = CascadeType.ALL, orphanRemoval = true)
  //@JoinColumn(name="id_test")
  //private List<DocItem> items= new ArrayList<>();
  private List<DocItem> items= new ArrayList<DocItem>();

  @Transient
  private int editIdx;


  //public Doc() { }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(String issueDate) {
    this.issueDate = issueDate;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public List<DocItem> getItems() {
    return items;
  }

  public void setItems(List<DocItem> items) {
    this.items = items;
  }

  public int getSize() { return items.size(); }

  public int getEditIdx() { return editIdx; }

  public void setEditIdx(int editIdx) { this.editIdx = editIdx; }

  public DocItem getEditItem() { return editIdx< items.size()? items.get(editIdx): null; }

  public void itemAdd(){
    items.add(new DocItem());
    //editIdx= items.size()- 1;
  }

  // Metoda dedykowana dla metody modify kontrolera.
  // -----------------------------------------------
  // Parametr add -wskazuje ????danie (tutaj tylko zamiar !) dodania nowego wiersza;
  // parametr editIdxNew nie ma wtedy znaczenia -standardowo jest null na wej??ciu.
  // Parametr editIdxNew -wskazuje ????dnie (tutaj tylko zamiar !) prze????czenia aktualnie edytowalnego wiersza;
  // parametru add powinien by?? wtedy null, poniewa?? w przeciwnym wypadku parametr editIdxNew zostanie zignorowany.
  // Parametr editIdxNew przekazuje nast??puj??c?? warto????:
  // -nowy indeks wiersza, kt??ry ma otrzyma?? edycj?? (gdy: editIdxNew< rozmiar listy),
  // -rozmiar listy, je??li edycja listy pozycji ma zosta?? zako??czona.
  // Zwracana warto????:
  // -pierwotna warto???? parametru editIdxNew, ew. skorygowana wskutek usuniecia pustego wiersza, gdy: add== null,
  // -rozmiar listy (po ew. korekcie wskutek usuni??cia pustego wiersza), gdy: add != null,
  // -null, gdy ????danie dodania/ prze????czenia wiersza ma zosta?? anulowane -bez zglaszania b????du,
  // -warto???? -1, gdy ????damnie dodania/ prze????cznenia ma zosta?? anulowane -ze zg??oszeniem b????du.
  public Integer itemSwitchIfEmpty( String add, Integer editIdxNew) {
    if (add != null) {
      // Zamiar dodania nowego wiersza.
      editIdxNew= items.size();
    }

    if (!items.isEmpty() && EMPTY_ITEM_MANAGE != EmptyItemManage.NONE){
      DocItem item= getEditItem();
      if (item!= null && item.getId()== null && item.getName().isEmpty()){
        if (EMPTY_ITEM_MANAGE == EmptyItemManage.PREVENT)
          return -1;

        boolean isEditItemLast= (editIdx== items.size()- 1);
        if (EMPTY_ITEM_MANAGE == EmptyItemManage.REMOVE_IFLAST && !isEditItemLast)
          return -1;

        if (add != null && isEditItemLast){
          // Sytuacja specyficzna: nie ma wielkiego sensu usuwanie pustego (ostatniego) wiersza,
          // ??eby za chwil??, ju?? w metodzie kontrolera, wywo??a?? moetod??, kt??ra doda (na ko??cu listy) nowy pusty wiersz.
          // St??d anulowanie takiej sekwencji zdarze?? ju?? teraz -poprzez zwr??cenie do kontrolera warto??ci null.
          return null;
          // Ale pozostawienie takiej sytuacji bez reakcji te?? zadzia??a??oby poprawnie.
        }

        if (editIdx<= editIdxNew && editIdxNew> 0)
          --editIdxNew;
        // Tu akurat wystarczy??by w zupe??no??ci warunek:
        // editIdx< editIdxNew
        // poniewa?? przypadek:
        // editIdx== editIdxNew
        // jest raczej niemo??liwy, gdy?? oznacza??oby to pr??b?? ustawienia edycji dla ju?? edytowalnego wiersza.

        // Usuni??cie pustego, aktualnie edytowalnego wiersza
        // i (tymczasowa) blokada edycji.
        items.remove(editIdx);
        editIdx= items.size();

        if (add == null && editIdxNew == editIdx)
          // Przypadek ????dania blokady edycji przez u??ytkownika w po????czeniu z
          // konieczno??ci?? ustawienia tymczasowej blokady edycji wskutek automatycznego usuni??cia
          // aktualnie edytowalnego (pustego) wiersza.
          // Blokada tymczasowa przejdzie w trwa???? -mo??emy anulowa?? dalsze akcje w metodzie kontrolera.
          editIdxNew= null;
          // Ale pozostawienie takiej sytuacji bez reakcji te?? zadzia??a??oby poprawnie.
      }
    }

    return editIdxNew;
  }

  public void itemRemove(int removeIdx){
    if (items.isEmpty()) return;

    items.remove(removeIdx);
    if (removeIdx<= editIdx && editIdx> 0)
      --editIdx;
  }

  public void itemRemoveAll() {
    items.clear();
    //items.removeAll(items);
    //items= new ArrayList<DocItem>();
    editIdx = 0;
  }

}