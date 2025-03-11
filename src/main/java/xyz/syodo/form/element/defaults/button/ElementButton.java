package xyz.syodo.form.element.defaults.button;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ElementButton {
    private String text;
    private ElementButtonImage image;

    public ElementButton(String text) {
        this(text, (ElementButtonImage) null);
    }

    public ElementButton(String text, String path) {
        this(text, new ElementButtonImage(ElementButtonImage.Type.PATH, path));
    }
}
