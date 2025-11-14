package utez.edu.mx.ruleman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.ruleman.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
}