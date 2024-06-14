package com.aiden.vectorcalculator;

import static com.aiden.vectorcalculator.VectorMath.*;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.agog.mathdisplay.MTMathView;
import com.aiden.vectorcalculator.databinding.FragmentFromVectorBinding;

public class FromVectorFragment extends Fragment {

    private FragmentFromVectorBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFromVectorBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get views for inputted values
        EditText posX = (EditText) view.findViewById(R.id.posX);
        EditText posY = (EditText) view.findViewById(R.id.posY);
        EditText posZ = (EditText) view.findViewById(R.id.posZ);
        EditText dir1X = (EditText) view.findViewById(R.id.dir1X);
        EditText dir1Y = (EditText) view.findViewById(R.id.dir1Y);
        EditText dir1Z = (EditText) view.findViewById(R.id.dir1Z);
        EditText dir2X = (EditText) view.findViewById(R.id.dir2X);
        EditText dir2Y = (EditText) view.findViewById(R.id.dir2Y);
        EditText dir2Z = (EditText) view.findViewById(R.id.dir2Z);

        // Arrays for each individual vector
        EditText[] posVectorText = {posX, posY, posZ};
        EditText[] dir1VectorText = {dir1X, dir1Y, dir1Z};
        EditText[] dir2VectorText = {dir2X, dir2Y, dir2Z};

        // Get button view
        Button calcButton = (Button) view.findViewById(R.id.calculateButton);

        // LaTeX !!!!!!!!!
        MTMathView paraX = (MTMathView) view.findViewById(R.id.parametricX);
        MTMathView paraY = (MTMathView) view.findViewById(R.id.parametricY);
        MTMathView paraZ = (MTMathView) view.findViewById(R.id.parametricZ);
        MTMathView symm = (MTMathView) view.findViewById(R.id.symmetricEqn);
        MTMathView scalar = (MTMathView) view.findViewById(R.id.scalarEqn);

        // Set sizes for LaTeX views
        paraX.setFontSize(48);
        paraY.setFontSize(48);
        paraZ.setFontSize(48);
        symm.setFontSize(48);
        scalar.setFontSize(48);

        // Text for equations
        TextView parametricText = (TextView) view.findViewById(R.id.parametricText);
        TextView symmText = (TextView) view.findViewById(R.id.symmetricText);
        TextView scalarText = (TextView) view.findViewById(R.id.scalarText);

        calcButton.setOnClickListener ( l -> {
            // Close keyboard
            FragmentActivity activity = getActivity();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

            double[] posArray = new double[3];
            double[] dir1Array = new double[3];
            double[] dir2Array = new double[3];

            boolean isPlane = true;

            for (int i = 0; i < 3; i++) {
                String text = posVectorText[i].getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                posArray[i] = Double.parseDouble(text);
            }
            for (int i = 0; i < 3; i++) {
                String text = dir1VectorText[i].getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                dir1Array[i] = Double.parseDouble(text);
            }
            // Since a line has no secondary direction vector, if this is empty the user entered
            // a line, otherwise it is a plane.
            boolean valueFilled = false;
            boolean valueUnfilled = false;
            for (int i = 0; i < 3; i++) {
                String text = dir2VectorText[i].getText().toString();
                if (text.isEmpty()) {
                    valueUnfilled = true;
                }
                else {
                    valueFilled = true;
                    dir2Array[i] = Double.parseDouble(text);
                }
            }

            if (valueUnfilled) {
                // If both are true, the user forgot to enter one of the values of the vector
                if (valueFilled) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                // If unfilled, the user entered a line
                isPlane = false;
            }

            // TODO: Work on sizing of latex views to better fit display
            // TODO: Add line/plane dependent equations
            if (isPlane) {
                Plane plane = new Plane(posArray, dir1Array, dir2Array);

                paraX.setLatex(plane.paraXLatex());
                paraY.setLatex(plane.paraYLatex());
                paraZ.setLatex(plane.paraZLatex());

                scalar.setLatex(plane.scalarEqnLatex());
                // Hide symmetric equation and show scalar equation
                symmText.setVisibility(View.INVISIBLE);
                symm.setVisibility(View.INVISIBLE);
                scalarText.setVisibility(View.VISIBLE);
                scalar.setVisibility(View.VISIBLE);
            }
            else {
                Line line = new Line(posArray, dir1Array);

                paraX.setLatex(line.paraXLatex());
                paraY.setLatex(line.paraYLatex());
                paraZ.setLatex(line.paraZLatex());

                symm.setLatex(line.symmEqnLatex());
                // Hide scalar equation and show symmetric equation
                scalarText.setVisibility(View.INVISIBLE);
                scalar.setVisibility(View.INVISIBLE);
                symmText.setVisibility(View.VISIBLE);
                symm.setVisibility(View.VISIBLE);
            }

            // Toggle visibility
            parametricText.setVisibility(View.VISIBLE);
            paraX.setVisibility(View.VISIBLE);
            paraY.setVisibility(View.VISIBLE);
            paraZ.setVisibility(View.VISIBLE);
        });

        binding.back.setOnClickListener(v ->
                NavHostFragment.findNavController(FromVectorFragment.this)
                        .navigate(R.id.action_to_FirstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}