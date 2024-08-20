package uwu.flauxy.module.impl.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;
import uwu.flauxy.event.Event;
import uwu.flauxy.event.impl.EventUpdate;
import uwu.flauxy.module.Category;
import uwu.flauxy.module.Module;
import uwu.flauxy.module.ModuleInfo;
import uwu.flauxy.module.setting.impl.NumberSetting;
import uwu.flauxy.utils.Wrapper;

import java.text.DecimalFormat;


@ModuleInfo(name = "Parkour", displayName = "Parkour", cat = Category.Movement, key = -1)
public class Parkour extends Module {

    NumberSetting margin = new NumberSetting("Distance",0.1,0.00,0.25,0.05);

    public Parkour(){
        addSettings(margin);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            DecimalFormat df = new DecimalFormat("#.###");
            double posX = mc.thePlayer.posX;
            double posZ = mc.thePlayer.posZ;
            double blockX = Math.floor(posX);
            double blockZ = Math.floor(posZ);

            double difX = posX - blockX;
            double difZ = posZ - blockZ;

            double edgeMargin = margin.getValue();
            double lowerThreshold = 0.3 - edgeMargin;
            double upperThreshold = 0.7 + edgeMargin;

            boolean onEdgeX = difX <= lowerThreshold || difX >= upperThreshold;
            boolean onEdgeZ = difZ <= lowerThreshold || difZ >= upperThreshold;
            BlockPos blockInFront = null;
            if (mc.thePlayer.moveForward > 0) { // forward
                blockInFront = mc.thePlayer.getPosition().add(mc.thePlayer.getLookVec().xCoord, -1, mc.thePlayer.getLookVec().zCoord);
            } else if (mc.thePlayer.moveForward < 0) { // backward
                blockInFront = mc.thePlayer.getPosition().add(-mc.thePlayer.getLookVec().xCoord, -1, -mc.thePlayer.getLookVec().zCoord);
            } else if (mc.thePlayer.moveStrafing > 0) { // right
                blockInFront = mc.thePlayer.getPosition().add(mc.thePlayer.getLookVec().zCoord, -1, -mc.thePlayer.getLookVec().xCoord);
            } else if (mc.thePlayer.moveStrafing < 0) { // left
                blockInFront = mc.thePlayer.getPosition().add(-mc.thePlayer.getLookVec().zCoord, -1, mc.thePlayer.getLookVec().xCoord);
            }

            boolean isBlockAir = blockInFront != null && mc.thePlayer.getEntityWorld().isAirBlock(blockInFront);
            boolean shouldJump = (onEdgeX || onEdgeZ);
            if (shouldJump && isBlockAir && mc.thePlayer.onGround && mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking()) {
                mc.gameSettings.keyBindJump.pressed = true;
            } else {
                mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
            }
        }
    }
}
