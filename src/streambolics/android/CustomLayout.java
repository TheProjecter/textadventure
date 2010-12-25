package streambolics.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;

/***
 * The base class for all custom layouts.
 * 
 * @author Stéphan Leclercq
 * 
 */

public abstract class CustomLayout extends ViewGroup
{
    private Context _Context;

    public CustomLayout (Context aContext)
    {
        super (aContext);
        _Context = aContext;
    }

    protected void optAdd (View aView)
    {
        if (aView != null)
        {
            addView (aView);
        }
    }

    protected void optRemove (View aView)
    {
        if (aView != null)
        {
            removeView (aView);
        }
    }

    protected void Debug (String aMessage)
    {
        new AlertDialog.Builder (_Context).setTitle ("Debug").setMessage (aMessage).setNeutralButton ("Continue", new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {
            }
        }).show ();
    }
}
