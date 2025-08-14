package i18n

/**
 * Automatically generated I18n string keys.
 * Do not modify manually. Use scripts/i18n_autogen.py instead.
 */
enum class I18nKeys(val key: String) {
    back("back"),
    east("east"),
    enterName("enterName"),
    greeting("greeting"),
    info_you_return("info_you_return"),
    north("north"),
    playerName("playerName"),
    randomName("randomName"),
    settings("settings"),
    start("start"),
    toggleMusic("toggleMusic"),
    toggleSound("toggleSound"),
    victoryMessage("victoryMessage"),
    welcome("welcome"),
    welcome_and_start("welcome_and_start"),
    west("west"),
    you("you"),

    ;

    override fun toString(): String = key
}