package ui.gui.game;

import audio.AudioManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import cores.Config;
import cores.Debugger;
import input.SystemInput;
import scenes.Game;
import scenes.GameAI;
import scenes.Menu;
import scenes.SceneController;
import ui.gui.ButtonGui;
import ui.gui.ImageGui;
import ui.gui.LocationGui;
import ui.gui.TextGui;
import ui.gui.buffs.BuffListGui;

public class AnnouncementGui {
    private static ImageGui background;
    private static ImageGui image;
    private static TextGui levelText, timeText;
    private static ButtonGui btn1;
    private static ButtonGui btn2;
    int nextLevel;

    public AnnouncementGui(boolean win) {
        if (win) {
            AudioManager.bgm.stop();
            AudioManager.victory.play();
        } else {
            AudioManager.bgm.stop();
            AudioManager.defeat.play();
        }
        SceneController.getCurrentScene().setActive(false); //pause game
        SystemInput.setActive(false); //pause input
        background = new ImageGui(new Vector2f(Config.WIDTH / 2f - 550, Config.HEIGHT / 2f - 300), new Vector2f(1100, 600), "Textures/Menu/announcement_background.png");
        this.nextLevel = SceneController.getCurrentScene().getLevel() + 1;
        int timeElapsed = (int) InfoGuiList.getTimeCount();
        if (this.nextLevel - 1 > Config.LEVEL_PLAYED) {
            Config.LEVEL_PLAYED = this.nextLevel - 1;
            Config.TIME_PLAYED = timeElapsed;
        } else if (this.nextLevel - 1 == Config.LEVEL_PLAYED) {
            if (timeElapsed < Config.TIME_PLAYED) {
                Config.TIME_PLAYED = timeElapsed;
            }
        }
        if (win) {
            image = new ImageGui(new Vector2f(Config.WIDTH / 2f - 500, Config.HEIGHT / 2f - 160), new Vector2f(1000, 414), "Textures/Menu/win.png");
            levelText = new TextGui("Level: " + (this.nextLevel - 1), ColorRGBA.White);
            timeText = new TextGui("Time Elapsed: " + timeElapsed + "s", ColorRGBA.White);
            LocationGui.centerXObject(levelText, background, Config.HEIGHT / 2f - 100);
            LocationGui.centerXObject(timeText, background, Config.HEIGHT / 2f - 130);
            btn1 = new ButtonGui("Next to level " + this.nextLevel, new Vector2f(Config.WIDTH / 2f - 210, Config.HEIGHT / 2f - 220), new Vector2f(200, 50)) {
                @Override
                public void onClick() {
                    Debugger.log(Debugger.EVENT, "Win next accepted. Next level = " + AnnouncementGui.this.nextLevel);
                    if (SceneController.getCurrentScene() instanceof Game) {
                        SceneController.setScene(new Game(AnnouncementGui.this.nextLevel));
                    } else if (SceneController.getCurrentScene() instanceof GameAI) {
                        SceneController.setScene(new GameAI(AnnouncementGui.this.nextLevel));
                    }
                    this.remove();

                }
            };
        } else {
            image = new ImageGui(new Vector2f(Config.WIDTH / 2f - 382, Config.HEIGHT / 2f - 120), new Vector2f(764, 373), "Textures/Menu/lose.png");
            levelText = new TextGui("Level: " + (nextLevel - 1), ColorRGBA.White);
            timeText = new TextGui("Time Elapsed: " + timeElapsed + "s", ColorRGBA.White);
            LocationGui.centerXObject(levelText, background, Config.HEIGHT / 2f - 100);
            LocationGui.centerXObject(timeText, background, Config.HEIGHT / 2f - 130);
            btn1 = new ButtonGui("New game", new Vector2f(Config.WIDTH / 2f - 210, Config.HEIGHT / 2f - 220), new Vector2f(200, 50)) {
                @Override
                public void onClick() {
                    Debugger.log(Debugger.EVENT, "Lose next accepted. Next level = 1");
                    if (SceneController.getCurrentScene() instanceof Game) {
                        SceneController.setScene(new Game(1));
                    } else if (SceneController.getCurrentScene() instanceof GameAI) {
                        SceneController.setScene(new GameAI(1));
                    }
                    InfoGuiList.setTimeCount(0);
                    this.remove();
                }
            };
        }
        btn2 = new ButtonGui("Back to main menu", new Vector2f(Config.WIDTH / 2f + 10, Config.HEIGHT / 2f - 220), new Vector2f(200, 50)) {
            @Override
            public void onClick() {
                Debugger.log(Debugger.EVENT, "Back to main menu accepted");
                SceneController.setScene(new Menu());
                this.remove();

            }
        };
        InfoGuiList.remove();
        BuffListGui.hardRemove();
        show();
    }

    public static void show() {
        background.show();
        image.show();
        btn1.show();
        btn2.show();
        levelText.show();
        timeText.show();
    }

    public static void remove() {
        if(background != null) background.remove();
        if(image != null) image.remove();
        if(btn1 != null) btn1.remove();
        if(btn2 != null) btn2.remove();
        if(levelText != null) levelText.remove();
        if(timeText != null) timeText.remove();
    }
}
