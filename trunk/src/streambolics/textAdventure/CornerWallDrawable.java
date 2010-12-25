package streambolics.textAdventure;

import android.graphics.drawable.Drawable;

public class CornerWallDrawable extends WallDrawable
{
    // TODO : Make sure there is exactly ONE drawn cell at the right.

    @Override
    public void setWall (Drawable aWall)
    {
        // TODO : Transform this to "bend" nicely
        setRight (aWall);

        // TODO : Rotate this 90°
        setBelowRight (aWall);
    }

    @Override
    public void setFloor (Drawable aFloor)
    {
        setBelow (aFloor);
    }

}
