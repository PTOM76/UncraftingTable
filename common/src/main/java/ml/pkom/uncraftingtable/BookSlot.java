package ml.pkom.uncraftingtable;

import ml.pkom.mcpitanlibarch.api.entity.Player;
import ml.pkom.mcpitanlibarch.api.util.ItemUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class BookSlot extends Slot {
    public Player player;

    public BookSlot(Inventory inventory, int index, int x, int y, Player player) {
        super(inventory, index, x, y);
        this.player = player;
    }

    public boolean canInsert(ItemStack stack) {
        return ItemUtil.isEqual(stack.getItem(), Items.BOOK);
    }
}
