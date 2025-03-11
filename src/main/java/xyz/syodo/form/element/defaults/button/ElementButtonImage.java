package xyz.syodo.form.element.defaults.button;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ElementButtonImage {
    private Type type;
    private String data;

    public enum Type {
        URL,
        PATH
    }
}
