package uwu.flauxy.ui.dropdown.components;


import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import uwu.flauxy.Flauxy;
import uwu.flauxy.module.Category;
import uwu.flauxy.module.Module;
import uwu.flauxy.ui.dropdown.ColorHelper;
import uwu.flauxy.utils.animtations.Animate;
import uwu.flauxy.utils.animtations.Easing;
import uwu.flauxy.utils.render.RenderUtil;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static uwu.flauxy.utils.font.FontManager.getFont;


public class CategoryFrame implements ColorHelper {

    // Stuff
    private int x, y, xDrag, yDrag;
    private int width, height;

    private int offset; // Used to scroll

    private boolean drag;

    private final Category category;

    private final ArrayList<ModuleFrame> modules;

    // Smooth animation
    private final Animate animation;

    // Asking x and y so categories are not on themself
    public CategoryFrame(Category category, int x, int y)
    {
        this.category = category;
        this.modules = new ArrayList<>();
        this.animation = new Animate().setEase(Easing.ELASTIC_OUT).setSpeed(150).setMin(0).setMax(defaultWidth / 2F);

        this.x = x;
        this.y = y;
        this.xDrag = 0;
        this.yDrag = 0;
        this.offset = 0;

        this.drag = false;

        this.width = defaultWidth;
        this.height = defaultHeight;

        for (Module module : Flauxy.INSTANCE.getModuleManager().getModules(category)) {
            this.modules.add(new ModuleFrame(module, this, 0, 0));
        }
    }

    public void initGui()
    {
        this.animation.setSpeed(100).reset();
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        AtomicInteger offCat = new AtomicInteger();
        this.modules.forEach(module -> offCat.addAndGet(module.getOffset()));

        // Calculate height
        height = Math.min(categoryNameHeight + offCat.get(), defaultHeight);

        if(Mouse.hasWheel() && RenderUtil.hover(x, y, mouseX, mouseY, defaultWidth, height))
        {
            int wheel = Mouse.getDWheel();
            if(wheel > 0 && offset - (moduleHeight - 1) > 0) {
                offset -= moduleHeight;
            } else if(wheel < 0 && offset + (moduleHeight - 1) <= offCat.get() - height + categoryNameHeight) {
                offset += moduleHeight;
            }
        }

        // Drawing category base
        // Drawing category name rect thing

        RenderUtil.drawRoundedRect2(getX(), getY(), getX() + width, getY() + getHeight(), 0, mainColor);
        RenderUtil.drawRoundedRect2(getX(), getY(), getX() + width, getY() + categoryNameHeight, 0, darkerMainColor);


        // Drag ClickGUI
        if(drag) {
            setX(this.xDrag + mouseX);
            setY(this.yDrag + mouseY);
        }

        // Drawing category name
        getFont().drawString(category.name(), x + 3, y + ((categoryNameHeight / 2F) - getFont().getHeight("A") / 2F) + 1, stringColor);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderUtil.prepareScissorBox(getX() + (width / 2F) - animation.update().getValue(), getY() + categoryNameHeight, x + (width / 2F) + animation.getValue(), y + getHeight());

        // Drawing modules
        int i = 0;
        for (ModuleFrame module : this.modules)
        {
            module.setX(x);
            module.setY(y + categoryNameHeight + i - offset);
            module.drawScreen(mouseX, mouseY);
            i += module.getOffset();
        }

        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        // I really need to explain?
        for (ModuleFrame module : this.modules)
        {
            if(module.mouseClicked(mouseX, mouseY, mouseButton)) {
                setDrag(false);
                return;
            }
        }

        if(RenderUtil.hover(x, y, mouseX, mouseY, width, height) && mouseButton == 0)
        {
            setDrag(true);
            setXDrag(getX() - mouseX);
            setYDrag(getY() - mouseY);
        } else
            setDrag(false);
    }

    @SuppressWarnings("unused")
    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        this.drag = false;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY()
    {
        return y;
    }

    public void setXDrag(int xDrag)
    {
        this.xDrag = xDrag;
    }

    public void setYDrag(int yDrag)
    {
        this.yDrag = yDrag;
    }

    public void setDrag(boolean drag)
    {
        this.drag = drag;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
