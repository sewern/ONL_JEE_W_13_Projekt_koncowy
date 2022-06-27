package pl.coderslab.finalproj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.finalproj.model.Doc;
import pl.coderslab.finalproj.model.DocItem;
import pl.coderslab.finalproj.repository.DocDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/doc")
public class DocController {
  private final DocDao docDao;

  public DocController(DocDao docDao) {
    this.docDao = docDao;
  }

  public String complete(Doc doc){
    if (doc.getItems().isEmpty())
      return "Brak pozycji!";
    return null;
  }

  public String completeItem(DocItem docItem){
    //if ("Test".equals(docItem.getName()))
    //  return "Test błędu!";
    return null;
  }

  @RequestMapping(value = "")
  String list(Model model) {
    model.addAttribute("docs", docDao.findAll());
    return "/doc/list";
  }

  @RequestMapping(value = {"/show/{id}", "/show/{id}/{option}"})
  String show(@PathVariable Long id, @PathVariable(required=false) String option, Model model){
    Doc doc= docDao.get(id);
    model.addAttribute("doc", doc);
    /*if (option != null)
      model.addAttribute("message",
        switch (option) {
          case "delete" -> "Potwierdź usunięcie";
          case "success" -> "Sukces";
          default -> throw new IllegalArgumentException(option);
        });*/
    model.addAttribute("option", option);
    return "/doc/show";
  }

  @RequestMapping(value = {"/show/{id}/delete"}, method = RequestMethod.POST, params= "delete")
  String delete(@PathVariable Long id){
    Doc doc= docDao.get(id);
    docDao.delete(doc);
    // Powrót do listy
    return "redirect:/doc";
  }

  @RequestMapping(value = {"/edit", "/edit/{id}"})
    //@RequestMapping(value = "/edit/{id}")
  String edit(@PathVariable(required=false) Long id, Model model) {
    // Parametr id tylko dla szybkiego sprawdzenia poza głównym menu. Edycji dokumentu nie przewidujemy.
    /*if (id != null) {
      Doc doc= docDao.get(id);
      docDao.delete(doc);
      return "/doc/show";
    }*/

    model.addAttribute("doc", id== null ? new Doc(): docDao.get(id));
    return "/doc/edit";
  }

  // Żądanie dodania nowego wiersza, żądanie zmiany aktualnie edytowalnego wiersza lub żądanie zakończenia edycji.
  @RequestMapping(value = "/edit", method = RequestMethod.POST, params= {"!remove", "!resolve"})
  public String modify(Model model, @Valid Doc doc, BindingResult bindResult,
                       @RequestParam(required=false) String add,
                       @RequestParam(required=false) Integer editIdxNew) {
    if (editIdxNew != null && editIdxNew == doc.getSize()) {
      // Żądanie zakończenia edycji listy pozycji.
      String completeError = complete(doc);
      if (completeError != null) {
        model.addAttribute("completeError", completeError);
        return "/doc/edit";
      }
    }

    editIdxNew= doc.itemSwitchIfEmpty(add, editIdxNew);
    if (editIdxNew == null) {
      // Dalsze przetwarzanie anulowane.
      return "/doc/edit";
    }
    if (editIdxNew ==-1){
      model.addAttribute("completeError", "Proszę uzupełnić wiersz!");
      return "/doc/edit";
    }

    DocItem docItem= doc.getEditItem();
    if (docItem != null){
      // Aktualnie jakiś wiersz jest edytowalny.
      // Validate result.
      if (bindResult.hasErrors()) {
        return "/doc/edit";
      }
      // If no errors, then complete for current item with extended validation.
      // model.addAttribute("completeError", null);
      String completeItemError= completeItem(docItem);
      if (completeItemError!= null) {
        model.addAttribute("completeError", completeItemError);
        return "/doc/edit";
      }
    };

    if (add != null)
      // Add new row.
      doc.itemAdd();
    // Change current editable row.
    doc.setEditIdx(editIdxNew);
    return "/doc/edit";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST, params= "remove")
  public String removeItem(Doc doc,
                       @RequestParam(required=false) Integer remove) {
    // Remove row(s) without any validation.
    if (remove == null) {
      doc.itemRemoveAll();
    } else {
      doc.itemRemove(remove);
    }
    return "/doc/edit";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST, params= {"resolve=Save"})
  public String save(Model model, @Valid Doc doc, BindingResult bindResult) {
    String completeError= complete(doc);
    if (completeError!= null) {
      model.addAttribute("completeError", completeError);
      return "/doc/edit";
    }

    // Validate result.
    if (bindResult.hasErrors()) {
      return "/doc/edit";
    }
    // If no errors, then complete for all items with extended validation.
    // model.addAttribute("completeError", null);
    for (DocItem docItem: doc.getItems()) {
      String completeItemError= completeItem(docItem);
      if (completeItemError!= null){
        model.addAttribute("completeError", completeItemError);
        return "/doc/edit";
      };
    }

    // Save header and all items and redirect to success view.
    docDao.save(doc);
    /*
    for (DocItem docItem: doc.getItems()) {
      //docItem.setNextId();
    }
    */

    //return "/doc/show";
    return "redirect:/doc/show/"+ doc.getId().toString()+ "/success";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST, params= {"resolve=Cancel"})
  public String cancel() {
    // Powrót do listy
    return "redirect:/doc";
  }

}
