package com.chrosciu.rxbattleships;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.EventQueue;

@SpringBootApplication
public class RxBattleshipsApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(RxBattleshipsApplication.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            BoardCanvas boardCanvas = ctx.getBean(BoardCanvas.class);
            boardCanvas.setVisible(true);
        });
    }
}



