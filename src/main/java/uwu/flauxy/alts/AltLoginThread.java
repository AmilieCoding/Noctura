package uwu.flauxy.alts;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;
import uwu.flauxy.Flauxy;
import uwu.flauxy.notification.Notification;
import uwu.flauxy.notification.NotificationType;

public final class AltLoginThread
extends Thread {
    private final String password;
    private String status;
    private final String username;
    private Minecraft mc = Minecraft.getMinecraft();

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = (Object)((Object)EnumChatFormatting.GRAY) + "Waiting...";
    }

    private Session createSession(String username, String password) {
        try {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            MicrosoftAuthResult result = null;

            result = authenticator.loginWithCredentials(username, password);
            //System.out.printf("Logged in with '%s'%n", result.getProfile().getName());
            return new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Logged in. (" + this.username + " - offline name)";
            Flauxy.INSTANCE.getNotificationManager().addToQueue(new Notification(NotificationType.INFO, "Alt Manager", "Username is now " + this.username, 1500));
            return;
        }
        this.status = (Object)((Object)EnumChatFormatting.YELLOW) + "Logging in...";
        Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = (Object)((Object)EnumChatFormatting.RED) + "Login failed!";
        } else {
            AltManager altManager = Flauxy.INSTANCE.altManager;
            AltManager.lastAlt = new Alt(this.username, this.password);
            this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Logged in. (" + auth.getUsername() + ")";
            Flauxy.INSTANCE.getNotificationManager().addToQueue(new Notification(NotificationType.INFO, "Alt Manager", "Logged into " + auth.getUsername(), 1500));
            this.mc.session = auth;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

