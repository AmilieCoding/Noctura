package uwu.flauxy.module.impl.display;

import org.lwjgl.input.Keyboard;
import uwu.flauxy.module.Category;
import uwu.flauxy.module.Module;
import uwu.flauxy.module.ModuleInfo;
import uwu.flauxy.module.setting.impl.NumberSetting;

@ModuleInfo(name = "ClickGUI", displayName = "ClickGui", key = Keyboard.KEY_RSHIFT, cat = Category.Visuals)
public class ClickGUI extends Module {


    public static NumberSetting red = new NumberSetting("R", 180, 1, 250, 1);
    public static NumberSetting green = new NumberSetting("G", 10, 1, 250, 1);
    public static NumberSetting blue = new NumberSetting("B", 120, 1, 250, 1);
    public ClickGUI() {
        addSettings(red, green, blue);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new uwu.flauxy.ui.dropdown.ClickGUI());
        toggle();
        super.onEnable();
    }

}
