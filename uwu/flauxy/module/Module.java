package uwu.flauxy.module;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import uwu.flauxy.event.Event;
import uwu.flauxy.module.setting.Setting;
import uwu.flauxy.utils.Methods;
import uwu.flauxy.utils.WorldUtil;

import java.util.ArrayList;


public class Module implements Methods {

    @Getter @Setter
    private boolean settingsShown = true;

    @Getter @Setter
    protected String name, displayName;

    @Getter @Setter
    protected int key;

    @Getter @Setter
    protected Category category;

    @Getter @Setter
    protected boolean toggled;
    @Getter
    protected Minecraft mc = Minecraft.getMinecraft();

    @Getter
    private ArrayList<Setting> settings = new ArrayList<>();

    public Module(){
        final ModuleInfo featureInfo = getClass().getAnnotation(ModuleInfo.class);

        this.name = featureInfo.name();
        this.displayName = featureInfo.displayName();
        this.key = featureInfo.key();
        this.category = featureInfo.cat();
    }

    public void onEnable() {
    }

    public void onDisable() {

    }

    public void toggle(){
        toggled = !toggled;
        if(toggled) onEnable();
        else onDisable();
    }

    public void onEvent(Event e){

        if(WorldUtil.shouldNotRun()) return;
    }
    public void onEventIgnore(Event e){}

    public void addSettings(final Setting... settings) {
        for (final Setting setting : settings) {
            this.getSettings().add(setting);
        }
    }

    public void addSetting(final Setting setting){
        this.getSettings().add(setting);
    }


}
