package streambolics.textAdventure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ItemListViewAdapter extends ArrayAdapter<Item>
{
    private Context _Context;

    public ItemListViewAdapter (Context aContext)
    {
        super (aContext, 0);
        _Context = aContext;
    }

    @Override
    public View getView (int aPosition, View aConvertView, ViewGroup aParent)
    {

        ItemView v;
        Item itm = getItem (aPosition);
        if (aConvertView != null)
        {
            v = (ItemView) aConvertView;
            v.setItem (itm);
        }
        else
        {
            v = new ItemView (_Context, itm);
            // aParent.addView (v);
        }

        return v;
    }

    protected void Debug (String aMessage)
    {
        new AlertDialog.Builder (_Context).setTitle ("ItemListViewAdapter").setMessage (aMessage)
                .setNeutralButton ("Continue", new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                    }
                }).show ();
    }

}
