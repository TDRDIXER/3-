package me.tdr.autoflight;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

public class FlightKeyHandler {

    private static KeyBinding flightKey;

    public static void register() {
        flightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autoflight.activate",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.autoflight"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (flightKey.wasPressed()) {
                activate(client);
            }
        });
    }

    private static void activate(MinecraftClient client) {
        if (client.player == null || client.interactionManager == null) return;

        int slot = findItem(client, Items.ELYTRA);
        if (slot != -1) {
            client.interactionManager.clickSlot(
                    client.player.currentScreenHandler.syncId,
                    slot,
                    0,
                    SlotActionType.QUICK_MOVE,
                    client.player
            );
        }

        client.options.jumpKey.setPressed(true);

        int rocketSlot = findItem(client, Items.FIREWORK_ROCKET);
        if (rocketSlot != -1) {
            client.interactionManager.clickSlot(
                    client.player.currentScreenHandler.syncId,
                    rocketSlot,
                    0,
                    SlotActionType.QUICK_MOVE,
                    client.player
            );
            client.interactionManager.interactItem(client.player, client.world, client.player.getActiveHand());
        }
    }

    private static int findItem(MinecraftClient client, net.minecraft.item.Item item) {
        for (int i = 0; i < client.player.getInventory().size(); i++) {
            if (client.player.getInventory().getStack(i).isOf(item)) {
                return i;
            }
        }
        return -1;
    }
}
