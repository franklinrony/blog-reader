package sv.gob.bandesal.blog.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the BLOGS database table.
 *
 */
@Entity
@Table(name = "BLOGS", schema = "BANDESAL")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "BLOG_SQ", table = "SECUENCIAS", pkColumnName = "NOMBRE",
            valueColumnName = "VALOR", pkColumnValue = "BLOG_ID_SQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BLOG_SQ")
    private Long id;

    private String description;

    private String title;

    @JsonBackReference
    @ManyToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "BLOGS_READERS",
            joinColumns = {
                @JoinColumn(name = "BLOGS_ID")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "READERS_ID")
            }
    )
    private List<Reader> readers;

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Reader> getReaders() {
        return this.readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
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
        if (!(object instanceof Blog)) {
            return false;
        }
        Blog other = (Blog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.bandesal.blog.entities.Blog[ id=" + id + " ]";
    }

}
