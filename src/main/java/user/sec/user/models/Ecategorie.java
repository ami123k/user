package user.sec.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "categorie")
@AllArgsConstructor
public class Ecategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categorie_id ;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private categorie name ;
    public Integer getId() {
        return categorie_id;
    }

    public void setId(Integer id) {
        this.categorie_id = id;
    }

    public categorie getName() {
        return name;
    }

    public void setName(categorie name) {
        this.name = name;
    }

    public Ecategorie(categorie name) {
        this.name = name;
    }

    public Ecategorie() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ecategorie)) return false;
        Ecategorie that = (Ecategorie) o;
        return Objects.equals(categorie_id, that.categorie_id) &&
                getName() == that.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(categorie_id, getName());
    }

    @Override
    public String toString() {
        return "Ecategorie{" +
                "categorie_id=" + categorie_id +
                ", name=" + name +
                '}';
    }
}
