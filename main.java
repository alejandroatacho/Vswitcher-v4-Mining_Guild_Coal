//Title: V4 Mining Guild Coal Miner and Banker
////____________________________________________________________________________________________
//Choose which mining location in the iron guild you want
//____________________________________________________________________________________________
int dragon_pickaxe_special_attack = 0; //0 = No pickaxe, 1 = Equipped Dragon pickaxe
//__________________________________________________________________________________________
// ID of the Coal Ore
int coal_ore = 453;
int coal_ore_slot = v.getInventory().slot(coal_ore);
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
WorldPoint wp1 = new WorldPoint(3014, 9720, 0); //Bank Box

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
private void mineCoal() {
    WorldPoint currentLocation = client.getLocalPlayer().getWorldLocation();

    if (!currentLocation.equals(wp1) && v.getInventory().inventoryFull()) { 

        handleRunning();
        v.getWalking().walk(wp1);
    } else {

        if (v.getInventory().inventoryFull() && !currentLocation.equals(wp1)) {
            v.getWalking().walk(wp1);
        }
            else if (currentLocation.equals(wp1) && v.getInventory().inventoryFull()){
                GameObject bankChest = v.getGameObject().findNearest(10529);
                if (bankChest != null) {
                    int bankChestSceneX = bankChest.getSceneMinLocation().getX();
                    int bankChestSceneY = bankChest.getSceneMinLocation().getY();

                    v.invoke("Deposit","<col=ffff>Bank Deposit Box",10529,3,bankChestSceneX,bankChestSceneY,false);
                    v.getCallbacks().afterTicks(3, () -> {
                        v.invoke("Default Quantity: <col=ff9040>All</col>","",1,57,-1,12582931,false);
                        if (v.getInventory().hasIn(gem_bag)) {
                            v.invoke("Empty","<col=ff9040>Open gem bag</col>",9,1007,gem_bag_slot,12582914,false);            
                        }
                        v.invoke("Deposit-All","<col=ff9040>Coal ore</col>",1,57,coal_ore_slot,12582914,false);
                        v.invoke("Close","",1,57,11,12582913,false);
                    });
                }
            } else if (v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving() && v.getWalking().nearEntity(Entity.GAME, 11366, 7) || v.getWalking().nearEntity(Entity.GAME, 11367, 7)) {
                    v.getMining().mine(11366, 11367);
                }          
            
        }
    }

    if (dragon_pickaxe_special_attack == 1) {
        useSpecialAttack();  
    }

if (v.getInventory().hasIn(ruby) || v.getInventory().hasIn(emerald) || v.getInventory().hasIn(diamond) || v.getInventory().hasIn(sapphire)) {
    log.info("We found gems");
    openGemBag();
    dropGems();
}
mineCoal();