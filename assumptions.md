## Enemies:
### Spawn:
- Assume slugs only spawns randomly on the path.
- Assume zombies only spawn in the zombie pit when the character completes the whole cycle.
- Assume vampires only spawn in the vampire castle when the character completes 5 cycles.

### Health:
- Assume the slugs’ health starts from 5.
- Assume zombies’ health starts from 15.
- Assume vampires’ health starts from 25.

### Attack Range:
- Assume the slugs’ attack radius is 1 tile.
- Assume the zombies’ attack radius is 2 tiles.
- Assume the vampires’ attack radius is 3 tiles.

### Support Range:
- Assume the slugs’ support radius is 1 tile.
- Assume the zombies’ support radius is 3 tiles.
- Assume the vampires’ support radius is 4 tiles.

### Character:
- Assume the character can be moved around on the path.
- Assume the character's health always starts from 100.
- Assume the character's lowest health value is 0.
- Assume the character wins when he reaches the goal.
- Assume the character experience always starts from 0.
- Assume the character can gain at most 100000 experience.
- Assume the character own 0 gold at the first beginning.
- Assume the character can own at most 10000 gold.
- Assume after the character uses health potion, the maximum health point can be 100.
- Assume the character cannot hurt enemies without any attacking equipment(i.e. sword, staff, stake)


## Equipment:
### Purchasing
- Assume the number of equipment is at most 24.
- Assume purchasing a sword needs 20  gold.
- Assume purchasing a stake needs 15 gold.
- Assume purchasing a staff requires 10 gold.
- Assume purchasing an armour requires 20 gold.
- Assume purchasing a shield requires 25 gold.
- Assume purchasing a helmet requires 30 gold.
- Assume purchasing a health potion requires 10 gold.

### Selling
- Assume selling a sword gains 15 gold.
- Assume selling a stake gains 10 gold.
- Assume selling a staff gains 5 gold.
- Assume selling an armour gains 15 gold.
- Assume selling a shield gains 20 gold.
- Assume selling a helmet gains 25 gold.

### Rare Items:
- Assume every time the character has 20% to win a rare item.

### Damages:
- Sword does 5 damage to any enemy.
- Stake does 8 damage to the vampire and does 4 damage to any other enemies.
- Staff does 2 damage to any enemy and has 50% probability of inflicting a trance.

### Defensive Equipment:
- Helmet decrease the enemies damage by 40%
- Helmet decrease the character’s attack by 20%

### Durability:
- A sword will be wrecked after 3 attacks.
- A stack will be wrecked after 4 attacks.
- An Armour will be wrecked after 5 attacks.
- A Shield will be wrecked 3 attacks.
- A Helmet will be wrecked  3 attacks.

### Item Replacement
 - An equipped item can be replaced by a new item(i.e. helmet, weapon, armour or shield). The old item will disappear.
## Buildings:
### Spawns:
- Assume a vampire castle card will spawn a vampire after the character completes 5 cycles, the castle will spawn near the path.
- Assume a zombie pit card will spawn a zombie after the character completes 1 cycle, the pit will spawn near the path.

### Durability
- Assume the vampire castle will disappear after 2 circles.
- Assume the Zombie pit will disappear after 2 circles.
- Assume the Tower will disappear after 1 circle.
- Assume the village will disappear after 1 circle.
- Assume the barracks will disappear after 2 circles.
- Assume the Trap will disappear after 2 circles.
- Assume the campfire will disappear after 1 circle.


## Battle:
### Experience 
- Assume the character gains 10 experience after killing a slug.
- Assume the character gains 50 experience after killing a zombie.
- Assume the character gains 100 experience after killing a vampire.

### Gold
- Assume the character gains 5 gold after killing a slug.
- Assume the character gains 25 gold after killing a zombie.
- Assume the character gains 50 gold after killing a vampire.
- Assume the character gains at most 50 gold when the character picks up from the ground.

### Building card
- Assume the character can have at most 10 cards at the same time.
- Assume character gains building card after killing enemies.
- Assume the character has 15% to gain a vampire castle card.
- Assume the character has 15% to gain a zombie pit card.
- Assume the character has 20% to gain a tower card.
- Assume the character has 10% to gain a village card.
- Assume the character has 10% to gain a barracks card.
- Assume the character has 10% to gain a trap card.
- Assume the character has 20% to gain a campfire card.

### Health points
- Assume the character loses 2 health points when attacked by slugs.
- Assume the character loses 3 health points when attacked by zombies.
- Assume the character loses 5 health points when attacked by vampires.
- Assume the character loses 10 health points when attacked by vampire critical attacks.

### Loot drop
- Assume a sword can be dropped from an enemy after battle.
- Assume a stake can be dropped from an enemy after battle.
- Assume a staff can be dropped from an enemy after battle.
- Assume an armour can be dropped from an enemy after battle.
- Assume a shield can be dropped from an enemy after battle.
- Assume a helmet can be dropped from an enemy after battle.
- Assume only one piece of equipment (i.e. sword, stake, staff, armour, shield, helmet) can be dropped after one battle with probability of 60%.
- Assume gold will be dropped from an enemy after battle.
- Assume the equipment cards can be dropped from an enemy after battle with a probability of 40%.
- Assume health potion can be dropped from an enemy after battle with a probability of 35%.
- Assume the one ring can be dropped from an enemy after battle with a probability of 5%.

### Priority:
- Assume character attacks first.

### Goal:
- Assume the character wins when he obtains 10000 golds.
- Assume the character wins when he obtains 12000 experience.
- Assume the character wins when he completes 50 cycles.
- Assume the character loses when the health point is 0.
