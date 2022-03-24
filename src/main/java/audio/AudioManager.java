package audio;

import cores.Config;

public class AudioManager {
    public static Audio lobby;
    public static Audio placeBomb;
    public static Audio explosion;
    public static Audio powerUp;
    public static Audio bgm;
    public static Audio victory;
    public static Audio defeat;
    private static float BGMVolume = 1f;
    private static float SFXVolume = 1f;

    public static void initialize() {
        lobby = new Audio("Sounds/Lobby.wav", false, true, 2);
        placeBomb = new Audio("Sounds/PlaceBomb.ogg", true, false, 1);
        explosion = new Audio("Sounds/Explosion.ogg", true, false, 2);
        powerUp = new Audio("Sounds/Powerup.ogg", true, false, 1);
        bgm = new Audio("Sounds/BGM.wav", false, true, 0.2f);
        victory = new Audio("Sounds/Victory.ogg", false, false, 1);
        defeat = new Audio("Sounds/Defeat.ogg", false, false, 1);
        setBGMVolume(Config.BGM_VOLUME);
        setSFXVolume(Config.SFX_VOLUME);
    }

    public static void setBGMVolume(float volume) {
        BGMVolume = volume;
        bgm.setVolume(0.2f * volume);
        lobby.setVolume(2 * volume);
    }

    public static void setSFXVolume(float volume) {
        SFXVolume = volume;
        placeBomb.setVolume(volume);
        explosion.setVolume(2 * volume);
        powerUp.setVolume(volume);
        victory.setVolume(volume * 0.8f);
        defeat.setVolume(volume * 0.8f);
    }

    public static float getBGMVolume() {
        return BGMVolume;
    }

    public static float getSFXVolume() {
        return SFXVolume;
    }
}
