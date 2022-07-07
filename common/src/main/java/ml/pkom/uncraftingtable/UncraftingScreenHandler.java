package ml.pkom.uncraftingtable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class UncraftingScreenHandler extends ScreenHandler {

    private final UncraftingInventory inventory;

    public UncraftingScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public UncraftingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(UncraftingTable.supplierUNCRAFTING_TABLE_MENU.getOrNull(), syncId);
        inventory = new UncraftingInventory();
        int m, l;
        InsertSlot insertSlot = new InsertSlot(inventory, 0, 36, 35, playerInventory.player);
        inventory.setInsertSlot(insertSlot);
        addSlot(insertSlot);
        int i = 0;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                i++;
                addSlot(new OutSlot(inventory, i, 94 + l * 18, 17 + m * 18, insertSlot));
            }
        }
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    public static void init() {

    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            // 経験値の確認
            if (slot instanceof OutSlot) {
                int needXp = Config.config.getInt("consume_xp");
                if (needXp != 0 && !player.isCreative()) {
                    if (needXp > player.totalExperience) {
                        player.sendMessage(Utils.translatableText("message.uncraftingtable76.not_enough_xp"), false);
                        return ItemStack.EMPTY;
                    }
                }
            }

            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            // Uncrafting Inventory
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    slot.markDirty();
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                slot.markDirty();
                return ItemStack.EMPTY;
            } else {
                // Player Inventory → つまり、InsertSlotへ入れている可能性が高い
                inventory.insertSlot.updateOutSlot(inventory.insertSlot.getStack());
                slot.markDirty();
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
                slot.markDirty();
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void close(PlayerEntity player) {
        inventory.onClose(player);
        super.close(player);
    }
}
