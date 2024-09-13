package projeto_final.cards.service.exception;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super("Não foi achado");
    }
}
