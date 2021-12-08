package com.example.assignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfomationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfomationFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button addbutton;
    private CardView baseCard;
    private LinearLayout root;
    private Button upButton;

    public InfomationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfomationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfomationFragment newInstance(String param1, String param2) {
        InfomationFragment fragment = new InfomationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_infomation, container, false);
        upButton = (Button) view.findViewById(R.id.btn_benefits);
        upButton.setOnClickListener(this);
        upButton = (Button) view.findViewById(R.id.btn_recyclabletype);
        upButton.setOnClickListener(this);
        upButton = (Button) view.findViewById(R.id.btn_whatisrecycling);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatisrecycling();
            }
        });
        upButton = (Button) view.findViewById(R.id.btn_facts);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facts();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        Fragment switchFragment = null;

        switch (v.getId()) {
            case R.id.btn_benefits:
                switchFragment= new BenefitsFragment();
                break;
            case R.id.btn_recyclabletype:
                switchFragment= new RecyclableTypesFragment();
                break;
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.info,switchFragment).commit();
    }

    public void whatisrecycling(){
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW, Uri.parse("https://www.conserve-energy-future.com/why-is-recycling-important.php")
        );
        requireContext().startActivity(browserIntent);
    }

    public void facts(){
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW, Uri.parse("https://www.roadrunnerwm.com/blog/50-interesting-recycling-facts ")
        );
        requireContext().startActivity(browserIntent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //baseCard=getView().findViewById(R.id.baseCard);
        //root=getView().findViewById(R.id.root);
        //addbutton = getView().findViewById(R.id.butAdd);
        //addbutton.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //CardView newCardView = new CardView(getContext()); // creating CardView
                //newCardView.setLayoutParams(baseCard.getLayoutParams());
                //root.addView(newCardView, root.getChildCount() - 1);
            //}
    };



}

