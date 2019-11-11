package jakarta.nosql.demo;

import jakarta.nosql.mapping.Param;
import jakarta.nosql.mapping.Query;
import jakarta.nosql.mapping.Repository;

import java.util.List;

public interface GodRepository extends Repository<God, Long> {

    @Query("select * from God where name = @name")
    List<God> query(@Param("name") String name);

    List<God> findByName(String name);


}
