package me.tdr.autoflight;

import net.fabricmc.api.ClientModInitializer;

public class AutoFlightMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FlightKeyHandler.register();
    }
}
