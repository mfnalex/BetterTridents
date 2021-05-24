package de.jeff_media.notridentvoid.config;

import de.jeff_media.notridentvoid.Main;

public class Config {

    private final Main main = Main.getInstance();

    public static final String VOID_SAVING = "void-saving";
    public static final String CHECK_FOR_UPDATES = "check-for-updates";
    public static final String UPDATE_CHECK_INTERVAL = "update-check-interval";

    public Config() {
        addDefault(VOID_SAVING, true);
    }

    private void addDefault(String node, Object value) {
        main.getConfig().set(node, value);
    }

}
