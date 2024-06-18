package com.aiden.vectorcalculator;

import static com.aiden.vectorcalculator.VectorMath.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.agog.mathdisplay.MTMathView;
import com.aiden.vectorcalculator.databinding.FragmentLineIntersectionsBinding;

public class LineIntersectionsFragment extends Fragment {

    private FragmentLineIntersectionsBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLineIntersectionsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get views for inputted values
        EditText line1PosX = binding.line1posX;
        EditText line1PosY = binding.line1posY;
        EditText line1PosZ = binding.line1posZ;
        EditText line1DirX = binding.line1dirX;
        EditText line1DirY = binding.line1dirY;
        EditText line1DirZ = binding.line1dirZ;
        EditText line2PosX = binding.line2posX;
        EditText line2PosY = binding.line2posY;
        EditText line2PosZ = binding.line2posZ;
        EditText line2DirX = binding.line2dirX;
        EditText line2DirY = binding.line2dirY;
        EditText line2DirZ = binding.line2dirZ;

        // Arrays for each individual vector
        EditText[] inputs = {line1PosX, line1PosY, line1PosZ, line1DirX, line1DirY, line1DirZ,
                             line2PosX, line2PosY, line2PosZ, line2DirX, line2DirY, line2DirZ};

        double[] inputNums = new double[12];

        // Get output views
        TextView outputText = binding.outputText;
        MTMathView outputEqn = binding.outputEqn;

        // Set LaTeX sizing
        outputEqn.setFontSize(60);

        binding.calculateButton.setOnClickListener(l -> {
            // TODO: Catch inputs of "-", ".", etc. (ALL FRAGMENTS)
            for (int i = 0; i < 12; i++) {
                String text = inputs[i].getText().toString();
                if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) {
                    Toast.makeText(this.getContext(), "A Field was Left Empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                inputNums[i] = Double.parseDouble(text);
            }

            Line line1 = new Line(inputNums[0], inputNums[1], inputNums[2],
                    inputNums[3], inputNums[4], inputNums[5]);
            Line line2 = new Line(inputNums[6], inputNums[7], inputNums[8],
                    inputNums[9], inputNums[10], inputNums[11]);

            Intersections intersectionType = intersection(line1, line2);

            switch (intersectionType) {
                case SKEW:
                    outputText.setText("Lines are skew.\nNo solutions");
                    outputText.setVisibility(View.VISIBLE);
                    outputEqn.setVisibility(View.INVISIBLE);
                    break;
                case COINCIDENT:
                    outputText.setText("Lines are coincident.\nInfinite solutions.");
                    outputText.setVisibility(View.VISIBLE);
                    outputEqn.setVisibility(View.VISIBLE);
                    break;
                case POINT:
                    outputText.setText("Lines intersect at a point.\nOne solution.");
                    outputEqn.setLatex(getPOI(line1, line2).toLatex());
                    outputText.setVisibility(View.VISIBLE);
                    outputEqn.setVisibility(View.VISIBLE);
                    break;
                default:
                    outputText.setText("An error occured.");
                    outputText.setVisibility(View.VISIBLE);
                    outputEqn.setVisibility(View.INVISIBLE);
            }
        });

        binding.back.setOnClickListener(v ->
                NavHostFragment.findNavController(LineIntersectionsFragment.this)
                        .navigate(R.id.action_to_FirstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}