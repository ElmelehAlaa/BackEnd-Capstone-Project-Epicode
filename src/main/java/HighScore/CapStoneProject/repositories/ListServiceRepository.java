package HighScore.CapStoneProject.repositories;

import HighScore.CapStoneProject.entities.ListService;
import HighScore.CapStoneProject.entities.ServiziSito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListServiceRepository extends JpaRepository<ListService, Long> {
    Optional<ListService> findById(Long id);

    List<ListService> findByServiziSito(ServiziSito serviziSito);
}
