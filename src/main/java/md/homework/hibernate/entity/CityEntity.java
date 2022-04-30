package md.homework.hibernate.entity;

import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity    // Аннотация, указывающая, что мы работаем с сущностью
@ToString  // Аннотация для читабельного вида информации об объекте класса
@Table(name = "city", schema = "public", catalog = "postgres")
public class CityEntity {

    @Id                                                  // Идентификатор сущности
    @Column(name = "city_id")                            // Колонка, соответствующая полю
    private int cityId;

    @Basic
    @Column(name = "city_name")
    private String cityName;

    @Basic
    @Column(name = "country_id")
    private int countryId;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id", nullable = false, insertable = false, updatable = false)
    private CountryEntity countryByCountryId;

    @OneToMany(mappedBy = "cityByCityId", fetch=FetchType.EAGER)
    private Collection<StoreEntity> storesByCityId;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity that = (CityEntity) o;
        return cityId == that.cityId && countryId == that.countryId && Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, cityName, countryId);
    }

    public CountryEntity getCountryByCountryId() {
        return countryByCountryId;
    }

    public void setCountryByCountryId(CountryEntity countryByCountryId) {
        this.countryByCountryId = countryByCountryId;
    }

    public Collection<StoreEntity> getStoresByCityId() {
        return storesByCityId;
    }

    public void setStoresByCityId(Collection<StoreEntity> storesByCityId) {
        this.storesByCityId = storesByCityId;
    }
}
