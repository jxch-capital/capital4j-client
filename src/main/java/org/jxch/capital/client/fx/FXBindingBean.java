package org.jxch.capital.client.fx;

import javafx.scene.Parent;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FXBindingBean<T>  {
    private T controller;
    private Parent parent;
}
