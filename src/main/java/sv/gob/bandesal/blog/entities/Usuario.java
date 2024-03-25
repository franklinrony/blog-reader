package sv.gob.bandesal.blog.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USUARIO database table.
 *
 */
@Entity
@Table(name = "USUARIO", schema = "BANDESAL")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "USUARIO_SQ", table = "SECUENCIAS", pkColumnName = "NOMBRE",
            valueColumnName = "VALOR", pkColumnValue = "USUARIO_ID_SQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "USUARIO_SQ")
    private Long id;

    private String email;

    private String password;

    private String usuario;

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
  

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
