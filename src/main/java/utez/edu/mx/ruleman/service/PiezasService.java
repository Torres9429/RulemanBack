package utez.edu.mx.ruleman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ruleman.model.Piezas;
import utez.edu.mx.ruleman.repository.PiezasRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PiezasService {

    @Autowired
    private PiezasRepository piezasRepository;

    public List<Piezas> getAllPiezas() {
        return piezasRepository.findAll();
    }

    public Optional<Piezas> getPiezasById(Long id) {
        return piezasRepository.findById(id);
    }

    public Piezas savePiezas(Piezas piezas) {
        return piezasRepository.save(piezas);
    }

    public boolean deletePiezas(Long id) {
        if (piezasRepository.existsById(id)) {
            piezasRepository.deleteById(id);
            return true;
        }
        return false;
    }
}