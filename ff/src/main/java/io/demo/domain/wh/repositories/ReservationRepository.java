package io.demo.domain.wh.repositories;

import io.demo.domain.wh.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
