package HighScore.CapStoneProject.services;

import HighScore.CapStoneProject.entities.ServiziSito;
import HighScore.CapStoneProject.exceptions.NotFoundException;
import HighScore.CapStoneProject.payload.NewServizioDTO;
import HighScore.CapStoneProject.repositories.PrenotazioniRepository;
import HighScore.CapStoneProject.repositories.ServiziRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ServiziSitoService {
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;
    @Autowired
    private ServiziRepository serviziRepository;

    public ServiziSito save(NewServizioDTO body) {
        ServiziSito newServizio = new ServiziSito();
        newServizio.setDescription(body.description());
        newServizio.setTitle(body.title());
        newServizio.setImgUrl(body.imgUrl());
        newServizio.setCosto(body.costo());
        return serviziRepository.save(newServizio);
    }

    public Page<ServiziSito> getServizi(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return serviziRepository.findAll(pageable);
    }

    public ServiziSito findServizioById(long id) throws NotFoundException {
        return serviziRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public ServiziSito findByIdAndUpdate(long id, NewServizioDTO body) throws NotFoundException {
        ServiziSito found = this.findServizioById(id);
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setImgUrl(body.imgUrl());
        found.setCosto(body.costo());
        return serviziRepository.save(found);
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        ServiziSito found = this.findServizioById(id);
        serviziRepository.delete(found);

    }

}
