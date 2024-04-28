package org.jxch.capital.client.fx.progress;

import javafx.scene.control.ProgressBar;
import org.jetbrains.annotations.NotNull;

public interface Progress {

    ProgressBarPane registerProgress(@NotNull Integer total);


}
