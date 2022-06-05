package pl.coderslab.finalproj.model;

public enum AssortType {
  SLAB, STRAP, PLATE, SCRAP;

  @Override
  public String toString() {
    switch(super.name()){
      case "SLAB": return "Płyta";
      case "STRAP": return "Ciętka";
      case "PLATE": return "Blacha";
      case "SCRAP": return "Złom";
    }
    return null;
  }
}
