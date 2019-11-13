package jakarta.nosql.demo.hero;

import jakarta.nosql.document.DocumentDeleteQuery;
import jakarta.nosql.document.DocumentQuery;
import jakarta.nosql.mapping.Page;
import jakarta.nosql.mapping.Pagination;
import jakarta.nosql.mapping.document.DocumentQueryPagination;
import jakarta.nosql.mapping.document.DocumentTemplate;
import org.jboss.weld.util.collections.Sets;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jakarta.nosql.document.DocumentDeleteQuery.delete;
import static jakarta.nosql.document.DocumentQuery.select;

public class HeroApp2 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            DocumentTemplate template = container.select(DocumentTemplate.class).get();

            Hero iron = new Hero("Iron man", 32, Sets.newHashSet("Rich"));
            Hero thor = new Hero("Thor", 5000, Sets.newHashSet("Force", "Thunder"));
            Hero captainAmerica = new Hero("Captain America", 80, Sets.newHashSet("agility",
                    "strength", "speed", "endurance"));
            Hero spider = new Hero("Spider", 18, Sets.newHashSet("Spider"));

            DocumentDeleteQuery deleteQuery = delete().from("Hero")
                    .where("_id").in(Stream.of(iron, thor, captainAmerica, spider)
                            .map(Hero::getName).collect(Collectors.toList())).build();
            template.delete(deleteQuery);
            template.insert(Arrays.asList(iron, thor, captainAmerica, spider));

            DocumentQuery query = select()
                    .from("Hero")
                    .orderBy("_id")
                    .asc()
                    .build();

            DocumentQueryPagination pagination =
                    DocumentQueryPagination.of(query, Pagination.page(1).size(1));

            Page<Hero> page1 = template.select(pagination);

            System.out.println("Page 1: " + page1.getContent().collect(Collectors.toList()));

            Page<Hero> page2 = page1.next();

            System.out.println("Page 2: " + page2.getContent().collect(Collectors.toList()));

            Page<Hero> page3 = page1.next();
            System.out.println("Page 3: " + page3.getContent().collect(Collectors.toList()));

        }


    }
}
