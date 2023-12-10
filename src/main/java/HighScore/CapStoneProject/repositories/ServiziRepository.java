package HighScore.CapStoneProject.repositories;

import HighScore.CapStoneProject.entities.ServiziSito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiziRepository extends JpaRepository<ServiziSito, Long> {
    Optional<ServiziSito> findById(Long id);
}
