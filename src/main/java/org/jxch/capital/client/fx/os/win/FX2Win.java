package org.jxch.capital.client.fx.os.win;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.jxch.capital.client.fx.os.FX2OS;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@Conditional(value = WinCondition.class)
public class FX2Win implements FX2OS {

    @Nullable
    public WinDef.HWND getNativeHandleForStage(@NonNull Stage stage) {
        try {
            var getPeer = Window.class.getDeclaredMethod("getPeer", null);
            getPeer.setAccessible(true);
            var tkStage = getPeer.invoke(stage);
            var getRawHandle = tkStage.getClass().getMethod("getRawHandle");
            getRawHandle.setAccessible(true);
            var pointer = new Pointer((Long) getRawHandle.invoke(tkStage));
            return new WinDef.HWND(pointer);
        } catch (Exception ex) {
            log.warn("Unable to determine native handle for window");
            return null;
        }
    }

    @Override
    public Boolean setDarkMode(Stage stage, boolean darkMode) {
        var hwnd = getNativeHandleForStage(stage);
        if (Objects.nonNull(hwnd)) {
            var dwmapi = Dwmapi.INSTANCE;
            WinDef.BOOLByReference darkModeRef = new WinDef.BOOLByReference(new WinDef.BOOL(darkMode));
            dwmapi.DwmSetWindowAttribute(hwnd, 20, darkModeRef, Native.getNativeSize(WinDef.BOOLByReference.class));
            return true;
        } else {
            log.warn("Unable to determine Dark Mode");
        }
        return false;
    }

}
