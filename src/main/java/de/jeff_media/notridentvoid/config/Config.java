package de.jeff_media.notridentvoid.config;

import de.jeff_media.notridentvoid.Main;

public class Config {

    private final Main main = Main.getInstance();

    public static String VOID_SAVING = "void-saving";
    public static String CHECK_FOR_UPDATES = "check-for-updates";
    public static String UPDATE_CHECK_INTERVAL = "update-check-interval";

    public Config() {
        addDefault(VOID_SAVING, true);
    }

    private void addDefault(String node, Object value) {
        main.getConfig().set(node, value);
    }

}
