package ml.pkom.uncraftingtable;

import ml.pkom.mcpitanlibarch.api.entity.Player;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;

public class UncraftingInventory extends SimpleInventory {
    public UncraftingInventory() {
        super(10);
    }

    public InsertSlot insertSlot;

    public void setInsertSlot(InsertSlot insertSlot) {
        this.insertSlot = insertSlot;

    }

    public void onOpen(PlayerEntity player) {
        super.onOpen(player);
    }

    public void onClose(PlayerEntity playerEntity) {
        Player player = new Player(playerEntity);
        if (!insertSlot.getStack().isEmpty()) {
            insertSlot.player.offerOrDrop(insertSlot.getStack());
        }
        super.onClose(player.getPlayerEntity());
    }
}
