//Title: V4 Mining Guild Iron Ore Miner
////____________________________________________________________________________________________
//Choose which mining location in the iron guild you want
//____________________________________________________________________________________________
//User Decision variables 0 = Spot 0, 1 = Spot 1, 2 = Spot 2
int decision = 1; //0 = Spot 0, 1 = Spot 1, 2 = Spot 2
int dragon_pickaxe_special_attack = 0; //0 = No pickaxe, 1 = Equipped Dragon pickaxe
//__________________________________________________________________________________________
// ID of the iron ore & Depleted Iron Ore
int iron_ore = 440;
GameObject depletedObj_1 = v.getGameObject().findNearest(11390);
GameObject depletedObj_2 = v.getGameObject().findNearest(11391);
// Gems Id
int ruby = 1619;
int emerald = 1621;
int sapphire = 1623;
int diamond = 1617;
//gem bag variables story
int gem_bag = 24481;
int gem_bag_closed = 12020;
int gem_bag_slot = v.getInventory().slot(gem_bag);            
int gem_bag_closed_slot = v.getInventory().slot(gem_bag_closed);            
WorldPoint wp1 = new WorldPoint(3013, 9719, 0); //L1

private void openGemBag() {
    if (v.getInventory().hasIn(gem_bag_closed)) {
        v.invoke("Open","<col=ff9040>Gem bag</col>",3,57,gem_bag_closed_slot,9764864,false);
    }
}

private void dropGems() {
    if (v.getInventory().hasIn(gem_bag))
    {
      v.invoke("Fill","<col=ff9040>Open gem bag</col>",2,57,gem_bag_slot,9764864,false);
    }
    else {
         v.getInventory().drop(ruby, sapphire, diamond, emerald);   
    }
    
}

private void handleRunning() {
    if (client.getEnergy() == 2000) {
        v.getWalking().turnRunningOn();
    }
}
private void useSpecialAttack() {
    if (v.getCombat().specRemaining(100, 100)) {
        v.getCombat().spec(1);
    }
}
private void mineIronOre_location_0() {
    WorldPoint currentLocation = client.getLocalPlayer().getWorldLocation();

    if (!currentLocation.equals(wp1) && v.getInventory().inventoryFull()) { 

        handleRunning();
        v.getWalking().walk(wp1);
    } else {


        if (v.getInventory().inventoryFull()) {
            v.getInventory().drop(iron_ore);
            if (v.getInventory().hasIn(ruby) || v.getInventory().hasIn(emerald) || v.getInventory().hasIn(diamond) || v.getInventory().hasIn(sapphire)) {
                log.info("We found gems");
                openGemBag();
                dropGems();
            }
        } else {
            if (v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving() && v.getWalking().nearEntity(Entity.GAME, 11366, 10) || v.getWalking().nearEntity(Entity.GAME, 11367, 10)) {
                v.getMining().mine(11366, 11367);
            }          
        }
    }
}

if (dragon_pickaxe_special_attack == 1) {
    useSpecialAttack();  
}
mineIronOre_location_0();