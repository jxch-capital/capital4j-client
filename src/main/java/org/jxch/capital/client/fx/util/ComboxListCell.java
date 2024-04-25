package org.jxch.capital.client.fx.util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.function.Function;

@Setter
@Accessors(chain = true)
public class ComboxListCell<T> extends ListCell<T> {
    private Function<T, String> textGetter;

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (Objects.nonNull(textGetter)) {
            setText(empty || item == null ? "" : textGetter.apply(item));
        }
    }

    public static <T> void setComboxText(@NonNull ComboBox<T> combox, Function<T, String> comboxTextGetter) {
        combox.setCellFactory(lv -> new ComboxListCell<T>().setTextGetter(comboxTextGetter));
        combox.setButtonCell(new ComboxListCell<T>().setTextGetter(comboxTextGetter));
    }

}
