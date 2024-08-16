package com.javarush.vrubleuski;

import com.javarush.vrubleuski.dao.*;
import com.javarush.vrubleuski.entity.*;
import com.javarush.vrubleuski.session.MySessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class App {

    private SessionFactory sessionFactory;
    private ActorDao actorDao;
    private AddressDao addressDao;
    private CategoryDao categoryDao;
    private CityDao cityDao;
    private CountryDao countryDao;
    private CustomerDao customerDao;
    private FilmDao filmDao;
    private FilmTextDao filmTextDao;
    private InventoryDao inventoryDao;
    private LanguageDao languageDao;
    private PaymentDao paymentDao;
    private RentalDao rentalDao;
    private StaffDao staffDao;
    private StoreDao storeDao;

    public App() {
        sessionFactory = MySessionFactory.getSessionFactory();
        initializeDaoObjects(sessionFactory);
    }

    public static void main(String[] args) {
        App app = new App();
        Customer customer = app.createCustomer();
        System.out.println(customer.getFirstName());

    }

    private Customer createCustomer() {
        Customer newCustomer = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            try {
                Store store = null;
                if (storeDao.findById((byte) 1).isPresent()) {
                    store = storeDao.findById((byte) 1).get();
                }
                Country country = Country.builder()
                        .name("Belarus")
                        .build();
                City city = City.builder()
                        .name("Minsk")
                        .country(country)
                        .build();
                Address address = Address.builder()
                        .address("Kolasa, 18")
                        .district("Moskovkii")
                        .city(city)
                        .postal_code("220117")
                        .phone("375336628762")
                        .build();

                Customer customer = Customer.builder()
                        .store(store)
                        .firstName("Ruslan")
                        .lastName("Vrubleuski")
                        .email("6628762@mail.ru")
                        .address(address)
                        .isActive(true)
                        .build();
                if (countryDao.getByName(country.getName()).isPresent()) {
                    country = countryDao.getByName(country.getName()).get();
                    city.setCountry(country);
                }
                if (cityDao.getByName(city.getName()).isPresent()) {
                    city = cityDao.getByName(city.getName()).get();
                    address.setCity(city);
                }
                countryDao.save(country);
                cityDao.save(city);
                addressDao.save(address);
                newCustomer = customerDao.save(customer);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.out.println("Rollback");
            }
            return newCustomer;
        }
    }

    private void initializeDaoObjects(SessionFactory sessionFactory) {
        Class<App> clazzApp = App.class;
        Field[] declaredFields = clazzApp.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Class<?> superclass = declaredFields[i].getType().getSuperclass();
            if (superclass == DaoBase.class) {
                Class<?> type = declaredFields[i].getType();
                String nameObj = declaredFields[i].getName();
                Field field = null;
                try {
                    field = clazzApp.getDeclaredField(nameObj);
                    field.setAccessible(true);
                    Constructor<?> declaredConstructor = type.getDeclaredConstructor(SessionFactory.class);
                    Object obj = type.cast(declaredConstructor.newInstance(sessionFactory));
                    field.set(this, obj);
                    field.setAccessible(false);
                } catch (NoSuchFieldException | InvocationTargetException | NoSuchMethodException |
                         InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
