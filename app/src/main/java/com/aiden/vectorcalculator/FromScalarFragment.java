package com.aiden.vectorcalculator;

import static com.aiden.vectorcalculator.VectorMath.crossProduct;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.aiden.vectorcalculator.databinding.FragmentFromScalarBinding;

public class FromScalarFragment extends Fragment {

    private FragmentFromScalarBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFromScalarBinding.inflate(inflater, container, false);

        RelativeLayout layout = binding.getRoot();

        // Get views for inputted values
        EditText normX = layout.findViewById(R.id.normX);
        EditText normY = layout.findViewById(R.id.normY);
        EditText normZ = layout.findViewById(R.id.normZ);
        EditText DValue = layout.findViewById(R.id.D);

        // Arrays for each individual vector
        EditText[] normalVectorText = {normX, normY, normZ};

        // Get button view
        Button calcButton = (Button) layout.findViewById(R.id.calculateButton);

        // LaTeX !!!!!!!!!
        MTMathView paraX = (MTMathView) layout.findViewById(R.id.parametricX);
        MTMathView paraY = (MTMathView) layout.findViewById(R.id.parametricY);
        MTMathView paraZ = (MTMathView) layout.findViewById(R.id.parametricZ);
        MTMathView vectorEqn = (MTMathView) layout.findViewById(R.id.vectorEqn);

        // Set sizes for LaTeX views
        paraX.setFontSize(48);
        paraY.setFontSize(48);
        paraZ.setFontSize(48);
        vectorEqn.setFontSize(38);

        // Equation text views
        TextView parametricText = (TextView) layout.findViewById(R.id.parametricText);
        TextView vectorEqnText = (TextView) layout.findViewById(R.id.vectorText);

        calcButton.setOnClickListener ( l -> {
            // Close keyboard
            FragmentActivity activity = getActivity();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

            double[] normalVector = new double[3];

            for (int i = 0; i < 3; i++) {
                String text = normalVectorText[i].getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                normalVector[i] = Double.parseDouble(text);
            }

            String DValueText = DValue.getText().toString();
            if (DValueText.isEmpty()) {
                Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                return;
            }
            double D = Double.parseDouble(DValueText);

            double[] dir1Vector = new double[3];
            // Use shortcut to get first vector perpendicular to the normal
            dir1Vector[0] = -normalVector[2];
            // Y = 0
            dir1Vector[2] = normalVector[0];

            // Use cross product of normal and dir1Vector to find dir2Vector
            double[] dir2Vector = crossProduct(normalVector, dir1Vector);

            // Solve for an arbitrary point on the plane (y = 1, z = 1, solve for x)
            double[] posVector = {
                    - (D + normalVector[1] + normalVector[2]), // x = -By - Cz - D = -(By + Cz + D)
                    1,
                    1
            };

            // Show equations
            paraX.setLatex(formatParametric(posVector[0], dir1Vector[0], dir2Vector[0]));
            paraY.setLatex(formatParametric(posVector[1], dir1Vector[1], dir2Vector[1]));
            paraZ.setLatex(formatParametric(posVector[2], dir1Vector[2], dir2Vector[2]));
            vectorEqn.setLatex(formatVector(posVector, dir1Vector, dir2Vector));

            // Toggle visibility
            parametricText.setVisibility(View.VISIBLE);
            paraX.setVisibility(View.VISIBLE);
            paraY.setVisibility(View.VISIBLE);
            paraZ.setVisibility(View.VISIBLE);
            vectorEqn.setVisibility(View.VISIBLE);
            vectorEqnText.setVisibility(View.VISIBLE);
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.back.setOnClickListener(v ->
                NavHostFragment.findNavController(FromScalarFragment.this)
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

    private static String formatVector(double[] posVect, double[] vect1, double[] vect2) {
        return "[x,y,z]=[" + posVect[0] + "," + posVect[1] + "," + posVect[2] + "]+s["
                + vect1[0] + "," + vect1[1] + "," + vect1[2] + "]+t["
                + vect2[0] + "," + vect2[1] + "," + vect2[2] + "]";
    }
}