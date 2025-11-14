package utez.edu.mx.ruleman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ruleman.model.TipoServicio;
import utez.edu.mx.ruleman.repository.TipoServicioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoServicioService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    public List<TipoServicio> getAllTipoServicios() {
        return tipoServicioRepository.findAll();
    }

    public Optional<TipoServicio> getTipoServicioById(Long id) {
        return tipoServicioRepository.findById(id);
    }

    public TipoServicio saveTipoServicio(TipoServicio tipoServicio) {
        return tipoServicioRepository.save(tipoServicio);
    }

    public boolean deleteTipoServicio(Long id) {
        if (tipoServicioRepository.existsById(id)) {
            tipoServicioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}