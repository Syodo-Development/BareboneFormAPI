package xyz.syodo.form.element.defaults;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xyz.syodo.form.element.Element;

@Getter
@Setter
@AllArgsConstructor
public class ElementLabel extends Element {
    private String text;

    @Override
    protected Type getType() {
        return Type.LABEL;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "label");
        object.addProperty("text", this.text);
        return object;
    }
}
