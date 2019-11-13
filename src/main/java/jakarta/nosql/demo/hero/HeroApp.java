package jakarta.nosql.demo.hero;

import jakarta.nosql.document.DocumentDeleteQuery;
import jakarta.nosql.document.DocumentQuery;
import jakarta.nosql.mapping.document.DocumentTemplate;
import org.jboss.weld.util.collections.Sets;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jakarta.nosql.document.DocumentDeleteQuery.delete;
import static jakarta.nosql.document.DocumentQuery.select;
import static java.util.Arrays.asList;

public class HeroApp {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            DocumentTemplate template = container.select(DocumentTemplate.class).get();

            Hero iron = new Hero("Iron man", 32, Sets.newHashSet("Rich"));
            Hero thor = new Hero("Thor", 5000, Sets.newHashSet("Force", "Thunder", "Strength"));
            Hero captainAmerica = new Hero("Captain America", 80, Sets.newHashSet("agility",
                    "Strength", "speed", "endurance"));
            Hero spider = new Hero("Spider", 18, Sets.newHashSet("Spider", "Strength"));

            DocumentDeleteQuery deleteQuery = delete().from("Hero")
                    .where("_id").in(Stream.of(iron, thor, captainAmerica, spider)
                            .map(Hero::getName).collect(Collectors.toList())).build();
            template.delete(deleteQuery);


            template.insert(asList(iron, thor, captainAmerica, spider));
            //find by id
            Optional<Hero> hero = template.find(Hero.class, iron.getName());
            System.out.println(hero);

            //query younger
            DocumentQuery youngQuery = select().from("Hero")
                    .where("age").lt(20).build();

            //query seniors
            DocumentQuery seniorQuery = select().from("Hero")
                    .where("age").gt(20).build();

            DocumentQuery queryPower = select().from("Hero")
                    .where("powers").in(Collections.singletonList("Strength"))
                    .build();

            Stream<Hero> youngStream = template.select(youngQuery);

            Stream<Hero> seniorStream = template.select(seniorQuery);

            Stream<Hero> strengthStream = template.select(queryPower);

            String yongHeroes = youngStream.map(Hero::getName).collect(Collectors.joining(","));
            String seniorsHeroes = seniorStream.map(Hero::getName).collect(Collectors.joining(","));
            String strengthHeroes = strengthStream.map(Hero::getName).collect(Collectors.joining(","));

            System.out.println("Young result: " + yongHeroes);
            System.out.println("Seniors result: " + seniorsHeroes);
            System.out.println("Strength result: " + strengthHeroes);


        }


    }
}
