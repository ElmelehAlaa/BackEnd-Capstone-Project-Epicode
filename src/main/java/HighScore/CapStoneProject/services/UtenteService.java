package HighScore.CapStoneProject.services;

import HighScore.CapStoneProject.Enum.Role;
import HighScore.CapStoneProject.entities.Utente;
import HighScore.CapStoneProject.exceptions.NotFoundException;
import HighScore.CapStoneProject.repositories.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UtenteService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;


    public Page<Utente> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepository.findAll(pageable);
    }

    public Utente findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("utente con email" + email + "non trovato"));
    }

    public Utente findById(long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Utente findByIdAndUpdate(long id, Utente body) throws NotFoundException {
        Utente found = this.findById(id);
        found.setCognome(body.getCognome());
        found.setNome(body.getNome());
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        found.setInput(body.getInput());
        return userRepository.save(found);
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Utente found = this.findById(id);
        userRepository.delete(found);
    }

    public List<Utente> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public Utente uploadPicture(MultipartFile file, @PathVariable long id) throws IOException {
        Utente found = this.findById(id);
        String cloudinaryURL = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setUrlAvatar(cloudinaryURL);
        return userRepository.save(found);
    }
}
