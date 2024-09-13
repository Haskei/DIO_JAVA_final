package projeto_final.cards.controller.dto;
import java.math.BigDecimal;
import projeto_final.cards.model.Card;
public record CardDto(Long id, String number, BigDecimal limit) {
    public CardDto(Card card){
        this(card.getId(), card.getNumber(), card.getLimit());
    }
    public Card toCard(){
        Card card = new Card();
        card.setId(this.id);
        card.setNumber(this.number);
        card.setLimit(this.limit);
        return card;
    }
}
