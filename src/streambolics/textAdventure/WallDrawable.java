package streambolics.textAdventure;

import streambolics.android.SurroundedDrawable;
import android.graphics.drawable.Drawable;

public class WallDrawable extends SurroundedDrawable
{
    public WallDrawable ()
    {
        super (null);
    }

    public void setWall (Drawable aWall)
    {
        setLeft (aWall);
        setRight (aWall);
    }

    public void setDoor (Drawable aDoor)
    {
        setCenter (aDoor);
    }

    public void setFloor (Drawable aFloor)
    {
        setBelow (aFloor);
    }

}
