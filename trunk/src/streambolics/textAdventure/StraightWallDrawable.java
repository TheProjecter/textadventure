package streambolics.textAdventure;

import android.graphics.drawable.Drawable;

public class StraightWallDrawable extends WallDrawable
{

    @Override
    public void setFloor (Drawable aFloor)
    {
        setBelow (aFloor);
        setBelowLeft (aFloor);
        setBelowRight (aFloor);
    }

}
