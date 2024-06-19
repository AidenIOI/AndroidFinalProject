package com.aiden.vectorcalculator;

import static com.aiden.vectorcalculator.VectorMath.Intersections;
import static com.aiden.vectorcalculator.VectorMath.getLOI;
import static com.aiden.vectorcalculator.VectorMath.getPOI;
import static com.aiden.vectorcalculator.VectorMath.intersection;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.agog.mathdisplay.MTMathView;
import com.aiden.vectorcalculator.databinding.FragmentLinePlaneIntersectionsBinding;

public class LinePlaneIntersectionsFragment extends Fragment{

    private FragmentLinePlaneIntersectionsBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLinePlaneIntersectionsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Equation layouts
        LinearLayout planeScalar = binding.planeScalar;
        LinearLayout planeVector = binding.planeVector;

        // Populate Spinner
        Spinner spinner = binding.spinner;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.requireContext(),
                R.array.plane_equations,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (((String) parent.getItemAtPosition(pos)).equals("Vector")) {
                    planeVector.setVisibility(View.VISIBLE);
                    planeScalar.setVisibility(View.INVISIBLE);
                }
                else if (((String) parent.getItemAtPosition(pos)).equals("Scalar")) {
                    planeScalar.setVisibility(View.VISIBLE);
                    planeVector.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Get views for inputted values
        // Line
        EditText linePosX = binding.linePosX;
        EditText linePosY = binding.linePosY;
        EditText linePosZ = binding.linePosZ;
        EditText lineDirX = binding.lineDirX;
        EditText lineDirY = binding.lineDirY;
        EditText lineDirZ = binding.lineDirZ;
        EditText[] lineText = {linePosX, linePosY, linePosZ, lineDirX, lineDirY, lineDirZ};


        // Plane Vector
        EditText planePosX = binding.planePosX;
        EditText planePosY = binding.planePosY;
        EditText planePosZ = binding.planePosZ;
        EditText planeDir1X = binding.planeDir1X;
        EditText planeDir1Y = binding.planeDir1Y;
        EditText planeDir1Z = binding.planeDir1Z;
        EditText planeDir2X = binding.planeDir2X;
        EditText planeDir2Y = binding.planeDir2Y;
        EditText planeDir2Z = binding.planeDir2Z;
        EditText[] planeTextVector = {planePosX, planePosY, planePosZ,
                planeDir1X, planeDir1Y, planeDir1Z,
                planeDir2X, planeDir2Y, planeDir2Z};
        // Plane Scalar
        EditText planeNormX = binding.planeNormX;
        EditText planeNormY = binding.planeNormY;
        EditText planeNormZ = binding.planeNormZ;
        EditText planeD = binding.planeD;
        EditText[] planeTextScalar = {planeNormX, planeNormY, planeNormZ, planeD};

        // Get output views
        TextView outputText = binding.outputText;
        MTMathView outputEqn = binding.outputEqn;

        // Set LaTeX sizing
        outputEqn.setFontSize(60);

        binding.calculateButton.setOnClickListener(l -> {
            Line line;
            Plane plane;

            // Plane 1 using vector equation
            if (planeVector.getVisibility() == View.VISIBLE) {
                Log.println(Log.INFO, "PlaneIntersections", "1-Vector");
                double[] posVec = new double[3];
                double[] dir1Vec = new double[3];
                double[] dir2Vec = new double[3];
                for (int i = 0; i < 3; i++) {
                    String text = planeTextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    posVec[i] = Double.parseDouble(text);
                }
                for (int i = 3; i < 6; i++) {
                    String text = planeTextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    dir1Vec[i - 3] = Double.parseDouble(text);
                }
                for (int i = 6; i < 9; i++) {
                    String text = planeTextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    dir2Vec[i - 6] = Double.parseDouble(text);
                }
                plane = new Plane(posVec, dir1Vec, dir2Vec);
            }
            // Plane 1 using scalar equation
            else {
                double[] normal = new double[3];
                for (int i = 0; i < 3; i++) {
                    String text = planeTextScalar[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    normal[i] = Double.parseDouble(text);
                }
                String dText = planeTextScalar[3].getText().toString();
                if (dText.isEmpty() || dText.equals("-") || dText.equals(".") || dText.equals("-.")) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                plane = new Plane(new Vector(normal), Double.parseDouble(dText));
            }
            // Get line
            double[] lineNums = new double[6];
            for (int i = 0; i < 6; i++) {
                String text = lineText[i].getText().toString();
                if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                lineNums[i] = Double.parseDouble(text);
            }

            line = new Line(lineNums[0], lineNums[1], lineNums[2],
                    lineNums[3], lineNums[4], lineNums[5]);

            Intersections intersectionType = intersection(line, plane);

            switch (intersectionType) {
                case POINT:
                    outputText.setText("Line intersects the plane at a point.\nOne solution");
                    outputEqn.setLatex(getPOI(line, plane).toLatex());
                    outputText.setVisibility(View.VISIBLE);
                    outputEqn.setVisibility(View.VISIBLE);
                    break;
                case DISTINCT:
                    outputText.setText("Line is parallel to the plane, but does not lie on it.\nNo solutions.");
                    outputText.setVisibility(View.VISIBLE);
                    outputEqn.setVisibility(View.INVISIBLE);
                    break;
                case LINE:
                    outputText.setText("Plane lies on the plane.\nInfinite solutions.");
                    outputText.setVisibility(View.VISIBLE);
                    outputEqn.setVisibility(View.INVISIBLE);
                    break;
                default:
                    outputText.setText("An error occured.");
                    outputText.setVisibility(View.VISIBLE);
                    outputEqn.setVisibility(View.INVISIBLE);
            }
        });

        binding.back.setOnClickListener(v ->
                NavHostFragment.findNavController(LinePlaneIntersectionsFragment.this)
                        .navigate(R.id.action_to_FirstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}