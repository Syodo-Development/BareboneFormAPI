package xyz.syodo.form.element.defaults;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xyz.syodo.form.element.Element;

@Getter
@Setter
@AllArgsConstructor
public class ElementInput extends Element {
    private String text;
    private String placeholder;
    private String defaultText;

    public ElementInput(String text) {
        this(text, "");
    }

    public ElementInput(String text, String placeholder) {
        this(text, placeholder, "");
    }

    @Override
    protected Type getType() {
        return Type.INPUT;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "input");
        object.addProperty("text", this.text);
        object.addProperty("placeholder", this.placeholder);
        object.addProperty("default", this.defaultText);
        return object;
    }
}
