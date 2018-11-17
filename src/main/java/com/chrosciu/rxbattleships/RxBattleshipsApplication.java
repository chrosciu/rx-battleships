package com.chrosciu.rxbattleships;

import com.chrosciu.rxbattleships.gui.BoardCanvas;
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



