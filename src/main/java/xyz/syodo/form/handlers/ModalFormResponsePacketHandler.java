package xyz.syodo.form.handlers;


import lombok.Setter;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormResponsePacket;
import org.cloudburstmc.protocol.common.PacketSignal;
import xyz.syodo.form.IForm;
import xyz.syodo.network.PacketHandlerPipe;
import xyz.syodo.network.packet.PacketHandler;

public class ModalFormResponsePacketHandler extends PacketHandler<ModalFormResponsePacket> {

    public ModalFormResponsePacketHandler(PacketHandlerPipe HANDLER) {
        super(HANDLER);
    }
    @Setter
    private IForm form = null;

    @Override
    public PacketSignal handle(ModalFormResponsePacket packet) {
        if (form != null) {
            if (!form.handle(packet)) {
                form.setClosed();
                if(form.getFailedConsumer() != null) form.getFailedConsumer().accept(HANDLER);
            }
        }
        return PacketSignal.HANDLED;
    }
}
