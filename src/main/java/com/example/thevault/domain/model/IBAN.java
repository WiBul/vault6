// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IBAN {
    private String IBAN;

    private final Logger logger = LoggerFactory.getLogger(IBAN.class);

    public IBAN() {
        super();
        logger.info("New IBAN");
    }
}
