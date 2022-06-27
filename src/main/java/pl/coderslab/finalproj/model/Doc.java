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
  // Parametr add -wskazuje żądanie (tutaj tylko zamiar !) dodania nowego wiersza;
  // parametr editIdxNew nie ma wtedy znaczenia -standardowo jest null na wejściu.
  // Parametr editIdxNew -wskazuje żądnie (tutaj tylko zamiar !) przełączenia aktualnie edytowalnego wiersza;
  // parametru add powinien być wtedy null, ponieważ w przeciwnym wypadku parametr editIdxNew zostanie zignorowany.
  // Parametr editIdxNew przekazuje następującą wartość:
  // -nowy indeks wiersza, który ma otrzymać edycję (gdy: editIdxNew< rozmiar listy),
  // -rozmiar listy, jeśli edycja listy pozycji ma zostać zakończona.
  // Zwracana wartość:
  // -pierwotna wartość parametru editIdxNew, ew. skorygowana wskutek usuniecia pustego wiersza, gdy: add== null,
  // -rozmiar listy (po ew. korekcie wskutek usunięcia pustego wiersza), gdy: add != null,
  // -null, gdy żądanie dodania/ przełączenia wiersza ma zostać anulowane -bez zglaszania błędu,
  // -wartość -1, gdy żądamnie dodania/ przełącznenia ma zostać anulowane -ze zgłoszeniem błędu.
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
          // żeby za chwilę, już w metodzie kontrolera, wywołać moetodę, która doda (na końcu listy) nowy pusty wiersz.
          // Stąd anulowanie takiej sekwencji zdarzeń już teraz -poprzez zwrócenie do kontrolera wartości null.
          return null;
          // Ale pozostawienie takiej sytuacji bez reakcji też zadziałałoby poprawnie.
        }

        if (editIdx<= editIdxNew && editIdxNew> 0)
          --editIdxNew;
        // Tu akurat wystarczyłby w zupełności warunek:
        // editIdx< editIdxNew
        // ponieważ przypadek:
        // editIdx== editIdxNew
        // jest raczej niemożliwy, gdyż oznaczałoby to próbę ustawienia edycji dla już edytowalnego wiersza.

        // Usunięcie pustego, aktualnie edytowalnego wiersza
        // i (tymczasowa) blokada edycji.
        items.remove(editIdx);
        editIdx= items.size();

        if (add == null && editIdxNew == editIdx)
          // Przypadek żądania blokady edycji przez użytkownika w połączeniu z
          // koniecznością ustawienia tymczasowej blokady edycji wskutek automatycznego usunięcia
          // aktualnie edytowalnego (pustego) wiersza.
          // Blokada tymczasowa przejdzie w trwałą -możemy anulować dalsze akcje w metodzie kontrolera.
          editIdxNew= null;
          // Ale pozostawienie takiej sytuacji bez reakcji też zadziałałoby poprawnie.
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