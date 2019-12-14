package ru.sberbank.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.dao.entity.Rate;

import java.util.Optional;

@Repository
public interface RateCrudRepository extends CrudRepository<Rate, Long> {
    Optional<Rate> findByDate(String dateString);
    void delete(Rate entity);
    Rate save(Rate entity);
}
