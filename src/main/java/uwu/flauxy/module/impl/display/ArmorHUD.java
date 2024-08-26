package uwu.flauxy.module.impl.display;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uwu.flauxy.event.Event;
import uwu.flauxy.event.impl.EventRender2D;
import uwu.flauxy.module.Category;
import uwu.flauxy.module.Module;
import uwu.flauxy.module.ModuleInfo;
import uwu.flauxy.module.setting.impl.BooleanSetting;
import uwu.flauxy.utils.MathHelper;
import uwu.flauxy.utils.render.RenderUtil;

import java.awt.*;
import java.util.Arrays;

@ModuleInfo(name = "ArmorHUD", cat = Category.Display, key = -1, displayName = "Armor HUD")
public class ArmorHUD extends Module {

    BooleanSetting background = new BooleanSetting("Background",true);

    public ArmorHUD(){
        setHudMoveable(true);
        setMoveX(100);
        setMoveY(300);
        addSettings(background);
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventRender2D){
            int sizeBoxX = 24;
            int sizeBoxY = 20;
            int yOffset = 0;
            setMoveH(sizeBoxY * 4);
            setMoveW((float) sizeBoxX);
            RenderUtil.drawRoundedRect2(getMoveX(),getMoveY(),getMoveX() + getMoveW(),getMoveY() + getMoveH(), 4, new Color(0, 0, 0, 90).getRGB());

            ItemStack[] armor = Arrays.copyOf(mc.thePlayer.inventory.armorInventory, mc.thePlayer.inventory.armorInventory.length);
            MathHelper.reverse(armor);
            for(ItemStack i : armor){
                if(i != null){
                    mc.getRenderItem().renderItemIntoGUI(i, (int) getMoveX() + 4, (int) (getMoveY() + yOffset) + 2);
                }
                yOffset += sizeBoxY;
            }
        }
    }
}
