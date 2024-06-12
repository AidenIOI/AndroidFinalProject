package com.aiden.vectorcalculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

        RelativeLayout layout = binding.getRoot();

        // Get views for inputted values
        EditText posX = (EditText) layout.findViewById(R.id.posX);
        EditText posY = (EditText) layout.findViewById(R.id.posY);
        EditText posZ = (EditText) layout.findViewById(R.id.posZ);
        EditText dir1X = (EditText) layout.findViewById(R.id.dir1X);
        EditText dir1Y = (EditText) layout.findViewById(R.id.dir1Y);
        EditText dir1Z = (EditText) layout.findViewById(R.id.dir1Z);
        EditText dir2X = (EditText) layout.findViewById(R.id.dir2X);
        EditText dir2Y = (EditText) layout.findViewById(R.id.dir2Y);
        EditText dir2Z = (EditText) layout.findViewById(R.id.dir2Z);

        // Arrays for each individual vector
        EditText[] posVectorText = {posX, posY, posZ};
        EditText[] dir1VectorText = {dir1X, dir1Y, dir1Z};
        EditText[] dir2VectorText = {dir2X, dir2Y, dir2Z};

        // Get button view
        Button calcButton = (Button) layout.findViewById(R.id.calculateButton);

        // LaTeX !!!!!!!!!
        MTMathView paraX = (MTMathView) layout.findViewById(R.id.parametricX);
        MTMathView paraY = (MTMathView) layout.findViewById(R.id.parametricY);
        MTMathView paraZ = (MTMathView) layout.findViewById(R.id.parametricZ);

        // Set sizes for LaTeX views
        paraX.setFontSize(48);
        paraY.setFontSize(48);
        paraZ.setFontSize(48);

        calcButton.setOnClickListener ( l -> {
            double[] posVector = new double[3];
            double[] dir1Vector = new double[3];
            double[] dir2Vector = new double[3];

            boolean plane = true;

            for (int i = 0; i < 3; i++) {
                String text = posVectorText[i].getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                posVector[i] = Double.parseDouble(text);
            }
            for (int i = 0; i < 3; i++) {
                String text = dir1VectorText[i].getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                dir1Vector[i] = Double.parseDouble(text);
            }
            // Since a line has no secondary direction vector, if this is empty the user entered
            // a line, otherwise it is a plane.
            for (int i = 0; i < 3; i++) {
                String text = dir2VectorText[i].getText().toString();
                if (text.isEmpty()) {
                    plane = false;
                    break;
                }
                dir2Vector[i] = Double.parseDouble(text);
            }

            // Parametric Equation (both line and plane)
            // TODO: Work on sizing of latex views to better fit display
            // TODO: Add line/plane dependent equations
            paraX.setLatex("x=" + formatParametric(posVector[0], dir1Vector[0], dir2Vector[0]));
            paraY.setLatex("y=" + formatParametric(posVector[1], dir1Vector[1], dir2Vector[1]));
            paraZ.setLatex("z=" + formatParametric(posVector[2], dir1Vector[2], dir2Vector[2]));
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    private static String formatParametric(double pos, double vect1) {
        if (pos == 0) {
            if (vect1 == 0) {
                return "0";
            }
            return String.valueOf(vect1) + "s";
        }
        if (vect1 == 0) {
            return String.valueOf(pos);
        }
        if (vect1 < 0) {
            return String.valueOf(pos) + String.valueOf(vect1) + "s";
        }
        return String.valueOf(pos) + "+" + String.valueOf(vect1) + "s";
    }

    private static String formatParametric(double pos, double vect1, double vect2) {
        String partA = formatParametric(pos, vect1);
        if (vect2 == 0) {
            return partA;
        }
        if (partA.equals("0")) {
            return String.valueOf(vect2) + "t";
        }
        if (vect2 < 0) {
            return partA + String.valueOf(vect2) + "t";
        }
        return partA + "+" + String.valueOf(vect2) + "t";
    }
}