# Game Objects #

During the game, the player interacts with objects. Objects represent the rooms the player will visit, the doors that separate rooms, and items or NPCs he will encounter.

Game objects have a set of properties that describe how they relate to each other, and how they are presented to the user. Each property has a name, see ObjectProperty for a list of properties supported by objects.

# Name and Description #

Each object has a name which is composed of letters and digits. Every object in the game must have a unique name. The name is never shown to the player. Instead, the player will see the description.

The name is used to refer to the object in the description of other objects. For example, if you define a room with the name TreasureRoom, you can then say that the Treasure object is located in the treasure room:

```
  %TreasureRoom The treasure room

  %Treasure The treasure
    LOCATION TreasureRoom
```

# The player object #

Every game must define an object with the name "Player" that represents the player. This allows the game to define where the player stands initially, and also define which objects he carries:

```
  %Player The player
    LOCATION MainEntrance
  %Key A small key
    LOCATION Player
```

This says that the player is initially in the room named MainEntrance, and that the small key is initially located "in" the player, and so will appear in his inventory.

# Rooms #

Rooms are defined as any other object. The description of the room is shown to the player when he is in that room.

Rooms have exits that are defined with the NORTH, SOUTH, EAST, etc. properties. Each exit leads to another room, possibly through a door:

```
  %MainHall The main hall
    NORTH DiningRoom
    SOUTH WoodenDoor -> TreasureRoom
```

This indicates that the main hall has two exits. One obvious exit on the north leads to the dining room, while the exit on the south leads to the treasure room, through a wooden door.

# Doors #

Doors are defined as any other object. The description of the door is shown to the player when he is in a room with an exit through that door.

The "OPEN" property indicates whether the door is currently open or closed. The "LOCKED" property indicates whether the door is currently locked. If the door is not locked the player is free to open or close it. If the door is locked, the player cannot open or close it.

To lock and unlock a door, the player may use a key:

```
  %WoodenDoor A wooden door
  OPEN 0
  LOCKED 1
  KEY Key
```

This indicates that the wooden door is initially closed and locked, and can be unlocked by using the key.

# Light sources #

A room may have a light source. A light source is also defined as an object. The "LIT" property of a light source indicates whether it is lit or not.

If you do not define a light source for a room, it is assumed that the room is always visible. Otherwise, the player will only be able to interact with the room when either the light source for the room is lit, or he is carrying a lit object.

```
  %TreasureRoom The treasure room
    LIGHTSOURCE Lamp

  %Lamp A lamp
    LIT 0
```

Lighting has no intensity, the object is either completely lit or completely unlit. If it is lit, it produces enough light to see the entire room.