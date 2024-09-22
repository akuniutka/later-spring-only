package ru.practicum.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    Optional<Item> findByUserIdAndId(long userId, long id);

    List<Item> findByUserId(long userId);

    void deleteByUserIdAndId(long userId, long id);
}
