package xyz.syodo;

import xyz.syodo.form.handlers.ModalFormResponsePacketHandler;
import xyz.syodo.form.handlers.SetLocalPlayerAsInitializedPacketHandler;
import xyz.syodo.network.PacketHandlerRegistery;
import xyz.syodo.plugin.Plugin;

public class FormAPIPlugin extends Plugin {
    @Override
    public void load() {
        PacketHandlerRegistery packetHandlerRegistery = PacketHandlerRegistery.get();
        packetHandlerRegistery.addPacketHandler(ModalFormResponsePacketHandler.class);
        packetHandlerRegistery.addPacketHandler(SetLocalPlayerAsInitializedPacketHandler.class);
    }
}
