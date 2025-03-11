package xyz.syodo.form.element.defaults;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xyz.syodo.form.element.Element;

@Getter
@Setter
@AllArgsConstructor
public class ElementSlider extends Element {
    private String text;
    private float min;
    private float max;
    private int step;
    private float defaultValue;

    public ElementSlider(String text, float min, float max) {
        this(text, min, max, 0);
    }

    public ElementSlider(String text, float min, float max, int step) {
        this(text, min, max, step, 0);
    }

    @Override
    protected Type getType() {
        return Type.SLIDER;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "slider");
        object.addProperty("text", this.text);
        object.addProperty("min", this.min);
        object.addProperty("max", this.max);
        object.addProperty("step", this.step);
        object.addProperty("default", this.defaultValue);
        return object;
    }
}
