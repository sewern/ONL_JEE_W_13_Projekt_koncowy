package pl.coderslab.finalproj.repository;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.finalproj.model.Doc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DocDao {
  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void save(Doc doc) {
    entityManager.persist(doc);
  }

  //@Transactional
  //public void update(Doc doc) {
  //  entityManager.merge(doc);
  //}

  @Transactional
  public void delete(Doc doc) {
    entityManager.remove(entityManager.contains(doc)? doc : entityManager.merge(doc));
  }

  @Transactional
  public Doc get(Long id) {
    Doc doc = entityManager.find(Doc.class, id);
    Hibernate.initialize(doc.getItems());
    //doc.setItems(findAllDocItem(doc));
    doc.setEditIdx(doc.getSize());
    return doc;
  }

  public List<Doc> findAll() {
    TypedQuery<Doc> query = entityManager.createQuery("SELECT d from Doc d", Doc.class);
    return query.getResultList();
  }

  //public List<DocItem> findAllDocItem( Doc doc) {
  //  TypedQuery<DocItem> query = entityManager.createQuery("SELECT di from DocItem di where di.doc= :doc", DocItem.class);
  //  query.setParameter("doc", doc);
  //  return query.getResultList();
  //}

}
