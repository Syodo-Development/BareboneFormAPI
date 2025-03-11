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
public class ElementDropdown extends Element {
    private String text;
    private List<String> options;
    private int defaultOption;

    public ElementDropdown(String text) {
        this(text, new ArrayList<>());
    }

    public ElementDropdown(String text, List<String> options) {
        this(text, options, 0);
    }

    @Override
    protected Type getType() {
        return Type.DROPDOWN;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "dropdown");
        object.addProperty("text", this.text);
        object.addProperty("default", this.defaultOption);

        JsonArray optionsArray = new JsonArray();
        for (String option : this.options) {
            optionsArray.add(option);
        }
        object.add("options", optionsArray);
        return object;
    }
}
