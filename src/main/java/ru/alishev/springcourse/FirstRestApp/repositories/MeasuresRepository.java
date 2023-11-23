package ru.alishev.springcourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstRestApp.models.Measure;

@Repository
public interface MeasuresRepository extends JpaRepository<Measure, Integer>{
    Integer countMeasuresByRainingTrue();
}