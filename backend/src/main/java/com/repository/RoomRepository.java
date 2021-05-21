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
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.*;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID>, QuerydslPredicateExecutor<Room>, QuerydslBinderCustomizer<QRoom>  {

      /*
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
    */

    @Override
    default void customize(QuerydslBindings bindings, QRoom room) {
        bindings.bind(room.building.id).first((path, value) -> path.eq(value));;
        bindings.bind(room.time).all((final DateTimePath<ZonedDateTime> path, final Collection<? extends ZonedDateTime> values) -> {
                final List<? extends ZonedDateTime> dates = new ArrayList<>(values);
              BooleanBuilder predicate = new BooleanBuilder();
                Collections.sort(dates);
                if (dates.size() == 2) {
                      ZonedDateTime from = dates.get(0);
                      ZonedDateTime to = dates.get(1);

                      QSection section = QSection.section;
                      QReservation reservation = QReservation.reservation;

                      BooleanExpression roomIsAvailable = JPAExpressions.selectOne()
                            .from(reservation)
                            .rightJoin(reservation.sections, section)
                            .innerJoin(section.room, room)
                            .where((reservation.endTime.after(to).and(reservation.startTime.after(to))).or(reservation.endTime.before(from).and(reservation.startTime.before(from))).or(reservation.isNull())).exists();
                      predicate.or(roomIsAvailable);
                      return Optional.of(predicate);
                }
                throw new IllegalArgumentException("2 date params(from & to) expected." + " Found:" + values);
        });
        bindings.bind(room.level).first(SimpleExpression::eq);

        bindings.bind(room.search).first((path, value) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            List<String> searchWords = Arrays.asList(value.trim().split("\\s+"));
            searchWords.forEach(searchWord -> predicate.or(room.name.containsIgnoreCase(searchWord)));
            return predicate;
        });

        bindings.bind(room.capacity).first((path, value) -> {
              BooleanBuilder booleanBuilder = new BooleanBuilder();
              QSection section = QSection.section;
              JPQLQuery<Integer> roomCapacity = JPAExpressions.select(section.capacity.sum())
                    .from(section)
                    .where(section.room.eq(room));

              return booleanBuilder.or(roomCapacity.goe(value));
        });
    }
}
