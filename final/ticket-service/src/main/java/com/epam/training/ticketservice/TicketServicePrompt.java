package com.epam.training.ticketservice;

import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class TicketServicePrompt implements PromptProvider {


        @Override
        public final AttributedString getPrompt() {

            return new AttributedString("Ticket service>");

        }

}
