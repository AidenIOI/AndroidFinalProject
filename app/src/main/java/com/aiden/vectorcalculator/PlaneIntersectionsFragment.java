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
import com.aiden.vectorcalculator.databinding.FragmentPlaneIntersectionsBinding;

public class PlaneIntersectionsFragment extends Fragment{

    private FragmentPlaneIntersectionsBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentPlaneIntersectionsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Equation layouts
        LinearLayout equation1Scalar = binding.equation1Scalar;
        LinearLayout equation1Vector = binding.equation1Vector;
        LinearLayout equation2Scalar = binding.equation2Scalar;
        LinearLayout equation2Vector = binding.equation2Vector;
        LinearLayout equation3Scalar = binding.equation3Scalar;
        LinearLayout equation3Vector = binding.equation3Vector;

        // Extra view for equation 3
        LinearLayout eqn3 = binding.eqn3;

        // Populate Spinner
        Spinner equation1Spinner = binding.spinner1;
        Spinner equation2Spinner = binding.spinner2;
        Spinner equation3Spinner = binding.spinner3;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.requireContext(),
                R.array.plane_equations,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        equation1Spinner.setAdapter(adapter);
        equation2Spinner.setAdapter(adapter);
        equation3Spinner.setAdapter(adapter);

        equation1Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (((String) parent.getItemAtPosition(pos)).equals("Vector")) {
                    equation1Vector.setVisibility(View.VISIBLE);
                    equation1Scalar.setVisibility(View.INVISIBLE);
                }
                else if (((String) parent.getItemAtPosition(pos)).equals("Scalar")) {
                    equation1Scalar.setVisibility(View.VISIBLE);
                    equation1Vector.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        equation2Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (((String) parent.getItemAtPosition(pos)).equals("Vector")) {
                    equation2Vector.setVisibility(View.VISIBLE);
                    equation2Scalar.setVisibility(View.INVISIBLE);
                }
                else if (((String) parent.getItemAtPosition(pos)).equals("Scalar")) {
                    equation2Scalar.setVisibility(View.VISIBLE);
                    equation2Vector.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        equation3Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (((String) parent.getItemAtPosition(pos)).equals("Vector")) {
                    equation3Vector.setVisibility(View.VISIBLE);
                    equation3Scalar.setVisibility(View.INVISIBLE);
                }
                else if (((String) parent.getItemAtPosition(pos)).equals("Scalar")) {
                    equation3Scalar.setVisibility(View.VISIBLE);
                    equation3Vector.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Get views for inputted values
        // Plane 1 Vector
        EditText plane1PosX = binding.plane1posX;
        EditText plane1PosY = binding.plane1posY;
        EditText plane1PosZ = binding.plane1posZ;
        EditText plane1Dir1X = binding.plane1dir1X;
        EditText plane1Dir1Y = binding.plane1dir1Y;
        EditText plane1Dir1Z = binding.plane1dir1Z;
        EditText plane1Dir2X = binding.plane1dir2X;
        EditText plane1Dir2Y = binding.plane1dir2Y;
        EditText plane1Dir2Z = binding.plane1dir2Z;
        EditText[] plane1TextVector = {plane1PosX, plane1PosY, plane1PosZ,
                                       plane1Dir1X, plane1Dir1Y, plane1Dir1Z,
                                       plane1Dir2X, plane1Dir2Y, plane1Dir2Z};
        // Plane 1 Scalar
        EditText plane1NormX = binding.plane1NormX;
        EditText plane1NormY = binding.plane1NormY;
        EditText plane1NormZ = binding.plane1NormZ;
        EditText plane1D = binding.plane1D;
        EditText[] plane1TextScalar = {plane1NormX, plane1NormY, plane1NormZ, plane1D};

        // Plane 2 Vector
        EditText plane2PosX = binding.plane2posX;
        EditText plane2PosY = binding.plane2posY;
        EditText plane2PosZ = binding.plane2posZ;
        EditText plane2Dir1X = binding.plane2dir1X;
        EditText plane2Dir1Y = binding.plane2dir1Y;
        EditText plane2Dir1Z = binding.plane2dir1Z;
        EditText plane2Dir2X = binding.plane2dir2X;
        EditText plane2Dir2Y = binding.plane2dir2Y;
        EditText plane2Dir2Z = binding.plane2dir2Z;
        EditText[] plane2TextVector = {plane2PosX, plane2PosY, plane2PosZ,
                plane2Dir1X, plane2Dir1Y, plane2Dir1Z,
                plane2Dir2X, plane2Dir2Y, plane2Dir2Z};
        // Plane 2 Scalar
        EditText plane2NormX = binding.plane2NormX;
        EditText plane2NormY = binding.plane2NormY;
        EditText plane2NormZ = binding.plane2NormZ;
        EditText plane2D = binding.plane2D;
        EditText[] plane2TextScalar = {plane2NormX, plane2NormY, plane2NormZ, plane2D};

        // Plane 3 Vector
        EditText plane3PosX = binding.plane3posX;
        EditText plane3PosY = binding.plane3posY;
        EditText plane3PosZ = binding.plane3posZ;
        EditText plane3Dir1X = binding.plane3dir1X;
        EditText plane3Dir1Y = binding.plane3dir1Y;
        EditText plane3Dir1Z = binding.plane3dir1Z;
        EditText plane3Dir2X = binding.plane3dir2X;
        EditText plane3Dir2Y = binding.plane3dir2Y;
        EditText plane3Dir2Z = binding.plane3dir2Z;
        EditText[] plane3TextVector = {plane3PosX, plane3PosY, plane3PosZ,
                plane3Dir1X, plane3Dir1Y, plane3Dir1Z,
                plane3Dir2X, plane3Dir2Y, plane3Dir2Z};
        // Plane 3 Scalar
        EditText plane3NormX = binding.plane3NormX;
        EditText plane3NormY = binding.plane3NormY;
        EditText plane3NormZ = binding.plane3NormZ;
        EditText plane3D = binding.plane3D;
        EditText[] plane3TextScalar = {plane3NormX, plane3NormY, plane3NormZ, plane3D};

        // Get output views
        TextView outputText = binding.outputText;
        MTMathView outputEqn = binding.outputEqn;

        // Set LaTeX sizing
        outputEqn.setFontSize(60);

        binding.addEquationButton.setOnClickListener(l -> {
            eqn3.setVisibility(View.VISIBLE);
            binding.addEquationButton.setVisibility(View.GONE);
            if (((String) equation3Spinner.getSelectedItem()).equals("Vector")) {
                equation3Vector.setVisibility(View.VISIBLE);
            }
            else if (((String) equation3Spinner.getSelectedItem()).equals("Scalar")) {
                equation3Scalar.setVisibility(View.VISIBLE);
            }
        });

        binding.removeEquationButton.setOnClickListener(l -> {
            eqn3.setVisibility(View.GONE);
            binding.addEquationButton.setVisibility(View.VISIBLE);
            equation3Scalar.setVisibility(View.INVISIBLE);
            equation3Vector.setVisibility(View.INVISIBLE);
        });

        binding.calculateButton.setOnClickListener(l -> {
            Plane plane1;
            Plane plane2;

            // Plane 1 using vector equation
            if (equation1Vector.getVisibility() == View.VISIBLE) {
                Log.println(Log.INFO, "PlaneIntersections", "1-Vector");
                double[] posVec = new double[3];
                double[] dir1Vec = new double[3];
                double[] dir2Vec = new double[3];
                for (int i = 0; i < 3; i++) {
                    String text = plane1TextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    posVec[i] = Double.parseDouble(text);
                }
                for (int i = 3; i < 6; i++) {
                    String text = plane1TextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    dir1Vec[i - 3] = Double.parseDouble(text);
                }
                for (int i = 6; i < 9; i++) {
                    String text = plane1TextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    dir2Vec[i - 6] = Double.parseDouble(text);
                }
                plane1 = new Plane(posVec, dir1Vec, dir2Vec);
            }
            // Plane 1 using scalar equation
            else {
                double[] normal = new double[3];
                for (int i = 0; i < 3; i++) {
                    String text = plane1TextScalar[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    normal[i] = Double.parseDouble(text);
                }
                String dText = plane1TextScalar[3].getText().toString();
                if (dText.isEmpty() || dText.equals("-") || dText.equals(".") || dText.equals("-.")) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                plane1 = new Plane(new Vector(normal), Double.parseDouble(dText));
            }
            // Plane 2 using vector equation
            if (equation2Vector.getVisibility() == View.VISIBLE) {
                double[] posVec = new double[3];
                double[] dir1Vec = new double[3];
                double[] dir2Vec = new double[3];
                for (int i = 0; i < 3; i++) {
                    String text = plane2TextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    posVec[i] = Double.parseDouble(text);
                }
                for (int i = 3; i < 6; i++) {
                    String text = plane2TextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    dir1Vec[i - 3] = Double.parseDouble(text);
                }
                for (int i = 6; i < 9; i++) {
                    String text = plane2TextVector[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    dir2Vec[i - 6] = Double.parseDouble(text);
                }
                plane2 = new Plane(posVec, dir1Vec, dir2Vec);
            }
            // Plane 2 using scalar equation
            else {
                double[] normal = new double[3];
                for (int i = 0; i < 3; i++) {
                    String text = plane2TextScalar[i].getText().toString();
                    if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    normal[i] = Double.parseDouble(text);
                }
                String dText = plane2TextScalar[3].getText().toString();
                if (dText.isEmpty() || dText.equals("-") || dText.equals(".") || dText.equals("-.")) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                plane2 = new Plane(new Vector(normal), Double.parseDouble(dText));
            }
            // Check if Plane 3 is enabled
            if (eqn3.getVisibility() == View.VISIBLE) {
                Plane plane3;
                // Plane 2 using vector equation
                if (equation3Vector.getVisibility() == View.VISIBLE) {
                    double[] posVec = new double[3];
                    double[] dir1Vec = new double[3];
                    double[] dir2Vec = new double[3];
                    for (int i = 0; i < 3; i++) {
                        String text = plane3TextVector[i].getText().toString();
                        if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                            Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        posVec[i] = Double.parseDouble(text);
                    }
                    for (int i = 3; i < 6; i++) {
                        String text = plane3TextVector[i].getText().toString();
                        if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                            Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        dir1Vec[i - 3] = Double.parseDouble(text);
                    }
                    for (int i = 6; i < 9; i++) {
                        String text = plane3TextVector[i].getText().toString();
                        if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                            Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        dir2Vec[i - 6] = Double.parseDouble(text);
                    }
                    plane3 = new Plane(posVec, dir1Vec, dir2Vec);
                }
                // Plane 2 using scalar equation
                else {
                    double[] normal = new double[3];
                    for (int i = 0; i < 3; i++) {
                        String text = plane3TextScalar[i].getText().toString();
                        if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                            Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        normal[i] = Double.parseDouble(text);
                    }
                    String dText = plane3TextScalar[3].getText().toString();
                    if (dText.isEmpty() || dText.equals("-") || dText.equals(".") || dText.equals("-.")) {
                        Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    plane3 = new Plane(new Vector(normal), Double.parseDouble(dText));
                }

                // 3 Plane Intersection
                Intersections intersectionType = intersection(plane1, plane2, plane3);

                switch (intersectionType) {
                    case PAIRS:
                        outputText.setText("Planes are coplanar and intersect in pairs.\nNo solutions.");
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility(View.INVISIBLE);
                        break;
                    case DISTINCT:
                        outputText.setText("Planes are parallel and distinct.\nNo solutions.");
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility(View.INVISIBLE);
                    case POINT:
                        outputText.setText("Planes intersect at a point.\nOne solution.");
                        outputEqn.setLatex(getPOI(plane1, plane2, plane3).toLatex());
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility(View.VISIBLE);
                        break;
                    case LINE:
                        outputText.setText("Planes intersect at a line.\nInfinite solutions.");
                        outputEqn.setLatex(getLOI(plane1, plane2).vectorEqnLatex());
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility(View.VISIBLE);
                        break;
                    case COINCIDENT:
                        outputText.setText("Planes are coincident.\nInfinite solutions");
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility((View.INVISIBLE));
                        break;
                    default:
                        outputText.setText("An error occured.");
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility(View.INVISIBLE);
                }
            }
            else {
                // 2 Plane Intersection
                Intersections intersectionType = intersection(plane1, plane2);

                switch (intersectionType) {
                    case DISTINCT:
                        outputText.setText("Planes are parallel and distinct.\nNo solutions.");
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility(View.INVISIBLE);
                        break;
                    case LINE:
                        outputText.setText("Planes intersect at a line.\nInfinite solutions.");
                        outputEqn.setLatex(getLOI(plane1, plane2).vectorEqnLatex());
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility(View.VISIBLE);
                        break;
                    case COINCIDENT:
                        outputText.setText("Planes are coincident.\nInfinite solutions");
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility((View.INVISIBLE));
                        break;
                    default:
                        outputText.setText("An error occured.");
                        outputText.setVisibility(View.VISIBLE);
                        outputEqn.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.back.setOnClickListener(v ->
                NavHostFragment.findNavController(PlaneIntersectionsFragment.this)
                        .navigate(R.id.action_to_FirstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}