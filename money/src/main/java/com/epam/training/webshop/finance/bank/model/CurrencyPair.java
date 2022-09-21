package com.epam.training.webshop.finance.bank.model;

import java.util.Currency;
import lombok.Value;

@Value
public class CurrencyPair {

    private final Currency currencyFrom;
    private final Currency currencyTo;
}
