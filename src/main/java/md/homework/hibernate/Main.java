package md.homework.hibernate;

import md.homework.hibernate.entity.CountryEntity;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import md.homework.hibernate.entity.CityEntity;
import md.homework.hibernate.entity.UsersEntity;

import javax.persistence.metamodel.EntityType;
import java.util.stream.Collectors;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) {
        try (Session session = getSession()) {
            // INSERT
            CityEntity cityEntity = new CityEntity(); // Создаем сущность-город
            cityEntity.setCityId(300);                // Присваиваем id
            cityEntity.setCityName("Tula");           // Присваиваем название
            int id = 43;
            cityEntity.setCountryId(id);              // Присваиваем id страны (должен быть в таблице country)
            cityEntity.setCountryByCountryId(session.get(CountryEntity.class, id));      // Связываем город со страной
            session.get(CountryEntity.class, id).getCitiesByCountryId().add(cityEntity); // Связываем страну с городом
            session.save(cityEntity);                 // Сохраняем созданную сущность
            System.out.println("Created city:" + "\n" + session.get(CityEntity.class, cityEntity.getCityId()));
            System.out.println();

            // SELECT (Найдем все города, относящиеся к указанной стране)
            System.out.println("List of cities of the country:");
            session.get(CountryEntity.class, id).getCitiesByCountryId()
                    .stream()
                    .map(CityEntity::getCityName)
                    .forEach(System.out::println);
            System.out.println();

            // UPDATE
            cityEntity.setCityName(cityEntity.getCityName() + " changed");
            session.update(cityEntity);
            System.out.println("Changed city:" + "\n" + session.get(CityEntity.class, cityEntity.getCityId()));
            System.out.println();

            // DELETE
            session.delete(cityEntity);
            System.out.println("Deleted city:" + "\n" + session.get(CityEntity.class, cityEntity.getCityId()));
        }
    }
}