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
import android.widget.RelativeLayout;
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
        MTMathView symm = (MTMathView) layout.findViewById(R.id.symmetricEqn);
        MTMathView scalar = (MTMathView) layout.findViewById(R.id.scalarEqn);

        // Set sizes for LaTeX views
        paraX.setFontSize(48);
        paraY.setFontSize(48);
        paraZ.setFontSize(48);
        symm.setFontSize(48);
        scalar.setFontSize(48);

        // Text for equations
        TextView parametricText = (TextView) layout.findViewById(R.id.parametricText);
        TextView symmText = (TextView) layout.findViewById(R.id.symmetricText);
        TextView scalarText = (TextView) layout.findViewById(R.id.scalarText);

        calcButton.setOnClickListener ( l -> {
            // Close keyboard
            FragmentActivity activity = getActivity();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

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
            boolean valueFilled = false;
            boolean valueUnfilled = false;
            for (int i = 0; i < 3; i++) {
                String text = dir2VectorText[i].getText().toString();
                if (text.isEmpty()) {
                    valueUnfilled = true;
                }
                else {
                    valueFilled = true;
                    dir2Vector[i] = Double.parseDouble(text);
                }
            }

            if (valueUnfilled) {
                // If both are true, the user forgot to enter one of the values of the vector
                if (valueFilled) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                // If unfilled, the user entered a line
                plane = false;
            }

            // Parametric Equation (both line and plane)
            // TODO: Work on sizing of latex views to better fit display
            // TODO: Add line/plane dependent equations
            paraX.setLatex("x=" + formatParametric(posVector[0], dir1Vector[0], dir2Vector[0]));
            paraY.setLatex("y=" + formatParametric(posVector[1], dir1Vector[1], dir2Vector[1]));
            paraZ.setLatex("z=" + formatParametric(posVector[2], dir1Vector[2], dir2Vector[2]));

            // Toggle visibility
            parametricText.setVisibility(View.VISIBLE);
            paraX.setVisibility(View.VISIBLE);
            paraY.setVisibility(View.VISIBLE);
            paraZ.setVisibility(View.VISIBLE);

            // Symmetric Equation (line only)
            if (!plane) {
                symm.setLatex(formatSymmetric(posVector, dir1Vector));
                // Hide scalar equation and show symmetric equation
                scalarText.setVisibility(View.INVISIBLE);
                symmText.setVisibility(View.VISIBLE);
            }
            else {
                scalar.setLatex(formatScalar(posVector, dir1Vector, dir2Vector));
                // Hide symmetric equation and show scalar equation
                symmText.setVisibility(View.INVISIBLE);
                scalarText.setVisibility(View.VISIBLE);
            }
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

    // TODO: Work on handling signs and division by 1
    // TODO: Either change to integers or check if decimal can be hidden for whole numbers
    private static String formatSymmetric(double[] posVect, double[] dirVect) {
        String latex = "";
        // X part
        if (dirVect[0] != 0) {
            if (posVect[0] == 0) {
                latex += "\\frac{x}{" + String.valueOf(dirVect[0] + "}");
            }
            else {
                String top = "x";
                if (posVect[0] > 0) {
                    top += "+";
                }
                top += String.valueOf(posVect[0]);

                if (dirVect[0] == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + String.valueOf(dirVect[0]) + "}";
                }
            }
        }
        // Y part
        if (dirVect[1] != 0) {
            if (!latex.isEmpty()) latex += "=";
            if (posVect[1] == 0) {
                latex += "\\frac{y}{" + String.valueOf(dirVect[1] + "}");
            }
            else {
                String top = "y";
                if (posVect[1] > 0) {
                    top += "+";
                }
                top += String.valueOf(posVect[1]);

                if (dirVect[1] == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + String.valueOf(dirVect[1]) + "}";
                }
            }
        }
        // Z part
        if (dirVect[2] != 0) {
            if (!latex.isEmpty()) latex += "=";
            if (posVect[2] == 0) {
                latex += "\\frac{z}{" + String.valueOf(dirVect[2] + "}");
            }
            else {
                String top = "z";
                if (posVect[2] > 0) {
                    top += "+";
                }
                top += String.valueOf(posVect[2]);

                if (dirVect[2] == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + String.valueOf(dirVect[2]) + "}";
                }
            }
        }

        return latex;
    }

    private static String formatScalar(double[] posVect, double[] dirVect1, double[] dirVect2) {
        double[] normal = crossProduct(dirVect1, dirVect2);
        double d = -(posVect[0] * normal[0] + posVect[1] * normal[1] + posVect[2] * normal[2]);

        String latex = "";
        if (normal[0] != 0) {
            latex += String.valueOf(normal[0]) + "x";
        }
        if (normal[1] < 0 || (latex.isEmpty() && normal[1] != 0)) {
            latex += String.valueOf(normal[1]) + "y";
        }
        else if (normal[1] > 0) {
            latex += "+" + String.valueOf(normal[1]) + "y";
        }
        if (normal[2] < 0 || (latex.isEmpty() && normal[2] != 0)) {
            latex += String.valueOf(normal[2]) + "z";
        }
        else if (normal[1] > 0) {
            latex += "+" + String.valueOf(normal[2]) + "z";
        }
        if (d < 0 || (latex.isEmpty() && d != 0)) {
            latex += String.valueOf(d);
        }
        else if (d > 0) {
            latex += "+" + String.valueOf(d);
        }

        return latex.isEmpty() ? "" : "0=" + latex ;
    }
}