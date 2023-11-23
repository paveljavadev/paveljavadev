package ru.alishev.springcourse.FirstRestApp.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Measure;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.repositories.MeasuresRepository;
import ru.alishev.springcourse.FirstRestApp.repositories.SensorsRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Service
@Transactional(readOnly = true)
public class MeasuresService {

//    Configuration configuration = new Configuration().addAnnotatedClass(Measure.class);
//    SessionFactory sessionFactory = configuration.buildSessionFactory();
//    Session session = sessionFactory.getCurrentSession();

    private final MeasuresRepository measuresRepository;

    @Autowired
    public MeasuresService(MeasuresRepository measuresRepository) {
        this.measuresRepository = measuresRepository;
    }

    public List<Measure> findAll() {
        return measuresRepository.findAll();
    }

    //подсчет дождливых дней
    public Integer rainyDaysCount(){
        return measuresRepository.countMeasuresByRainingTrue();
    }

    //добавляем новое измерение в БД
    @Transactional
    public void save (Measure measure, Sensor sensorFromDB){

        //назначаем сенсор
        measure.setSensor(sensorFromDB);

        //добавляем актуальную дату в таблицу
        measure.setCreatedAt(new Date());
        measuresRepository.save(measure);
    }



}
