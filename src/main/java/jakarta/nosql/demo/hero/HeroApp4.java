package jakarta.nosql.demo.hero;

import jakarta.nosql.mapping.Page;
import jakarta.nosql.mapping.Pagination;
import org.eclipse.jnosql.artemis.DatabaseQualifier;
import org.jboss.weld.util.collections.Sets;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class HeroApp4 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            HeroRepository repository = container.select(HeroRepository.class
            , DatabaseQualifier.ofDocument()).get();

            Hero iron = new Hero("Iron man", 32, Sets.newHashSet("Rich"));
            Hero thor = new Hero("Thor", 5000, Sets.newHashSet("Force", "Thunder", "Strength"));
            Hero captainAmerica = new Hero("Captain America", 80, Sets.newHashSet("agility",
                    "Strength", "speed", "endurance"));
            Hero spider = new Hero("Spider", 18, Sets.newHashSet("Spider", "Strength"));


            repository.save(asList(iron, thor, captainAmerica, spider));
            //find by id
            Optional<Hero> hero = repository.findById(iron.getName());
            System.out.println(hero);

            Stream<Hero> youngStream = repository.findByAgeLessThan(20);
            Stream<Hero> seniorStream = repository.findByAgeGreaterThan(20);
            Stream<Hero> strengthStream = repository.findByPowersIn("Strength");
            Stream<Hero> allStream = repository.findAll();

            String yongHeroes = youngStream.map(Hero::getName).collect(Collectors.joining(","));
            String seniorsHeroes = seniorStream.map(Hero::getName).collect(Collectors.joining(","));
            String strengthHeroes = strengthStream.map(Hero::getName).collect(Collectors.joining(","));
            String allHeroes = allStream.map(Hero::getName).collect(Collectors.joining(","));

            System.out.println("Young result: " + yongHeroes);
            System.out.println("Seniors result: " + seniorsHeroes);
            System.out.println("Strength result: " + strengthHeroes);
            System.out.println("All heroes result: " + allHeroes);

            //Pagination
            Pagination pagination = Pagination.page(1).size(1);
            Page<Hero> page1 = repository.findAll(pagination);
            System.out.println("Page 1: " + page1.getContent().collect(Collectors.toList()));
            Page<Hero> page2 = page1.next();
            System.out.println("Page 2: " + page2.getContent().collect(Collectors.toList()));
            Page<Hero> page3 = page1.next();
            System.out.println("Page 3: " + page3.getContent().collect(Collectors.toList()));

        }


    }
}
