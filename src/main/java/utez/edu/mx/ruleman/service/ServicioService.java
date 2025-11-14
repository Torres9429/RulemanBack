package utez.edu.mx.ruleman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ruleman.model.Servicio;
import utez.edu.mx.ruleman.repository.ServicioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> getServicioById(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio saveServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public boolean deleteServicio(Long id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}