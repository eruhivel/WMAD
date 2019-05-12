package util;

/**
 *
 * @author juanluis
 */
public class Topic {
  
  public String name;

  public Topic(String name) {
    this.name = name;
  }
  
  @Override
  public int hashCode() {
    int hash = 0;
    hash += (name != null ? name.hashCode() : 0);
    return hash;
  }
  
  @Override
  public boolean equals(Object object) {
    
    if (!(object instanceof Topic)) {
      return false;
    }
    Topic other = (Topic) object;
    if ((this.name == null || other.name == null) || 
        (!this.name.equals(other.name))) {
      return false;
    }
    return true;
  }
  
}
