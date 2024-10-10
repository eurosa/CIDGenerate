package com.rnjn.cidgenerate;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import java.util.ArrayList;

public class TennisAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<TennisModel> tennisModelArrayList;
    private ArrayList<TennisModel> searchList;


    private int i;
    // private FriendFilter friendFilter;

    public TennisAdapter(Context context, ArrayList<TennisModel> tennisModelArrayList ) {

        this.context = context;
        this.tennisModelArrayList = tennisModelArrayList;

        if(tennisModelArrayList!=null) {
            this.searchList = tennisModelArrayList;
        }

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return tennisModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return tennisModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        return convertView;
    }
/*
    @Override
    public Filter getFilter() {
        if (friendFilter == null) {

            friendFilter = new FriendFilter();

        }

        return friendFilter;
    }

*/

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               // searchList.clear();
                tennisModelArrayList = (ArrayList<TennisModel>) results.values; // has the filtered values

                notifyDataSetChanged();


            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                i=0;
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<TennisModel> FilteredArrList = new ArrayList<>();

               // if (tennisModelArrayList == null) {
                    tennisModelArrayList = new ArrayList<>(searchList); // saves the original data in mOriginalValues
               // }

                Log.d("Size","Sara"+searchList.size()+" "+tennisModelArrayList.size());

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = tennisModelArrayList.size();
                    results.values = tennisModelArrayList;
                   // Toast.makeText(context, results.count, Toast.LENGTH_SHORT).show();
                } else {
                    constraint = constraint.toString().toLowerCase();

                    while ( i < tennisModelArrayList.size()) {
                     //   String data = tennisModelArrayList.get(i).getName() + " " +

                        //           tennisModelArrayList.get(i).getTitle();

                      //  Toast.makeText(context, constraint, Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(context, "lob "+tennisModelArrayList.size(), Toast.LENGTH_SHORT).show();

                       if (tennisModelArrayList.get(i).getName().toLowerCase().startsWith(constraint.toString())||tennisModelArrayList.get(i).getTitle().toLowerCase().startsWith(constraint.toString())) {
                        FilteredArrList.add(tennisModelArrayList.get(i));
                         //  Log.d("increment","sexy "+i+" Tennis: "+tennisModelArrayList.get(i).getName());

                       }
                      //  Toast.makeText(context, "ince "+i, Toast.LENGTH_SHORT).show();

                        i++;
                    }
                    Log.d("increment",i+" Tennis: "+tennisModelArrayList.size()+""+searchList.size());

                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    public ArrayList<TennisModel> getSearchList()
    {
        return searchList;
    }


    private class ViewHolder {

        TextView tvname, tvcountry, tvcity,ipGrabberLink;
        ImageView iv;

    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */


    private void setUpFadeAnimation(final TextView textView) {
        // Start from 0.1f if you desire 90% fade animation
        final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(500);//1000
        fadeIn.setStartOffset(500);//3000
        // End to 0.1f if you desire 90% fade animation
        final Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(500);//1000
        fadeOut.setStartOffset(500);//3000

        fadeIn.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start fadeOut when fadeIn ends (continue)
                textView.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start fadeIn when fadeOut ends (repeat)
                textView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        textView.startAnimation(fadeOut);
    }


    }




