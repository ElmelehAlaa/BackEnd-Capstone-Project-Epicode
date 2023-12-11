package HighScore.CapStoneProject.services;

import HighScore.CapStoneProject.entities.ListService;
import HighScore.CapStoneProject.entities.ServiziSito;
import HighScore.CapStoneProject.exceptions.NotFoundException;
import HighScore.CapStoneProject.payload.ListServiceDTO;
import HighScore.CapStoneProject.repositories.ListServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListServiceService {

    @Autowired
    private ListServiceRepository listServiceRepository;
    @Autowired
    private ServiziSitoService serviziSitoService;

    public ListService save(ListServiceDTO body) {
        ListService newListService = new ListService();
        ServiziSito serviziSitoFound = serviziSitoService.findServizioById(body.idService());
        newListService.setTitle(body.title());
        newListService.setDescription(body.description());
        newListService.setImageUrl(body.imgUrl());
        newListService.setServiziSito(serviziSitoFound);
        return listServiceRepository.save(newListService);
    }

    public List<ListService> getAllListServices() {
        return listServiceRepository.findAll();
    }

    public Page<ListService> getListService(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return listServiceRepository.findAll(pageable);
    }

    public ListService findListServiceById(long id) throws NotFoundException {
        return listServiceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public ListService findByIdAndUpdate(long id, ListServiceDTO body) throws NotFoundException {
        ListService found = this.findListServiceById(id);
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setImageUrl(body.imgUrl());
        return listServiceRepository.save(found);
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        ListService found = this.findListServiceById(id);
        listServiceRepository.delete(found);
    }

    public List<ListService> getListServicesByServiziSito(ServiziSito serviziSito) {
        return listServiceRepository.findByServiziSito(serviziSito);
    }
}
