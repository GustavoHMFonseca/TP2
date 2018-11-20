package com.gustavofonseca.tp2;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentInData extends DialogFragment {

    public interface OnInputListener{
        void sendInput(String input);
    }

    public OnInputListener mOnInputListener;

    //widgets
    private TextView mnome;
    private EditText mInput;
    private TextView mActionOk;



    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indata, container, false);
        mnome = view.findViewById(R.id.nomeDocumento);
        mActionOk = view.findViewById(R.id.action_ok);
        mInput = view.findViewById(R.id.input);
        Bundle bundle = this.getArguments();
        if(bundle != null)
        mnome.setText(bundle.getString("Nome",""));

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mInput.getText().toString();
                mOnInputListener.sendInput(input);
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("FragmentInData", "onAttach: ClassCastException: " + e.getMessage() );
        }
    }
}
