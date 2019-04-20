package com.zhi.qing.asyncchannel.activityToFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zhi.qing.asyncchannel.R;
import com.zhi.qing.channellib.ChannelConnector;
import com.zhi.qing.channellib.IReceiveMsgCallback;

import static com.zhi.qing.channellib.ChannelConstants.CHANNEL_TAG;


public class BlankFragment extends Fragment {
private final static String TAG=CHANNEL_TAG;
    ChannelConnector channelConnector;
Button button;
    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channelConnector=new ChannelConnector(getContext(), new IReceiveMsgCallback() {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "BlankFragment handleMessage: "+msg.toString());
                Toast.makeText(getContext(),"BlankFragment handleMessage: "+msg.toString(),Toast.LENGTH_LONG).show();
            }
        });
        if (getArguments() != null) {
            channelConnector.connect(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blank, container, false);
        button=view.findViewById(R.id.fragmentbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=Message.obtain();
                message.what=2;
                channelConnector.sendMessage(message);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
