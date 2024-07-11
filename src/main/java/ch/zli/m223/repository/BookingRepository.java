package ch.zli.m223.repository;

import ch.zli.m223.model.impl.BookingImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingImpl, Long> {
    List<BookingImpl> findAllByDateBetween(LocalDate startOfWeek, LocalDate endOfWeek);
}
