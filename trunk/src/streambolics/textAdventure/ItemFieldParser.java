package streambolics.textAdventure;

import streambolics.core.ConvertedFieldParser;

public abstract class ItemFieldParser extends ConvertedFieldParser<Item, Item>
{
    private Game _Game;

    public ItemFieldParser (Game aGame)
    {
        _Game = aGame;
    }

    @Override
    protected Item convertToInternal (String aValue)
    {
        return _Game.accessItem (aValue);
    }

    @Override
    protected String convertFromInternal (Item aValue)
    {
        return aValue.getName ();
    }

}
