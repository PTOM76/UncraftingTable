package ml.pkom.uncraftingtable.client;

import dev.architectury.registry.menu.MenuRegistry;
import ml.pkom.uncraftingtable.UncraftingScreen;
import ml.pkom.uncraftingtable.UncraftingTable;

public class UncraftingTableClient {
    public static void init() {
        MenuRegistry.registerScreenFactory(UncraftingTable.supplierUNCRAFTING_TABLE_MENU.getOrNull(), UncraftingScreen::new);
    }
}
