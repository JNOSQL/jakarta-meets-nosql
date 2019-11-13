package jakarta.nosql.demo.hero;

import jakarta.nosql.document.DocumentDeleteQuery;
import jakarta.nosql.document.DocumentQuery;
import jakarta.nosql.mapping.PreparedStatement;
import jakarta.nosql.mapping.document.DocumentTemplate;
import org.jboss.weld.util.collections.Sets;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jakarta.nosql.document.DocumentDeleteQuery.delete;
import static jakarta.nosql.document.DocumentQuery.select;

public class HeroApp3 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            DocumentTemplate template = container.select(DocumentTemplate.class).get();

            Hero iron = new Hero("Iron man", 32, Sets.newHashSet("Rich"));
            Hero thor = new Hero("Thor", 5000, Sets.newHashSet("Force", "Thunder"));
            Hero captainAmerica = new Hero("Captain America", 80, Sets.newHashSet("agility",
                    "strength", "speed", "endurance"));
            Hero spider = new Hero("Spider", 18, Sets.newHashSet("Spider"));

            template.query("delete from Hero where _id in ('Iron man', 'Thor', 'Captain America', 'Spider')");
            template.insert(Arrays.asList(iron, thor, captainAmerica, spider));
            //query younger
            PreparedStatement prepare = template.prepare("select * from Hero where age < @age");
            prepare.bind("age", 20);

            Stream<Hero> youngStream = prepare.getResult();

            Stream<Hero> seniorStream = template.query("select * from Hero where age > 20");

            Stream<Hero> powersStream = template.query("select * from Hero where powers in ('Strength')");

            Stream<Hero> allStream = template.query("select * from Hero");

            Stream<Hero> skipLimitStream = template.query("select * from Hero skip 0 limit 1 order by _id asc");

            String yongHeroes = youngStream.map(Hero::getName).collect(Collectors.joining(","));
            String seniorsHeroes = seniorStream.map(Hero::getName).collect(Collectors.joining(","));
            String allHeroes = allStream.map(Hero::getName).collect(Collectors.joining(","));
            String skipLimitHeroes = skipLimitStream.map(Hero::getName).collect(Collectors.joining(","));
            String powersHeroes = powersStream.map(Hero::getName).collect(Collectors.joining(","));

            System.out.println("Young result: " + yongHeroes);
            System.out.println("Seniors result: " + seniorsHeroes);
            System.out.println("Powers Strength result: " + powersHeroes);
            System.out.println("All heroes result: " + allHeroes);
            System.out.println("All heroes skip result: " + skipLimitHeroes);

        }


    }
}
