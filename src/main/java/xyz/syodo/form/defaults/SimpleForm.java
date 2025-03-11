package xyz.syodo.form.defaults;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormResponsePacket;
import xyz.syodo.form.ElementForm;
import xyz.syodo.form.element.defaults.button.ElementButton;
import xyz.syodo.form.element.defaults.button.ElementButtonImage;
import xyz.syodo.form.response.defaults.FormIDResponse;
import xyz.syodo.network.PacketHandlerPipe;
import xyz.syodo.utils.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class SimpleForm extends ElementForm {
    protected FormType type = FormType.DEFAULT;

    protected String content = "";

    protected HashMap<ElementButton, Consumer<PacketHandlerPipe>> elements = new HashMap<>();

    public SimpleForm(PacketHandlerPipe handler) {
        super(handler);
    }

    public SimpleForm setContent(String content) {
        this.content = content;
        return this;
    }

    public SimpleForm setElements(HashMap<ElementButton, Consumer<PacketHandlerPipe>> elements) {
        this.elements = elements;
        return this;
    }

    public SimpleForm addElement(ElementButton element) {
        return addElement(element, null);
    }

    public SimpleForm addElement(ElementButton element, Consumer<PacketHandlerPipe> callback) {
        elements.put(element, callback);
        return this;
    }

    @Override
    public SimpleForm setTitle(String title) {
        return (SimpleForm) super.setTitle(title);
    }

    @Override
    public SimpleForm onClose(Consumer<PacketHandlerPipe> callback) {
        return (SimpleForm) super.onClose(callback);
    }

    @Override
    protected String windowType() {
        return "form";
    }

    @Override
    protected String toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", this.windowType());
        object.addProperty("title", this.getTitle());
        object.addProperty("content", this.getContent());

        JsonArray elements = new JsonArray();
        this.getElements().keySet().forEach(element -> {
            JsonObject elementObject = new JsonObject();
            elementObject.addProperty("text", element.getText());

            ElementButtonImage image = element.getImage();
            if (image != null) {
                JsonObject elementImageObject = new JsonObject();
                elementImageObject.addProperty("type", image.getType().name().toLowerCase());
                elementImageObject.addProperty("data", image.getData());

                elementObject.add("image", elementImageObject);
            }

            elements.add(elementObject);
        });
        object.add("buttons", elements);

        return object.toString();
    }

    public SimpleForm onSubmit(BiConsumer<PacketHandlerPipe, FormIDResponse> callback) {
        this.submit = (player, response) -> callback.accept(player, (FormIDResponse) response);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean handle(ModalFormResponsePacket packet) {
        if (!super.handle(packet)) {
            return false;
        }

        Map.Entry<ElementButton, Consumer<PacketHandlerPipe>>[] entries = this.elements.entrySet().toArray(new Map.Entry[0]);

        int clickedId = Parser.parseValue(packet.getFormData(), Parser.INTEGER_PARSER, 0);
        if (entries.length < clickedId || clickedId == -1) {
            return false;
        }

        Map.Entry<ElementButton, Consumer<PacketHandlerPipe>> entry = entries[clickedId];

        Consumer<PacketHandlerPipe> action = entry.getValue();
        if (action != null) {
            action.accept(handler);
        }

        this.setSubmitted(handler, new FormIDResponse(clickedId, entry.getKey().getText()));
        return true;
    }
}
