package entity;

import java.io.Serializable;

/**
 *
 * @author upcnet
 */
public class Topic implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  public String name;

  public Topic() {
  }
  
  public Topic(String name) {
    this.name = name;
  }

  public Topic(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
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
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Topic)) {
      return false;
    }
    Topic other = (Topic) object;
    if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "entity.Topic[ id=" + id + " ]";
  }
  
}
