package HighScore.CapStoneProject.repositories;

import HighScore.CapStoneProject.Enum.Role;
import HighScore.CapStoneProject.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);

    List<Utente> findByRole(Role role);


}
