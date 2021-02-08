package io.github.eddiediamondfire.economyplus.currency;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.UUID;

@Getter
@Setter
public class Currency {

    private UUID id;
    private String plural;
    private String singular;
    private ChatColor currencyColour;
    private boolean isDecimal;
    private boolean isExchangeAble;
    private boolean isDefault;
    private double startBalance;
    private char symbol;

    public Currency(UUID id, String plural, String singular){
        setId(id);
        setPlural(plural);
        setSingular(singular);
        setCurrencyColour(ChatColor.WHITE);
        setDecimal(true);
        setExchangeAble(true);
        setDefault(false);
        setStartBalance(0);
        setSymbol(' ');
    }

}
