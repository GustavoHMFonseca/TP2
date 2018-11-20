package com.gustavofonseca.tp2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentOutData extends DialogFragment {
    public FragmentInData.OnInputListener mOnInputListener;

    //widgets
    private TextView mnome;
    private TextView mOutPut;
    private TextView mActionOk;



    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outdata, container, false);
        mnome = view.findViewById(R.id.nomeDocumentoout);
        mActionOk = view.findViewById(R.id.action_ok_out);
        mOutPut = view.findViewById(R.id.output);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            mnome.setText(bundle.getString("Nome", ""));
            mOutPut.setText(bundle.getString("Dados",""));
        }
        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (FragmentInData.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("FragmentInData", "onAttach: ClassCastException: " + e.getMessage() );
        }
    }
}

