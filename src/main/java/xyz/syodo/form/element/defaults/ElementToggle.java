package xyz.syodo.form.element.defaults;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xyz.syodo.form.element.Element;

@Getter
@Setter
@AllArgsConstructor
public class ElementToggle extends Element {
    private String text;
    private boolean defaultValue;

    public ElementToggle(String text) {
        this(text, false);
    }

    @Override
    protected Type getType() {
        return Type.TOGGLE;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "toggle");
        object.addProperty("text", this.text);
        object.addProperty("default", this.defaultValue);
        return object;
    }
}
