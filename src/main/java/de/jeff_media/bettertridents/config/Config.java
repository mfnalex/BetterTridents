package de.jeff_media.bettertridents.config;

import de.jeff_media.bettertridents.Main;

public class Config {

    private final Main main = Main.getInstance();

    public static final String BEDROCK_IMPALING = "bedrock-impaling";
    public static final String CONFIG_PLUGIN_VERSION = "plugin-version";
    public static final String VOID_SAVING = "void-saving";
    public static final String CONFIG_VERSION = "config-version";
    public static final String CHECK_FOR_UPDATES = "check-for-updates";
    public static final String UPDATE_CHECK_INTERVAL = "update-check-interval";
    public static final String DROP_BEDROCK_CHANCE = "bedrock-drop-chance";
    public static final String RETURN_TO_OFFHAND = "return-to-offhand";
    public static final String DISABLE_LOYALTY_PORTALS = "disable-loyalty-portals";
    public static final String DEBUG = "debug";

    public Config() {
        addDefault(VOID_SAVING, true);
        addDefault(DROP_BEDROCK_CHANCE, true);
        addDefault(CHECK_FOR_UPDATES, "true");
        addDefault(UPDATE_CHECK_INTERVAL, 4);
        addDefault(BEDROCK_IMPALING, true);
        addDefault(RETURN_TO_OFFHAND, true);
        addDefault(DISABLE_LOYALTY_PORTALS, true);
        addDefault(DEBUG, false);
    }

    private void addDefault(String node, Object value) {
        main.getConfig().addDefault(node, value);
    }

}
