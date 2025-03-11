package xyz.syodo.form.defaults;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormResponsePacket;
import xyz.syodo.form.IForm;
import xyz.syodo.form.response.defaults.FormIDResponse;
import xyz.syodo.network.PacketHandlerPipe;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class ModalForm extends IForm {
    protected String content = "";

    protected Consumer<PacketHandlerPipe> yes = null;
    protected Consumer<PacketHandlerPipe> no = null;

    protected String yesText = "Yes";
    protected String noText = "No";

    public ModalForm(PacketHandlerPipe handler) {
        super(handler);
    }

    public ModalForm setContent(String content) {
        this.content = content;
        return this;
    }

    public ModalForm onYes(Consumer<PacketHandlerPipe> yes) {
        this.yes = yes;
        return this;
    }

    public ModalForm setYes(PacketHandlerPipe player) {
        if (this.yes != null)
            this.yes.accept(player);
        return this;
    }

    public ModalForm onNo(Consumer<PacketHandlerPipe> no) {
        this.no = no;
        return this;
    }

    public ModalForm setNo(PacketHandlerPipe player) {
        if (this.no != null)
            this.no.accept(player);
        return this;
    }

    public ModalForm setText(String yes, String no) {
        this.yesText = yes;
        this.noText = no;
        return this;
    }

    @Override
    public ModalForm setTitle(String title) {
        return (ModalForm) super.setTitle(title);
    }

    @Override
    public ModalForm onClose(Consumer<PacketHandlerPipe> callback) {
        return (ModalForm) super.onClose(callback);
    }

    @Override
    protected String windowType() {
        return "modal";
    }

    @Override
    protected String toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", this.windowType());
        object.addProperty("title", this.getTitle());
        object.addProperty("content", this.getContent());
        object.addProperty("button1", this.yesText);
        object.addProperty("button2", this.noText);

        return object.toString();
    }

    public ModalForm onSubmit(BiConsumer<PacketHandlerPipe, FormIDResponse> callback) {
        this.submit = (player, response) -> callback.accept(player, (FormIDResponse) response);
        return this;
    }

    @Override
    public boolean handle(ModalFormResponsePacket packet) {
        boolean yes;
        if (!super.handle(packet)) {
            yes = false;
        } else yes = packet.getFormData().trim().equals("true");

        if (yes) this.setYes(handler);
        else this.setNo(handler);

        this.setSubmitted(handler, new FormIDResponse(yes ? 0 : 1, yes ? this.yesText : this.noText));
        return true;
    }
}
