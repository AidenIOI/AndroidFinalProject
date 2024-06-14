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
        EditText line1PosX = (EditText) view.findViewById(R.id.line1posX);
        EditText line1PosY = (EditText) view.findViewById(R.id.line1posY);
        EditText line1PosZ = (EditText) view.findViewById(R.id.line1posZ);
        EditText line1DirX = (EditText) view.findViewById(R.id.line1dirX);
        EditText line1DirY = (EditText) view.findViewById(R.id.line1dirY);
        EditText line1DirZ = (EditText) view.findViewById(R.id.line1dirZ);
        EditText line2PosX = (EditText) view.findViewById(R.id.line2posX);
        EditText line2PosY = (EditText) view.findViewById(R.id.line2posY);
        EditText line2PosZ = (EditText) view.findViewById(R.id.line2posZ);
        EditText line2DirX = (EditText) view.findViewById(R.id.line2dirX);
        EditText line2DirY = (EditText) view.findViewById(R.id.line2dirY);
        EditText line2DirZ = (EditText) view.findViewById(R.id.line2dirZ);

        // Arrays for each individual vector
        EditText[] line1PosVectorText = {line1PosX, line1PosY, line1PosZ};
        EditText[] line1DirVectorText = {line1DirX, line1DirY, line1DirZ};
        EditText[] line2PosVectorText = {line2PosX, line2PosY, line2PosZ};
        EditText[] line2DirVectorText = {line2DirX, line2DirY, line2DirZ};

        // Get button view
        Button calcButton = (Button) view.findViewById(R.id.calculateButton);

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