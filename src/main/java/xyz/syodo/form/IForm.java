package xyz.syodo.form;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormResponsePacket;
import xyz.syodo.form.handlers.ModalFormResponsePacketHandler;
import xyz.syodo.form.response.FormResponse;
import xyz.syodo.network.PacketHandlerPipe;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public abstract class IForm {

    public static int id_counter = 0;

    public static Gson GSON = new Gson();

    protected String title = "";
    protected Consumer<PacketHandlerPipe> closed = null;
    protected BiConsumer<PacketHandlerPipe, FormResponse> submit = null;
    @Setter
    protected Consumer<PacketHandlerPipe> failedConsumer;
    protected PacketHandlerPipe handler;
    protected int id;

    public IForm(PacketHandlerPipe handler) {
        this.handler = handler;
        this.id = id_counter++;
    }

    public IForm setTitle(String title) {
        this.title = title;
        return this;
    }

    public IForm onClose(Consumer<PacketHandlerPipe> callback) {
        this.closed = callback;
        return this;
    }

    public IForm setClosed() {
        if (this.closed != null)
            this.closed.accept(handler);
        return this;
    }

    public IForm setSubmitted(PacketHandlerPipe player, FormResponse data) {
        if (this.submit != null)
            this.submit.accept(player, data);
        return this;
    }

    public void send() {
        ModalFormRequestPacket packet = new ModalFormRequestPacket();
        packet.setFormId(this.title.hashCode());
        packet.setFormData(this.toJson());
        ((ModalFormResponsePacketHandler) handler.getHandlers().stream().filter(packetHandler -> packetHandler.getType() == ModalFormResponsePacket.class).findFirst().get()).setForm(this);
        handler.getPlayer().sendPacket(packet);
    }


    public boolean handle(ModalFormResponsePacket packet) {
        return packet.getFormData() != null && !packet.getFormData().equals("null");
    }

    protected abstract String windowType();

    protected abstract String toJson();
}
