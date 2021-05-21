package com.repository;

import com.model.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.SimpleExpression;
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
public interface SectionRepository extends JpaRepository<Section, UUID>, QuerydslPredicateExecutor<Section>, QuerydslBinderCustomizer<QSection> {
      List<Section> findAllByRoomId(UUID roomId);
      List<Section> findSectionsByRoom(Room room);

      @Query("SELECT s FROM Section s " +
            "INNER JOIN s.reservations r " +
            "WHERE r.startTime <= :startTime " +
            "AND r.endTime >= :endTime " +
            "AND s.id = :id")
      Optional<Section> findAvailableSection(@Param("id") UUID uuid, @Param("startTime") ZonedDateTime from, @Param("endTime") ZonedDateTime to);

      @Override
      default void customize(QuerydslBindings bindings, QSection section) {
            //bindings.bind(section.room).first((path, value) -> path//TODO);
            bindings.bind(section.time).all((final DateTimePath<ZonedDateTime> path, final Collection<? extends ZonedDateTime> values) -> {
                  final List<? extends ZonedDateTime> dates = new ArrayList<>(values);
                  BooleanBuilder predicate = new BooleanBuilder();
                  Collections.sort(dates);
                  if (dates.size() == 2) {
                        ZonedDateTime from = dates.get(0);
                        ZonedDateTime to = dates.get(1);

                        QReservation reservation = QReservation.reservation;

                        BooleanExpression sectionIsAvailable = JPAExpressions.selectOne()
                                .from(section)
                                .leftJoin(section.reservations, reservation)
                                .where((reservation.endTime.after(to).and(reservation.startTime.after(to))).or(reservation.endTime.before(from).and(reservation.startTime.before(from))).or(reservation.isNull())).exists();
                        predicate.or(sectionIsAvailable);
                        return Optional.of(predicate);
                  }
                  throw new IllegalArgumentException("2 date params(from & to) expected." + " Found:" + values);
            });
      }
}
