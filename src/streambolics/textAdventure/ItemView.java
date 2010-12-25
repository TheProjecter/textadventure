package streambolics.textAdventure;

import streambolics.android.IconAndTextView;
import android.content.Context;

public class ItemView extends IconAndTextView
{
    public ItemView (Context aContext, Item aItem)
    {
        super (aContext, aItem.getIconResourceId (), aItem.getDescription ());
    }

    public void setItem (Item aItem)
    {
        setText (aItem.getDescription ());
        setIcon (aItem.getIconResourceId ());
    }
}
