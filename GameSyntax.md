# Introduction #

This document describes the format of the map description file.

# File Name #

Map description files have a name composed of the name of the adventure, in camel case, and extension '.game'. For example: AdventureLand.game

When displaying the name of the adventure, TAC will split the words at each uppercase letter.

# General Syntax #

The game file is a pure text file, encoded in the ANSI character set. UTF-8 and/or UTF-16 are not supported yet.

The file consists of commands, each on a separate line. Commands relate either to an object or to a script.

# Defining an object #

The game objects (see GameObject) are defined by a line beginning wit a single % sign:

```
  %objname description
```

objname is the internal name of the object, and description is the description shown to the user. For example:

```
  %Key A small key
```

defines an object named Key, described to the user as "A small key".

The object description line can be followed by more instructions that set the properties of the object:

```
  %Key A small key
  LOCATION Room
  ICON Key
```

says that the key's initial location is the object named "Room" (rooms and other locations are also defined as objects) and should be represented with the icon of a key.

See ObjectProperties for a list of properties supported by objects.