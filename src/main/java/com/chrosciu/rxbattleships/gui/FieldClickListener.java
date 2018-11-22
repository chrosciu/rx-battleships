package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.model.Field;

/**
 * Listener used to be notified about clicked fields
 */
public interface FieldClickListener {
    /**
     * Field click event
     * @param field - board field where user has clicked
     */
    void onFieldClicked(Field field);
}
