package ui.gui;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector2f;
import cores.Debugger;
import cores.Main;

public abstract class ImageButtonGui extends ItemGui {
    private final ImageGui button;
    private boolean active = true;

    public ImageButtonGui(Vector2f position, Vector2f size, String path) {
        button = new ImageGui(position, size, path);
        setSize(size.x, size.y);
        setPosition(position.x, position.y);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void onAction(String name, boolean keyPressed, float tpf) {
                if (active) {
                    if (keyPressed && name.equals("LClick")) {
                        Vector2f mousePos = Main.INPUT_MANAGER.getCursorPosition();
                        if (getPosition().x <= mousePos.x && getPosition().y <= mousePos.y
                                && getPosition().x + getSize().x >= mousePos.x
                                && getPosition().y + getSize().y >= mousePos.y) {
                            onClick();
                            Debugger.log(Debugger.EVENT, "Clicked " + this);
                        }
                    }
                }
            }
        };
        Main.INPUT_MANAGER.removeListener(actionListener);
        Main.INPUT_MANAGER.addListener(actionListener, "LClick");
    }

    public abstract void onClick();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void show() {
        button.show();
    }

    @Override
    public void remove() {
        button.remove();
        active = false;
    }

    @Override
    public void setSize(float sizeX, float sizeY) {
        super.setSize(sizeX, sizeY);
        button.setSize(sizeX, sizeY);
    }

    @Override
    public void setSizeX(float sizeX) {
        super.setSizeX(sizeX);
        this.button.setSizeX(sizeX);
    }

    @Override
    public void setSizeY(float sizeY) {
        super.setSizeY(sizeY);
        this.button.setSizeY(sizeY);
    }

    @Override
    public void setPosition(float posX, float posY) {
        super.setPosition(posX, posY);
        button.setPosition(posX, posY);
    }

    @Override
    public void setPositionX(float posX) {
        super.setPositionX(posX);
        button.setPositionX(posX);
    }

    @Override
    public void setPositionY(float posY) {
        super.setPositionY(posY);
        button.setPositionY(posY);
    }
}
