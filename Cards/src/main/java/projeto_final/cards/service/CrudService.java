package projeto_final.cards.service;
import java.util.List;
public abstract interface CrudService<ID, T> {
    List<T> findAll();
    T findById(ID id);
    T create(T t);
    T update(ID id, T t);
    void delete(ID id);
}
