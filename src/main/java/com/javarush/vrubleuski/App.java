package com.javarush.vrubleuski;

import com.javarush.vrubleuski.dao.*;
import com.javarush.vrubleuski.entity.*;
import com.javarush.vrubleuski.session.MySessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

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
        app.returnRentalFilm();
        app.paymentAndRentInventory();
        app.addFilmForRent();
    }

    private void addFilmForRent() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            try {
                Language language = Language.builder()
                        .name("English")
                        .build();
                if (languageDao.getByName(language.getName()).isPresent()) {
                    language = languageDao.getByName(language.getName()).get();
                } else {
                    languageDao.save(language);
                }
                Set<Category> categories = new HashSet<>() {{
                    add(Category.builder().name("Action").build());
                    add(Category.builder().name("Drama").build());
                }};
                Set<Actor> actors = new HashSet<>() {{
                    add(Actor.builder().firstName("RUSSEL").lastName("CROWE").build());
                    add(Actor.builder().firstName("JOAQUIN").lastName("PHOENIX").build());
                }};
                Film newFilm = Film.builder()
                        .title("Гладиатор")
                        .description("Cool film about the gladiator")
                        .releaseYear(Year.of(2006))
                        .language(language)
                        .rentalDuration((byte) 122)
                        .rentalRate(BigDecimal.valueOf(5.99))
                        .replacementCost(BigDecimal.valueOf(35.99))
                        .rating(Rating.NC17)
                        .categories(categories)
                        .actors(actors)
                        .build();
                boolean isExistFilm = filmDao.getByNameYearLanguage(newFilm.getTitle(), newFilm.getReleaseYear(), language.getId()).isPresent();
                if (isExistFilm) {
                    newFilm = filmDao.getByNameYearLanguage(newFilm.getTitle(), newFilm.getReleaseYear(), language.getId()).get();
                    System.out.printf("Film №-%d is exist; Description:%s; language:%s;\n", newFilm.getId(), newFilm.getDescription(), newFilm.getLanguage().getName());
                } else {
                    HashSet<Category> categoryWithId = new HashSet<>();
                    for (Category category : categories) {
                        String name = category.getName();
                        if (categoryDao.getByName(name).isPresent()) {
                            categoryWithId.add(categoryDao.getByName(name).get());
                        } else {
                            Category categoryId = categoryDao.save(category);
                            categoryWithId.add(categoryId);
                        }
                    }
                    HashSet<Actor> actorWithId = new HashSet<>();
                    for (Actor actor : actors) {
                        String firstName = actor.getFirstName();
                        String lastName = actor.getLastName();
                        if (actorDao.getByFirstNameLastName(firstName, lastName).isPresent()) {
                            actorWithId.add(actorDao.getByFirstNameLastName(firstName, lastName).get());
                        } else {
                            Actor actorId = actorDao.save(actor);
                            actorWithId.add(actorId);
                        }
                    }

                    newFilm.setCategories(categoryWithId);
                    newFilm.setActors(actorWithId);
                    filmDao.save(newFilm);
                    FilmText newFilmText = FilmText.builder()
                            .id(newFilm.getId())
                            .title(newFilm.getTitle())
                            .description(newFilm.getDescription())
                            .build();
                    filmTextDao.save(newFilmText);
                    Inventory inventory = Inventory.builder()
                            .film(newFilm)
                            .store(storeDao.findById((byte) 2).get())
                            .build();
                    inventoryDao.save(inventory);
                    System.out.printf("Film №-%d is added, Inventory №-%d\n", newFilm.getId(), inventory.getId());
                }
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    private void paymentAndRentInventory() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            try {
                short count = (short) customerDao.getGeneralCount();
                short random = (short) ((Math.random() * count) + 1);
                Customer randomCustomer = null;
                if (customerDao.findById(random).isPresent()) {
                    randomCustomer = customerDao.findById(random).get();
                }
                Store store = storeDao.findById((byte) 1).get();
                Inventory inventory = null;
                if (inventoryDao.getAvailableInventory().isPresent()) {
                    inventory = inventoryDao.getAvailableInventory().get();
                }
                Rental rental = Rental.builder()
                        .inventory(inventory)
                        .customer(randomCustomer)
                        .staff(store.getManager())
                        .returnDate(null)
                        .build();
                Payment payment = Payment.builder()
                        .customer(randomCustomer)
                        .staff(store.getManager())
                        .rental(rental)
                        .amount(new BigDecimal("5.33"))
                        .build();
                rentalDao.save(rental);
                paymentDao.save(payment);
                session.getTransaction().commit();
                System.out.printf("Payment №-%s\n", payment.getId());
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }

        }
    }

    private void returnRentalFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            try {
                Rental rental = null;
                Optional<Rental> unreturnedFilm = rentalDao.getUnreturnedFilm();
                if (unreturnedFilm.isPresent()) {
                    rental = unreturnedFilm.get();
                    rental.setReturnDate(LocalDateTime.now());
                    rentalDao.update(rental);
                }
                session.getTransaction().commit();
                System.out.printf("Прокат №-%s вернул заказчик №-%s инвентарь №-%s\n",
                        rental.getId(), rental.getCustomer().getId(), rental.getInventory().getId());
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
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
