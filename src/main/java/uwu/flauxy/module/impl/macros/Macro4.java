package uwu.flauxy.module.impl.macros;

import uwu.flauxy.commands.impl.CommandMacro;
import uwu.flauxy.module.Category;
import uwu.flauxy.module.Module;
import uwu.flauxy.module.ModuleInfo;
import uwu.flauxy.utils.Wrapper;

@ModuleInfo(name = "Macro4", displayName = "Macro 4", key = -1, cat = Category.Macro)
public class Macro4 extends Module {

    int macroId = 3;

    @Override
    public void onEnable() {
        if(CommandMacro.macros[macroId] != null){
            if(!CommandMacro.macros[macroId].isEmpty()){
                mc.thePlayer.sendChatMessage(CommandMacro.macros[macroId]);
            }else{
                Wrapper.instance.log("Macro empty");
            }
        }else{
            Wrapper.instance.log("Macro empty");
        }
        this.toggle();
    }
}
