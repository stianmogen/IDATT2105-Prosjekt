package com.repository;

import com.model.QRoom;
import com.model.Room;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID>, QuerydslPredicateExecutor<Room>, QuerydslBinderCustomizer<QRoom>  {


    @Query("SELECT r FROM Room r "+
            "INNER JOIN r.sections s " +
             "WHERE :startTime <> :endTime " +
            "AND :participants <> 69")
            //"WHERE s = (SELECT sec FROM Section sec " +
            //"INNER JOIN sec.reservations res " +
            //"WHERE res.startTime < :startTime " +
            //"AND res.endTime > :endTime " +
            //"AND :participants >= 0) ")
            //"AND (SELECT SUM(capacity) from s) > :participants)"
    List<Room> findAvailableRoom(@Param("startTime") ZonedDateTime from,
                                 @Param("endTime") ZonedDateTime to,
                                 @Param("participants") int participants);

    @Override
    default void customize(QuerydslBindings bindings, QRoom room) {
        bindings.bind(room.building).first((path, value) -> path.eq(value));
        bindings.bind(room.availableFrom, room.availableTo).first((path, values) -> {

        });
        bindings.bind(room.level).first(SimpleExpression::eq);
        bindings.bind(room.capacity).first((path, value) ->{
            room.sections.any()
            ListPath<> listPath = room.sections;
                });
        bindings.bind(room.search).first(((path, value) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            List<String> searchWords = Arrays.asList(value.trim().split("\\s+"));
            searchWords.forEach(searchWord -> predicate.or(room.name.containsIgnoreCase(searchWord)));
            return predicate;
        }));
    }
}
