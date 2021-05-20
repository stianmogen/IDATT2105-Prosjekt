package com.repository;

import com.model.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.*;

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
        bindings.bind(room.building).first((path, value) -> path.id.eq(value.getId()));
        bindings.bind(room.availableAfter, room.availableBefore).all((path, values) -> {
                  BooleanBuilder predicate = new BooleanBuilder();
                  List<? extends ZonedDateTime> dates = new ArrayList<>(values);
                  if (dates.size() != 1) {
                          ZonedDateTime from = dates.get(0);
                          ZonedDateTime to = dates.get(1);

                          QSection section = QSection.section;
                          QReservation reservation = QReservation.reservation;

                          JPQLQuery<UUID> availableSections = JPAExpressions
                                .select(section.id)
                                .from(section)
                                .leftJoin(section.reservations, reservation)
                                .where(reservation.startTime.notBetween(from, to), reservation.endTime.notBetween(from, to));

                          BooleanExpression query = JPAExpressions.selectOne()
                              .from(room)
                              .innerJoin(room.sections, section)
                              .where(availableSections.contains(section.id)).exists();

                          predicate.or(query);
                  }
                  return Optional.of(predicate);
              }
        );
        bindings.bind(room.level).first(SimpleExpression::eq);

        bindings.bind(room.search).first((path, value) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            List<String> searchWords = Arrays.asList(value.trim().split("\\s+"));
            searchWords.forEach(searchWord -> predicate.or(room.name.containsIgnoreCase(searchWord)));
            return predicate;
        });
    }
}
