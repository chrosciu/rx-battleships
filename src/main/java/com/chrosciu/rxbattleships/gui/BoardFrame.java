package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.config.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Container;

import static com.chrosciu.rxbattleships.config.Constants.BOARD_SIZE;

@Component
@RequiredArgsConstructor
public class BoardFrame extends JFrame {
    private final BoardCanvas boardCanvas;

    private static final String TITLE = "RxBattleships";

    @PostConstruct
    private void init() {
        Container contentPane = getContentPane();
        contentPane.add(boardCanvas);
        setSize((BOARD_SIZE + 1) * Constants.CELL_SIZE + 30,
                (BOARD_SIZE + 1) * Constants.CELL_SIZE + 70);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(TITLE);
        setVisible(true);
    }
}
