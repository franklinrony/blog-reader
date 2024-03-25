package sv.gob.bandesal.blog.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the READERS database table.
 *
 */
@Entity
@Table(name = "READERS", schema = "BANDESAL")
public class Reader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "READER_SQ", table = "SECUENCIAS", pkColumnName = "NOMBRE",
            valueColumnName = "VALOR", pkColumnValue = "READER_ID_SQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "READER_SQ")
    private Long id;

    private String name;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "readers", fetch =FetchType.EAGER)
    private List<Blog> blogs;

    public Reader() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return this.blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

 @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reader)) {
            return false;
        }
        Reader other = (Reader) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.bandesal.blog.entities.Reader[ id=" + id + " ]";
    }

}
