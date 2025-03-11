package xyz.syodo.form.element.defaults;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xyz.syodo.form.element.Element;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ElementStepSlider extends Element {
    private String text;
    private List<String> steps;
    private int defaultStep;

    public ElementStepSlider(String text) {
        this(text, new ArrayList<>());
    }

    public ElementStepSlider(String text, List<String> steps) {
        this(text, steps, 0);
    }

    @Override
    protected Type getType() {
        return Type.STEPSLIDER;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "step_slider");
        object.addProperty("text", this.text);
        object.addProperty("default", this.defaultStep);
        JsonArray stepsArray = new JsonArray();
        for (String step : this.steps) {
            stepsArray.add(step);
        }
        object.add("steps", stepsArray);
        return object;
    }
}
