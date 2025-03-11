package xyz.syodo.form.handlers;

import org.cloudburstmc.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;
import org.cloudburstmc.protocol.bedrock.packet.ShowProfilePacket;
import org.cloudburstmc.protocol.common.PacketSignal;
import xyz.syodo.form.defaults.SimpleForm;
import xyz.syodo.form.element.defaults.button.ElementButton;
import xyz.syodo.network.PacketHandlerPipe;
import xyz.syodo.network.packet.PacketHandler;


public class SetLocalPlayerAsInitializedPacketHandler extends PacketHandler<SetLocalPlayerAsInitializedPacket> {

    public SetLocalPlayerAsInitializedPacketHandler(PacketHandlerPipe HANDLER) {
        super(HANDLER);
    }

    @Override
    public PacketSignal handle(SetLocalPlayerAsInitializedPacket packet) {

        SimpleForm form = new SimpleForm(HANDLER);
        form.setContent("test");
        form.addElement(new ElementButton("test"), wha -> {
            ShowProfilePacket showProfilePacket = new ShowProfilePacket();
            showProfilePacket.setXuid("2535452984683773");
            HANDLER.getPlayer().sendPacket(showProfilePacket);
        });
        form.setFailedConsumer(wha -> {
            form.send();
        });
        form.send();

        return PacketSignal.HANDLED;
    }
}
