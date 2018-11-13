package com.chrosciu.rxbattleships;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Container;

@Component
@RequiredArgsConstructor
public class BoardFrame extends JFrame {
    private final BoardCanvas boardCanvas;

    @PostConstruct
    private void init() {
        Container contentPane = getContentPane();
        contentPane.add(boardCanvas);
        setSize(580, 620);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
