package HighScore.CapStoneProject.services;

import HighScore.CapStoneProject.Enum.Periferica;
import HighScore.CapStoneProject.Enum.Role;
import HighScore.CapStoneProject.entities.Utente;
import HighScore.CapStoneProject.exceptions.BadRequestException;
import HighScore.CapStoneProject.exceptions.UnauthorizedException;
import HighScore.CapStoneProject.payload.NewUserDTO;
import HighScore.CapStoneProject.payload.UserLoginDTO;
import HighScore.CapStoneProject.repositories.UserRepository;
import HighScore.CapStoneProject.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public String autheticateUser(UserLoginDTO body) {
        Utente user = utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("credenziali non valide!");
        }
    }


    public Utente save(NewUserDTO body) throws IOException {
        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email" + user.getEmail() + "è già utilizzata!");
        });
        Utente newUser = new Utente();
        newUser.setNome(body.nome());
        newUser.setEmail(body.email());
        newUser.setCognome(body.cognome());
        newUser.setUsername(body.username());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setRole(Role.USER);
        newUser.setInput(Periferica.valueOf(body.input()));
        return userRepository.save(newUser);
    }

}
