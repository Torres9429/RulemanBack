package utez.edu.mx.ruleman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.ruleman.model.Piezas;

@Repository
public interface PiezasRepository extends JpaRepository<Piezas, Long> {
}