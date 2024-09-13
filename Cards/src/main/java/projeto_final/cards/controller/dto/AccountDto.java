package projeto_final.cards.controller.dto;
import projeto_final.cards.model.Account;
import java.math.BigDecimal;
public record AccountDto(Long id, String number, String agency, BigDecimal balance, BigDecimal limit) {
    public AccountDto(Account account){
        this(account.getId(), account.getNumber(), account.getAgency(), account.getBalance(), account.getLimit());
    }
    public Account toAccount(){
        Account account = new Account();
        account.setId(this.id);
        account.setNumber(this.number);
        account.setAgency(this.agency);
        account.setBalance(this.balance);
        account.setLimit(this.limit);
        return account;
    }
}
