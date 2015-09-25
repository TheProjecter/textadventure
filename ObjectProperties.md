# Object Property Reference

# Introduction #

Every item in the game has a set of named properties that can be set in the game description file, then set or checked in scripts.

# Changing the values of properties #
Some properties control how the player interacts with the item. For example, if an item is locked, the player cannot easily open or close that item. However, a script has no such restrictions: you can change the value of the `OPEN` property even when the `LOCKED` property is true.

# Properties #

## EMPTY ##
Whether the item is empty.

An item is empty if it contains no other items.

While you can check the value of this property in scripts, you cannot change its value. To make an item empty, remove all items it contains by changing their `LOCATION` property.

## FIXED ##
Whether the item is fixed in the room.

## ICON ##
The icon to be used to represent the item.

## KEY ##
The name of an item that acts as a key.

## LIGHT ##
The name of an item that acts as a light source.

This property is useful mainly in items describing rooms. When a room has a light source, the light source must be lit for the contents of a room to be visible.

If you do not define a light source for a room, the game engine considers that the room is lit by an eternal light source and is always visible.

## LIT ##
Whether the object is lit.

## LOCATION ##
The name of the item that contains this item.

Items have a "contents" relationship with each other, as every item may be contained in other items. For example:
  * The player item is contained in the current room
  * Items that the player is carrying are contained in the Player item

## LOCKED ##
Whether the item is locked.

This property works in conjunction with the `KEY` property. When the user operates the key on the item, it becomes locked or unlocked.

When an item is locked, the player cannot easily open or close it.

## OPEN ##
Whether the item is open or closed.