package projeto_final.cards.service.impl;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import projeto_final.cards.service.*;
import projeto_final.cards.model.*;
import projeto_final.cards.repository.*;
import projeto_final.cards.service.exception.BusinessException;
import projeto_final.cards.service.exception.NotFoundException;
import static java.util.Optional.ofNullable;
@Service
public class UserServiceImpl implements UserService {
    public static final Long serialVersionUID = 1L;
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private void validateId(Long id, String message) {
        if (serialVersionUID.equals(id)) {
            throw new BusinessException("Id de usuario não pode ser "+serialVersionUID+" não pode ser "+message );
        }
    }
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    @Transactional
    public User create(User user) {
        ofNullable(user).orElseThrow(() -> new BusinessException("Não pode ser nulo"));
        ofNullable(user.getAccount()).orElseThrow(() -> new BusinessException("Usuário precisa ter conta"));
        ofNullable(user.getCard()).orElseThrow(()-> new BusinessException("Usuario precisa ter carta"));
        this.validateId(user.getId(), "created");
        if(userRepository.existsByAccountNumber(user.getAccount().getNumber())){
            throw new BusinessException("Este numero já está sendo usado");
        }
        if (userRepository.existsByCardNumber(user.getCard().getNumber())) {
            throw new BusinessException("Já existe uma carta com este numero");
        }
        return this.userRepository.save(user);
    }
    @Transactional
    public User update(Long id, User user) {
        this.validateId(id, "update");
        User TUser= this.findById(id);
        if(!TUser.getId().equals(user.getId())){
            throw new BusinessException("Ids dever ser as mesmas");
        }
        TUser.setAccount(user.getAccount());
        TUser.setCard(user.getCard());
        TUser.setNome(user.getNome());
        TUser.setNews(user.getNews());
        TUser.setFeatures(user.getFeatures());
        return this.userRepository.save(TUser);
    }
    @Transactional
    public void delete(Long id) {
        this.validateId(id, "delete");
        User TUser= this.findById(id);
        this.userRepository.delete(TUser);
    }
}
