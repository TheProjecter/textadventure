package streambolics.textAdventure;

import streambolics.android.StandardListView;
import android.content.Context;

public class InventoryView extends StandardListView implements ItemVisitor
{
    ItemListViewAdapter _Adapter;

    public InventoryView (Context aContext)
    {
        super (aContext);
        _Adapter = new ItemListViewAdapter (aContext);
        setAdapter (_Adapter);
    }

    public void setContainer (Item aContainer)
    {
        _Adapter.clear ();
        if (aContainer.hasLight ())
        {
            setBackgroundDrawable (StockDrawables.TiledFloor (getContext ()));
            aContainer.visitContents (this);
        }
        else
        {
            setBackgroundDrawable (StockDrawables.PitchBlack (getContext ()));
        }
    }

    @Override
    public void visit (Item aItem)
    {
        if (aItem.getName ().equals ("Player"))
        {
            return;
        }
        _Adapter.add (aItem);
    }
}
