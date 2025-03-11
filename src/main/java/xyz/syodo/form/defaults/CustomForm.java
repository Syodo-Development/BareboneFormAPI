package xyz.syodo.form.defaults;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormResponsePacket;
import xyz.syodo.form.ElementForm;
import xyz.syodo.form.element.Element;
import xyz.syodo.form.element.defaults.*;
import xyz.syodo.form.response.defaults.FormCustomResponse;
import xyz.syodo.form.response.defaults.FormIDResponse;
import xyz.syodo.network.PacketHandlerPipe;
import xyz.syodo.utils.Parser;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class CustomForm extends ElementForm {
    protected static Type LIST_STRING_TYPE = new TypeToken<List<String>>(){}.getType();

    protected ObjectArrayList<Element> elements = new ObjectArrayList<>();

    public CustomForm(PacketHandlerPipe handler) {
        super(handler);
    }

    public CustomForm setElements(ObjectArrayList<Element> elements) {
        this.elements = elements;
        return this;
    }

    public CustomForm addElement(Element element) {
        elements.add(element);
        return this;
    }

    @Override
    public CustomForm setTitle(String title) {
        return (CustomForm) super.setTitle(title);
    }

    @Override
    public CustomForm onClose(Consumer<PacketHandlerPipe> callback) {
        return (CustomForm) super.onClose(callback);
    }

    @Override
    protected String windowType() {
        return "custom_form";
    }

    @Override
    protected String toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", this.windowType());
        object.addProperty("title", this.getTitle());

        JsonArray elementArray = new JsonArray();
        this.getElements().forEach(element -> elementArray.add(element.toJson()));

        object.add("content", elementArray);
        return object.toString();
    }

    public CustomForm onSubmit(BiConsumer<PacketHandlerPipe, FormCustomResponse> callback) {
        this.submit = (player, response) -> callback.accept(player, (FormCustomResponse) response);
        return this;
    }

    @Override
    public boolean handle(ModalFormResponsePacket packet) {
        if (!super.handle(packet)) {
            return false;
        }

        List<String> elementResponses = GSON.fromJson(packet.getFormData(), LIST_STRING_TYPE);

        Int2ObjectOpenHashMap<Object> responses = new Int2ObjectOpenHashMap<>();
        Int2ObjectOpenHashMap<FormIDResponse> dropdownResponses = new Int2ObjectOpenHashMap<>();
        Int2ObjectOpenHashMap<String> inputResponses = new Int2ObjectOpenHashMap<>();
        HashMap<Integer, Float> sliderResponses = new HashMap<>();
        Int2ObjectOpenHashMap<FormIDResponse> stepSliderResponses = new Int2ObjectOpenHashMap<>();
        HashMap<Integer, Boolean> toggleResponses = new HashMap<>();
        Int2ObjectOpenHashMap<String> labelResponses = new Int2ObjectOpenHashMap<>();

        
        int i = 0;
        for (String elementResponse : elementResponses) {
            if (i == this.elements.size()) {
                break;
            }
            
            Element element = this.elements.get(i);

            if (element instanceof ElementDropdown dropdown) {
                int option = Parser.parseValue(elementResponse, Parser.INTEGER_PARSER, 0);
                String answer = dropdown.getOptions().get(option);

                dropdownResponses.put(i, new FormIDResponse(option, answer));
                responses.put(i, answer);
            } else if (element instanceof ElementInput) {
                inputResponses.put(i, elementResponse);
                responses.put(i, elementResponse);
            } else if (element instanceof ElementSlider slider) {
                float answer = Parser.parseValue(elementResponse, Parser.FLOAT_PARSER, 0f);

                sliderResponses.put(i, answer);
                responses.put(i, (Float) answer);
            } else if (element instanceof ElementStepSlider stepSlider) {
                int step = Parser.parseValue(elementResponse, Parser.INTEGER_PARSER, 0);
                String answer = stepSlider.getSteps().get(step);

                stepSliderResponses.put(i, new FormIDResponse(step, answer));
                responses.put(i, answer);
            } else if (element instanceof ElementToggle) {
                boolean answer = elementResponse.equals("true");

                toggleResponses.put(i, answer);
                responses.put(i, (Boolean) answer);
            }

            i++;
        }

        this.setSubmitted(handler, new FormCustomResponse(responses, dropdownResponses, inputResponses, sliderResponses, stepSliderResponses, toggleResponses, labelResponses));
        return true;
    }
}
