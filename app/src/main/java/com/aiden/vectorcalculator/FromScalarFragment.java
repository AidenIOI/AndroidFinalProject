package com.aiden.vectorcalculator;

import static com.aiden.vectorcalculator.VectorMath.crossProduct;

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
import com.aiden.vectorcalculator.databinding.FragmentFromScalarBinding;

public class FromScalarFragment extends Fragment {

    private FragmentFromScalarBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFromScalarBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get views for inputted values
        EditText normX = view.findViewById(R.id.normX);
        EditText normY = view.findViewById(R.id.normY);
        EditText normZ = view.findViewById(R.id.normZ);
        EditText DValue = view.findViewById(R.id.D);

        // Arrays for each individual vector
        EditText[] normalVectorText = {normX, normY, normZ};

        // Get button view
        Button calcButton = (Button) view.findViewById(R.id.calculateButton);

        // LaTeX !!!!!!!!!
        MTMathView paraX = (MTMathView) view.findViewById(R.id.parametricX);
        MTMathView paraY = (MTMathView) view.findViewById(R.id.parametricY);
        MTMathView paraZ = (MTMathView) view.findViewById(R.id.parametricZ);
        MTMathView vectorEqn = (MTMathView) view.findViewById(R.id.vectorEqn);

        // Set sizes for LaTeX views
        paraX.setFontSize(48);
        paraY.setFontSize(48);
        paraZ.setFontSize(48);
        vectorEqn.setFontSize(38);

        // Equation text views
        TextView parametricText = (TextView) view.findViewById(R.id.parametricText);
        TextView vectorEqnText = (TextView) view.findViewById(R.id.vectorText);

        calcButton.setOnClickListener ( l -> {
            // Close keyboard
            FragmentActivity activity = getActivity();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

            double[] normalArray = new double[3];

            for (int i = 0; i < 3; i++) {
                String text = normalVectorText[i].getText().toString();
                if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                normalArray[i] = Double.parseDouble(text);
            }
            Vector normalVector = new Vector(normalArray);

            String DValueText = DValue.getText().toString();
            if (DValueText.isEmpty() || DValueText.equals("-") || DValueText.equals(".") || DValueText.equals("-.")) {
                Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                return;
            }
            double D = Double.parseDouble(DValueText);

            Vector dir1Vector = new Vector(-normalVector.z, 0, normalVector.x);

            // Use cross product of normal and dir1Vector to find dir2Vector
            Vector dir2Vector = crossProduct(normalVector, dir1Vector);

            // Solve for an arbitrary point on the plane (y = 1, z = 1, solve for x)
            Vector posVector = new Vector (
                    - (D + normalVector.y + normalVector.z), // x = -By - Cz - D = -(By + Cz + D)
                    1,
                    1
            );

            Plane plane = new Plane(posVector, dir1Vector, dir2Vector);

            // Show equations
            paraX.setLatex(plane.paraXLatex());
            paraY.setLatex(plane.paraYLatex());
            paraZ.setLatex(plane.paraZLatex());
            vectorEqn.setLatex(plane.vectorEqnLatex());

            // Toggle visibility
            parametricText.setVisibility(View.VISIBLE);
            paraX.setVisibility(View.VISIBLE);
            paraY.setVisibility(View.VISIBLE);
            paraZ.setVisibility(View.VISIBLE);
            vectorEqn.setVisibility(View.VISIBLE);
            vectorEqnText.setVisibility(View.VISIBLE);
        });

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
}