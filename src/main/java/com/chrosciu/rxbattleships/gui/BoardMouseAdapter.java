package com.chrosciu.rxbattleships.gui;

public interface BoardMouseAdapter extends MouseAdapter {
    /**
     * Register listener to be notified about clicked fields
     * NOTE: Only one listener can be registered, registering next will discard previous one
     * @param fieldClickListener - listener to be registered
     */
    void registerFieldListener(FieldClickListener fieldClickListener);
}
