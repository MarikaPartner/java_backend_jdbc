package md.homework.hibernate.entity;

import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
//@ToString
@Table(name = "country", schema = "public", catalog = "postgres")
public class CountryEntity {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "country_id")
    private int countryId;
    @Basic
    @Column(name = "country_name")
    private String countryName;
    @OneToMany(mappedBy = "countryByCountryId")
    private Collection<CityEntity> citiesByCountryId;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryEntity that = (CountryEntity) o;
        return countryId == that.countryId && Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, countryName);
    }

    public Collection<CityEntity> getCitiesByCountryId() {
        return citiesByCountryId;
    }

    public void setCitiesByCountryId(Collection<CityEntity> citiesByCountryId) {
        this.citiesByCountryId = citiesByCountryId;
    }
}
