// ColorDialogFragment.java
// Allows user to set the drawing color on the DoodleView
package com.deitel.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;

// class for the Select Color dialog
public class ColorDialogFragment extends DialogFragment
{
    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private View colorView;
    private int color;
    private int backgroundColor;
    private Spinner colorSpinner;
    private Spinner imageSpinner;
    private Drawable customImage;



//    Resources res;
//    Drawable blue = res.getDrawable(R.drawable.blue);
//    Drawable black = res.getDrawable(R.drawable.black);
//    Drawable green = res.getDrawable(R.drawable.green);

    // create an AlertDialog and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        View colorDialogView =
                getActivity().getLayoutInflater().inflate(
                        R.layout.fragment_color, null);
        builder.setView(colorDialogView); // add GUI to dialog

        // set the AlertDialog's message
        builder.setTitle(R.string.title_color_dialog);
        builder.setCancelable(true);

        // get the color SeekBars and set their onChange listeners
        alphaSeekBar = (SeekBar) colorDialogView.findViewById(R.id.alphaSeekBar);
        redSeekBar = (SeekBar) colorDialogView.findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) colorDialogView.findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) colorDialogView.findViewById(R.id.blueSeekBar);
        colorView = colorDialogView.findViewById(R.id.colorView);
        colorSpinner = (Spinner) colorDialogView.findViewById(R.id.backgroundSpinner);
        imageSpinner = (Spinner) colorDialogView.findViewById(R.id.backgroundImageSpinner);

        // register SeekBar event listeners
        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String colorName = parent.getItemAtPosition(position).toString();
                if (colorName.equals("Blue")) {
                    backgroundColor = Color.parseColor("blue");
                } else if (colorName.equals("Brown")) {
                    backgroundColor = Color.parseColor("brown");
                } else if (colorName.equals("Black")) {
                    backgroundColor = Color.parseColor("black");
                } else if (colorName.equals("Red")) {
                    backgroundColor = Color.parseColor("red");
                } else if (colorName.equals("Yellow")) {
                    backgroundColor = Color.parseColor("yellow");
                } else if (colorName.equals("Purple")) {
                    backgroundColor = Color.parseColor("purple");
                } else if (colorName.equals("Green")) {
                    backgroundColor = Color.parseColor("green");
                } else if (colorName.equals("Silver")) {
                    backgroundColor = Color.parseColor("silver");
                } else {
                    backgroundColor = Color.parseColor("white");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String imageName = parent.getItemAtPosition(position).toString();
//                if (imageName.equals("Background 1")) {
//                    customImage = black;
//                } else if (imageName.equals("Background 2")) {
//                    customImage = blue;
//                } else if (imageName.equals("Background 3")) {
//                    customImage = green;
//                }// else{
//                 //   doodleView.setBackgroundColor(Color.parseColor("white"));
//               //  }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        // use current drawing color to set SeekBar values
        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        color=doodleView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

        // add Set Color Button
        builder.setPositiveButton(R.string.button_set_color,
                new DialogInterface.OnClickListener()

        {
            public void onClick (DialogInterface dialog,int id)
            {
                doodleView.setDrawingColor(color);
                doodleView.setBackgroundColor(backgroundColor);
                //doodleView.setBackgroundImage(customImage);
            }
        }

        ); // end call to setPositiveButton

        return builder.create(); // return dialog
    } // end method onCreateDialog


            // gets a reference to the DoodleFragment

    private DoodleFragment getDoodleFragment()
    {
        return (DoodleFragment) getFragmentManager().findFragmentById(
                R.id.doodleFragment);
    }

    // tell DoodleFragment that dialog is now displayed
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        DoodleFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(true);
    }

    // tell DoodleFragment that dialog is no longer displayed
    @Override
    public void onDetach()
    {
        super.onDetach();
        DoodleFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(false);
    }

// OnSeekBarChangeListener for the SeekBars in the color dialog
private OnSeekBarChangeListener colorChangedListener =
        new OnSeekBarChangeListener()
        {
            // display the updated color
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser)
            {
                if (fromUser) // user, not program, changed SeekBar progress
                    color = Color.argb(alphaSeekBar.getProgress(),
                            redSeekBar.getProgress(), greenSeekBar.getProgress(),
                            blueSeekBar.getProgress());
                colorView.setBackgroundColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) // required
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) // required
            {
            }
        }; // end colorChanged
} // end class ColorDialogFragment

/**************************************************************************
 * (C) Copyright 1992-2012 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/
